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

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * The creatures defined by filter can't attack any opponent
 *
 * @author LevelX2
 */
public class CantAttackAnyPlayerAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantAttackAnyPlayerAllEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        StringBuilder sb = new StringBuilder(filter.getMessage()).append(" can't attack");
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append(" this turn");
            } else {
                sb.append(' ').append(duration.toString());
            }
        }
        staticText = sb.toString();        
    }

    public CantAttackAnyPlayerAllEffect(final CantAttackAnyPlayerAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public CantAttackAnyPlayerAllEffect copy() {
        return new CantAttackAnyPlayerAllEffect(this);
    }

}
