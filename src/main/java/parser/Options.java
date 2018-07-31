package parser;

public class Options {

    /**
     * Варианты ключей входящего потока
     * inputKey -  ключ, указывающий как парсить
     * inputArgument - ключ, указывающий что парсить
     * далее можно добавить еще ключи:
     * outputKey - ключ, указывающий что сделать с результатом анализа
     * OutputArgument - ключ, указывающий например в какой файл записать (имя файла)
     */
    private InputKeys inputKey;
    private String inputArgument;

    /**
     * устанавливает inputKey -  ключ, указывающий как парсить
     * @param inputKey
     */
    public void setInputKey(final InputKeys inputKey) {
        this.inputKey = inputKey;
    }

    /**
     * получает inputKey -  ключ, указывающий как парсить
     * @return
     */
    public InputKeys getInputKey() {
        return this.inputKey;
    }

    /**
     * устанавливает inputArgument - ключ, указывающий что парсить
     * @param inputArgument
     */
    public void setInputArgument(final String inputArgument) {
        this.inputArgument = inputArgument;
    }

    /**
     * получает inputArgument - ключ, указывающий что парсить
     * @return
     */
    public String getInputArgument() {
        return this.inputArgument;
    }
}
