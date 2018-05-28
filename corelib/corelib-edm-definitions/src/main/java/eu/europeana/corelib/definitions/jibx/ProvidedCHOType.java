
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/2002/07/owl#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ProvidedCHOType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns1:BaseProvidedCHOType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:type" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ProvidedCHOType extends BaseProvidedCHOType
{
    private Type2 type;
    private List<SameAs> sameAList = new ArrayList<SameAs>();

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public Type2 getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(Type2 type) {
        this.type = type;
    }

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }
}
