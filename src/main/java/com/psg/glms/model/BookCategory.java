package com.psg.glms.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book_category")
public class BookCategory {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
    private Long bookcatId;
    
    @Column
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "bookcatId")
    private List<Book> book;
    
    public BookCategory() {	}

    public Long getBookcatId() {
		return bookcatId;
	}

	public void setBookcatId(Long bookcatId) {
		this.bookcatId = bookcatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}
    
}