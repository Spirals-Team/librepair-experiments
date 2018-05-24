package spoon.compiler;


public abstract class SpoonResourceHelper {
    private SpoonResourceHelper() {
    }

    public static boolean isArchive(java.io.File f) {
        return (f.getName().endsWith(".jar")) || (f.getName().endsWith(".zip"));
    }

    public static boolean isFile(java.io.File f) {
        return (f.isFile()) && (!(spoon.compiler.SpoonResourceHelper.isArchive(f)));
    }

    public static java.util.List<spoon.compiler.SpoonResource> resources(java.lang.String... paths) throws java.io.FileNotFoundException {
        java.util.List<spoon.compiler.SpoonResource> files = new java.util.ArrayList<>();
        for (java.lang.String path : paths) {
            files.add(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(path)));
        }
        return files;
    }

    public static spoon.compiler.SpoonFile createFile(java.io.File f) throws java.io.FileNotFoundException {
        if (!(f.exists())) {
            throw new java.io.FileNotFoundException(f.toString());
        }
        return new spoon.support.compiler.FileSystemFile(f);
    }

    public static spoon.compiler.SpoonResource createResource(java.io.File f) throws java.io.FileNotFoundException {
        if (spoon.compiler.SpoonResourceHelper.isFile(f)) {
            return spoon.compiler.SpoonResourceHelper.createFile(f);
        }
        return spoon.compiler.SpoonResourceHelper.createFolder(f);
    }

    public static spoon.compiler.SpoonFolder createFolder(java.io.File f) throws java.io.FileNotFoundException {
        if (!(f.exists())) {
            throw new java.io.FileNotFoundException(((f.toString()) + " does not exist"));
        }
        try {
            if (f.isDirectory()) {
                return new spoon.support.compiler.FileSystemFolder(f);
            }
            if (spoon.compiler.SpoonResourceHelper.isArchive(f)) {
                return new spoon.support.compiler.ZipFolder(f);
            }
        } catch (java.io.IOException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}

