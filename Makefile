JAR_URL = $(shell curl ${TRAVIS_GITHUB_CREDENTIALS} -s "https://api.github.com/repos/libpasta/libpasta-java/releases/latest" \
    | jq -r '.assets[] | select(.name=="libpasta-java.x86_64-unknown-linux-gnu.jar") | .browser_download_url')
SO_URL = $(shell curl ${TRAVIS_GITHUB_CREDENTIALS} -s "https://api.github.com/repos/libpasta/pasta-bindings/releases/latest" \
    | jq -r '.assets[] | select(.name=="libpasta_jni.x86_64-unknown-linux-gnu.so") | .browser_download_url')


all: mvn

clean:
	rm -rf src/main/resources
	mvn clean

get-precompiled:
	wget $(JAR_URL)

using-systemlib: libpasta-sync
	make -C ../pasta-bindings java
	make mvn

using-staticlib: USE_STATIC=1
using-staticlib using-sharedlib:
	USE_STATIC=$(USE_STATIC) make -C ../pasta-bindings libpasta java
	make mvn

using-precompiled:
	rm -rf ../pasta-bindings/java/META-INF/lib/linux_64
	mkdir -p ../pasta-bindings/java/META-INF/lib/linux_64
	wget $(SO_URL) -O ../pasta-bindings/java/META-INF/lib/linux_64/libpasta_jni.so
	make mvn

update-sources:
	mkdir -p src/main/java/io/github/libpasta/
	USE_STATIC=1 make -C ../pasta-bindings java
	cp ../pasta-bindings/java/*.java src/main/java/io/github/libpasta/

mvn:
	mkdir -p src/main/resources/
	cp -r ../pasta-bindings/java/META-INF src/main/resources/
	mvn -X -Dorg.slf4j.simpleLogger.defaultLogLevel=debug package

