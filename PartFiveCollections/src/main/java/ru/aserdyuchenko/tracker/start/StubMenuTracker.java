package ru.aserdyuchenko.tracker.start;

import ru.aserdyuchenko.tracker.models.*;
import ru.aserdyuchenko.tracker.start.Input;
import ru.aserdyuchenko.tracker.start.Tracker;

class StubEditItem implements UserAction {

	public int key() {
		return 2;
	}

	public void execute (Input input, Tracker tracker) {
		String findName = input.askNameForNewItem("Enter the taks`s name: ");
		String id = tracker.findIdByName(findName);
		String name = input.askNameForNewItem("Enter the taks`s name: ");
		String desc = input.askDescForNewItem("Enter the taks`s desc: ");
		Task task = new Task(name, desc);
		task.setId(id);
		tracker.updateItem(id, task);
	}

	public String info() {
		return String.format("%s. %s", this.key(), "Edit item.");
	}
}

class StubRemoveItem implements UserAction {

	public int key() {
		return 3;
	}

	public void execute (Input input, Tracker tracker) {
		String findName = input.askNameForNewItem("Enter the taks`s name: ");
		String id = tracker.findIdByName(findName);
		tracker.delete(id);
	}

	public String info() {
		return String.format("%s. %s", this.key(), "Remove item.");
	}
}

public class StubMenuTracker implements Input{
	private Input input;
	private Tracker tracker;
	private UserAction[] actions = new UserAction[5];
	private String[] answers;
	private int position = 0;
/*
	public void arrayForAnswers(String[] answers) {
		this.answers = answers;
	}
*/
	public StubMenuTracker(String[] answers){
		this.answers = answers;
	}

	public String nextAnswers(){
		return answers[position++];
	}

	public void fillActions() {
		this.actions[0] = new StubAddItem();
		this.actions[1] = new StubMenuTracker.StubShowItems();
		this.actions[2] = new StubEditItem();
		this.actions[3] = new StubRemoveItem();
	}

	public void select(int key) {
		this.actions[key].execute(this.input, this.tracker);
	}

	public void displaysAListOfAllItem(){}
	public void addNewItem(){}
	public void editItem(){}
	public void removeItem(){}
	public String askMenuChoice(String menuQuestion){
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
	public String menuChoice(String choice){
		return answers[position++];
	}

	public void show() {
		for (UserAction action : this.actions) {
			if (action !=null) {
				System.out.println(action.info());
			}
		}
	}

	private class StubAddItem implements UserAction {
		public int key() {
			return 0;
		}

		public void execute(Input input, Tracker tracker) {
			String name = answers[position++];
			String desc = answers[position++];
			tracker.add(new Task(name, desc));
		}

		public String info() {
			return String.format("%s. %s", this.key(), "Add the new item.");
		}
	}

	private static class StubShowItems implements UserAction {
		public int key() {
			return 1;
		}

		public void execute(Input input, Tracker tracker) {
			for (Item item : tracker.getAll()) {
				if (item != null) {
					System.out.println(
						String.format("%s. %s", item.getId(), item.getName())
					);
				}
			}
		}

		public String info() {
			return String.format("%s. %s", this.key(), "Show all items.");
		}
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
