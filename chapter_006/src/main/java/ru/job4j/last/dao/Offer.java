package ru.job4j.last.dao;

/**
 * A pojo class for representing offer
 * @author Yury Matskevich
 */
public class Offer {
	private String id;
	private String head;
	private String descrition;
	private long create;

	public Offer(String id, String head, String descrition, long create) {
		this.id = id;
		this.head = head;
		this.descrition = descrition;
		this.create = create;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getDescrition() {
		return descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public long getCreate() {
		return create;
	}

	public void setCreate(long create) {
		this.create = create;
	}
}
