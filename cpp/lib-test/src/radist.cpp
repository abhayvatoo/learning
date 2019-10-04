#include <opencv2/opencv.hpp>
#include <iostream>
#include <limits.h>

using namespace cv;
using namespace std;

#include "radist.h" 

static float calcRadialDistortion(
	const vector<Point2f> &distorted, // distorted (image) points
	const vector<Point2f> &undistorted, // corresponding undistorted (pattern) points
	const Rect &roi,
	Point2f &c,		// center of distortion
	Mat &H, 
	float &K1,
	float &K2) ;

static void calcRadialDistortion(
	const vector<Point2f> &distorted, // distorted (image) points
	const vector<Point2f> &undistorted, // corresponding undistorted (pattern) points
	const Point2f &c,		// center of distortion
	Mat &H, 
	float &K1,
	float &K2) ;

static float calcScale( 
	const vector<Point2f> &p0,
	float range) ;

static void scalePoints( 
	const vector<Point2f> &p0,
	float scale,
	vector<Point2f> &pf) ;

static Point2f centerPoints( 
	const vector<Point2f> &p0,
	Point2f center,
	vector<Point2f> &pf) ;

static void translatePoints( 
	const vector<Point2f> &p0,
	Point2f shift,
	vector<Point2f> &pf) ;

static Point2f undistortPoint( 	
	const Point2f &d, 
	const Point2f &c,
	const float &K1,
	const float &K2,
	const Mat &H) ;

static void calcReprojectionError(
	const vector< Point2f > &d,
	const vector< Point2f > &u,
	const Point2f &c,
	const float &K1,
	const float &K2,
	const Mat &H,
	float &min,
	float &max,
	float &av) ;

static int undistortImage( 
	const Mat &dImage,
	const Point2f &centerPt,
	float K1,
	float K2,
	const Mat &H,
	float scale,	
	const vector<Point2f> &pts,
	Mat &uImage) ;

static void calcDistortionMaps( 
	const Mat &image,
	const vector<Point2f> &imagePts,
	const Point2f &centerPt,
	float K1,
	float K2,
	const Mat &H,
	float scale,	
	Mat &iMap1,
	Mat &iMap2) ;
	
static float calcRadialDistortion(
	const vector<Point2f> &distorted, // distorted (image) points
	const vector<Point2f> &undistorted, // corresponding undistorted (pattern) points
	const Rect &roi,	// center search range
	Point2f &c,		// center of distortion
	Mat &H, 
	float &K1,
	float &K2)
{
	Point2f center, bestCenter ;
	Mat bestH ;
	float bestK1, bestK2, 
		bestAvSqErr = std::numeric_limits<float>::max() ;
	float min, max, av ;

	vector< Point2f > patternPts1, patternPts2 ;
	centerPoints( 
		//	patternPts, Point2f(0.0,0.0), patternPts1 ) ;
		undistorted, Point2f(0.0,0.0), patternPts1 ) ;
	float scale = calcScale(
		patternPts1, 
		2.0) ;
	scalePoints(
		patternPts1, 
		scale,
		patternPts2 ) ;

	vector< Point2f > imagePts1, imagePts2, imagePts3 ;
	for (int x=roi.x; x<roi.x+roi.width-1; x++)
	{
		for (int y=roi.y; y<roi.y+roi.height-1; y++)
		{

			center.x = x ;
			center.y = y ;

			//	cout << center << endl ;

			//	centerPoints( imagePts, center, imagePts1 ) ;
			centerPoints( distorted, center, imagePts1 ) ;
			float scaleD = calcScale(
				imagePts1, 
				2.0) ;
			scalePoints(
				imagePts1, 
				scaleD,
				imagePts2 ) ;
			centerPoints( imagePts2, Point2f(0.0,0.0), imagePts3 ) ;

			calcRadialDistortion(
				imagePts3,
				patternPts2,
				Point2f(0.0,0.0),
				H,
				K1,
				K2 ) ;

			calcReprojectionError(
				imagePts3,
				patternPts2,
				Point2f(0.0,0.0),
				K1,
				K2,
				H,
				min,
				max,
				av ) ;
	
			if (av < bestAvSqErr)
			{
				bestAvSqErr = av ;
				bestCenter = center ;
				bestK1 = K1 ;
				bestK2 = K2 ;
				bestH = H ;
/*
				cout << setprecision(12) << "\t\terr = " << av 
					<< ", " << center << endl ;
*/
			}
		}
	}

	c = bestCenter ;	
	K1 = bestK1 ;
	K2 = bestK2 ;
	H = bestH ;
	
	return( bestAvSqErr ) ;
}

static void calcRadialDistortion(
	const vector<Point2f> &distorted, // distorted (image) points
	const vector<Point2f> &undistorted, // corresponding undistorted (pattern) points
	const Point2f &c,		// center of distortion
	Mat &H, 
	float &K1,
	float &K2)
{
	//	cout << "center = " << c << endl ;

	int nPts = distorted.size() ;
	int rows=nPts*2, cols=21 ;
	Mat A(rows, cols, CV_32FC1) ;

	for (int i=0; i<nPts; i++)
	{
		Point2f d = distorted[i] ;
		Point2f u = undistorted[i] ;

		float r2 = (d.x-c.x)*(d.x-c.x) 
				+ (d.y-c.y)*(d.y-c.y) ;
		float r4 = r2*r2 ;

		int j=0 ;
		A.at<float>(2*i,j++) = u.x ;
		A.at<float>(2*i,j++) = u.y ;
		A.at<float>(2*i,j++) = 1 ;
		A.at<float>(2*i,j++) = r2 * u.x ;
		A.at<float>(2*i,j++) = r2 * u.y ;
		A.at<float>(2*i,j++) = r2 ;
		A.at<float>(2*i,j++) = r4 * u.x ;
		A.at<float>(2*i,j++) = r4 * u.y ;
		A.at<float>(2*i,j++) = r4 ;

		for (int k=0; k<9; k++)
		{
			A.at<float>(2*i,j++) = 0.0 ;
		}

		A.at<float>(2*i,j++) = -u.x*d.x ;
		A.at<float>(2*i,j++) = -u.y*d.x ;
		A.at<float>(2*i,j++) = -d.x ;

		// next row ...

		j = 0 ;
		for (int k=0; k<9; k++)
		{
			A.at<float>(2*i+1,j++) = 0.0 ;
		}

		A.at<float>(2*i+1,j++) = u.x ;
		A.at<float>(2*i+1,j++) = u.y ;
		A.at<float>(2*i+1,j++) = 1 ;
		A.at<float>(2*i+1,j++) = r2 * u.x ;
		A.at<float>(2*i+1,j++) = r2 * u.y ;
		A.at<float>(2*i+1,j++) = r2 ;
		A.at<float>(2*i+1,j++) = r4 * u.x ;
		A.at<float>(2*i+1,j++) = r4 * u.y ;
		A.at<float>(2*i+1,j++) = r4 ;

		A.at<float>(2*i+1,j++) = -u.x*d.y ;
		A.at<float>(2*i+1,j++) = -u.y*d.y ;
		A.at<float>(2*i+1,j++) = -d.y ;
	}

	Mat X1 ;
	SVD svd1 ;
	svd1.solveZ(A, X1) ;
	float h9 = X1.at<float>(20) ;
	X1 = X1 / h9 ;
/*
	cout << endl << endl << endl ;
	cout << "X1 = " << endl ;
	cout << X1 << endl ;
*/

	Mat X = X1.clone() ;
	float h1 = X.at<float>(0) ;
	float h2 = X.at<float>(1) ;
	float h3 = X.at<float>(2) ;
	float h4 = X.at<float>(9) ;
	float h5 = X.at<float>(10) ;
	float h6 = X.at<float>(11) ;
	float h7 = X.at<float>(18) ;
	float h8 = X.at<float>(19) ;
	h9 = X.at<float>(20) ;

	K1 = X.at<float>(3)/h1 ;
	K2 = X.at<float>(6)/h1 ;

	H = Mat::eye(3, 3, CV_32FC1) ;
	H.at<float>(0,0) = h1 ;
	H.at<float>(0,1) = h2 ;
	H.at<float>(0,2) = h3 ;
	H.at<float>(1,0) = h4 ;
	H.at<float>(1,1) = h5 ;
	H.at<float>(1,2) = h6 ;
	H.at<float>(2,0) = h7 ;
	H.at<float>(2,1) = h8 ;
	H.at<float>(2,2) = h9 ;

	H = H.inv() ;
}

static float calcScale( 
	const vector<Point2f> &p0,
	float range)
{
	float xMin = std::numeric_limits<float>::max() ;
	float yMin = std::numeric_limits<float>::max() ;
	float xMax = std::numeric_limits<float>::min() ;
	float yMax = std::numeric_limits<float>::min() ;

	Point2f p ;
	for (int i=0; i<p0.size(); i++)
	{
		p = p0[i] ;
		if (p.x < xMin) xMin = p.x ;
		if (p.x > xMax) xMax = p.x ;
		if (p.y < yMin) yMin = p.y ;
		if (p.y > yMax) yMax = p.y ;
	}

	float dx = xMax - xMin ;
	float dy = yMax - yMin ;
	
	float scale = dx ;
	if (dy > dx) scale = dy ;

	scale = range/scale ;

	return( scale ) ;
}

static void scalePoints( 
	const vector<Point2f> &p0,
	float scale,
	vector<Point2f> &pf)
{
	pf.clear() ;
	for (int i=0; i<p0.size(); i++)
	{
		pf.push_back(p0[i] * scale) ;
		//	cout << p0[i] << ", " << pf[i]  << endl ;
	}
}

static Point2f centerPoints( 
	const vector<Point2f> &p0,
	Point2f center,
	vector<Point2f> &pf)
{
	Point2f sum(0.0,0.0) ;
	for (int i=0; i<p0.size(); i++)
	{
		sum += p0[i] ;
	}

	sum.x = sum.x/(float) p0.size() ;
	sum.y = sum.y/(float) p0.size() ;
	pf.clear() ;
	for (int i=0; i<p0.size(); i++)
	{
		pf.push_back(p0[i] - sum + center) ;
	}

	return( center - sum ) ;
}

static void translatePoints( 
	const vector<Point2f> &p0,
	Point2f t,
	vector<Point2f> &pf)
{
	pf.clear() ;
	for (int i=0; i<p0.size(); i++)
	{
		pf.push_back(p0[i] + t) ;
	}
}

static Point2f undistortPoint( const Point2f &d, 
		const Point2f &c,
		const float &K1,
		const float &K2,
		const Mat &H)
{
	Point2f u ;

	float r2 = (d.x-c.x)*(d.x-c.x)
			+ (d.y-c.y)*(d.y-c.y) ;

	u.x = (c.x + (d.x-c.x)/(1 + K1*r2 + K2*r2*r2) ) ;
	u.y = (c.y + (d.y-c.y)/(1 + K1*r2 + K2*r2*r2) ) ;

	Point2f u1, u2 ;
	u1 = u ;

	u2.x = (H.at<float>(0,0)*u1.x + H.at<float>(0,1)*u1.y + H.at<float>(0,2))
	/ (H.at<float>(2,0)*u1.x + H.at<float>(2,1)*u1.y + H.at<float>(2,2)) ;
	u2.y = (H.at<float>(1,0)*u1.x + H.at<float>(1,1)*u1.y + H.at<float>(1,2))
	/ (H.at<float>(2,0)*u1.x + H.at<float>(2,1)*u1.y + H.at<float>(2,2)) ;

	u = u2 ;

	return(u) ;
}

static void calcReprojectionError(
	const vector< Point2f > &d,
	const vector< Point2f > &u,
	const Point2f &c,
	const float &K1,
	const float &K2,
	const Mat &H,
	float &min,
	float &max,
	float &av) 
{
	//	cout << "scale = " << scale << endl ;

	min = std::numeric_limits<float>::max() ;
	max = std::numeric_limits<float>::min() ;
	av = 0.0 ;

	for (int i=0; i<d.size(); i++)
	{
		Point2f u2 = undistortPoint( d[i], c, K1, K2, H ) ;

		float sqErr = (u2.x-u[i].x)*(u2.x-u[i].x) 
				+ (u2.y-u[i].y)*(u2.y-u[i].y) ;
		if (min > sqErr) min = sqErr ;
		if (max < sqErr) max = sqErr ;
		av += sqErr ;
	}

	av /= d.size() ;
}

static int undistortImage( 
	const Mat &dImage,
	const vector<Point2f> &imagePts,
	const Point2f &centerPt,
	float K1,
	float K2,
	const Mat &H,
	float scale,	
	Mat &uImage)
{
	vector<Point2f> imagePts1, imagePts2, imagePts3, 
		imagePts4, imagePts5, imagePts6 ;
	Point2f shift = centerPoints( imagePts, centerPt, imagePts1 ) ;
	float scaleD = calcScale(
		imagePts1, 
		2.0 ) ;
/*
	scalePoints(
		imagePts1, 
		scaleD,
		imagePts2 ) ;
	centerPoints( imagePts2, Point2f(0.0,0.0), imagePts3 ) ;
		
	for (int i=0; i<imagePts3.size(); i++)
	{
		Point2f p = undistortPoint(
			imagePts3[i],
			Point2f(0.0,0.0),
			K1,
			K2,
			H ) ;
		imagePts4.push_back( p ) ;
	}

	scalePoints(
		imagePts4, 
		scale,	
		imagePts5 ) ;
	centerPoints( imagePts5, centerPt, imagePts6 ) ;
*/
	vector<Point2f> dPts1 ;
	for (int x=0; x<dImage.cols; x++)
	{
		for (int y=0; y<dImage.rows; y++)
		{
			dPts1.push_back( Point2f(x,y) ) ;
		}
	}
			
	vector<Point2f> dPts2, dPts3, dPts4 ;
	//	centerPoints( dPts1, centerPt, dPts2 ) ;
	translatePoints( dPts1, shift, dPts2 ) ;
	scalePoints(
		dPts2, 
		scaleD,
		dPts3 ) ;
	centerPoints( dPts3, Point2f(0.0,0.0), dPts4 ) ;
			
	vector<Point2f> uPts1 ;
	for (int i=0; i<dPts4.size(); i++)
	{
		Point2f p = undistortPoint(
			dPts4[i],
			Point2f(0.0,0.0),
			K1,
			K2,
			H ) ;
		uPts1.push_back( p ) ;
	}

	vector<Point2f> uPts2, uPts3 ;
	scalePoints(
		uPts1, 
		scale,	
		uPts2 ) ;
	centerPoints( uPts2, centerPt, uPts3 ) ;

	int nPts = 0 ;
	uImage = Mat::zeros(dImage.size(), CV_8UC3) ;
	//	uImage = Mat::zeros( dImage.size(), dImage.type() ) ;

	Mat i_map1_32F = Mat::zeros( uImage.size(), CV_32F ) ;
	Mat i_map2_32F = Mat::zeros( uImage.size(), CV_32F ) ;

	for (int i=0; i<dPts4.size(); i++)
	{
		Point2f uPt = uPts3[i] ;
		if ((0 <= uPt.x)&&(uPt.x < uImage.cols)
			&& (0 <= uPt.y)&&(uPt.y < uImage.rows))
		{
			Point2f dPt = dPts1[i] ;
/*
			uImage.at<Vec3b>(uPt.y,uPt.x)
				= dImage.at<Vec3b>(dPt.y,dPt.x) ;
*/	
			i_map1_32F.at<float>( (int) uPt.y, (int) uPt.x)
				= (float) dPt.x ;
			i_map2_32F.at<float>( (int) uPt.y, (int) uPt.x)
				= (float) dPt.y ;
			nPts++ ;
		}
	}

	Mat i_map1, i_map2 ;

	convertMaps(
                i_map1_32F,
                i_map2_32F,
                i_map1,
                i_map2,
                CV_16SC2,
                true);

	//	int interpolation = INTER_NEAREST ;
	//	int interpolation = INTER_LINEAR + CV_WARP_FILL_OUTLIERS ;
	int interpolation = INTER_LINEAR ;
	//	int interpolation = INTER_CUBIC;
	//	int interpolation = INTER_LANCZOS4 ;
	//	int borderMode = BORDER_CONSTANT ;
	int borderMode = BORDER_WRAP ;
	//	int borderMode = BORDER_TRANSPARENT ;
	//	remap( dImage, uImage, i_map1, i_map2, interpolation, borderMode ) ;
	remap( dImage, uImage, i_map1_32F, i_map2_32F, interpolation, borderMode ) ;
/*
	for (int i=0; i<imagePts6.size(); i++)
	{
		Point2f p = imagePts6[i] ;
		
		if ((p.x >= 0) && (p.x < uImage.cols)
			&& (p.y >= 0) && (p.y < uImage.rows))
		{
			circle( 	
				uImage,
				imagePts6[i],
				4,
				Scalar(0,0,255),
				-1) ;
		}
	}
*/

	return( nPts ) ;
}

static void calcDistortionMaps( 
	const Mat &dImage,
	const vector<Point2f> &imagePts,
	const Point2f &centerPt,
	float K1,
	float K2,
	const Mat &H,
	float scale,	
	Mat &iMap1,
	Mat &iMap2)
{
	vector<Point2f> imagePts1, imagePts2, imagePts3, 
		imagePts4, imagePts5, imagePts6 ;
	Point2f shift = centerPoints( imagePts, centerPt, imagePts1 ) ;
	float scaleD = calcScale(
		imagePts1, 
		2.0 ) ;

	vector<Point2f> dPts1 ;
	for (int x=0; x<dImage.cols; x++)
	{
		for (int y=0; y<dImage.rows; y++)
		{
			dPts1.push_back( Point2f(x,y) ) ;
		}
	}
			
	vector<Point2f> dPts2, dPts3, dPts4 ;
	//	centerPoints( dPts1, centerPt, dPts2 ) ;
	translatePoints( dPts1, shift, dPts2 ) ;
	scalePoints(
		dPts2, 
		scaleD,
		dPts3 ) ;
	centerPoints( dPts3, Point2f(0.0,0.0), dPts4 ) ;
			
	vector<Point2f> uPts1 ;
	for (int i=0; i<dPts4.size(); i++)
	{
		Point2f p = undistortPoint(
			dPts4[i],
			Point2f(0.0,0.0),
			K1,
			K2,
			H ) ;
		uPts1.push_back( p ) ;
	}

	vector<Point2f> uPts2, uPts3 ;
	scalePoints(
		uPts1, 
		scale,	
		uPts2 ) ;
	centerPoints( uPts2, centerPt, uPts3 ) ;

	//	uImage = Mat::zeros(dImage.size(), CV_8UC3) ;
	//	uImage = Mat::zeros( dImage.size(), dImage.type() ) ;

	iMap1 = Mat::zeros( dImage.size(), CV_32F ) ;
	iMap2 = Mat::zeros( dImage.size(), CV_32F ) ;

	for (int i=0; i<dPts4.size(); i++)
	{
		Point2f uPt = uPts3[i] ;
		if ((0 <= uPt.x)&&(uPt.x < dImage.cols)
			&& (0 <= uPt.y)&&(uPt.y < dImage.rows))
		{
			Point2f dPt = dPts1[i] ;
/*
			iMap1.at<float>( (int) uPt.y, (int) uPt.x)
				= (float) dPt.x ;
			iMap2.at<float>( (int) uPt.y, (int) uPt.x)
				= (float) dPt.y ;
*/
			iMap1.at<float>( (float) uPt.y, (float) uPt.x)
				= (float) dPt.x ;
			iMap2.at<float>( (float) uPt.y, (float) uPt.x)
				= (float) dPt.y ;
		}
	}
}


float calcRadialDistortionMaps(
	const Mat &image,		// image of chessboard	
	const Size &boardSize,		// # of inner chessboard corners
	float scale,			// scale for mapped image
	Point2f &centerPt,		// center of distortion
	Mat &H, 
	float &K1,
	float &K2,
	Mat &iMapx,
	Mat &iMapy ) 
{
	vector<Point2f> imagePts ;
	bool patternFound = findChessboardCorners( 
			image, 
			boardSize, 
			imagePts,
			CALIB_CB_ADAPTIVE_THRESH
				+ CALIB_CB_NORMALIZE_IMAGE ) ;

	if ( !patternFound )
	{
		return(-1.0) ;
	}

	
	vector<Point2f> patternPts ;
	for (int j=0; j<boardSize.height; j++)
	{
		for (int i=0; i<boardSize.width; i++)
		{
			Point2f p((float) i, (float) j) ;
			patternPts.push_back(p) ;
		}
	}
			
	int R = 20 ;
	int dx = image.cols/R, dy = image.rows/R ;
	Rect roi ;
	roi.x = image.cols/2 - dx ;
	roi.y = image.rows/2 - dy ;
	roi.width = 2*dx ;
	roi.height = 2*dy ;
		
	float err = calcRadialDistortion(
		imagePts, // distorted (image) points
		patternPts, // corresponding undistorted (pattern) points
		roi,
		centerPt,
		H, 
		K1,
		K2) ;

	calcDistortionMaps( 
		image,
		imagePts,
		centerPt,
		K1,
		K2,
		H,
		scale,
		iMapx,
		iMapy ) ;

	return(err) ;
}
