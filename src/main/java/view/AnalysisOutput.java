package view;

import analyzeModel.TextStatistics;

public class AnalysisOutput {
    /**
     * Метод выводит статистику анализа на экран.
     * На вход принимает статистику анализа (analyzeModel.TextStatistics)
     *
     * @param textStatistics - итоговый анализ текста.
     */
    public static void textPrinter(final TextStatistics textStatistics) {
        System.out.println(textStatistics.toString());
    }
}
