package co.spraybot;

/**
 * A distinct piece of information stored in a ChatBot's HardDrive.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface HardDriveSector {

    /**
     * @return An arbitrary identifier used to keep track of the HardDriveSector
     */
    String identifier();

    /**
     * @return The actual data in your HardDriveSector
     */
    String cellContents();

}
