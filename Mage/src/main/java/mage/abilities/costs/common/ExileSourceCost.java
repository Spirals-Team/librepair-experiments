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
package mage.abilities.costs.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSourceCost extends CostImpl {

    private boolean toUniqueExileZone;

    public ExileSourceCost() {
        this.text = "exile {this}";
    }

    /**
     *
     * @param toUniqueExileZone moves the card to a source object dependant
     * unique exile zone, so another effect of the same source object (e.g.
     * Deadeye Navigator) can identify the card
     */
    public ExileSourceCost(boolean toUniqueExileZone) {
        this.text = "exile {this}";
        this.toUniqueExileZone = toUniqueExileZone;
    }

    public ExileSourceCost(ExileSourceCost cost) {
        super(cost);
        this.toUniqueExileZone = cost.toUniqueExileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        MageObject sourceObject = ability.getSourceObject(game);
        Player controller = game.getPlayer(controllerId);
        if (controller != null && sourceObject != null && (sourceObject instanceof Card)) {
            UUID exileZoneId = null;
            String exileZoneName = "";
            if (toUniqueExileZone) {
                exileZoneId = CardUtil.getExileZoneId(game, ability.getSourceId(), ability.getSourceObjectZoneChangeCounter());
                exileZoneName = sourceObject.getName();
                game.getState().setValue(sourceObject.getId().toString(), ability.getSourceObjectZoneChangeCounter());
            }
            controller.moveCardToExileWithInfo((Card) sourceObject, exileZoneId, exileZoneName, sourceId, game, game.getState().getZone(sourceObject.getId()), true);
                // 117.11. The actions performed when paying a cost may be modified by effects.
            // Even if they are, meaning the actions that are performed don't match the actions
            // that are called for, the cost has still been paid.
            // so return state here is not important because the user indended to exile the target anyway
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null;
    }

    @Override
    public ExileSourceCost copy() {
        return new ExileSourceCost(this);
    }

}
