
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author FenrisulfrX
 */
public final class SteamcoreWeird extends CardImpl {

    public SteamcoreWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.WEIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Steamcore Weird enters the battlefield, if {R} was spent to cast Steamcore Weird, it deals 2 damage to any target.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new ConditionalTriggeredAbility(ability, new ManaWasSpentCondition(ColoredManaSymbol.R),
                "if {R} was spent to cast {this}, it deals 2 damage to any target."),
                new ManaSpentToCastWatcher());        
    }

    public SteamcoreWeird(final SteamcoreWeird card) {
        super(card);
    }

    @Override
    public SteamcoreWeird copy() {
        return new SteamcoreWeird(this);
    }
}
