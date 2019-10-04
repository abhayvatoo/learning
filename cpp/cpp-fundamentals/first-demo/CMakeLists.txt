# cmake needs this line
cmake_minimum_required(VERSION 3.1)

# Enable C++11
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

# Define project name
project(fundamentals-cpp-first)

# Include
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

# Add all cpp source files
FILE(GLOB_RECURSE cpp_source_files ${CMAKE_CURRENT_SOURCE_DIR}/source/*.cpp)

# Declare the executable target built from your sources
add_executable(app main.cpp ${cpp_source_files})