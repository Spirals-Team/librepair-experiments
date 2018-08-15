package psidev.psi.mi.jami.model;

/**
 * A feature range indicates the positions of a feature in the interactor sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */
public interface Range {

    /** Constant <code>POSITION_SEPARATOR="-"</code> */
    public static final String POSITION_SEPARATOR = "-";
    /** Constant <code>N_TERMINAL_POSITION_SYMBOL="n"</code> */
    public static final String N_TERMINAL_POSITION_SYMBOL = "n";
    /** Constant <code>C_TERMINAL_POSITION_SYMBOL="c"</code> */
    public static final String C_TERMINAL_POSITION_SYMBOL = "c";
    /** Constant <code>UNDETERMINED_POSITION_SYMBOL="?"</code> */
    public static final String UNDETERMINED_POSITION_SYMBOL = "?";
    /** Constant <code>FUZZY_POSITION_SYMBOL=".."</code> */
    public static final String FUZZY_POSITION_SYMBOL = "..";
    /** Constant <code>GREATER_THAN_POSITION_SYMBOL="&gt;"</code> */
    public static final String GREATER_THAN_POSITION_SYMBOL = ">";
    /** Constant <code>LESS_THAN_POSITION_SYMBOL="&lt;"</code> */
    public static final String LESS_THAN_POSITION_SYMBOL = "<";

    /**
     * The start position of the feature range in the interactor sequence
     * It cannot be null
     *
     * @return the start position
     */
    public Position getStart();

    /**
     * The end position of the feature range in the interactor sequence
     * It cannot be null
     *
     * @return the end position
     */
    public Position getEnd();

    /**
     * Set the positions of the feature range in the interactor sequence
     *
     * @param start : start position
     * @param end : end position
     * @throws java.lang.IllegalArgumentException if
     * - start or end is null
     * - start is greater than end
     */
    public void setPositions(Position start, Position end);

    /**
     * Link boolean to know if two amino acids/nucleic acids are linked in the feature range (ex: disulfure bridges).
     *
     * @return true if two amino acids/nucleic acids are linked together (does not form a linear feature).
     */
    public boolean isLink();

    /**
     * Sets the link boolean for this Range
     *
     * @param link : the linked value
     */
    public void setLink(boolean link);

    /**
     * The resultingSequence in case of mutation or variant.
     * It can be null if not relevant for this range.
     *
     * @return the ResultingSequence if there is a sequence change for this range object, null otherwise
     */
    public ResultingSequence getResultingSequence();

    /**
     * Sets the resultingSequence of this range object
     *
     * @param resultingSequence : the resulting sequence
     */
    public void setResultingSequence(ResultingSequence resultingSequence);

    /**
     * The participating molecule that this range refers to. It can refer to any entity (participant, participant candidate, etc.)
     * It can be null. If null, the range is applied to the participant reporting the feature.
     *
     * @return The participant that the range is referring to
     */
    public Entity getParticipant();

    /**
     * Sets the participating molecule that the range refers to
     *
     * @param participant a {@link psidev.psi.mi.jami.model.Entity} object.
     */
    public void setParticipant(Entity participant);
}
