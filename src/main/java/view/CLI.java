package view;

import analyzeModel.TextStatistics;
import analyzer.TextAnalyzer;
import parser.CommandLineParser;
import parser.InputKeys;
import parser.Options;
import java.util.*;

public class CLI {

    /**
     * main метод, ожидает строку ввода из консоли,
     * затем анализирует введенный текст и обрабатывает результат.
     * @param args
     */
    public static void main(String[] args) {

        boolean runAnalyze = true;

        while (runAnalyze) {

            String inputStream = getInputText();

            Options options = parseParams(inputStream);
            if (InputKeys.help.equals(options.getInputKey())) {
                Helper.printHelp();
                continue;
            }else if (InputKeys.quit.equals(options.getInputKey())) {
                runAnalyze = false;
                continue;
            }

            TextStatistics textStatistics = analyzeText(options);

            processResult(textStatistics);
        }
    }

    private static void processResult(final TextStatistics textStatistics) {
        AnalysisOutput.textPrinter(textStatistics);
    }

    private static TextStatistics analyzeText(Options options) {

        TextAnalyzer textAnalyzer = new TextAnalyzer();

        TextStatistics textStatistics = null;

        InputKeys inputKey = options.getInputKey();
        String inputArgument = options.getInputArgument();

        switch (inputKey) {
            case chr:
                textStatistics = textAnalyzer.getCharTextStatistics(inputArgument);
                break;
            case word:
                textStatistics = textAnalyzer.getWordTextStatistics(inputArgument);
                break;
            case deflt: default:
                textStatistics = textAnalyzer.getTextStatistics(inputArgument);
                break;
        }
        return textStatistics;
    }

    private static Options parseParams(String inputStream) {
        return new CommandLineParser().parse(inputStream);
    }

    private static String getInputText() {
        System.out.printf("TextAnalyzer_>");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
