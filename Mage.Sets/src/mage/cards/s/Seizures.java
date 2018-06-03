
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Seizures extends CardImpl {

    public Seizures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted creature becomes tapped, Seizures deals 3 damage to that creature's controller unless that player pays {3}.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new SeizuresEffect(), "enchanted creature"));
    }

    public Seizures(final Seizures card) {
        super(card);
    }

    @Override
    public Seizures copy() {
        return new Seizures(this);
    }
}


class SeizuresEffect extends OneShotEffect {

    public SeizuresEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to that creature's controller unless that player pays {3}";
    }

    public SeizuresEffect(final SeizuresEffect effect) {
        super(effect);
    }

    @Override
    public SeizuresEffect copy() {
        return new SeizuresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if(enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if(enchanted == null) {
            return false;
        }
        Player player = game.getPlayer(enchanted.getControllerId());
        if(player != null) {
            Cost cost = new ManaCostsImpl("{3}");
            if(cost.canPay(source, source.getSourceId(), player.getId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + " to avoid damage?", source, game)) {
                cost.clearPaid();
                if(cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                    return true;
                }
            }
            player.damage(3, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
