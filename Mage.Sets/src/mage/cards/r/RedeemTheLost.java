
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ClashWinReturnToHandSpellEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RedeemTheLost extends CardImpl {

    public RedeemTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Clash with an opponent. If you win, return Redeem the Lost to its owner's hand.
        this.getSpellAbility().addEffect(ClashWinReturnToHandSpellEffect.getInstance());
    }

    public RedeemTheLost(final RedeemTheLost card) {
        super(card);
    }

    @Override
    public RedeemTheLost copy() {
        return new RedeemTheLost(this);
    }
}
