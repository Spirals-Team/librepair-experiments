package com.codesquad.library.domain;

import com.codesquad.library.dtos.model.article.NewArticleDocument;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Article extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ARTICLE_ID")
    private long id;

    @Column(name = "ARTICLE_TITLE")
    private String title;

    @Column(name = "ARTICLE_CONTENT", columnDefinition = "NVARCHAR(2000)")
    private String content;

    @Column(name = "IS_URGENT")
    private boolean urgent;

    public static Article getObjectByDto(NewArticleDocument document) {
        return Article.builder()
                .title(document.getTitle())
                .content(document.getContent())
                .urgent(document.getUrgent())
                .build();
    }


}
