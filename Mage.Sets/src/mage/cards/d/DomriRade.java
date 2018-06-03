
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.command.emblems.DomriRadeEmblem;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DomriRade extends CardImpl {

    public DomriRade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOMRI);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand.
        this.addAbility(new LoyaltyAbility(new DomriRadeEffect1(), 1));

        // -2: Target creature you control fights another target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new FightTargetsEffect(), -2);
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setTargetTag(1);
        ability2.addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature to fight");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        ability2.addTarget(target2);
        this.addAbility(ability2);

        // -7: You get an emblem with "Creatures you control have double strike, trample, hexproof and haste."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DomriRadeEmblem()), -7));

    }

    public DomriRade(final DomriRade card) {
        super(card);
    }

    @Override
    public DomriRade copy() {
        return new DomriRade(this);
    }
}

class DomriRadeEffect1 extends OneShotEffect {

    public DomriRadeEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand";
    }

    public DomriRadeEffect1(final DomriRadeEffect1 effect) {
        super(effect);
    }

    @Override
    public DomriRadeEffect1 copy() {
        return new DomriRadeEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards(sourceObject.getName(), cards, game);
                if (card.isCreature()) {
                    if (controller.chooseUse(outcome, "Reveal " + card.getName() + " and put it into your hand?", source, game)) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
