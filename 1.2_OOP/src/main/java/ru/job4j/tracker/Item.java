package ru.job4j.tracker;
/**
*Класс Item.
*@author ifedorenko
*@since 24.08.2017
*@version 1
*/
public class Item {
	/**
	*@param хранит id
	*/
	private String id;
	/**
	*@param хранит имя
	*/
	private String name;
	/**
	*@param хранит описание
	*/
	private String description;
	/**
	*@param хранит дату создания
	*/
	private long create;
	/**
	*@param comments комментарии
	*/
	private String[] comments;
	/**
	*Конструктор по-умолчанию.
	*/
	public Item() {
	}
	/**
	*Конструктор.
	*@param name Задаем имя при создании объекта
	*@param description Задаем описание при создании объекта
	*@param create Задаем дату создания объекта
	*/
	public Item(String name, String description, long create) {
			this.name = name;
			this.description = description;
			this.create = create;
	}
	/**
	*@return имя
	*/
	public String getName() {
		return this.name;
	}
	/**
	*@return описание
	*/
	public String getDescription() {
		return this.description;
	}
	/**
	*@return дату создания
	*/
	public long getCreate() {
		return this.create;
	}
	/**
	*@return Id
	*/
	public String getId() {
		return this.id;
	}
	/**
	*@param id Задаем id
	*/
	public void setId(String id) {
		this.id = id;
	}
}
