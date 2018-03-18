package co.spraybot;

import io.vertx.core.Future;

public interface HardDriveAwareDrone {

    /**
     * Simple access to store data in the HardDrive.
     *
     * @param key The identifier for the data you want to store
     * @param value The contents that should be associated with the key
     * @return Whether or not the value was stored
     */
    Future<Boolean> storeData(String key, String value);

    /**
     * @param key The identifier used to storeData
     * @return The value identified by key
     */
    Future<String> fetchData(String key);

    /**
     * @param key The HardDriveSector identifier you want to destroy
     * @return Simply resolve when completed or fail the Future
     */
    Future<Void> destroyData(String key);

    /**
     * Hugely destructive! Will cause the HardDrive to wipe all learned BrainNeurons.
     *
     * @return Simply resolve when completed or fail the Future
     */
    Future<Void> destroyAllData();

}
