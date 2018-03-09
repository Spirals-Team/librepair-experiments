package ru.aserdyuchenko.tracker.start;

public class ValidateInput extends ConsoleInput{
	public int askMenuChoice(String question, int[] range){
		boolean invalid = true;
		int value = -1;
		do {
			try {
				value = super.askMenuChoice(question, range);
				invalid = false;
			} catch(MenuOutException moe) {
				System.out.println("Please select key from menu again.");
			} catch(NumberFormatException nfe) {
				System.out.println("Please enter validate date again.");
			}
		} while (invalid);
		return value;
	}
}
