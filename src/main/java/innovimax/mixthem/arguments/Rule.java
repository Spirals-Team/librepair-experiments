package innovimax.mixthem.arguments;

import java.util.EnumSet;

/**
* <p>This is a detailed enumeration of rules used to mix files.</p>
* <p>Some rules may not be implemented yet.<.p>
* <p>(Also used to print usage.)</p>
* @author Innovimax
* @version 1.0
*/
public enum Rule { 
    FILE_1("1", "1", "will output file1", true, EnumSet.noneOf(RuleParam.class), EnumSet.of(Mode.CHAR, Mode.BYTE)),
    FILE_2("2", "2", "will output file2", true, EnumSet.noneOf(RuleParam.class), EnumSet.of(Mode.CHAR, Mode.BYTE)),
    ADD("+", "add", "will output file1+file2+...+fileN", true, EnumSet.of(RuleParam.ADD_FILES), EnumSet.of(Mode.CHAR, Mode.BYTE)),
    ALT_LINE("alt-line", "altline", "will output one line of each starting with first line of file1", true, EnumSet.noneOf(RuleParam.class), EnumSet.of(Mode.CHAR)), 
    ALT_CHAR("alt-char", "altchar", "will output one char of each starting with first char of file1", true, EnumSet.noneOf(RuleParam.class), EnumSet.of(Mode.CHAR)),
    ALT_BYTE("alt-byte", "altbyte", "will output one byte of each starting with first byte of file1", true, EnumSet.noneOf(RuleParam.class), EnumSet.of(Mode.BYTE)),
    RANDOM_ALT_LINE("random-alt-line", "random-altline", "will output one line of each code randomly based on a seed for reproducability", true, EnumSet.of(RuleParam.RANDOM_SEED), EnumSet.of(Mode.CHAR)),
    RANDOM_ALT_CHAR("random-alt-char", "random-altchar", "will output one char of each code randomly based on a seed for reproducability", true, EnumSet.of(RuleParam.RANDOM_SEED), EnumSet.of(Mode.CHAR)),
    RANDOM_ALT_BYTE("random-alt-byte", "random-altbyte", "will output one byte of each code randomly based on a seed for reproducability", true, EnumSet.of(RuleParam.RANDOM_SEED), EnumSet.of(Mode.BYTE)),
    JOIN("join", "join", "will output merging of lines that have common occurrence", true, EnumSet.of(RuleParam.JOIN_COLS), EnumSet.of(Mode.CHAR)),
    ZIP_LINE("zip-line", "zipline", "will output zip of line from file1 and file2 to fileN", true, EnumSet.of(RuleParam.ZIP_SEP), EnumSet.of(Mode.CHAR)),
    ZIP_CHAR("zip-char", "zipchar", "will output zip of char from file1 and file2 to fileN", true, EnumSet.of(RuleParam.ZIP_SEP), EnumSet.of(Mode.CHAR)),
    ZIP_CELL("zip-cell", "zipcell", "will output zip of cell from file1 and file2 to fileN", true, EnumSet.of(RuleParam.ZIP_SEP), EnumSet.of(Mode.CHAR));

    private final String name, extension, description;
    private final boolean implemented;
    private final EnumSet<RuleParam> params;
    private final EnumSet<Mode> modes;

    private Rule(final String name, final String extension, final String description, final boolean implemented, final EnumSet<RuleParam> params, final EnumSet<Mode> modes) {
        this.name = name;
        this.extension = extension;
        this.description = description;
        this.implemented = implemented;
        this.params = params;
        this.modes = modes;
    }

    /**
    * Returns true if the rule is currently implemented.
    * @return True if the rule is currently implemented
    */
    public boolean isImplemented() {
        return this.implemented;
    }

    /**
    * Returns the name of this rule on command line.
    * @return The name of the rule on command line
    */
    public String getName() {
        return this.name;
    }

    /**
    * Returns the file extension used for outputting.
    * @return The file extension for the rule
    */ 
    public String getExtension() {
        return this.extension;
    }

    /**
    * Returns the description of this rule.
    * @return The description of the rule
    */ 
    public String getDescription() {
        return this.description;
    }

    /**
    * Returns an iterator over the additional parameters in this rule.
    * @return An iterator over the additional parameters in this rule
    */ 
    public Iterable<RuleParam> getParams() {
        return this.params;
    }
    
    /**
    * Returns true if the rule accept the running mode.
    * @return True if the rule accept the running mode
    */ 
    public boolean acceptMode(final Mode mode) {
        return modes.contains(mode);
    }
    
    /**
    * Finds the Rule object correponding to a name
    * @param name The name of the rule in command line
    * @param mode The running {@link Mode) 
    * @return The {@link Rule} object
    */    
    public static Rule findByName(final String name, final Mode mode) {
        for(Rule rule : values()){
            if (rule.getName().equals(name) && rule.acceptMode(mode)) {
                return rule;
            }
        }
        return null;
    }

}
