package fileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PcapFileReader{

    private FileLineReader fileLineReader;

    public PcapFileReader(File file) {
        try {
            fileLineReader = new FileLineReader(new FileReader(file.getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public String read(){
        List<Character> characterList = new ArrayList<>();
        String line = "";
        while(!fileLineReader.ifEof())
            line += fileLineReader.readLine().replaceAll(" ", "");

        return line;
    }

}
