package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.*;
import com.codesquad.library.dtos.model.book.BookQueryDocument;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@Transactional
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final Logger log = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findByQuery(BookQueryDocument document) {
        log.debug(document.getQuery());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        List<Predicate> conditions = Lists.newArrayList();

        Join<Book, Author> author = root.join("author", JoinType.LEFT);

        if(document.getSearchCriteria() == BookSearchCriteria.AUTHOR) {
            conditions.add(cb.like(author.get(Author_.name).as(String.class), generateLikeParameter(document.getQuery())));
        }

        if(document.getSearchCriteria() == BookSearchCriteria.TITLE) {
            conditions.add(cb.like(root.get(Book_.title).as(String.class), generateLikeParameter(document.getQuery())));
        }

        if(document.getSearchCriteria() == BookSearchCriteria.ISBN) {
            conditions.add(cb.like(root.get(Book_.isbn), generateLikeParameter(document.getQuery())));
        }

        log.debug(conditions.toString());

        query.select(root).where(conditions.toArray(new Predicate[]{}));
        TypedQuery<Book> bookTypedQuery = entityManager.createQuery(query);

        return bookTypedQuery.getResultList();
    }

    private String generateLikeParameter(String query) {
        StringBuilder sb = new StringBuilder();

        sb.append("%");
        sb.append(query);
        sb.append("%");

        return sb.toString();
    }
}
