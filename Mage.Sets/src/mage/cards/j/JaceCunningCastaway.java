
package mage.cards.j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JaceCunningCastawayIllusionToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class JaceCunningCastaway extends CardImpl {

    public JaceCunningCastaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card.
        this.addAbility(new LoyaltyAbility(new JaceCunningCastawayEffect1(), 1));

        // -2: Create a 2/2 blue Illusion creature token with "When this creature becomes the target of a spell, sacrifice it."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new JaceCunningCastawayIllusionToken()), -2));

        // -5: Create two tokens that are copies of Jace, Cunning Castaway, except they're not legendary.
        this.addAbility(new LoyaltyAbility(new JaceCunningCastawayCopyEffect(), -5));
    }

    public JaceCunningCastaway(final JaceCunningCastaway card) {
        super(card);
    }

    @Override
    public JaceCunningCastaway copy() {
        return new JaceCunningCastaway(this);
    }
}

class JaceCunningCastawayEffect1 extends OneShotEffect {

    public JaceCunningCastawayEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card";
    }

    public JaceCunningCastawayEffect1(final JaceCunningCastawayEffect1 effect) {
        super(effect);
    }

    @Override
    public JaceCunningCastawayEffect1 copy() {
        return new JaceCunningCastawayEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new JaceCunningCastawayDamageTriggeredAbility();
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class JaceCunningCastawayDamageTriggeredAbility extends DelayedTriggeredAbility {

    List<UUID> damagedPlayerIds = new ArrayList<>();

    public JaceCunningCastawayDamageTriggeredAbility() {
        super(new DrawDiscardControllerEffect(1, 1), Duration.EndOfTurn, false);
    }

    public JaceCunningCastawayDamageTriggeredAbility(final JaceCunningCastawayDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JaceCunningCastawayDamageTriggeredAbility copy() {
        return new JaceCunningCastawayDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.isControlledBy(controllerId)
                        && !damagedPlayerIds.contains(event.getTargetId())) {
                    damagedPlayerIds.add(event.getTargetId());
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card";
    }
}

class JaceCunningCastawayCopyEffect extends OneShotEffect {

    JaceCunningCastawayCopyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create two tokens that are copies of {this}, except they're not legendary";
    }

    JaceCunningCastawayCopyEffect(final JaceCunningCastawayCopyEffect effect) {
        super(effect);
    }

    @Override
    public JaceCunningCastawayCopyEffect copy() {
        return new JaceCunningCastawayCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 2);
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            effect.setIsntLegendary(true);
            return effect.apply(game, source);
        }
        return false;
    }
}
