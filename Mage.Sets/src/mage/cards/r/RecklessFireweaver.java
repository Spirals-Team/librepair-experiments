
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class RecklessFireweaver extends CardImpl {

    public RecklessFireweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, Reckless Fireweaver deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT),
                new FilterControlledArtifactPermanent(), false,
            "Whenever an artifact enters the battlefield under your control, Reckless Fireweaver deals 1 damage to each opponent."));
    }

    public RecklessFireweaver(final RecklessFireweaver card) {
        super(card);
    }

    @Override
    public RecklessFireweaver copy() {
        return new RecklessFireweaver(this);
    }
}
