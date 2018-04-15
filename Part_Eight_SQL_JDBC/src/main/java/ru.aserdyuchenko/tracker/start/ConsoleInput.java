package ru.aserdyuchenko.tracker.start;

import ru.aserdyuchenko.tracker.models.Item;
import ru.aserdyuchenko.tracker.models.Task;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleInput implements Input{
	Tracker tracker = new Tracker();
	private Scanner scanner = new Scanner (System.in);
	String name;
	String description;

	public String askMenuChoice(String menuQuestion){
		System.out.print(menuQuestion);
		return scanner.nextLine();
	}

	public String askNameForNewItem(String nameForNewItem){
		System.out.print(nameForNewItem);
		return scanner.nextLine();
	}

	public String askDescForNewItem(String descForNewItem){
		System.out.print(descForNewItem);
		return scanner.nextLine();
	}

	public String askItemId(String itemId){
		System.out.print(itemId);
		return scanner.nextLine();
	}

	public void addNewItem(){
		System.out.println("Your choice is: Adding a new application.");
		String name = new ConsoleInput().askNameForNewItem("Enter name for new item: ");
		String description = new ConsoleInput().askDescForNewItem("Enter desc for new item: ");
		tracker.add(new Task(name, description));
		System.out.println("Add new item.");
	}

	public void editItem(){
		System.out.println("Your choice is: Edit applications.");
		String itemId = new ConsoleInput().askItemId("Enter itemId for editing item: ");
		String name = new ConsoleInput().askNameForNewItem("Enter new name for item: ");
		String desc = new ConsoleInput().askDescForNewItem("Enter new desc for item: ");
		tracker.updateItem(itemId, new Task(name, desc));
		System.out.println("Item is edit.");
	}

	public void removeItem(){
		System.out.println("Your choice is: Removing the application.");
		String itemId = new ConsoleInput().askItemId("Enter itemId for removing item: ");
		tracker.delete(itemId);
		System.out.println("Item is removing.");
	}

	public void displaysAListOfAllItem() throws SQLException {
		System.out.println("Your choice is: Displays a list of all applications.");
			for (Item item : tracker.getAll()){
            	if (item != null){
               		System.out.println("Item Id: " + item.getId());
					System.out.println("Name: " + item.getName());
					System.out.println("Desc: " + item.getDescription());
				/**
		 		 * if this item already remove
		 		 */
            	} else {
                	System.out.println("Now, this item is null");
            	}
			}
		System.out.println("Displays a list of all applications.");
	}

	public String menuChoice(String choice) throws SQLException {
		if (choice == "0") {
		/**
		 * Add new item
		 */
			addNewItem();
		} else if (choice == "1") {
		/**
		 * Edit item
		 */
			editItem();
		} else if (choice == "2") {
		/**
		 * Remove item
		 */
			removeItem();
		} else if (choice == "3") {
		/**
		 * Displays a list of all item
		 */
			displaysAListOfAllItem();
		} else if (choice == "4") {
		/**
		 * Exite
		 */
			System.out.println("Your choice is: Exite.");
		} else {
		/**
		 * if you console number dont 1-4
		 */
			System.out.println("Your choice is incorrect, please enter number from 1 to 4!");
		}
	return choice;
	}

	public int askMenuChoice(String question, int[] range) {
		int key = Integer.valueOf(this.askMenuChoice(question));
		boolean	exist = false;
		for (int value : range){
			if (value == key) {
				exist = true;
				break;
			}
		}
		if (exist) {
			return key;
		} else {
			throw new MenuOutException("Out of menu range.");
		}
	}
}
