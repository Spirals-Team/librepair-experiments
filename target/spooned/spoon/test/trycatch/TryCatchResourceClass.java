package spoon.test.trycatch;


public class TryCatchResourceClass {
    public java.lang.String readFirstLineFromFile(java.lang.String path) throws java.io.IOException {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(path))) {
            return br.readLine();
        }
    }

    public void writeToFileZipFileContents(java.lang.String zipFileName, java.lang.String outputFileName) throws java.io.IOException {
        java.nio.charset.Charset charset = java.nio.charset.Charset.forName("US-ASCII");
        java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);
        try (java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)) {
            for (java.util.Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
                java.lang.String newLine = java.lang.System.getProperty("line.separator");
                java.lang.String zipEntryName = (((java.util.zip.ZipEntry) (entries.nextElement())).getName()) + newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }
}

