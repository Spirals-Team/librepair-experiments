package uk.co.ohmgeek.jdcraw.operations;

/**
 * This is an Enum that stores the Colour Spaces used to be output.
 *
 * Created by ryan on 29/06/17.
 */
public enum ColourSpaceEnum {
    RAW(0), SRGB(1), ADOBE_RGB(2), WIDE(3), ProPhoto(4), XYZ(5), ACES(6);

    private final int num;

    /**
     * Creates an instance of a colour space (from enum).
     * @param num : the associated number in DCRaw corresponding to the colour space.
     */
    ColourSpaceEnum(int num) {
        this.num = num;
    }


    /**
     * Fetch the DCRaw colour space code.
     * @return code : the DCRaw colour space code, as a string.
     */
    public String dcrawConstant() {
        return String.valueOf(this.num);
    }
}
