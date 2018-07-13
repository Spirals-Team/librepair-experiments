package ru.job4j.professions;
/*
    Класс Проект подкласс класса Субъект, характеризуется признаком "проект"
 */

public class Project extends Subject {
    // конструктор для создания объекта Проект с "визуализацией"

    public void Project(String name, int age, boolean isMan, Profession.Special spec, Profession.Status status){
        this.setName(name);
        this.setIsMan(isMan);
        this.setAge(age);
        this.setSpec(spec);
        this.setStatus(status);
        this.setWho("проект");
        System.out.println("Приветствуем "+this.getWho()+" "+this.getName()+" в нашей базе");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специализация: "+this.getSpec()+" Состояние по специализации: "+this.getStatus());
    }

}
