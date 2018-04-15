package ru.aserdyuchenko.tracker.start;

import java.sql.SQLException;

public interface Input {
	String askMenuChoice(String menuQuestion);
	String askNameForNewItem(String nameForNewItem);
	String askDescForNewItem(String descForNewItem);
	String askItemId(String itemId);
	String menuChoice(String choice) throws SQLException;
	void addNewItem();
	void editItem();
	void removeItem();
	void displaysAListOfAllItem() throws SQLException;
	int askMenuChoice(String question, int[] range);
}
