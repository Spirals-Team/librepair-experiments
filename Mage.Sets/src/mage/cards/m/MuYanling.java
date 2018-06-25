
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MuYanling extends CardImpl {

    public MuYanling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANLING);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +2: Target creature can't be blocked this turn.
        LoyaltyAbility ability = new LoyaltyAbility(new CantBeBlockedTargetEffect(), 2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -3: Draw two cards.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(2), -3));

        // -10: Tap all creatures your opponents control. You take an extra turn after this one.
        this.addAbility(new LoyaltyAbility(new MuYanlingEffect(), -10));
    }

    public MuYanling(final MuYanling card) {
        super(card);
    }

    @Override
    public MuYanling copy() {
        return new MuYanling(this);
    }
}

class MuYanlingEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public MuYanlingEffect() {
        super(Outcome.Tap);
        staticText = "tap all creatures your opponents control. You take an extra turn after this one.";
    }

    public MuYanlingEffect(final MuYanlingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, player.getId(), source.getSourceId(), game)) {
            creature.tap(game);
        }
        return new AddExtraTurnControllerEffect().apply(game, source);
    }

    @Override
    public MuYanlingEffect copy() {
        return new MuYanlingEffect(this);
    }
}
