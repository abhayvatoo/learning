#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <iostream>
#include <QTimer>
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/core/core.hpp"

using namespace cv;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    Timer = new QTimer(this);
    connect(Timer, SIGNAL(timeout()), this, SLOT(DisplayImage()));
    Timer->start();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::DisplayImage(){
    Mat img = createGrayCanvas();
    //cv::resize(img, img, Size(100, 100), 0, 0, INTER_LINEAR);
    cv::cvtColor(img,img,cv::COLOR_BGR2RGB); //Qt reads in RGB whereas CV in BGR
    QImage imdisplay(static_cast<uchar*>(img.data), img.cols, img.rows, int (img.step), QImage::Format_RGB888);
    ui->display_image->setPixmap(QPixmap::fromImage(imdisplay));
}

cv::Mat MainWindow::createGrayCanvas()
{
    cv::Size canvas_size = cv::Size (500, 500);
    //simply creates a middle tone gray canvas based on the canvas_size
    cv::Mat output = cv::Mat::zeros (canvas_size, CV_8UC1);
    for (int j = 0; j < canvas_size.width; ++j)
        for (int i = 0; i < canvas_size.height; ++i)
            output.at<char>(i, j) = char (128);
    return output;
}
