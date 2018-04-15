package ru.aserdyuchenko.start;

import java.util.*;
import ru.aserdyuchenko.models.Task;
import ru.aserdyuchenko.models.Item;

/**
 *This class fo testing, without console imput.
 */

public class StubInput implements Input {
	private String[] answers;
 	private int position = 0;
 	Tracker tracker = new Tracker();

 	public StubInput(String[] answers) {
		this.answers = answers;
 	}

 	public String askMenuChoice(String menuQuestion) {
		System.out.print(menuQuestion);
 		return answers[position++];
 	}

	public String askNameForNewItem(String nameForNewItem){
 		return answers[position++];
 	}

	public String askDescForNewItem(String descForNewItem){
 		return answers[position++];
 	}

	public String askItemId(String itemId){
 		return answers[position++];
 	}

	public void addNewItem(){
		String name = answers[position++];
		String description = answers[position++];
		tracker.add(new Task(name, description));
		System.out.print("Add new item.");
	}

	public void editItem(){
		String name = answers[position++];
		String description = answers[position++];
		String itemId = tracker.findIdByName(name);
		tracker.updateItem(itemId, new Task(name, description));
		System.out.println("Item is edit.");
	}

	public void removeItem(){
		String name = answers[position++];
		String itemId = tracker.findIdByName(name);
		tracker.delete(itemId);
		System.out.println("Item is remove.");
	}

	public void displaysAListOfAllItem(){
		System.out.println("\nYour choice is: Displays a list of all applications.");
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

	public String menuChoice(String choice) {
		/**
		 * Add new item
		 */
		if (choice == "1") {
		/**
		 * Add new item
		 */
			addNewItem();

		} else if (choice == "2") {
		/**
		 * Edit item
		 */
			editItem();

		} else if (choice == "3") {
		/**
		 * Remove item
		 */
			removeItem();

		} else if (choice == "4") {
		/**
		 * Displays a list of all item
		 */
			displaysAListOfAllItem();

		} else if (choice == "5") {
		/**
		 * Exite
		 */
			System.out.println("\nYour choice is: Exite.");
		/**
		 * if you console number dont 1-5
		 */
		} else {
			System.out.println("Your choice is incorrect, please enter number from 1 to 5!");
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
