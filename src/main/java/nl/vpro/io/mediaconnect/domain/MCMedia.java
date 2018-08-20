package nl.vpro.io.mediaconnect.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Michiel Meeuwissen
 * @since 0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MCMedia extends MCAbstractObject {

    List<MCAsset> content;

}
