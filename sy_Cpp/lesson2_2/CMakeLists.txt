add_subdirectory(add)

link_libraries(${CMAKE_CURRENT_SOURCE_DIR}/lib/libmyadd.dll)
add_executable(lesson2_2 main.cpp)