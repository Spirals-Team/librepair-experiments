package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class LilianaTheNecromancer extends CardImpl {

    public LilianaTheNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Target player loses 2 life.
        Ability ability = new LoyaltyAbility(new LoseLifeTargetEffect(2), 1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // −1: Return target creature card from your graveyard to your hand.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -1);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // −7: Destroy up to two target creatures. Put up to two creature cards from graveyards onto the battlefield under your control.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -7);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        ability.addEffect(new LilianaTheNecromancerEffect());
        this.addAbility(ability);
    }

    public LilianaTheNecromancer(final LilianaTheNecromancer card) {
        super(card);
    }

    @Override
    public LilianaTheNecromancer copy() {
        return new LilianaTheNecromancer(this);
    }
}

class LilianaTheNecromancerEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards from graveyards");

    public LilianaTheNecromancerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put up to two creature cards from graveyards onto the battlefield under your control";
    }

    public LilianaTheNecromancerEffect(final LilianaTheNecromancerEffect effect) {
        super(effect);
    }

    @Override
    public LilianaTheNecromancerEffect copy() {
        return new LilianaTheNecromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInGraveyard(0, 2, filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Cards cardsToMove = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                cardsToMove.add(card);
            }
        }
        return player.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game);
    }
}
