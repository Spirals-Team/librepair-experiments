
#!/bin/bash
# Copyright (c) 2018 Shapelets.io
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.

if [[ "$TRAVIS_OS_NAME" == "osx" ]]; then
    mkdir -p installers && cd installers
    
    #update python
    brew upgrade python
    #brew install --force doxygen graphviz

    # Installing conan
    sudo pip3 install --upgrade pip
    sudo pip3 install conan #sphinx sphinx_rtd_theme breathe

    # Installing library dependencies with conan
    conan remote add -f conan-mpusz https://api.bintray.com/conan/mpusz/conan-mpusz
    conan remote add -f boost_math https://api.bintray.com/conan/bincrafters/public-conan

    # Install Khiva
    git clone https://github.com/shapelets/khiva.git
    cd khiva
    git checkout develop
    mkdir -p build && cd build
    conan install .. -s compiler=apple-clang -s compiler.version=9.1 -s compiler.libcxx=libc++ --build missing
    cmake ..  -DKHIVA_ONLY_CPU_BACKEND=ON -DKHIVA_BUILD_DOCUMENTATION=OFF -DKHIVA_BUILD_EXAMPLES=OFF -DKHIVA_BUILD_BENCHMARKS=OFF -DKHIVA_BUILD_TESTS=OFF
    make install
    cd ${TRAVIS_BUILD_DIR}
else
    if [ ! -e "installers/khiva-v0.1.0-ci.sh" ]; then
        wget https://github.com/shapelets/khiva/releases/download/v0.1.0/khiva-v0.1.0-ci.sh -O installers/khiva-v0.1.0-ci.sh
        chmod +x installers/khiva-v0.1.0-ci.sh
    fi

    sudo ./installers/khiva-v0.1.0-ci.sh --prefix=/usr/local --skip-license
    sudo ldconfig
fi