package ru.job4j.tracker;
/**
 * ValidateInput.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ValidateInput extends ConsoleInput {
    /**
     * Method 'ask' with exception handling.
     * @param question - question
     * @param range - range
     * @return key
     */
    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int key = -1;
        do {
            try {
                key = super.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Select key from menu!");
            } catch (NumberFormatException nfe) {
                System.out.println("Enter validate data again!");
            }
        } while (invalid);
        return key;
    }
}
