package ru.job4j.tracker;

import ru.job4j.sql.SQLConnect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    private boolean problem = false;
    private Setup setup;
    private final SQLConnect connect;
    private final SQLRequestTrecker request;

    public Tracker() {
        setup = new Setup(this, "chapter_006/tracker.ini");
        connect = new SQLConnect(setup.getUrl(), setup.getLogin(), setup.getPassword(), false);
        request = new SQLRequestTrecker(connect.getConnection());
        if (setup.getExecuteSQLList().size() > 0) {
            request.executeSQL(setup.getExecuteSQLList());
        }
    }

    public boolean isProblem() {
        return problem;
    }

    public void setProblem(boolean problem) {
        this.problem = problem;
    }

    /**
     * Метод добавляет заявку, переданную в аргументах в массив заявок this.items.
     * @param item - заявка.
     */
    public Item add(Item item) {
        request.addItem(item);
        return item;
    }

    public void addCom(String id, String text) {
        request.addComment(id, text);
    }

    /**
     * Метод заменяет заявку в массиве this.items.
     * @param id - заменяемой заявки.
     * @param item - заявка на которую заменяем.
     */
    public void replace(String id, Item item) {
        request.replaceItem(id, item);
    }

    /**
     *  Метод удалит заявку в массиве this.items.
     * @param id - удаляемой заявки.
     */
    public void delete(String id) {
        request.removeItem(id);
    }

    /**
     * Метод возвращает списко всех заявок;
     * @return - список заявок.
     */
    public List<Item> findAll() {
        return request.getListItems(null, null);
    }

    /**
     *  Метод возвращает все заявки по ключевому параметру в имени.
     * @param name - параметр по которому ищем.
     * @return - список заявок содержащих ключ в имени.
     */
    public List<Item> findByName(String name) {
        return request.getListItems("NAME", name);
    }

    /**
     * Метод ищет заявку по ID.
     * @param id - ключ по которому ищем заявку.
     * @return - заявку в которой ID соответсвует ключу.
     */
    public List<Item> findById(String id) {
        return request.getListItems("ID", id);
    }

    public void exit() {
        connect.close();
    }
}
