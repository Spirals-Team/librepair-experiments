package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

class ShowItems extends BaseAction {
    ShowItems(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("-----------Проссмотр всех заявок-----------");
        if (tracker.getAll().size() == 0) {
            System.out.println("-----------У Вас нет заявок :" + "-----------");
        } else if (tracker.getAll().size() != 0) {
            for (Item item : tracker.getAll()) {
                System.out.println("-----------Заявка с идентификатором:" + item.getId() + "-----------");
                System.out.println("Имя заявки :" + item.getName());
                System.out.println("Описание заявки :" + item.getDescription());
            }
        }
    }

}

public class MenuTracker {
    private Input input;
    private Tracker tracker;
    private  int position = 0;
    private List<UserAction> actions = new ArrayList<>();

    MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int fillActions() {
        int count = 0;
        this.actions.add(count, new Exit(count++, "Выход."));
        this.actions.add(count, new Add(count++, "Добавить новую заявку"));
        this.actions.add(count, new Edit(count++, "Редактировать заявку."));
        this.actions.add(count, new ShowItems(count++, "Показать все заявки"));
        this.actions.add(count, new Delete(count++, "Удалить заявку."));
        this.actions.add(count, new FindBuID(count++, "Найти заявку по идентификатору."));
        this.actions.add(count, new FindByName(count++, "Найти заявку по имени."));
        return count;
    }


    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }
    private class Exit extends BaseAction {
        Exit(int key, String name) {
            super(key, name);
        }
        public void execute(Input input, Tracker tracker) {
        }
    }
    private class Add extends BaseAction {

        Add(int key, String name) {
            super(key, name);
        }
        public void execute(Input input, Tracker tracker) {
            System.out.println("-----------Добавление новой заявки-----------");
            String name = input.ask("Введите имя заявки:");
            String desc = input.ask("Введите описание заявки:");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("-----------Новая заявка с getID :" + item.getId() + "-----------");
        }

    }
    private static class Edit extends BaseAction {
        Edit(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Tracker tracker) {
            Item previous = new Item();
            previous.setId(input.ask("Введите идентификатор заявки."));
            previous.setName(input.ask("Введите имя заявки."));
            previous.setDescription(input.ask("Введите описание заявки."));
            tracker.replace(previous);
        }
    }

    private class Delete extends BaseAction {
        Delete(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Tracker tracker) {
            System.out.println("-----------Удаление заявки-----------");
            String id = input.ask("Введите идентификатор заявки:");
            for (Item item : tracker.getAll()) {
                if (item.getId().equals(id) && item.getId() != null) {
                    tracker.delete(id);
                    System.out.println("Заявка с идентификатором :" + item.getId() + " удалена.");
                } else {
                    System.out.println("Заявка с идентификатором :" + id + " не найдена.");
                }
            }
        }

        }
    private class FindBuID extends BaseAction {
        FindBuID(int key, String name) {
            super(key, name);
        }
        public void execute(Input input, Tracker tracker) {
            System.out.println("-----------Поиск заявки по идентификатору-----------");
            String id = input.ask("Введите идентификтор заявки.");
            for (Item item : tracker.getAll()) {
                if (item.getId().equals(id) && item.getId() != null) {
                    tracker.findById(id);
                    System.out.println("Заявка с идентификатором :" + id + " найдена :");
                    System.out.println("Имя заявки :" + item.getName());
                    System.out.println("Описание заявки :" + item.getDescription());
                } else if (!(item.getId() == null && item.getId().equals(id))) {
                    System.out.println("Заявка с идентификатором :" + id + " не найдена.");
                }
            }
        }
    }
    private class FindByName extends BaseAction {
        FindByName(int key, String name) {
            super(key, name);
        }
        public void execute(Input input, Tracker tracker) {
            System.out.println("-----------Поиск заявки по имени.-----------");
            String name = input.ask("Введите имя заявки");
            for (Item item : tracker.getAll()) {
                tracker.findByName(name);
                if (item != null && item.getName().equals(name)) {
                    System.out.println("Заявка с именем :" + item.getName() + " найдена :");

                } else {
                    System.out.println("Заявок с именем :" + name + " не обноружено.");
                }
            }
        }
    }
}