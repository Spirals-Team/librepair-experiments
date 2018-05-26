

class SkipException extends java.lang.Exception {
    private static final long serialVersionUID = 1L;

    java.lang.Object skipped;

    SkipException(java.lang.Object e) {
        super(("skipping " + (e.toString())));
        skipped = e;
    }
}

