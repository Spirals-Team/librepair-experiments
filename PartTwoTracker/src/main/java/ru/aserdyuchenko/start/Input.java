package ru.aserdyuchenko.start;

public interface Input {
	String askMenuChoice(String menuQuestion);
	String askNameForNewItem(String nameForNewItem);
	String askDescForNewItem(String descForNewItem);
	String askItemId(String itemId);
	String menuChoice(String choice);
	void addNewItem();
	void editItem();
	void removeItem();
	void displaysAListOfAllItem();
	int askMenuChoice(String question, int[] range);
}
