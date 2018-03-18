package com.rolety.roletycrud.domain;



public class Rolety
{
    private int id;
    private String name;
    private int size;
    private int price;
	/**
	 * @param i
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param l the id to set
	 */
	public void setId(int l) {
		this.id = l;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}


    
}