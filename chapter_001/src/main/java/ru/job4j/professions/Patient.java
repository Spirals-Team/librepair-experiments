package ru.job4j.professions;
/*
    Класс Пациент подкласс класса Субъект, характеризуется признаком "больного"
 */

public class Patient extends Subject {
    // конструктор для создания объекта Пациент с "визуализацией"

    public void Patient(String name, int age, boolean isMan, Profession.Special spec, Profession.Status status){
        this.setName(name);
        this.setIsMan(isMan);
        this.setAge(age);
        this.setSpec(spec);
        this.setStatus(status);
        this.setWho("больного");
        System.out.println("Приветствуем "+this.getWho()+" "+this.getName()+" в нашей базе");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специализация: "+this.getSpec()+" Состояние по специализации: "+this.getStatus());
    }

}
