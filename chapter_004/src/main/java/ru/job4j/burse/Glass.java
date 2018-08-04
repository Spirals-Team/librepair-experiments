package ru.job4j.burse;

import ru.job4j.burse.comparator.Ascending;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Glass {
     private Set<Form> askForms = new TreeSet<>(new Ascending());
     private Set<Form> bidForms = new TreeSet<>();

    public Glass(Form form) {
        add(form);
    }

    public void displayGlass() {
        System.out.println("Продажа\tЦена\tПокупка");
        for (Form item : new TreeSet<>(askForms)) {
            System.out.println(String.format(Locale.US, "\t\t%.2f\t%d", item.getPrice(), item.getVolume()));
        }
        for (Form item : bidForms) {
            System.out.println(String.format(Locale.US, "%d\t\t%.2f", item.getVolume(), item.getPrice()));
        }
    }

    public boolean isEmpty() {
        return askForms.isEmpty() && bidForms.isEmpty();
    }

     void add(Form form) {
        if (form.getAction().equals(Action.ask)) {
            choiceSet(form, askForms);
        }
        if (form.getAction().equals(Action.bid)) {
            choiceSet(form, bidForms);
        }
    }

    private void choiceSet(Form form, Set<Form> set) {
        Type type = form.getType();
        if (type.equals(Type.add)) {
            addInSet(form, set);
        }
        if (type.equals(Type.delete)) {
            delete(form, set);
        }
    }

    private void addInSet(Form form, Set<Form> set) {
        unionFormWithSamePrice(form, set);
        execute(form);
    }

    private void delete(Form form, Set<Form> set) {
        Form delete = new Form(form, Type.add);
        for (Form item : set) {
            if (item.equals(delete)) {
                set.remove(delete);
                break;
            }
        }
    }

    private void unionFormWithSamePrice(Form form, Set<Form> set) {
        Form current = null;
        for (Form item : set) {
            if (item.getPrice() == form.getPrice()) {
                current = item;
                break;
            }
        }
        if (current != null) {
            current.changeVolume(form.getVolume());
        }
    }

    private void execute(Form form) {
        List<Form> list = new ArrayList<>();
        boolean choice = form.getAction().equals(Action.bid);
        Set<Form> setFind = choice ? askForms : bidForms;
        Set<Form> setCur = choice ? bidForms : askForms;
        Iterator<Form> it = setFind.iterator();
        double formPrice = form.getPrice();
        Form current = it.hasNext() ? it.next() : null;
        while (it.hasNext() && current != null && condition(current, formPrice)) {
            int formVolume = form.getVolume();
            int currentVolume = current.getVolume();
            if (currentVolume == formVolume) {
                setFind.remove(current);
                form.changeVolume(-formVolume);
                break;
            }
            if (currentVolume > formVolume) {
                current.changeVolume(-formVolume);
                form.changeVolume(-formVolume);
                break;
            }
            if (currentVolume < formVolume) {
                list.add(current);
                form.changeVolume(-currentVolume);
            }
            current = it.next();
        }
        for (Form aList : list) {
            setFind.remove(aList);
        }
        if (form.getVolume() != 0) {
            setCur.add(form);
        }
    }

    private boolean condition(Form form, double price) {
        boolean result = false;
        double formPrice = form.getPrice();
        boolean bid = form.getAction().equals(Action.bid);
        boolean ask = form.getAction().equals(Action.ask);
        if ((ask & formPrice <= price) | (bid & formPrice >= price)) {
            result = true;
        }
        return result;
    }
}
