package ru.job4j.professions;
/*
    Класс Учитель подкласс класса Профессия, характеризуется специализацией "учитель" и действием "учит"
 */

public class Teacher extends Profession {

    // конструктор для создания объекта Учитель с "визуализацией"

    public void Teacher(String name,int age,boolean isMan,Special spec,Qualification qualif){
        this.setName(name);
        this.setSpec(spec);
        this.setAge(age);
        this.setIsMan(isMan);
        this.setQualif(qualif);
        this.setWork("учит");
        this.setObSpec("учитель");
        System.out.println(this.getObSpec()+" "+this.getName()+" введен в базу");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специалзация: "+this.getSpec()+" Квалификация: "+this.getQualif());
    }
}
