# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             native-lib.cpp )

find_library( # Defines the name of the path variable that stores the
        # location of the NDK library.
        log-lib

        # Specifies the name of the NDK library that
        # CMake needs to locate.
        log )

set(ffmpeg_DIR ${CMAKE_SOURCE_DIR}/ffmpeg)
add_library(avutil-55 SHARED IMPORTED)
set_target_properties(avutil-55 PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_DIR}/lib/${ANDROID_ABI}/libavutil-55.so)

add_library(avformat-57 SHARED IMPORTED)
set_target_properties(avformat-57 PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_DIR}/lib/${ANDROID_ABI}/libavformat-57.so)

add_library(avcodec-57 SHARED IMPORTED)
set_target_properties(avcodec-57 PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_DIR}/lib/${ANDROID_ABI}/libavcodec-57.so)

include_directories(${ffmpeg_DIR}/lib/${ANDROID_ABI}/include)
target_link_libraries( # Specifies the target library.
        native-lib

        avutil-55
        avformat-57
        avcodec-57
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})