# Sample application for H3-Java on Android

This is a sample application for using H3-Java on Android. It was compiled with 4.1.1 (as of writing, the latest published version of h3-java.)

## Notes

* It was necessary to manually copy the libh3-java.so files from the distributed JAR into the applicable jniLibs directories in the project.
* It was necessary to add an externalNativeBuild section to the build, so that the C++ library was switched to shared.

# License

Copyright 2024 Isaac Brodsky.
Licensed under the [Apache 2.0 License](./LICENSE).

[H3](https://github.com/uber/h3) Copyright 2018 Uber Technologies Inc. (Apache 2.0 License)

DGGRID Copyright (c) 2015 Southern Oregon University
