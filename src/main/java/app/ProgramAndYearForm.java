package app;

import lombok.Data;

/**
 * Created by Faisal on 2018-03-21.
 */
@Data
public class ProgramAndYearForm {

    private Program program;
    private String year;

    /**
     * Default constructor
     */
    public ProgramAndYearForm() {}
}
