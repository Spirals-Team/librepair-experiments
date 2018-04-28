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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class RudeAwakening extends CardImpl {

    public RudeAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Untap all lands you control;
        this.getSpellAbility().addEffect(new UntapAllLandsControllerEffect());
        // or until end of turn, lands you control become 2/2 creatures that are still lands.
        Mode mode = new Mode();
        mode.getEffects().add(new BecomesCreatureAllEffect(new RudeAwakeningToken(), "lands", new FilterControlledLandPermanent("lands you control"), Duration.EndOfTurn));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{G}
        this.addAbility(new EntwineAbility("{2}{G}"));
    }

    public RudeAwakening(final RudeAwakening card) {
        super(card);
    }

    @Override
    public RudeAwakening copy() {
        return new RudeAwakening(this);
    }
}

class RudeAwakeningToken extends TokenImpl {

    public RudeAwakeningToken() {
        super("", "2/2 creatures");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
    public RudeAwakeningToken(final RudeAwakeningToken token) {
        super(token);
    }

    public RudeAwakeningToken copy() {
        return new RudeAwakeningToken(this);
    }

}
