
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterInstantOrSorcerySpell;

/**
 * @author Loki
 */
public final class WeeDragonauts extends CardImpl {

    public WeeDragonauts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), new FilterInstantOrSorcerySpell(), false));
    }

    public WeeDragonauts(final WeeDragonauts card) {
        super(card);
    }

    @Override
    public WeeDragonauts copy() {
        return new WeeDragonauts(this);
    }
}
