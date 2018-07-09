
package automation;

/**
 * The Interface ExpectedDataInterface.
 */
public interface ExpectedDataInterface {

    /**
     * On platform.
     *
     * @param platform the platform
     * @return the expected data interface
     */
    ExpectedDataInterface onPlatform(String platform);

    /**
     * Tagged.
     *
     * @param tag the tag
     * @return the object
     */
    Object tagged(String tag);

    // Object forTag(String tag);
    // Object withTag(String tag);
    // Object withCharacteristic(String tag);

}
