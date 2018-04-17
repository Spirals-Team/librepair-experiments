package validator;

import java.io.File;

public class FileExtensionValidator {//TODO Esto tiene que extender de alguna cosa que lea un archivo de configuracion

    private String extension;
    private String pattern;

    public FileExtensionValidator(String s) {
        extension = s;
        pattern = ".*\\.{0,1}" + extension;
    }

    public boolean validateExtension(File file){
        return this.validateExtension(file.getAbsoluteFile());
    }

    public boolean validateExtension(String fileName){

        return fileName.matches(pattern);
    }
}
