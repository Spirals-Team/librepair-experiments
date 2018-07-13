package ru.job4j.professions;
/*
    Класс Доктор подкласс класса Профессия, характеризуется специализацией "врач" и действием "лечит"
 */
public class Doctor extends Profession {

    // конструктор для создания объекта Доктор с "визуализацией"
    public void Doctor(String name,int age,boolean isMan,Special spec,Qualification qualif){
        this.setName(name);
        this.setSpec(spec);
        this.setAge(age);
        this.setIsMan(isMan);
        this.setQualif(qualif);
        this.setWork("лечит");
        this.setObSpec("врач");
        System.out.println(this.getObSpec()+" "+this.getName()+" введен в базу");
        System.out.println("Возраст: "+this.getAge()+" Пол: "+(getIsMan()?"муж":"жен"));
        System.out.println("Специалзация: "+this.getSpec()+" Квалификация: "+this.getQualif());
    }
}
