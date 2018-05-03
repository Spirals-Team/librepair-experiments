package ru.job4j.tracker.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * я так и не понял заким использовался интерфйс в данном случае... ,
 * к примеру мы могли сделать метод  в этом классе, который бы возвращал сканнер некст лайн.
 * и было бы тоже самое
 */
public class Tracker {
    private ArrayList<Items> items = new ArrayList<>();
    private int index = 0;
    private Random rn = new Random();
    private boolean exitProgramm = true; //пока параметр переменной будет равен шести, программу будет продолжнать работать

    /**
     * планирую сделать так: привыборе в меню цифры 6, у нас вызовется бьект класса
     * Exitprogramm , и его метод  public void execute(Input input, Tracker tracker)
     * этот метод вызовет нашь метод setExitProgramm. который в свою очередь изменит параметр
     * переменно exitProgramm и программа завершит свою работу
     */
    public void setExitPrograpp() {
        this.exitProgramm = false;
    }

    public boolean getExitProgramm() {
        return exitProgramm;
    }

    /**
     * добавление заявок - и мы просто делаем один шаг по элемену++
     *
     * @param item
     * @return
     */
    public Items add(Items item) {
        item.setId(this.generate());
        this.items.add(item);
        return item;
    }

    /**
     * редактирование заявок здесь у нас водит id  заявки и новые данные заявки
     * в методе id  присваивается объекту итем и после мы шагаем по массиву и ищем заявку с таким же id а когда накодим присваиваем этой заявке
     * новые знавения с входящей заявке при этом id  останется преждним
     *
     * @param id
     * @param items
     */
    public void replace(String id, Items items) {
        items.setId(id);
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(items)) {
                this.items.set(i, items);
                break;
            }
        }
    }

    /**
     * удаление заявок  ну здесь я придумал так - иду по массиву заявок нахоу заявку с нужным id
     * и присваиваю ей значеие null
     *
     * @param id
     */
    public void delete(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().contains(id)) {
                this.items.remove(i);
                break;
            }
        }
    }

    /**
     * получение списка всех заявок - этот метод нам просто возвращает массив заявок
     *
     * @return
     */
    public ArrayList<Items> findAll() {
        return items;
    }

    /**
     * получение списка по имени  - в этом методе я создал новый массив и шёл по старому массиву,
     * находил все заявки с похожим именем, для удобства поиска я спецально сделал чтобы мы могли искать совпадения
     * например если мы ищемвсе заявки в имени которых есть слово "хелп" - он их вернёт
     *
     * @param key
     * @return
     */
    public ArrayList<Items> findByName(String key) {
        ArrayList<Items> res = new ArrayList<>();
        for (Items i : this.items) {
            if (i.getName().contains(key)) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * получение заявки по id находим заявку с нужным id и возвращаем пользвателю, ничего заумного
     *
     * @param id
     * @return
     */
    public Items findById(String id) {
        Items res = null;
        for (Items i : this.items) {
            if (i.getId().equals(id)) {
                res = i;
            }

        }
        return res;
    }

    /**
     * генерацию id  я  перенёс в этот класс мне это показалось более логичным
     *
     * @return
     */
    private String generate() {
        String id = String.valueOf(System.currentTimeMillis() + rn.nextInt() * 100);
        return id;
    }

    /**
     * Метод добавления комментарий в заявку
     */
    public void addComment(String id, String comments) {
        Items res = findById(id);
        res.addComment(comments);
    }

}
