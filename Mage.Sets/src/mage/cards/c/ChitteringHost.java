
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ChitteringHost extends MeldCard {
    public ChitteringHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.nightCard = true; // Meld card

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Menace <i>(This creature can't be blocked except by two or more creatures.
        this.addAbility(new MenaceAbility());

        // When Chittering Host enters the battlefield, other creatures you control get +1/+0 and gain menace until end of turn.
        Effect effect = new BoostControlledEffect(1, 0, Duration.EndOfTurn, true);
        effect.setText("other creatures you control get +1/+0");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("other creatures"), true);
        effect.setText("and gain menace until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public ChitteringHost(final ChitteringHost card) {
        super(card);
    }

    @Override
    public ChitteringHost copy() {
        return new ChitteringHost(this);
    }
}
