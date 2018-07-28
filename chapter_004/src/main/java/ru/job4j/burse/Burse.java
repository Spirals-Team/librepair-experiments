package ru.job4j.burse;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Burse {
    private Map<String, Glass> glasses = new HashMap<>(); //хранилище стаканов
    private Map<String, List<String>> history = new HashMap<>(); //для сохранения истории заявок эммитента, в порядке их поступления

    /**
     * Добавление заявки на биржу
     * @param form добавляемая заявка
     */
    public void add(Form form) {
        addGlass(form);
        addHistory(form);
        clear();
    }

    /**
     * Вспомогательный класс для добавления заявки в стакан
     * эммитента.
     * @param form добавляемая заявка
     */
    private void addGlass(Form form) {
        Glass cur = glasses.putIfAbsent(form.getBook(), new Glass(form));
        if (cur != null) {
            cur.add(form);
        }
    }

    /**
     * Вспомогательный класс для добавления заявки в историю заявок
     * данноко эммитента.
     * @param form добавляемая заявка
     */
    private void addHistory(Form form) {
        String name = form.getBook();
        String cons = form.toString();
        List<String> list = history.get(name);
        if (list == null) {
            list = new ArrayList<>();
            list.add(cons);
            history.put(name, list);
        } else {
            list.add(cons);
        }
    }

    /**
     * Выводит список эммитентов присутствующих на бирже
     */
    public void displayEmmiters() {
        for (String items : getEmmiters()) {
            System.out.println(items);
        }
    }

    /**
     * Выводит бирживой стакан выбранного эмитента
     * @param emmiters эммитент для которого будет отображаться стакан
     */
    public void displayGlass(String emmiters) {
        if (glasses.get(emmiters) != null) {
            glasses.get(emmiters).displayGlass();
        } else {
            System.out.println("На бирже нет заявок данного эммитента");
        }
    }

    /**
     * Выводит список эммитентов присутствующих в данный момента
     * или присутствующих раньше на бирже
     */
    public void displayHistoryEmmiters() {
        for (String items : getHistoryEmmiters()) {
            System.out.println(items);
        }
    }

    /**
     * Отобразить историю заявок для данного эммитента
     * @param emmiters эммитент для которого будет отображаться история заявок
     */
    public void displayHistoryForm(String emmiters) {
        for (String item : history.get(emmiters)) {
            System.out.println(item);
        }
    }

    /**
     * Возвращает эммитентов которые имеют актуальные заявки на бирже
     * @return массив эммитентов
     */
    private String[] getEmmiters() {
        Set<String> setEmmiters = glasses.keySet();
        String[] emmiters = new String[setEmmiters.size()];
        setEmmiters.toArray(emmiters);
        return emmiters;
    }

    /**
     * Возвращает эммитентов которые имеют/имели заявки на бирже
     * @return массив эммитентов
     */
    private String[] getHistoryEmmiters() {
        Set<String> setEmmiters = history.keySet();
        String[] emmiters = new String[setEmmiters.size()];
        setEmmiters.toArray(emmiters);
        return emmiters;
    }

    /**
     * Удаление стакана эммитента у которого нет заявок
     */
    private void clear() {
        String key = null;
        for (Map.Entry<String, Glass> item : glasses.entrySet()) {
            if (item.getValue().isEmpty()) {
                key = item.getKey();
                break;
            }
        }
        if (key != null) {
            glasses.remove(key);
        }
    }
}
