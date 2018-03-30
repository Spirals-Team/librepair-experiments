package com.codesquad.library.domain;

import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.dtos.model.review.ReviewDocument;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Size(min = 6, message = "제목은 6자 이상 써 주세요.")
    @Nullable
    @Column(name = "REVIEW_TITLE", columnDefinition = "TEXT NULL")
    private String title;

    @Size(max = 300, message = "서평은 300자를 초과할 수 없습니다.")
    @Nullable
    @Column(name = "REVIEW_CONTENT", columnDefinition = "TEXT NULL")
    private String content;

    public ReviewDocument generateDocument() {
        return new ReviewDocument(this.title, this.content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equal(book, review.book) &&
                Objects.equal(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(book, content);
    }
}
