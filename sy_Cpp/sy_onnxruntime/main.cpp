#include <iostream>
#include <iomanip>
#include "inference.h"
#include <filesystem>
#include <fstream>
using namespace cv;
using namespace std;

/* int frameCounter = 0;
int fps = 0; */
double startTime = static_cast<double>(cv::getTickCount());
void task1(DCSP_CORE*& p , Mat& img) {
	std::vector<DCSP_RESULT> res;
	p->RunSession(img , res);
	for (auto& re : res) {
		float confidence = floor(100 * re.confidence) / 100;
		//std::cout << std::fixed << std::setprecision(2);
		if (re.confidence > 0.35) {
			std::string label = p->classes [re.classId] + " " +
				std::to_string(confidence).substr(0 , std::to_string(confidence).size() - 4);


			cout << label << ":" << "," << re.confidence << ":" << re.box.x << "," << re.box.y << endl;

			// Detection box
			cv::RNG rng(cv::getTickCount());
			cv::Scalar color(rng.uniform(0 , 256) , rng.uniform(0 , 256) , rng.uniform(0 , 256));
			cv::rectangle(img , re.box , color , 2);

			// Detection box text
			std::string classString = label;
			cv::Size textSize = cv::getTextSize(classString , cv::FONT_HERSHEY_DUPLEX , 1 , 2 , 0);
			cv::Rect textBox(re.box.x , re.box.y - 40 , textSize.width + 10 , textSize.height + 20);

			cv::rectangle(img , textBox , color , cv::FILLED);
			cv::putText(img , classString , cv::Point(re.box.x + 5 , re.box.y - 10) , cv::FONT_HERSHEY_DUPLEX , 1 , cv::Scalar(0 , 0 , 0) , 2 , 0);
		}

		/* frameCounter++;

		// 计算帧率
		double endTime = static_cast<double>(cv::getTickCount());
		double elapsedTime = (endTime - startTime) / cv::getTickFrequency();
		fps = static_cast<double>(frameCounter) / elapsedTime;
		// 在窗口左上角显示帧率
		cv::putText(img , "FPS: " + std::to_string(fps) , cv::Point(10 , 30) ,
		cv::FONT_HERSHEY_SIMPLEX , 1 , cv::Scalar(255 , 255 , 255) , 2); */

		//cout << "------------------------------" << endl;
	}
	//cout << "``````````````````````````````````````" << endl;

};

void file_iterator(DCSP_CORE*& p) {
	VideoCapture cap;   //声明相机捕获对象
	//cap.set(cv::CAP_PROP_FOURCC , cv::VideoWriter::fourcc('M' , 'J' , 'P' , 'G'));
	cap.open("E:/1.mp4");


	cv::Mat img;
	
	while (true)
	{
		cap >> img; //以流形式捕获图像
		cout << img.cols << "*" << img.rows << endl;
		namedWindow("example" , WINDOW_FREERATIO); //创建一个窗口用于显示图像，1代表窗口适应图像的分辨率进行拉伸。
		if (img.empty() != false) {//图像不为空则显示图像 COLOR_RGBA2BGR
			return;
		}
		task1(p , img);
		cv::imshow("example" , img);
		int  key = waitKey(30); //等待30ms
		if (key == int('q')) //按下q退出
		{
			break;
		}
	}
	cap.release(); //释放相机捕获对象
	destroyAllWindows(); //关闭所有窗口 
}

int read_coco_yaml(DCSP_CORE*& p) {
	// Open the YAML file
	std::ifstream file("coco.yaml");
	if (!file.is_open()) {
		std::cerr << "Failed to open file" << std::endl;
		return 1;
	}

	// Read the file line by line
	std::string line;
	std::vector<std::string> lines;
	while (std::getline(file , line)) {
		lines.push_back(line);
	}

	// Find the start and end of the names section
	std::size_t start = 0;
	std::size_t end = 0;
	for (std::size_t i = 0; i < lines.size(); i++) {
		if (lines [i].find("names:") != std::string::npos) {
			start = i + 1;
		}
		else if (start > 0 && lines [i].find(':') == std::string::npos) {
			end = i;
			break;
		}
	}

	// Extract the names
	std::vector<std::string> names;
	for (std::size_t i = start; i < end; i++) {
		std::stringstream ss(lines [i]);
		std::string name;
		std::getline(ss , name); // Extract the string after the delimiter
		std::getline(ss , name , ':'); // Extract the number before the delimiter
		names.push_back(name);
	}

	p->classes = names;
	return 0;
}
int main() {
#define USE_CUDA
	DCSP_CORE* yoloDetector = new DCSP_CORE;
	std::string model_path = "D:/yo/yolov8n.onnx";
	//read_coco_yaml(yoloDetector);
	yoloDetector->classes = { "xusanshi","tangshan","beibei","mei","mei2","mei3","jixie" };
#ifdef USE_CUDA
	//GPU FP32 inference
	DCSP_INIT_PARAM params { model_path, YOLO_ORIGIN_V8, {640, 640},  0.1, 0.5, true };
	// GPU FP16 inference
	// DCSP_INIT_PARAM params{ model_path, YOLO_ORIGIN_V8_HALF, {640, 640},  0.1, 0.5, true };
#else
	// CPU inference
	DCSP_INIT_PARAM params { model_path, YOLO_ORIGIN_V8, {640, 640}, 0.1, 0.5, false };
#endif
	yoloDetector->CreateSession(params);
	file_iterator(yoloDetector);

	return 0;
}