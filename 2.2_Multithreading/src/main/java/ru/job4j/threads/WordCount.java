package ru.job4j.threads;
/**
 * Class WordCount.
 */
public class WordCount extends WordCheck {

    /**
     * Constructor.
     * @param word word for check
     */
    public WordCount(String word) {
        super(word);
    }

    /**
     * Method run.
     */
    @Override
    public void run() {
        int countW = 0;
        int count = 0;
        int index = 0;
        int lenght = this.str.length;
        while (!Thread.currentThread().isInterrupted()) {
            if (this.str[index] != ' ') {
                count++;
                if (index == this.str.length - 1) {
                    countW++;
                }
            } else if (this.str[index] == ' ' && count > 0) {
                countW++;
                count = 0;
            }
            if (index == lenght - 1) {
                System.out.println("Words: " + countW);
                break;
            }
            index++;
        }
    }
}
