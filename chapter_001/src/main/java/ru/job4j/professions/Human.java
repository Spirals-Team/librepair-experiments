package ru.job4j.professions;
/*
    Супер класс для родительских классов Профессии и Субъекты
    Наследование от этого класса субьекта Проект немного натяжка абстракции))
 */
public class Human {

    private String name; //имя
    private int age; //возраст
    private boolean isMan; //является ли мужчиной

    // сет/геты для параметров класса
    public String getName(){
        return this.name;
    }
    public void setName(String nname){
        this.name=nname;
    }
    public int getAge(){
        return this.age;
    }
    public void setAge(int aage){
        this.age=aage;
    }
    public boolean getIsMan(){
        return this.isMan;
    }
    public void setIsMan(boolean iIsMan){
        this.isMan=iIsMan;
    }

}
