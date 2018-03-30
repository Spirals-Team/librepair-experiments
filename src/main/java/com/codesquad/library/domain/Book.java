package com.codesquad.library.domain;

import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.domain.constraints.LongerThan;
import com.codesquad.library.dtos.model.book.BookDocument;
import com.codesquad.library.dtos.model.book.NewBookDocument;
import com.codesquad.library.dtos.model.review.ReviewDocument;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString(exclude = "member")
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "BOOK_ID")
    private long id;

    @NotNull
    private String title;

    @Column(name = "BOOK_DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = Lists.newArrayList();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "RENT_MEMBER_ID")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FEATURED_ID")
    private Featured featured;

    private boolean isPossessed;

    @Column(name = "SERIES_CODE")
    private int seriesNumber;

    @Column(name = "BOOK_ISBN")
    private String isbn;

    @Column(name = "BOOK_LOCATION")
    @Enumerated(EnumType.STRING)
    private BookLocations bookLocations;

    @Column(name = "BOOK_CATEGORY")
    @Enumerated(EnumType.STRING)
    private BookCategories bookCategories;

    @Column(name = "LAST_RENT_DATE")
    private LocalDateTime lastRentDate;

    @Column(name = "RENT_ELAPSED_DATE")
    private int elapsedDate;

    public void setPossessed(Member member) {
        this.isPossessed = true;
        this.lastRentDate = LocalDateTime.now();

        if(this.member != null) {
            this.member.getLendedBooks().remove(this);
        }

        this.member = member;
        if(member.getLendedBooks() == null) {
            member.setLendedBooks(Lists.newArrayList());
        }

        member.getLendedBooks().add(this);
    }

    public void returnBook(Member member) {
        if(member.equals(this.member)) {
            this.member = null;
            this.isPossessed = false;
            this.elapsedDate = 0;
            member.getLendedBooks().remove(this);
        }
    }

    public void addReview(Review review) {
        if (this.reviews == null) {
            this.reviews = Lists.newArrayList();
        }

        this.reviews.add(review);
        review.setBook(this);
    }
    //TODO implement document-return logic.

    public BookDocument generateDocument() {
        return new BookDocument("book", this.id, this.title, this.description, null, this.author, this.isPossessed, this.seriesNumber, BookStatus.generateStatus(this), this.bookCategories, this.bookLocations);
    }

    public Book getObjectByDocument(NewBookDocument document) {
        return Book.builder()
                .title(document.getTitle())
                .description(document.getDescription())
                .isbn(document.getIsbn())
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equal(title, book.title) &&
                Objects.equal(description, book.description) &&
                Objects.equal(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, description, author);
    }
}
