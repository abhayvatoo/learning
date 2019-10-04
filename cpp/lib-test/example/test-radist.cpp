#include <opencv2/opencv.hpp>
#include <iostream>
#include <limits.h>

using namespace cv;
using namespace std;

#include "../include/radist.h"

int main( int argc, char** argv )
{
    	if( argc < 2)
    	{
     		cout <<" Usage: undist <image>" << endl;
     		return -1;
    	}

	int iArg = 1 ;
	string imageFile = argv[iArg] ;
	Mat image = imread( imageFile, CV_LOAD_IMAGE_COLOR ) ;

	imshow( "image", image ) ;
	waitKey(100) ;

	float K1, K2 ;
	Size boardSize(11,6) ;
	Point2f centerPt ;
	Mat H, iMapx, iMapy ;
	float err = calcRadialDistortionMaps(
		image, 	
		boardSize, 
		150.0,
		centerPt,
		H, 
		K1,
		K2,
		iMapx,
		iMapy) ;

	cout << "av reprojection error = " << err << endl ;

	Mat uImage = Mat::zeros(image.size(), CV_8UC3) ;
	remap( image, uImage, iMapx, iMapy, INTER_LINEAR, BORDER_WRAP ) ;

	imshow( "undistorted image", uImage ) ;

	waitKey() ;
	
    	return 0;
}

