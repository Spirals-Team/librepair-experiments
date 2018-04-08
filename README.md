Java support for libpasta
=========================

This repo produces a self-contained jar file for libpasta.

## Requirements

Currently, only 64-bit linux is supported, but we are aiming to support more
platforms.

Both of the below approaches require `libcrypto.so.1.0.0` usually available through
`libssl` or similar package. The currently required version of GLIBC is 2.14.

Currently [SWIG](http://www.swig.org/) is required to produce the library
bindings, but these will be added pregenerated when the library has stabilised.

## Using libpasta-java

### Precompiled .jar

Download a released jar from [Releases](https://github.com/libpasta/libpasta-java/releases).

As a shortcut: `make get-precompiled`

Include jar in classpath.

### With system library installed (preferred, requires SWIG)

The best way to use libpasta is to have the system library preinstalled.
See [here](https://github.com/libpasta/libpasta#installation) for installation
instructions.

**Run:** `make using-systemlib`

Performs the following steps:

* Sync submodules: `git submodule init && git submodule sync --recursive`
* Using `pasta-bindings` submodule, produce the java libpasta_jni.so file: `cd pasta-bindings && make java && cd ..`.
* Move the appropriate file to the maven directory: `mkdir -p src/main/resources/ && cp -r pasta-bindings/java/META-INF src/main/resources/META-INF`
* Compile the jar: `mvn package`.

The resultant jar file should be in target/libpasta-java-{version}-jar-with-dependencies.jar.

Alteratively, run `make using-sharedlib` to build/install the sytem library as part of the installation (requires Rust).

### Steps to produce from source (requires Rust and SWIG):

**Run:** `make using-staticlib`

Performs the following steps:

 * Sync submodules: `git submodule init && git submodule sync --recursive`
 * Using `pasta-bindings` submodule, produce the java libpasta_jni.so file: `cd pasta-bindings && USE_STATIC=1 make java && cd ..`.
 * Move the appropriate file to the maven directory: `mkdir -p src/main/resources/ && cp -r pasta-bindings/java/META-INF src/main/resources/META-INF`
 * Compile the jar: `mvn package`.

The resultant jar file should be in target/libpasta-java-{version}-jar-with-dependencies.jar.

### Steps to produce using precompiled library:

**Run:** `make using-precompiled`

Performs the following steps:

 * Get the most recent precompiled library from the pasta-bindings releases [https://github.com/libpasta/pasta-bindings/releases/]
   - e.g. `wget https://github.com/libpasta/pasta-bindings/releases/download/v0.0.5/libpasta_jni.x86_64-unknown-linux-gnu.so -O libpasta_jni.so`
 * Move to the appropriate directory: `mkdir -p src/main/resources/META-INF/lib/linux_64/ && mv libpasta_jni.so src/main/resources/META-INF/lib/linux_64/`
 * Compile the jar: `mvn package`.

The resultant jar file should be in target/libpasta-java-{version}-jar-with-dependencies.jar.
