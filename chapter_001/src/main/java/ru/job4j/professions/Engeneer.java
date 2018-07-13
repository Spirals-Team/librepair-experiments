package ru.job4j.professions;
/*
    Класс Инженер подкласс класса Профессия, характеризуется специализацией "инженер" и действием "делает"
 */

public class Engeneer extends Profession{

    // конструктор для создания объекта Инженер с "визуализацией"
    public void Engeneer(String name,  int age, boolean isMan,Profession.Special spec, Profession.Qualification qualif){
        this.setName(name);
        this.setSpec(spec);
        this.setAge(age);
        this.setIsMan(isMan);
        this.setQualif(qualif);
        this.setWork("делает");
        this.setObSpec("инженер");
        System.out.println(this.getObSpec()+" "+this.getName()+" введен в базу");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специалзация: "+this.getSpec()+" Квалификация: "+this.getQualif());
    }

}
