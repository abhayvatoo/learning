cmake_minimum_required(VERSION 2.8)
project( test-radist )
find_package( OpenCV REQUIRED )
add_executable( test-radist test-radist.cpp )
target_link_libraries( test-radist ${OpenCV_LIBS} ${CMAKE_CURRENT_SOURCE_DIR}/../lib/libradist.a)
