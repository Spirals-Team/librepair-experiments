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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class KorDirge extends CardImpl {

    public KorDirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead.
        this.getSpellAbility().addEffect(new KorDirgeEffect());
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    public KorDirge(final KorDirge card) {
        super(card);
    }

    @Override
    public KorDirge copy() {
        return new KorDirge(this);
    }
}

class KorDirgeEffect extends RedirectionEffect {

    protected TargetSource target = new TargetSource();

    KorDirgeEffect() {
        super(Duration.EndOfTurn);
        staticText = "All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead";
    }

    KorDirgeEffect(final KorDirgeEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public KorDirgeEffect copy() {
        return new KorDirgeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(this.getTargetPointer().getFirst(game, source))
                && event.getSourceId().equals(this.target.getFirstTarget())) {
            this.redirectTarget = source.getTargets().get(1);
            return true;
        }
        return false;
    }
}
