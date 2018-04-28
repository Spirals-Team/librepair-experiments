/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author LevelX2
 */
public class GazeOfTheGorgon extends CardImpl {

    public GazeOfTheGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B/G}");

        // <i>({BG} can be paid with either {B} or {G}.)</i>
        // Regenerate target creature. At end of combat, destroy all creatures that blocked or were blocked by that creature this turn.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
    }

    public GazeOfTheGorgon(final GazeOfTheGorgon card) {
        super(card);
    }

    @Override
    public GazeOfTheGorgon copy() {
        return new GazeOfTheGorgon(this);
    }
}

class GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    public GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "At this turn's next end of combat, destroy all creatures that blocked or were blocked by it this turn";
    }

    public GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect(final GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect copy() {
        return new GazeOfTheGorgonCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty() && source.getFirstTarget() != null) {
            MageObjectReference mor = new MageObjectReference(source.getFirstTarget(), game);
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new GazeOfTheGorgonEffect(mor));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class GazeOfTheGorgonEffect extends OneShotEffect {
    
    MageObjectReference targetCreature;

    public GazeOfTheGorgonEffect(MageObjectReference targetCreature) {
        super(Outcome.DestroyPermanent);
        this.targetCreature = targetCreature;
    }

    public GazeOfTheGorgonEffect(final GazeOfTheGorgonEffect effect) {
        super(effect);
        targetCreature = effect.targetCreature;
    }

    @Override
    public GazeOfTheGorgonEffect copy() {
        return new GazeOfTheGorgonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetCreature != null) {
            BlockedAttackerWatcher watcher = (BlockedAttackerWatcher) game.getState().getWatchers().get(BlockedAttackerWatcher.class.getSimpleName());
            if (watcher != null) {
                List<Permanent> toDestroy = new ArrayList<>();
                for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source.getSourceId(), game)) {
                    if (!creature.getId().equals(targetCreature.getSourceId())) {
                        if (watcher.creatureHasBlockedAttacker(new MageObjectReference(creature, game), targetCreature, game) || watcher.creatureHasBlockedAttacker(targetCreature, new MageObjectReference(creature, game), game)) {
                            toDestroy.add(creature);
                        }
                    }
                }
                for (Permanent creature : toDestroy) {
                    creature.destroy(source.getSourceId(), game, false);
                }
                return true;
            }
        }
        return false;
    }
}
