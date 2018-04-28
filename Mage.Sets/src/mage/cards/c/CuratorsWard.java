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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author keelahnkhan
 */
public class CuratorsWard extends CardImpl {

    public CuratorsWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent has hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.AURA)
                        .setText("Enchanted permanent has hexproof")));

        // When enchanted permanent leaves the battlefield, if it was historic, draw two cards.
        this.addAbility(new CuratorsWardTriggeredAbility());
    }

    public CuratorsWard(final CuratorsWard card) {
        super(card);
    }

    @Override
    public CuratorsWard copy() {
        return new CuratorsWard(this);
    }
}

class CuratorsWardTriggeredAbility extends TriggeredAbilityImpl {

    public CuratorsWardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), false);
    }

    public CuratorsWardTriggeredAbility(CuratorsWardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
            Permanent enchanted = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (enchanted != null && enchanted.getAttachments().contains(getSourceId()) && enchanted.isHistoric()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted permanent leaves the battlefield, if it was historic, " + super.getRule()
                + " <i>(Artifacts, legendaries, and Sagas are historic.)</i>";
    }

    @Override
    public CuratorsWardTriggeredAbility copy() {
        return new CuratorsWardTriggeredAbility(this);
    }

}
