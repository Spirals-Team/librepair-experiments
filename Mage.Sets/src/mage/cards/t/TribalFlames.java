
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class TribalFlames extends CardImpl {

    public TribalFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Domain - Tribal Flames deals X damage to any target, where X is the number of basic land types among lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new DomainValue()));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public TribalFlames(final TribalFlames card) {
        super(card);
    }

    @Override
    public TribalFlames copy() {
        return new TribalFlames(this);
    }
}
