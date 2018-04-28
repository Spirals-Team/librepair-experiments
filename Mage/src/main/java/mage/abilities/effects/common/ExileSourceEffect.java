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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSourceEffect extends OneShotEffect {

    private boolean toUniqueExileZone;

    public ExileSourceEffect() {
        this(false);
    }

    /**
     *
     * @param toUniqueExileZone moves the card to a source object dependant
     * unique exile zone, so another effect of the same source object (e.g.
     * Deadeye Navigator) can identify the card
     */
    public ExileSourceEffect(boolean toUniqueExileZone) {
        super(Outcome.Exile);
        staticText = "exile {this}";
        this.toUniqueExileZone = toUniqueExileZone;
    }

    public ExileSourceEffect(final ExileSourceEffect effect) {
        super(effect);
        this.toUniqueExileZone = effect.toUniqueExileZone;
    }

    @Override
    public ExileSourceEffect copy() {
        return new ExileSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
            if (sourceObject instanceof Card) {
                if (sourceObject instanceof Permanent) {
                    if (!((Permanent) sourceObject).isPhasedIn()) {
                        return true;
                    }
                }
                UUID exileZoneId = null;
                String exileZoneName = "";
                if (toUniqueExileZone) {
                    exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    exileZoneName = sourceObject.getName();
                }
                Card sourceCard = (Card) sourceObject;
                return controller.moveCardsToExile(sourceCard, source, game, true, exileZoneId, exileZoneName);
            }
            return true;
        }
        return false;
    }
}
