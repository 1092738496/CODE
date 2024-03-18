set(CMAKE_CXX_STANDARD 17)
include_directories("D:/Development_Tool/opencv/build/include"  "D:/Development_Tool/onnxruntime-win-x64-gpu-1.14.0/include")
link_directories("D:/Development_Tool/onnxruntime-win-x64-gpu-1.14.0/lib" "D:/Development_Tool/opencv/build/x64/vc16/lib")
add_executable(sy_onnxruntime "main.cpp" "inference.cpp")

target_link_libraries(sy_onnxruntime "opencv_world490.lib" "onnxruntime.lib" "onnxruntime_providers_cuda.lib" "onnxruntime_providers_shared.lib")
