package ua.epam.spring.hometask.domain;

/**
 * @author Yuriy_Tkach
 */
public class DomainObject {

    public static final Long START_SEQ = 0L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

}
