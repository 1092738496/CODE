add_subdirectory(add)

link_libraries(${CMAKE_CURRENT_SOURCE_DIR}/lib/add_static.a)
add_executable(lesson2_1 main.cpp)