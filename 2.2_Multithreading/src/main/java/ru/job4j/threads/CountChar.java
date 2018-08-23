package ru.job4j.threads;


public class CountChar extends WordCheck {

    /**
     * Constructor.
     *
     * @param word word for check
     */
    public CountChar(String word) {
        super(word);
    }

    /**
     * Method run.
     */
    @Override
    public void run() {
        int countS = 0;
            for (int i = 0; i < this.str.length; i++) {
                if (!Thread.currentThread().isInterrupted()) {
                    if (this.str[i] != ' ') {
                        countS++;
                    }
                } else {
                    System.out.println("Выполнение программы остановлено!");
                    break;
                }
            }
            System.out.println("Chars: " + countS);
    }
}
