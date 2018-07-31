package parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommandLineParser {

    /**
     * Парсит входящий поток данных, получает из него ключи
     * и устанавливает значения ключей.
     * В дальнейшем можно добавить исходящий ключ (OutputKey),
     * а так же исходящий аргумент (OutputArgument) - например имя файла,
     * в который записать статистику анализа
     * @param inputStream
     * @return
     */
    public Options parse(final String inputStream) {

        Options options = new Options();

        if (inputStream.contains("--char")) {
            options.setInputKey(InputKeys.chr);
            options.setInputArgument(inputStream.substring(7));
        } else if (inputStream.contains("--word")) {
            options.setInputKey(InputKeys.word);
            options.setInputArgument(inputStream.substring(7));
        } else if (inputStream.contains("--h")) {
            options.setInputKey(InputKeys.help);
        } else if (inputStream.contains("--quit")) {
            options.setInputKey(InputKeys.quit);
        } else if (inputStream.contains("--ffile")) {
            options.setInputKey(InputKeys.ffile);
            options.setInputArgument(readFromFile(inputStream.substring(7)));
        } else {
            options.setInputKey(InputKeys.deflt);
            options.setInputArgument(inputStream);
        }

        return options;
    }

    private String readFromFile(String fileName) {
        String str = "";
        try {
            str += Files.lines(Paths.get("src/test/java/testFile.txt"), StandardCharsets.UTF_8).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return str;
    }
}
