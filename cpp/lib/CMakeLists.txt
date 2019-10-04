cmake_minimum_required(VERSION 3.9)

project(mylib VERSION 1.0.1 DESCRIPTION "learning build  lib")

include(GNUInstallDirs)

# Include
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

add_library(mylib SHARED source/mylib.cpp)

set_target_properties(mylib PROPERTIES
    VERSION ${PROJECT_VERSION}
    SOVERSION 1
    PUBLIC_HEADER include/mylib.h)

configure_file(mylib.pc.in mylib.pc @ONLY)

target_include_directories(mylib PRIVATE .)

install(TARGETS mylib
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    PUBLIC_HEADER DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})

install(FILES ${CMAKE_BINARY_DIR}/mylib.pc
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/pkgconfig)

#sudo /sbin/ldconfig -v

INCLUDE(FindPkgConfig)
pkg_check_modules(MYLIB REQUIRED mylib)    

# Declare the executable target
add_executable(test-case test.cpp)

#link
target_link_libraries(test-case ${MYLIB_LIBRARIES})