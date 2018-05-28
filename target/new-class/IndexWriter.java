

public final class IndexWriter {
    private org.apache.lucene.store.Directory directory;

    private org.apache.lucene.analysis.Analyzer analyzer;

    private SegmentInfos segmentInfos = new SegmentInfos();

    private final org.apache.lucene.store.Directory ramDirectory = new org.apache.lucene.store.RAMDirectory();

    public IndexWriter(java.lang.String path, org.apache.lucene.analysis.Analyzer a, boolean create) throws java.io.IOException {
        this(org.apache.lucene.store.FSDirectory.getDirectory(path, create), a, create);
    }

    public IndexWriter(java.io.File path, org.apache.lucene.analysis.Analyzer a, boolean create) throws java.io.IOException {
        this(org.apache.lucene.store.FSDirectory.getDirectory(path, create), a, create);
    }

    public IndexWriter(org.apache.lucene.store.Directory d, org.apache.lucene.analysis.Analyzer a, final boolean create) throws java.io.IOException {
        directory = d;
        analyzer = a;
        org.apache.lucene.store.Lock writeLock = directory.makeLock("write.lock");
        if (!(writeLock.obtain()))
            throw new java.io.IOException(("Index locked for write: " + writeLock));

        synchronized(directory) {
            new org.apache.lucene.store.Lock.With(directory.makeLock("commit.lock")) {
                public java.lang.Object doBody() throws java.io.IOException {
                    if (create)
                        segmentInfos.write(directory);
                    else
                        segmentInfos.read(directory);

                    return null;
                }
            }.run();
        }
    }

    public final synchronized void close() throws java.io.IOException {
        flushRamSegments();
        ramDirectory.close();
        directory.makeLock("write.lock").release();
        directory.close();
    }

    public final synchronized int docCount() {
        int count = 0;
        for (int i = 0; i < (segmentInfos.size()); i++) {
            SegmentInfo si = segmentInfos.info(i);
            count += si.docCount;
        }
        return count;
    }

    public int maxFieldLength = 10000;

    public final void addDocument(org.apache.lucene.document.Document doc) throws java.io.IOException {
        DocumentWriter dw = new DocumentWriter(ramDirectory, analyzer, maxFieldLength);
        java.lang.String segmentName = newSegmentName();
        dw.addDocument(segmentName, doc);
        synchronized(this) {
            segmentInfos.addElement(new SegmentInfo(segmentName, 1, ramDirectory));
            maybeMergeSegments();
        }
    }

    private final synchronized java.lang.String newSegmentName() {
        return "_" + (java.lang.Integer.toString(((segmentInfos.counter)++), java.lang.Character.MAX_RADIX));
    }

    public int mergeFactor = 10;

    public int maxMergeDocs = java.lang.Integer.MAX_VALUE;

    public java.io.PrintStream infoStream = null;

    public final synchronized void optimize() throws java.io.IOException {
        flushRamSegments();
        while (((segmentInfos.size()) > 1) || (((segmentInfos.size()) == 1) && (SegmentReader.hasDeletions(segmentInfos.info(0))))) {
            int minSegment = (segmentInfos.size()) - (mergeFactor);
            mergeSegments((minSegment < 0 ? 0 : minSegment));
        } 
    }

    public final synchronized void addIndexes(org.apache.lucene.store.Directory[] dirs) throws java.io.IOException {
        optimize();
        int minSegment = segmentInfos.size();
        int segmentsAddedSinceMerge = 0;
        for (int i = 0; i < (dirs.length); i++) {
            SegmentInfos sis = new SegmentInfos();
            sis.read(dirs[i]);
            for (int j = 0; j < (sis.size()); j++) {
                segmentInfos.addElement(sis.info(j));
                if ((++segmentsAddedSinceMerge) == (mergeFactor)) {
                    mergeSegments((minSegment++), false);
                    segmentsAddedSinceMerge = 0;
                }
            }
        }
        optimize();
    }

    private final void flushRamSegments() throws java.io.IOException {
        int minSegment = (segmentInfos.size()) - 1;
        int docCount = 0;
        while ((minSegment >= 0) && ((segmentInfos.info(minSegment).dir) == (ramDirectory))) {
            docCount += segmentInfos.info(minSegment).docCount;
            minSegment--;
        } 
        if (((minSegment < 0) || ((docCount + (segmentInfos.info(minSegment).docCount)) > (mergeFactor))) || (!((segmentInfos.info(((segmentInfos.size()) - 1)).dir) == (ramDirectory))))
            minSegment++;

        if (minSegment >= (segmentInfos.size()))
            return;

        mergeSegments(minSegment);
    }

    private final void maybeMergeSegments() throws java.io.IOException {
        long targetMergeDocs = mergeFactor;
        while (targetMergeDocs <= (maxMergeDocs)) {
            int minSegment = segmentInfos.size();
            int mergeDocs = 0;
            while ((--minSegment) >= 0) {
                SegmentInfo si = segmentInfos.info(minSegment);
                if ((si.docCount) >= targetMergeDocs)
                    break;

                mergeDocs += si.docCount;
            } 
            if (mergeDocs >= targetMergeDocs)
                mergeSegments((minSegment + 1));
            else
                break;

            targetMergeDocs *= mergeFactor;
        } 
    }

    private final void mergeSegments(int minSegment) throws java.io.IOException {
        mergeSegments(minSegment, true);
    }

    private final void mergeSegments(int minSegment, boolean delete) throws java.io.IOException {
        java.lang.String mergedName = newSegmentName();
        int mergedDocCount = 0;
        if ((infoStream) != null)
            infoStream.print("merging segments");

        SegmentMerger merger = new SegmentMerger(directory, mergedName);
        final java.util.Vector segmentsToDelete = new java.util.Vector();
        for (int i = minSegment; i < (segmentInfos.size()); i++) {
            SegmentInfo si = segmentInfos.info(i);
            if ((infoStream) != null)
                infoStream.print(((((" " + (si.name)) + " (") + (si.docCount)) + " docs)"));

            SegmentReader reader = new SegmentReader(si);
            merger.add(reader);
            if (delete)
                segmentsToDelete.addElement(reader);

            mergedDocCount += si.docCount;
        }
        if ((infoStream) != null) {
            infoStream.println();
            infoStream.println(((((" into " + mergedName) + " (") + mergedDocCount) + " docs)"));
        }
        merger.merge();
        segmentInfos.setSize(minSegment);
        segmentInfos.addElement(new SegmentInfo(mergedName, mergedDocCount, directory));
        synchronized(directory) {
            new org.apache.lucene.store.Lock.With(directory.makeLock("commit.lock")) {
                public java.lang.Object doBody() throws java.io.IOException {
                    segmentInfos.write(directory);
                    deleteSegments(segmentsToDelete);
                    return null;
                }
            }.run();
        }
    }

    private final void deleteSegments(java.util.Vector segments) throws java.io.IOException {
        java.util.Vector deletable = new java.util.Vector();
        deleteFiles(readDeleteableFiles(), deletable);
        for (int i = 0; i < (segments.size()); i++) {
            SegmentReader reader = ((SegmentReader) (segments.elementAt(i)));
            if ((reader.directory) == (this.directory))
                deleteFiles(reader.files(), deletable);
            else
                deleteFiles(reader.files(), reader.directory);

        }
        writeDeleteableFiles(deletable);
    }

    private final void deleteFiles(java.util.Vector files, org.apache.lucene.store.Directory directory) throws java.io.IOException {
        for (int i = 0; i < (files.size()); i++)
            directory.deleteFile(((java.lang.String) (files.elementAt(i))));

    }

    private final void deleteFiles(java.util.Vector files, java.util.Vector deletable) throws java.io.IOException {
        for (int i = 0; i < (files.size()); i++) {
            java.lang.String file = ((java.lang.String) (files.elementAt(i)));
            try {
                directory.deleteFile(file);
            } catch (java.io.IOException e) {
                if (directory.fileExists(file)) {
                    if ((infoStream) != null)
                        infoStream.println(((e.getMessage()) + "; Will re-try later."));

                    deletable.addElement(file);
                }
            }
        }
    }

    private final java.util.Vector readDeleteableFiles() throws java.io.IOException {
        java.util.Vector result = new java.util.Vector();
        if (!(directory.fileExists("deletable")))
            return result;

        org.apache.lucene.store.InputStream input = directory.openFile("deletable");
        try {
            for (int i = input.readInt(); i > 0; i--)
                result.addElement(input.readString());

        } finally {
            input.close();
        }
        return result;
    }

    private final void writeDeleteableFiles(java.util.Vector files) throws java.io.IOException {
        org.apache.lucene.store.OutputStream output = directory.createFile("deleteable.new");
        try {
            output.writeInt(files.size());
            for (int i = 0; i < (files.size()); i++)
                output.writeString(((java.lang.String) (files.elementAt(i))));

        } finally {
            output.close();
        }
        directory.renameFile("deleteable.new", "deletable");
    }
}

