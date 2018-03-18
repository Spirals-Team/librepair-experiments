package co.spraybot;

import io.vertx.core.Future;
import io.vertx.core.Verticle;

/**
 * The ChatBot's HardDrive, where all of the knowledge it is in possession of is stored. HardDrives operate around the
 * concept of a HardDriveSector, a key-value pair representing a distinct piece of information. In more traditional terms
 * you can think of this as the persistence layer.
 *
 * No explicit connection requests will be sent to the underlying HardDrive connection, if one is necessary. Connections to
 * the underlying persistence system should be triggered when the Verticle starts or stops.
 *
 * Unless you're hacking directly on spraybot source your Commands will primarily interact with this Verticle. Combined
 * with the fact that persistence is often dependent upon network or file I/O this represents a prime opportunity to
 * present blocking code and violate the 1st spraybot Directive. Please ensure that your HardDrives are asynchronous by
 * design and are not allowed to block.
 *
 * ### Verticle Messages
 *
 * HardDrives should not publish many messages themselves but act as a server, of sorts, for other Verticles who will send
 * RequestResponse style messages with the Request body and headers describing the action to perform and the Response
 * being the results of those operations.
 *
 * - address: spraybot.harddrive
 * - op: write
 * - body: HardDriveSector the key-value pair you want to write to storage
 * - response: Boolean
 *
 * - address: spraybot.harddrive
 * - op: read
 * - body: String the sectorIdentifier you want to remember
 * - response: HardDriveSector
 *
 * - address: spraybot.harddrive
 * - op: erase
 * - body: String the sectorIdentifier you want to remove from storage
 * - response: void
 *
 * - address: spraybot.harddrive
 * - op: eraseEverything
 * - body: null no body required.
 * - response: void
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface HardDrive extends Verticle {

    /**
     * @param hardDriveSector Pass a HardDriveSector you want this HardDrive to write to storage
     * @return Whether or not the HardDriveSector was successfully put into storage
     */
    Future<Boolean> write(HardDriveSector hardDriveSector);

    /**
     * Read a HardDriveSector from storage, if one can be found for the given sectorIdentifier.
     *
     * @param sectorIdentifier The thing that you want to remember more details about
     * @return The HardDriveSector if we could find one or null.
     */
    Future<HardDriveSector> read(String sectorIdentifier);

    /**
     * Remove the HardDriveSector from storage, if one can be found for the given sectorIdentifier.
     *
     * @param sectorIdentifier The identifier for the HardDriveSector you want your ChatBot to erase
     * @return A future that completes successfully when the HardDriveSector has been destroyed
     */
    Future<Void> erase(String sectorIdentifier);

    /**
     * Perform a lobotomy on your ChatBot ensuring they have forgotten everything they've been taught.
     *
     * @return A future that completes successfully when ALL data has been erased from the HardDrive
     */
    Future<Void> eraseEverything();

}
