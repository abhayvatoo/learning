#include "mainview.h"
#include "ui_mainview.h"

#include <iostream>
#include <opencv2/opencv.hpp>

cv::Mat createGrayCanvas()
{
    cv::Size canvas_size = cv::Size (500, 500);
    //simply creates a middle tone gray canvas based on the canvas_size
    cv::Mat output = cv::Mat::zeros (canvas_size, CV_8UC1);
    for (int j = 0; j < canvas_size.width; ++j)
        for (int i = 0; i < canvas_size.height; ++i)
            output.at<char>(i, j) = char (128);
    return output;
}

MainView::MainView(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainView)
{
    ui->setupUi(this);
    cv::Mat canvas= createGrayCanvas();
    cv::imshow ("canvas", canvas);
}

MainView::~MainView()
{
    delete ui;
}
