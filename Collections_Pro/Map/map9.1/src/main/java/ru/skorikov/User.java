package ru.skorikov;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 04.11.17
 * @ version: java_kurs_standart
 * 10. Рассказать и продемонстрировать как переопределяют метод hashCode..
 * Сделаем на примере этого класса.
 */
public class User {
    /**
     * Field name.
     */
    private String name;
    /**
     * Field children.
     */
    private int children;
    /**
     * Field birthday.
     */
    private Calendar birthday = Calendar.getInstance();

    /**
     * Create new User.
     *
     * @param name     name.
     * @param children children.
     * @param day      dayBirthday
     * @param month    mounthBirthday
     * @param year     yearBirthday.
     */
    public User(String name, int children, int day, int month, int year) {
        this.name = name;
        this.children = children;
        this.birthday.set(year, month - 1, day);

    }

    /**
     * Get User name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set User name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get User children.
     *
     * @return children.
     */
    public int getChildren() {
        return children;
    }

    /**
     * Set User children.
     *
     * @param children children.
     */
    public void setChildren(int children) {
        this.children = children;
    }

    /**
     * Get User birthday.
     *
     * @return birthday.
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * Set birthday.
     *
     * @param day   new Day
     * @param month new Mouth
     * @param year  new Year
     */
    public void setBirthday(int day, int month, int year) {
        this.birthday.set(year, month - 1, day);
    }

    /**
     * По умолчанию Java методом equals() сравнивает ссылки на адреса в памяти, по этому его
     * (как и метод hashCode()) нужно переопределять.
     *
     * Существуют следующие правила для переопределения:
     *
     * 1. Рефлексивность: Объект должен равняться себе самому.
     * 2. Симметричность: Если объект а равен объекту б, тои объект б должен быть равен а.
     * 3. Транзитивность: Если из трех объектов а, б, с а==б, б==с, то и а должен быть ==с.
     * 4. Постоянство: Сравнение двух объектов должно возвращать одно и то же, пока не изменится
     *    сравниваемая информация объектов.
     * Еще одной важной деталью является то, что тип аргумента, метода equals, должен быть Object,
     * а не классом, который сравнивается - т.к. метод должен быть переопределенным а не перегруженным.
     *
     * @param o Object.
     * @return IsEquals
     */
    @Override
    // Переопределим метод equals
    public boolean equals(Object o) {
        //Если объекты ссылаются на один и тот же адрес
        if (this == o) {
            return true;
        }
        //Проверим что объект о не равен null и они принадлежат одному классу.
        //Проверку с помощью instanceof лучше не делать, так как такая проверка будет возвращать
        // true для подклассов и будет работать правильно только в случае если класс объявлен как immutable
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //Объявим переменную типа который сравниваем и приведем к нему (Object o).
        User user = (User) o;
        //Теперь сравниваем каждый атрибут объектов начиная с численных - они проверяются быстрее с
        if (children != user.children) {
            return false;
        }

        if (birthday.getTime().getDay() != user.birthday.getTime().getDay()) {
            return false;
        }
        if (birthday.getTime().getMonth() != user.birthday.getTime().getMonth()) {
            return false;
        }
        if (birthday.getTime().getYear() != user.birthday.getTime().getYear()) {
            return false;
        }

        return name.equals(user.name);
    }

    /**
     * Метод hashCode() необходимо переопределять, если переопределен метод equals().
     * Существуют ощепринятые правила:
     * 1. Присвойте результирующей переменной (result) некоторое ненулевое простое число (например, 17)
     * 2. Если поле value имеет тип boolean, вычислите (value ? О : 1)
     * 3. Если поле value имеет тип byte, char, short или int, вычислите (int)value
     * 4. Если поле value имеет тип long, вычислите (int)(value - (value >>> 32))
     * 5. Если поле value имеет тип float, вычислите Float.floatToIntBits(value)
     * 6. Если поле value имеет тип double, вычислите Double.doubleToLongBits(value), а затем преобразуйте полученное
     * значение, как указано в п.4
     * 7. Если поле value является ссылкой на объект, вызывайте метод hashCode() этого объекта
     * 8. Если поле value является ссылкой на объект и равно null, используйте число 0 для представления его хэш-кода
     * 9. Объедините полученные в п. 2 - п. 8 значения следующим образом: 37*result + value
     * 10. Если поле является массивом, примените правило 9 для каждого элемента массива
     * 11. Проверьте, что равные объекты возвращают одинаковый hashCode
     *
     * @return hasCode
     */
    @Override
    //Переопределим согласно правилам.
    public int hashCode() {
        int result = 17;
        //Вычислим значение для поля name
        result = 37 * result + (name == null ? 0 : name.hashCode());
        //Вычислим значение поля children
        result = 37 * result + children;
        //Вычислять значение поля birthday даже через Data - "не вариант"
        //т.к. равенство дня, месяца и года не гарантирует равенство поля.
        //по этому получим числовое представление поля birthday в части дня, месяца и года
        //и будем работать с ними.
        Date date = birthday.getTime();
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        result = 37 * result + day;
        result = 37 * result + month;
        result = 37 * result + year;
        //Вернем полученное значение.
        return result;
    }
}










