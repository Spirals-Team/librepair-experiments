# Fake SFTP Server Lambda

Work in progress. Please wait for the first release.

[![Build Status](https://travis-ci.org/stefanbirkner/fake-sftp-server-lambda.svg?branch=master)](https://travis-ci.org/stefanbirkner/fake-sftp-server-lambda)

Fake SFTP Server Lambda runs an in-memory SFTP server while your tests are
running. It uses the SFTP server of the
[Apache SSHD](http://mina.apache.org/sshd-project/index.html) project.

Fake SFTP Server Lambda is published under the
[MIT license](http://opensource.org/licenses/MIT). It requires Java 8.

For JUnit 4 there is an alternative to Fake SFTP Server Lambda. Its name is
[Fake SFTP Server Rule](https://github.com/stefanbirkner/fake-sftp-server-rule).


## Installation

Fake SFTP Server Lambda is available from
[Maven Central](https://search.maven.org/#search|ga|1|fake-sftp-server-lambda).

    <dependency>
      <groupId>com.github.stefanbirkner</groupId>
      <artifactId>fake-sftp-server-lambda</artifactId>
      <version>not-published</version>
      <scope>test</scope>
    </dependency>

## Usage

Fake SFTP Server Lambda is used by wrapping your test code with the method
`withSftpServer`.

    import static com.github.stefanbirkner.fakesftpserver.lambda.FakeSftpServer.withSftpServer;

    public class TestClass {
      @Test
      public void someTest() throws Exception {
        withSftpServer(server -> {
          //test code
        });
      }
    }

`withSftpServer` starts an SFTP server before executing the test code and shuts
down the server afterwards. The test code uses the provided server object to
obtain information about the running server or use additional features of Fake
SFTP Server Lambda.

You can interact with the SFTP server by using the SFTP protocol with password
authentication. By default the server accepts every pair of username and
password, but you can restrict it to specific pairs.

    withSftpServer(server -> {
      server.addUser("username", "password");
      ...
    });

By default the SFTP server listens on an auto-allocated port. During the test
this port can be obtained by `server.getPort()`. It can be changed by calling
`setPort(int)`. The server is restarted whenever this method is called.


    withSftpServer(server -> {
      server.setPort(1234);
      ...
    });


### Testing code that reads files

If you test code that reads files from an SFTP server then you need a server
that provides these files. The server object has a shortcut for putting files
to the server.

    @Test
    public void testTextFile() throws Exception {
      withSftpServer(server -> {
        server.putFile("/directory/file.txt", "content of file", UTF_8);
        //code that reads the file using the SFTP protocol
      });
    }

    @Test
    public void testBinaryFile() throws Exception {
      withSftpServer(server -> {
        byte[] content = createContent();
        server.putFile("/directory/file.bin", content);
        //code that reads the file using the SFTP protocol
      });
    }

Test data that is provided as an input stream can be put directly from that
input stream. This is very handy if your test data is available as a resource.

    @Test
    public void testFileFromInputStream() throws Exception {
      withSftpServer(server -> {
        InputStream is = getClass().getResourceAsStream("data.bin");
        server.putFile("/directory/file.bin", is);
        //code that reads the file using the SFTP protocol
      });
    }

If you need an empty directory then you can use the method
`createDirectory(String)`.

    @Test
    public void testDirectory() throws Exception {
      withSftpServer(server -> {
        server.createDirectory("/a/directory");
        //code that reads from or writes to that directory
      });
    }

You may create multiple directories at once with `createDirectories(String...)`.

    @Test
    public void testDirectories() throws Exception {
      withSftpServer(server -> {
        server.createDirectories(
          "/a/directory",
          "/another/directory"
        );
        //code that reads from or writes to that directories
      });
    }


### Testing code that writes files

If you test code that writes files to an SFTP server then you need to verify
the upload. The server object provides a shortcut for getting the file's
content from the server.

    @Test
    public void testTextFile() throws Exception {
      withSftpServer(server -> {
        //code that uploads the file using the SFTP protocol
        String fileContent = server.getFileContent("/directory/file.txt", UTF_8);
        //verify file content
      });
    }

    @Test
    public void testBinaryFile() throws Exception {
      withSftpServer(server -> {
        //code that uploads the file using the SFTP protocol
        byte[] fileContent = server.getFileContent("/directory/file.bin");
        //verify file content
      });
    }

### Testing existence of files

If you want to check whether a file was created or deleted then you can verify
that it exists or not.

    @Test
    public void testFile() throws Exception {
      withSftpServer(server -> {
        //code that uploads or deletes the file
        boolean exists = server.existsFile("/directory/file.txt");
        //check value of exists variable
      });
    }

The method returns `true` iff the file exists and it is not a directory.

### Delete all files

If you want to reuse the SFTP server then you can delete all files and
directories on the SFTP server. (This is rarely necessary because the method
`withSftpServer` takes care that it starts and ends with a clean SFTP server.)

    server.deleteAllFilesAndDirectories()

## Contributing

You have three options if you have a feature request, found a bug or
simply have a question about Fake SFTP Server Lambda.

* [Write an issue.](https://github.com/stefanbirkner/fake-sftp-server-lambda/issues/new)
* Create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
* [Write a mail to mail@stefan-birkner.de](mailto:mail@stefan-birkner.de)


## Development Guide

Fake SFTP Server Lambda is build with [Maven](http://maven.apache.org/). If you
want to contribute code then

* Please write a test for your change.
* Ensure that you didn't break the build by running `mvn verify -Dgpg.skip`.
* Fork the repo and create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))

The basic coding style is described in the
[EditorConfig](http://editorconfig.org/) file `.editorconfig`.

Fake SFTP Server Lambda supports [Travis CI](https://travis-ci.org/) for
continuous integration. Your pull request will be automatically build by Travis
CI.


## Release Guide

* Select a new version according to the
  [Semantic Versioning 2.0.0 Standard](http://semver.org/).
* Set the new version in `pom.xml` and in the `Installation` section of
  this readme.
* Commit the modified `pom.xml` and `README.md`.
* Run `mvn clean deploy` with JDK 8.
* Add a tag for the release: `git tag fake-sftp-server-lambda-X.X.X`
