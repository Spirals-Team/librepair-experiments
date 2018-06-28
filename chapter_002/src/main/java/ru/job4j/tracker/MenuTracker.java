package ru.job4j.tracker;

import ru.job4j.models.*;
import java.util.ArrayList;



public class MenuTracker {
	private Input input;
	private Tracker tracker;
	private ArrayList<UserAction> actions = new ArrayList<>();
	
	public MenuTracker(Input input, Tracker tracker) {
		this.input = input;
		this.tracker = tracker;
	}
	
	public void fillActions() {
		actions.add(new AddItem(0, "Add new Item"));
		actions.add(new MenuTracker.ShowItems(1, "Show all items"));
		actions.add(new EditItem(2, "Edit item"));
		actions.add(new DeleteItem(3, "Delete item"));
		actions.add(new FindById(4, "Find item by Id"));
		actions.add(new FindByName(5, "Find items by name"));
		actions.add(new ExitProgram(6, "Exit Program"));
		
	}
	
	public void select(int key) {
		actions.get(key).execute(this.input, this.tracker);
	}
	
	public void show() {
		for (UserAction action : actions) {
			if (action != null) {
				System.out.println(action.info());
			}	
		}
	}
	
	public class AddItem extends BaseAction {
		public AddItem(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ Add new item --------------");
			String name = input.ask("Enter item name :");
			String desc = input.ask("Enter item description :");
			Item item = new Item(name, desc);
			tracker.add(item);
			System.out.println("------------ New item with getId : " + item.getId() + "-----------");
		}
	}
	
	public class EditItem extends BaseAction {
		public EditItem(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ Edit item --------------");
			String id = input.ask("Enter item id  :");
			String name = input.ask("Enter item name :");
			String desc = input.ask("Enter item description :");
			Item item = new Item(name, desc);
			tracker.replace(id, item);
		}
	}
	
	public class DeleteItem extends BaseAction {
		public DeleteItem(int key, String name) {
			super(key, name);
		}	
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ Delete item --------------");
			String id = input.ask("Enter item id :");
			tracker.delete(id);
		}
	}
	
	public class FindById extends BaseAction {
		public FindById(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ Search item by id --------------");
			String id = input.ask("Enter item id :");
			System.out.println(
				String.format("%s, %s", tracker.findById(id).getId(), tracker.findById(id).getName()));
		}
	}
	
	public class FindByName extends BaseAction {
		public FindByName(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ Search item by name --------------");
			String name = input.ask("Enter item name :");
			System.out.println(
				String.format("%s, %s", tracker.findByName(name).getId(), tracker.findByName(name).getName()));
		}
	}
	
	public class ExitProgram extends BaseAction {
		public ExitProgram(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {

		}
	}
	
	private static class ShowItems extends BaseAction {
		public ShowItems(int key, String name) {
			super(key, name);
		}
		
		@Override
		public void execute(Input input, Tracker tracker) {
			System.out.println("------------ All items --------------");
			for (Item item : tracker.findAll()) {
				System.out.println(String.format("%s, %s", item.getId(), item.getName()));
			}
		}
	}
}