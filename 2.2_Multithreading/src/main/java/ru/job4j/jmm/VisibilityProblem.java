package ru.job4j.jmm;

/**
 * Class VisibilityProblem
 */
public class VisibilityProblem {
    /**
     * @param flag current condition
     */
    private boolean flag = true;

    /**
     * Getter for flag.
     * @return flag
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Setter for flag.
     * @param flag change flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Method main
     * @param args args
     */
    public static void main(String[] args) {
        VisibilityProblem vp = new VisibilityProblem();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    count++;
                    if (!vp.getFlag()) {
                        System.out.println("Count changes: " + count);
                        break;
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                vp.setFlag(false);
            }
        });

        thread1.start();
        thread2.start();
    }
}
