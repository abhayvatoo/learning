# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.10

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/abhay/learn/lib

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/abhay/learn/lib/build

# Include any dependencies generated for this target.
include CMakeFiles/mylib.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/mylib.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/mylib.dir/flags.make

CMakeFiles/mylib.dir/source/mylib.cpp.o: CMakeFiles/mylib.dir/flags.make
CMakeFiles/mylib.dir/source/mylib.cpp.o: ../source/mylib.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/abhay/learn/lib/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/mylib.dir/source/mylib.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/mylib.dir/source/mylib.cpp.o -c /home/abhay/learn/lib/source/mylib.cpp

CMakeFiles/mylib.dir/source/mylib.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/mylib.dir/source/mylib.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/abhay/learn/lib/source/mylib.cpp > CMakeFiles/mylib.dir/source/mylib.cpp.i

CMakeFiles/mylib.dir/source/mylib.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/mylib.dir/source/mylib.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/abhay/learn/lib/source/mylib.cpp -o CMakeFiles/mylib.dir/source/mylib.cpp.s

CMakeFiles/mylib.dir/source/mylib.cpp.o.requires:

.PHONY : CMakeFiles/mylib.dir/source/mylib.cpp.o.requires

CMakeFiles/mylib.dir/source/mylib.cpp.o.provides: CMakeFiles/mylib.dir/source/mylib.cpp.o.requires
	$(MAKE) -f CMakeFiles/mylib.dir/build.make CMakeFiles/mylib.dir/source/mylib.cpp.o.provides.build
.PHONY : CMakeFiles/mylib.dir/source/mylib.cpp.o.provides

CMakeFiles/mylib.dir/source/mylib.cpp.o.provides.build: CMakeFiles/mylib.dir/source/mylib.cpp.o


# Object files for target mylib
mylib_OBJECTS = \
"CMakeFiles/mylib.dir/source/mylib.cpp.o"

# External object files for target mylib
mylib_EXTERNAL_OBJECTS =

libmylib.so.1.0.1: CMakeFiles/mylib.dir/source/mylib.cpp.o
libmylib.so.1.0.1: CMakeFiles/mylib.dir/build.make
libmylib.so.1.0.1: CMakeFiles/mylib.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/abhay/learn/lib/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX shared library libmylib.so"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/mylib.dir/link.txt --verbose=$(VERBOSE)
	$(CMAKE_COMMAND) -E cmake_symlink_library libmylib.so.1.0.1 libmylib.so.1 libmylib.so

libmylib.so.1: libmylib.so.1.0.1
	@$(CMAKE_COMMAND) -E touch_nocreate libmylib.so.1

libmylib.so: libmylib.so.1.0.1
	@$(CMAKE_COMMAND) -E touch_nocreate libmylib.so

# Rule to build all files generated by this target.
CMakeFiles/mylib.dir/build: libmylib.so

.PHONY : CMakeFiles/mylib.dir/build

CMakeFiles/mylib.dir/requires: CMakeFiles/mylib.dir/source/mylib.cpp.o.requires

.PHONY : CMakeFiles/mylib.dir/requires

CMakeFiles/mylib.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/mylib.dir/cmake_clean.cmake
.PHONY : CMakeFiles/mylib.dir/clean

CMakeFiles/mylib.dir/depend:
	cd /home/abhay/learn/lib/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/abhay/learn/lib /home/abhay/learn/lib /home/abhay/learn/lib/build /home/abhay/learn/lib/build /home/abhay/learn/lib/build/CMakeFiles/mylib.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/mylib.dir/depend

