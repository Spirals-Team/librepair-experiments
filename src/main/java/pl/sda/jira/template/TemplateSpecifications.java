package pl.sda.jira.template;

import org.springframework.data.jpa.domain.Specification;

public class TemplateSpecifications {
    public static Specification<Template> byName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Template> byDescription(String description) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("description"), new Description(description));
    }
}
