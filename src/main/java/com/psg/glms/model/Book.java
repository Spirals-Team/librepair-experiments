package com.psg.glms.model;

import javax.persistence.*;

@Entity
public class Book{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@Column
	private String bookname;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bookcatId", updatable = false, insertable = true)
	private BookCategory bookcategory;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

}