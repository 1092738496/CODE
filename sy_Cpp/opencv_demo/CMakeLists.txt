include_directories("D:/Development_Tool/opencv/build/include")
link_directories("D:/Development_Tool/opencv/build/x64/vc16/lib")
# link_libraries(D:/opencv/build/x64/vc16/bin/opencv_world490.dll)
add_executable(opencv_demo "main.cpp" "inference.cpp")
target_link_libraries(opencv_demo "opencv_world490.lib")

