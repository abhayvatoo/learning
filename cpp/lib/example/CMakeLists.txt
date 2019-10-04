# cmake needs this line
cmake_minimum_required(VERSION 3.1)

# Enable C++11
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

# Define project name
project(learning-mylib-example)

INCLUDE(FindPkgConfig)
pkg_check_modules(MYLIB REQUIRED mylib)    

# Declare the executable target
add_executable(app example.cpp)

#link
target_link_libraries(app ${MYLIB_LIBRARIES})
