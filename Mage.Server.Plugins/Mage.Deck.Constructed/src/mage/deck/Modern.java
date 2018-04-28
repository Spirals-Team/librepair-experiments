/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.deck;

import java.util.Date;
import java.util.GregorianCalendar;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public class Modern extends Constructed {

    public Modern() {
        super("Constructed - Modern");

        Date cutoff = new GregorianCalendar(2003, 6, 28).getTime(); // Eight edition release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if ((set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))
                    && (set.getSetType() == SetType.CORE || set.getSetType() == SetType.EXPANSION)) {
                setCodes.add(set.getCode());
            }
        }

        banned.add("Ancient Den");
        banned.add("Birthing Pod");
        banned.add("Blazing Shoal");
        banned.add("Chrome Mox");
        banned.add("Cloudpost");
        banned.add("Dark Depths");
        banned.add("Deathrite Shaman");
        banned.add("Dig Through Time");
        banned.add("Dread Return");
        banned.add("Eye of Ugin");
        banned.add("Gitaxian Probe");
        banned.add("Glimpse of Nature");
        banned.add("Golgari Grave-Troll");
        banned.add("Great Furnace");
        banned.add("Green Sun's Zenith");
        banned.add("Hypergenesis");
        banned.add("Mental Misstep");
        banned.add("Ponder");
        banned.add("Preordain");
        banned.add("Punishing Fire");
        banned.add("Rite of Flame");
        banned.add("Seat of the Synod");
        banned.add("Second Sunrise");
        banned.add("Seething Song");
        banned.add("Sensei's Divining Top");
        banned.add("Stoneforge Mystic");
        banned.add("Skullclamp");
        banned.add("Splinter Twin");
        banned.add("Summer Bloom");
        banned.add("Treasure Cruise");
        banned.add("Tree of Tales");
        banned.add("Umezawa's Jitte");
        banned.add("Vault of Whispers");
    }
}
