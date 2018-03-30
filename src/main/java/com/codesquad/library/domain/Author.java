package com.codesquad.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "AUTHOR_ID")
    private long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books;

}
