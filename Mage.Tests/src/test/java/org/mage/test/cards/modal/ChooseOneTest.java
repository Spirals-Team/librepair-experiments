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
package org.mage.test.cards.modal;

import mage.abilities.keyword.SwampwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ChooseOneTest extends CardTestPlayerBase {

    @Test
    public void testFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.HAND, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", playerB);
        setModeChoice(playerA, "1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void testSecondMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", "Silvercoat Lion");
        setModeChoice(playerA, "2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 1);
    }

    @Test
    public void testThirdMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Choose one
        // - Target player discards a card
        // - Target creature gets +2/-1 until end of turn.
        // - Target creature gains swampwalk until end of turn.
        addCard(Zone.HAND, playerA, "Funeral Charm"); // Instant {B}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Funeral Charm", "Silvercoat Lion");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Funeral Charm", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), true);
    }
}
