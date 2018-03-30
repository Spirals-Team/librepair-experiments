package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
