
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 * @author LevelX2
 */
public final class SakashimaTheImpostor extends CardImpl {

    public SakashimaTheImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may have Sakashima the Impostor enter the battlefield as a copy of any creature on the battlefield, except its name is Sakashima the Impostor, it's legendary in addition to its other types, and it has "{2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step."
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new SakashimaTheImpostorApplier());
        effect.setText("as a copy of any creature on the battlefield, except its name is Sakashima the Impostor, it's legendary in addition to its other types, and it has \"{2}{U}{U}: Return {this} to its owner's hand at the beginning of the next end step.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public SakashimaTheImpostor(final SakashimaTheImpostor card) {
        super(card);
    }

    @Override
    public SakashimaTheImpostor copy() {
        return new SakashimaTheImpostor(this);
    }
}

class SakashimaTheImpostorApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        permanent.addSuperType(SuperType.LEGENDARY);

        permanent.setName("Sakashima the Impostor");
        // {2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step
        permanent.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)), false),
                new ManaCostsImpl("{2}{U}{U}")
        ), game);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        mageObject.addSuperType(SuperType.LEGENDARY);
        mageObject.setName("Sakashima the Impostor");
        // {2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step
        mageObject.getAbilities().add(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)), false),
                new ManaCostsImpl("{2}{U}{U}")
        ));
        return true;
    }

}
