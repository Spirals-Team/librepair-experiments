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

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public class DarksteelColossus extends CardImpl {

    public DarksteelColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{11}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Darksteel Colossus is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // If Darksteel Colossus would be put into a graveyard from anywhere, reveal Darksteel Colossus and shuffle it into its owner's library instead.        
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    public DarksteelColossus(final DarksteelColossus card) {
        super(card);
    }

    @Override
    public DarksteelColossus copy() {
        return new DarksteelColossus(this);
    }

}
