package ar.edu.undav.semillero.dto;

/**
 * @author Alejandro Gomez
 */
public class CompanyDTO extends BaseDTO {

    private final long id;
    private final String name;
    private final String email;
    private final int working;
    private final int interviewed;

    public CompanyDTO(Number id, String name, String email, Number working, Number interviewed) {
        this.id = id.longValue();
        this.name = name;
        this.email = email;
        this.working = working.intValue();
        this.interviewed = interviewed.intValue();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getWorking() {
        return working;
    }

    public int getInterviewed() {
        return interviewed;
    }
}
