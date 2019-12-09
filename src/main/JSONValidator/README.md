## A JSON validator and schema checker in WASM

#### Prerequisites
- CMake
- emsdk (with an Emscripten SDK installed)

#### Submodules

This part of the project requires RapidJSON, which is automatically pulled as a git submodule.  
Run `git submodule update --init --recursive` to obtain the submodules

#### Build

Rather straightforward, given you have CMake and EmSDK setup already. Tested with Clang 9.0.0.
```sh
mkdir build
cd build
emcmake cmake .. && cmake --build . -j
```