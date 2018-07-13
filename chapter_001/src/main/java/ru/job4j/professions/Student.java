package ru.job4j.professions;
/*
    Класс Студент подкласс класса Субъект, характеризуется признаком "студента"
 */

public class Student extends Subject {
    // конструктор для создания объекта Студент с "визуализацией"

    public void Student(String name, int age, boolean isMan, Profession.Special spec, Profession.Status status){
        this.setName(name);
        this.setIsMan(isMan);
        this.setAge(age);
        this.setSpec(spec);
        this.setStatus(status);
        this.setWho("студента");
        System.out.println("Приветствуем "+this.getWho()+" "+this.getName()+" в нашей базе");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специализация: "+this.getSpec()+" Состояние по специализации: "+this.getStatus());
    }
}
