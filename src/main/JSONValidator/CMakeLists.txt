cmake_minimum_required(VERSION 3.10)
project (CoffeeMine_JSONValidator)

set (CMAKE_CXX_STANDARD 17)

set (CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${PROJECT_SOURCE_DIR}/CMake/Modules")
set (CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../resources/META-INF/resources/wasm-parts)

include_directories (${PROJECT_SOURCE_DIR}/thirdparty/rapidjson/include)

add_compile_options (-o JSONValidator.js)
add_compile_options (-s WASM=1)
add_compile_options (-fno-rtti -fno-exceptions -Oz)

set (CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} -s EXPORTED_FUNCTIONS='[\"_test\"]' -s EXTRA_EXPORTED_RUNTIME_METHODS='[\"ccall\", \"cwrap\"]'")

add_executable (JSONValidator
    module.cxx)