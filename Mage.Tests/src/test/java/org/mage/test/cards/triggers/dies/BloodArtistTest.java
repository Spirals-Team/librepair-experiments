
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 * Whenever Blood Artist or another creature dies, target player loses 1 life
 * and you gain 1 life.
 */
public class BloodArtistTest extends CardTestPlayerBase {

    /**
     * Tests that whenever Blood Artist goes to graveyard, it would trigger its
     * ability. Tests that after Blood Artist went to graveyard, his ability
     * doesn't work anymore.
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 2);
        addCard(Zone.GRAVEYARD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Bloodflow Connoisseur", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blood Artist");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Bloodflow Connoisseur");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 17);
    }

    @Test
    public void testWithBoneSplinters() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }

    @Test
    public void testWithBoneSplinters2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Blood Artist");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Blood Artist", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertLife(playerA, 21); // For sacrifice both Blood Artist trigger, for destoy effect only one ist left
        assertLife(playerB, 19);
    }

    @Test
    public void testWithBoneSplinters3() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terror", 1); // Instant - {1}{B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Blood Artist");
        // Blood Artist may no longer trigger from destroyed creature because already in the graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", "Silvercoat Lion", "Bone Splinters");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Terror", 1);
        assertGraveyardCount(playerA, "Blood Artist", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertLife(playerA, 21); // For sacrifice both Blood Artist trigger, for destoy effect only one ist left
        assertLife(playerB, 19);
    }

}
