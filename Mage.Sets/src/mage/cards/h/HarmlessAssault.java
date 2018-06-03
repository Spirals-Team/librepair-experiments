
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 * 
 * @author Rafbill
 */
public final class HarmlessAssault extends CardImpl {

    public HarmlessAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");


        // Prevent all combat damage that would be dealt this turn by attacking
        // creatures.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(new FilterAttackingCreature(), Duration.EndOfTurn, true));
    }

    public HarmlessAssault(final HarmlessAssault card) {
        super(card);
    }

    @Override
    public HarmlessAssault copy() {
        return new HarmlessAssault(this);
    }
}
