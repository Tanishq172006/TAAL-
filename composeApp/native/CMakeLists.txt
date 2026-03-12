cmake_minimum_required(VERSION 3.18)
project(miniaudio)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c99")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

file(
        GLOB_RECURSE shared_library_sources
        ${CMAKE_CURRENT_SOURCE_DIR}/include/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/include/*.cpp)

file(
        GLOB_RECURSE shared_library_headers
        ${CMAKE_CURRENT_SOURCE_DIR}/include/*.h
)

add_library(
        ${PROJECT_NAME}
        SHARED
        ${shared_library_sources}
        ${shared_library_headers}
)

target_include_directories(
        ${PROJECT_NAME}
        PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include/
)

target_link_libraries(
    ${PROJECT_NAME}
    android
    log
)
