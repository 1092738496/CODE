#include <string>
#include <vector>
#include <cstdio>
#include <opencv2/opencv.hpp>
#include "onnxruntime_cxx_api.h"


/* // 读取类别标签
std::vector<std::string> readClassNames(const std::string& labels_txt_file) {
    std::vector<std::string> classNames;
    std::ifstream fp(labels_txt_file);
    std::string name;
    while (std::getline(fp, name)) {
        classNames.push_back(name);
    }
    return classNames;
} */

int main() {
    // 设置模型路径和类别标签
    std::string model_path = "D:/yo/yolov8n.onnx";
    //dstd::string labels_txt_file = "classes.txt";
    std::vector<std::string> classNames = { "person" };

    // 创建 OrtEnv 和 OrtSessionOptions
    Ort::Env env = Ort::Env(ORT_LOGGING_LEVEL_WARNING , "Yolo");
    Ort::SessionOptions session_options;
    session_options.SetIntraOpNumThreads(1); // 设置线程数
    session_options.SetGraphOptimizationLevel(ORT_ENABLE_BASIC); // 设置图优化级别

    // 创建 InferenceSession
    Ort::Session session(env , model_path.c_str() , session_options);

    // 获取输入和输出信息
    int input_node_count = session.GetInputCount();
    int output_node_count = session.GetOutputCount();
    std::vector<std::string> input_node_names;
    std::vector<std::string> output_node_names;
    for (int i = 0; i < input_node_count; ++i) {
        input_node_names.push_back(session.GetInputName(i , OrtApi::ORT_LOSS_NONE));
    }
    for (int i = 0; i < output_node_count; ++i) {
        output_node_names.push_back(session.GetOutputName(i , OrtApi::ORT_LOSS_NONE));
    }

    // 读取图像并进行预处理
    cv::Mat image = cv::imread("E:/1 (10).jpg");
    cv::Mat blob = cv::dnn::blobFromImage(image , 1.0 , cv::Size(416 , 416) , cv::Scalar(0 , 0 , 0) , true , false);
    std::vector<float> input_data(blob.size());
    blob.convertTo(input_data , CV_32F);

    // 创建输入 Tensor
    Ort::Value input_tensor = Ort::Value::CreateTensor<float>(Ort::MemoryInfo::CreateCpu(OrtMemTypeDefault) , input_data.data() , input_data.size());

    // 执行推理
    std::vector<Ort::Value> output_tensors;
    session.Run(Ort::RunOptions { nullptr } , input_node_names.data() , &input_tensor , 1 , output_node_names.data() , output_node_count , output_tensors.data());

    // 解析输出
    for (size_t i = 0; i < output_tensors.size(); ++i) {
        float* output_data = output_tensors [i].GetTensorMutableData<float>();
        // 输出数据解析和后处理，这里需要根据 YOLOv8 的输出格式来解析坐标和类别信息
        // ...
    }

    return 0;
}