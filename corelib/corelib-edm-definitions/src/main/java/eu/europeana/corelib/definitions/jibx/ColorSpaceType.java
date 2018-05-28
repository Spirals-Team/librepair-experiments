
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ColorSpaceType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="sRGB"/>
 *     &lt;xs:enumeration value="grayscale"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ColorSpaceType {
    S_RGB("sRGB"), GRAYSCALE("grayscale");
    private final String value;

    private ColorSpaceType(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static ColorSpaceType convert(String value) {
        for (ColorSpaceType inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
