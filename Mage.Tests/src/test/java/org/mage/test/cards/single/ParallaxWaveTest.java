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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ParallaxWaveTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=16732&hilit=Sharuum+the+Hegemon#p172791
     * 
     * While playing the AI I had put out Opalescence and Parallax Wave. 
     * With proper ordering of the stack I should be able to permanently exile all my 
     * opponents creatures. This does not happen
     * 
        The proper sequence should work as follows
        -Activate Parallax Wave to exile target enemy creature(s),
        -Respond and assign Parallax Wave to target itself.
        -Resolve the stack, Parallax wave targets itself for exile
        -This triggers the return all exiled cards to the field effect, to the top of the stack
        -Stack resolves, A "NEW unique id" parallax wave should enter the field. **this is not happening**
        -The stack should finish resolution, the enemy creatures are exiled *however* the return to the 
         battle field effect should be tied to the ORIGINAL id Parallax Wave and thus will never trigger as
         the original wave no longer exists) **this doesn't happen, as the "NEW" Parallax Wave leaves play 
         the cards exiled with the "previous" wave are returned to the battlefield**
         
     */
    @Test
    public void testFirstExileHandlingOfItself() {
        // Each other non-Aura enchantment is a creature with power and toughness each equal to its converted mana cost. It's still an enchantment.
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Fading 5 (This enchantment enters the battlefield with five fade counters on it. At the beginning of your upkeep, remove a fade counter from it. If you can't, sacrifice it.)
        // Remove a fade counter from Parallax Wave: Exile target creature.
        // When Parallax Wave leaves the battlefield, each player returns to the battlefield all cards he or she owns exiled with Parallax Wave.
        addCard(Zone.HAND, playerA, "Parallax Wave");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Parallax Wave");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Parallax Wave");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Parallax Wave", 1);        
        assertCounterCount("Parallax Wave", CounterType.FADE, 5);
        assertExileCount("Silvercoat Lion", 1); // The lion is still exiled
        
    }
    
    @Test
    public void testFirstAndSecondExileHandlingOfItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Parallax Wave");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Parallax Wave");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Parallax Wave");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Pillarfield Ox");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Remove a fade counter from {this}: Exile target creature", "Parallax Wave");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Parallax Wave", 1);        
        
        assertCounterCount("Parallax Wave", CounterType.FADE, 5);
        
        assertExileCount("Silvercoat Lion", 1); // The Lion is exiled and never returns
        assertExileCount("Pillarfield Ox", 1); // The Ox is exiled and never returns
        
    }    
}