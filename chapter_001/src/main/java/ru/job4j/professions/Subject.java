package ru.job4j.professions;
/*
    Родительский класс для Студента, Пациента и Проекта
 */

public class Subject extends Human{
    private Profession.Special spec; // специализация субъекта. только специалист с такой же специализацией может взаимодействовать на субъект
    private Profession.Status status;// уровень освоения специализации субъектом. меняеться только специалистом
    private String who; // для красоты сообщений. кем является субъект.

    // сет/геты для параметров класса
    public String getWho(){
        return this.who;
    }
    public void setWho(String wwho){
        this.who=wwho;
    }
    public Profession.Special getSpec(){
        return this.spec;
    }
    public void setSpec(Profession.Special sspec){
        this.spec=sspec;
    }
    public Profession.Status getStatus(){
        return this.status;
    }
    public void setStatus(Profession.Status sstatus){
        this.status=sstatus;
    }
}
