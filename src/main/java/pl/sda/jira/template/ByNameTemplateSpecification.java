package pl.sda.jira.template;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ByNameTemplateSpecification implements Specification<Template> {
    private final String name;

    public ByNameTemplateSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("name"), name);
    }
}
