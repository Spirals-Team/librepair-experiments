# Contributing to Zeebe

Most Zeebe source files are made available under the [Apache License, Version
2.0](/APACHE-2.0) except for the [broker-core](/broker-core) component. The
[broker-core](/broker-core) source files are made available under the terms of
the [GNU Affero General Public License (GNU AGPLv3)](/GNU-AGPL-3.0). See
individual source files for details.

If you would like to contribute something, or simply want to hack on the code
this document should help you get started.

## Code of Conduct

This project adheres to the Contributor Covenant [Code of
Conduct](/CODE_OF_CONDUCT.md). By participating, you are expected to uphold
this code. Please report unacceptable behavior to
code-of-conduct@zeebe.io.

## Using GitHub issues

We use [GitHub issues][issues] to track bugs and enhancements. If you have a
general usage question please ask on the [forum][] or [slack][] channel.

If you are reporting a bug, please help to speed up problem diagnosis by
providing as much information as possible. Ideally, that would include a small
[sample project][sample] that reproduces the problem.

[issues]: https://github.com/zeebe-io/zeebe/issues
[forum]: https://forum.zeebe.io/
[slack]: https://zeebe-slackin.herokuapp.com/
[sample]: https://github.com/zeebe-io/zeebe-test-template-java

## Building zb from Source

## Building the Documentation

The documentation is located under `docs/`.

In order to build the documentation you need the [mdbook](https://github.com/azerupi/mdBook/) utilitity.

The [binaries](https://github.com/azerupi/mdBook/releases) can be downloaded from the github release page.

In order to use mdbook you need to put it into your path. After this you can simply type

```bash
cd docs/
mdbook serve
```

This will assemble the documentation from the sources and serve it under [http://localhost:3000](http://localhost:3000).
