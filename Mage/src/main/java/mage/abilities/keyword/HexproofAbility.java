package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.Zone;

/**
 * Hexproof (This creature or player can't be the target of spells or abilities
 * your opponents control.)
 *
 * @author loki
 */
public class HexproofAbility extends SimpleStaticAbility implements MageSingleton {

    private static final HexproofAbility instance;

    static {
        instance = new HexproofAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static HexproofAbility getInstance() {
        return instance;
    }

    private HexproofAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public HexproofAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "hexproof";
    }
}
