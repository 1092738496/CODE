#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <thread>
#include "inference.h"
using namespace std;
using namespace cv;


int frameCounter = 0;
int fps = 0;
double startTime = static_cast<double>(cv::getTickCount());
void task1(Inference& inf , Mat& img) {
	std::vector<Detection> output = inf.runInference(img);
	if (output.size() != 0) {
		for (int i = 0; i < output.size(); i++)
		{
			Detection detection = output [i];
			cv::Rect box = detection.box;
			cv::Scalar color = detection.color;

			cout << box.x << endl;
			cout << box.y << endl;
			cout << box.height << endl;
			cout << box.width << endl;


			// Detection box
			cv::rectangle(img , box , color , 2);

			// Detection box text
			std::string classString = detection.className + ' ' + std::to_string(detection.confidence).substr(0 , 4);
			cv::Size textSize = cv::getTextSize(classString , cv::FONT_HERSHEY_DUPLEX , 1 , 2 , 0);
			cv::Rect textBox(box.x , box.y - 40 , textSize.width + 10 , textSize.height + 20);

			cv::rectangle(img , textBox , color , cv::FILLED);
			cv::putText(img , classString , cv::Point(box.x + 5 , box.y - 10) , cv::FONT_HERSHEY_DUPLEX , 1 , cv::Scalar(0 , 0 , 0) , 2 , 0);


			frameCounter++;
	
			// 计算帧率
			double endTime = static_cast<double>(cv::getTickCount());
			double elapsedTime = (endTime - startTime) / cv::getTickFrequency();
			fps = static_cast<double>(frameCounter) / elapsedTime;
			// 在窗口左上角显示帧率
			cv::putText(img , "FPS: " + std::to_string(fps) , cv::Point(10 , 30),
			cv::FONT_HERSHEY_SIMPLEX , 1 , cv::Scalar(255 , 255 , 255) , 2);
			// 画框
			cv::Rect rect(box.x , box.y , box.height , box.width);
			// 在图像中绘制每个矩形框
			cv::rectangle(img , rect , cv::Scalar(0 , 0 , 255) , 2);   
		}
	}
	else {
		cout << "没识别到" << endl;
	}
}
int main()
{
	VideoCapture cap;   //声明相机捕获对象
	cap.open("E:/vidou/萝莉户外被后入.mp4");

	Inference inf("D:/datasets/runs/detect/train3/weights/best.onnx" , cv::Size(640 , 640) , "classes.txt" , false);

	Mat img;
	while (true) {
		cap >> img; //以流形式捕获图像
		namedWindow("example" , WINDOW_FREERATIO); //创建一个窗口用于显示图像，1代表窗口适应图像的分辨率进行拉伸。
		if (img.empty() != false) {//图像不为空则显示图像 COLOR_RGBA2BGR
			return 0;
		}
		task1(inf,img);
		/* std::thread threadWithBind(std::bind(task1 , inf , img));
		threadWithBind.join(); */
		cv::imshow("example" , img);
		int  key = waitKey(30); //等待30ms
		if (key == int('q')) //按下q退出
		{
			break;
		}
	}
	cap.release(); //释放相机捕获对象
	destroyAllWindows(); //关闭所有窗口 
	return 0;
}

