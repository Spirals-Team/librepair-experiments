package ru.job4j.threads;

/**
 * Class SpaceCount.
 */
public class SpaceCount extends WordCheck {
    /**
     * Constructor.
     *
     * @param word word for check
     */
    public SpaceCount(String word) {
        super(word);
    }

    /**
     * Method run.
     */
    @Override
    public void run() {
        int countS = 0;
        for (int i = 0; i < this.str.length; i++) {
            if (this.str[i] == ' ') {
                countS++;
            }
        }
        System.out.println("Spaces: " + countS);
    }
}


