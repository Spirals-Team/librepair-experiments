
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DeadReckoning extends CardImpl {

    public DeadReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // You may put target creature card from your graveyard on top of your library. If you do, Dead Reckoning deals damage equal to that card's power to target creature.
        this.getSpellAbility().addEffect(new DeadReckoningEffect());

    }

    public DeadReckoning(final DeadReckoning card) {
        super(card);
    }

    @Override
    public DeadReckoning copy() {
        return new DeadReckoning(this);
    }
}

class DeadReckoningEffect extends OneShotEffect {

    public DeadReckoningEffect() {
        super(Outcome.Damage);
        this.staticText = "You may put target creature card from your graveyard on top of your library. If you do, {this} deals damage equal to that card's power to target creature";
    }

    public DeadReckoningEffect(final DeadReckoningEffect effect) {
        super(effect);
    }

    @Override
    public DeadReckoningEffect copy() {
        return new DeadReckoningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        TargetCardInYourGraveyard target1 = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent();

        if (you != null) {
            if (target1.canChoose(source.getControllerId(), game)
                    && you.choose(Outcome.Benefit, target1, source.getSourceId(), game)
                    && target2.canChoose(source.getControllerId(), game)
                    && you.choose(Outcome.Damage, target2, source.getSourceId(), game)) {
                Card creatureInGraveyard = game.getCard(target1.getFirstTarget());
                if (creatureInGraveyard != null) {
                    if (creatureInGraveyard.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true)) {
                        int power = creatureInGraveyard.getPower().getValue();
                        Permanent creature = game.getPermanent(target2.getFirstTarget());
                        if (creature != null) {
                            creature.damage(power, source.getSourceId(), game, true, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
