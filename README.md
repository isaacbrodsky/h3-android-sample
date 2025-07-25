# Sample application for H3-Java on Android

<img align="right" src="./Screenshot.png" width="270" alt="Hello world screenshot" />

[![Test](https://github.com/isaacbrodsky/h3-android-sample/actions/workflows/test.yaml/badge.svg)](https://github.com/isaacbrodsky/h3-android-sample/actions/workflows/test.yaml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

This is a sample application for using H3-Java on Android. It was compiled with [v4.1.1]((https://github.com/uber/h3-java/releases/tag/v4.1.1)) (as of writing, the latest published version of h3-java.)

**Notes**

* It was necessary to automatically copy the libh3-java.so files from the distributed JAR into the applicable jniLibs directories in the project.
* It was necessary to add an externalNativeBuild section to the build, so that the C++ library was switched to shared.

<br clear="right" />

# License

Copyright 2024 Isaac Brodsky.
Licensed under the [Apache 2.0 License](./LICENSE).

[H3](https://github.com/uber/h3) Copyright 2018 Uber Technologies Inc. (Apache 2.0 License)

DGGRID Copyright (c) 2015 Southern Oregon University
