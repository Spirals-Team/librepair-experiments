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
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public class Theros extends ExpansionSet {

    private static final Theros instance = new Theros();

    public static Theros getInstance() {
        return instance;
    }

    private Theros() {
        super("Theros", "THS", ExpansionSet.buildDate(2013, 9, 27), SetType.EXPANSION);
        this.blockName = "Theros";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abhorrent Overlord", 75, Rarity.RARE, mage.cards.a.AbhorrentOverlord.class));
        cards.add(new SetCardInfo("Agent of Horizons", 148, Rarity.COMMON, mage.cards.a.AgentOfHorizons.class));
        cards.add(new SetCardInfo("Agent of the Fates", 76, Rarity.RARE, mage.cards.a.AgentOfTheFates.class));
        cards.add(new SetCardInfo("Akroan Crusader", 111, Rarity.COMMON, mage.cards.a.AkroanCrusader.class));
        cards.add(new SetCardInfo("Akroan Hoplite", 185, Rarity.UNCOMMON, mage.cards.a.AkroanHoplite.class));
        cards.add(new SetCardInfo("Akroan Horse", 210, Rarity.RARE, mage.cards.a.AkroanHorse.class));
        cards.add(new SetCardInfo("Anax and Cymede", 186, Rarity.RARE, mage.cards.a.AnaxAndCymede.class));
        cards.add(new SetCardInfo("Anger of the Gods", 112, Rarity.RARE, mage.cards.a.AngerOfTheGods.class));
        cards.add(new SetCardInfo("Annul", 38, Rarity.COMMON, mage.cards.a.Annul.class));
        cards.add(new SetCardInfo("Anthousa, Setessan Hero", 149, Rarity.RARE, mage.cards.a.AnthousaSetessanHero.class));
        cards.add(new SetCardInfo("Anvilwrought Raptor", 211, Rarity.UNCOMMON, mage.cards.a.AnvilwroughtRaptor.class));
        cards.add(new SetCardInfo("Aqueous Form", 39, Rarity.COMMON, mage.cards.a.AqueousForm.class));
        cards.add(new SetCardInfo("Arbor Colossus", 150, Rarity.RARE, mage.cards.a.ArborColossus.class));
        cards.add(new SetCardInfo("Arena Athlete", 113, Rarity.UNCOMMON, mage.cards.a.ArenaAthlete.class));
        cards.add(new SetCardInfo("Artisan of Forms", 40, Rarity.RARE, mage.cards.a.ArtisanOfForms.class));
        cards.add(new SetCardInfo("Artisan's Sorrow", 151, Rarity.UNCOMMON, mage.cards.a.ArtisansSorrow.class));
        cards.add(new SetCardInfo("Ashen Rider", 187, Rarity.MYTHIC, mage.cards.a.AshenRider.class));
        cards.add(new SetCardInfo("Ashiok, Nightmare Weaver", 188, Rarity.MYTHIC, mage.cards.a.AshiokNightmareWeaver.class));
        cards.add(new SetCardInfo("Asphodel Wanderer", 77, Rarity.COMMON, mage.cards.a.AsphodelWanderer.class));
        cards.add(new SetCardInfo("Baleful Eidolon", 78, Rarity.COMMON, mage.cards.b.BalefulEidolon.class));
        cards.add(new SetCardInfo("Battlewise Hoplite", 189, Rarity.UNCOMMON, mage.cards.b.BattlewiseHoplite.class));
        cards.add(new SetCardInfo("Battlewise Valor", 1, Rarity.COMMON, mage.cards.b.BattlewiseValor.class));
        cards.add(new SetCardInfo("Benthic Giant", 41, Rarity.COMMON, mage.cards.b.BenthicGiant.class));
        cards.add(new SetCardInfo("Bident of Thassa", 42, Rarity.RARE, mage.cards.b.BidentOfThassa.class));
        cards.add(new SetCardInfo("Blood-Toll Harpy", 79, Rarity.COMMON, mage.cards.b.BloodTollHarpy.class));
        cards.add(new SetCardInfo("Boon of Erebos", 80, Rarity.COMMON, mage.cards.b.BoonOfErebos.class));
        cards.add(new SetCardInfo("Boon Satyr", 152, Rarity.RARE, mage.cards.b.BoonSatyr.class));
        cards.add(new SetCardInfo("Borderland Minotaur", 114, Rarity.COMMON, mage.cards.b.BorderlandMinotaur.class));
        cards.add(new SetCardInfo("Boulderfall", 115, Rarity.COMMON, mage.cards.b.Boulderfall.class));
        cards.add(new SetCardInfo("Bow of Nylea", 153, Rarity.RARE, mage.cards.b.BowOfNylea.class));
        cards.add(new SetCardInfo("Breaching Hippocamp", 43, Rarity.COMMON, mage.cards.b.BreachingHippocamp.class));
        cards.add(new SetCardInfo("Bronze Sable", 212, Rarity.COMMON, mage.cards.b.BronzeSable.class));
        cards.add(new SetCardInfo("Burnished Hart", 213, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Cavalry Pegasus", 2, Rarity.COMMON, mage.cards.c.CavalryPegasus.class));
        cards.add(new SetCardInfo("Cavern Lampad", 81, Rarity.COMMON, mage.cards.c.CavernLampad.class));
        cards.add(new SetCardInfo("Celestial Archon", 3, Rarity.RARE, mage.cards.c.CelestialArchon.class));
        cards.add(new SetCardInfo("Centaur Battlemaster", 154, Rarity.UNCOMMON, mage.cards.c.CentaurBattlemaster.class));
        cards.add(new SetCardInfo("Chained to the Rocks", 4, Rarity.RARE, mage.cards.c.ChainedToTheRocks.class));
        cards.add(new SetCardInfo("Chosen by Heliod", 5, Rarity.COMMON, mage.cards.c.ChosenByHeliod.class));
        cards.add(new SetCardInfo("Chronicler of Heroes", 190, Rarity.UNCOMMON, mage.cards.c.ChroniclerOfHeroes.class));
        cards.add(new SetCardInfo("Coastline Chimera", 44, Rarity.COMMON, mage.cards.c.CoastlineChimera.class));
        cards.add(new SetCardInfo("Colossus of Akros", 214, Rarity.RARE, mage.cards.c.ColossusOfAkros.class));
        cards.add(new SetCardInfo("Commune with the Gods", 155, Rarity.COMMON, mage.cards.c.CommuneWithTheGods.class));
        cards.add(new SetCardInfo("Coordinated Assault", 116, Rarity.UNCOMMON, mage.cards.c.CoordinatedAssault.class));
        cards.add(new SetCardInfo("Crackling Triton", 45, Rarity.COMMON, mage.cards.c.CracklingTriton.class));
        cards.add(new SetCardInfo("Curse of the Swine", 46, Rarity.RARE, mage.cards.c.CurseOfTheSwine.class));
        cards.add(new SetCardInfo("Cutthroat Maneuver", 82, Rarity.UNCOMMON, mage.cards.c.CutthroatManeuver.class));
        cards.add(new SetCardInfo("Dark Betrayal", 83, Rarity.UNCOMMON, mage.cards.d.DarkBetrayal.class));
        cards.add(new SetCardInfo("Dauntless Onslaught", 6, Rarity.UNCOMMON, mage.cards.d.DauntlessOnslaught.class));
        cards.add(new SetCardInfo("Daxos of Meletis", 191, Rarity.RARE, mage.cards.d.DaxosOfMeletis.class));
        cards.add(new SetCardInfo("Deathbellow Raider", 117, Rarity.COMMON, mage.cards.d.DeathbellowRaider.class));
        cards.add(new SetCardInfo("Decorated Griffin", 7, Rarity.UNCOMMON, mage.cards.d.DecoratedGriffin.class));
        cards.add(new SetCardInfo("Defend the Hearth", 156, Rarity.COMMON, mage.cards.d.DefendTheHearth.class));
        cards.add(new SetCardInfo("Demolish", 118, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Destructive Revelry", 192, Rarity.UNCOMMON, mage.cards.d.DestructiveRevelry.class));
        cards.add(new SetCardInfo("Disciple of Phenax", 84, Rarity.COMMON, mage.cards.d.DiscipleOfPhenax.class));
        cards.add(new SetCardInfo("Dissolve", 47, Rarity.UNCOMMON, mage.cards.d.Dissolve.class));
        cards.add(new SetCardInfo("Divine Verdict", 8, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Dragon Mantle", 119, Rarity.COMMON, mage.cards.d.DragonMantle.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 9, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Ember Swallower", 120, Rarity.RARE, mage.cards.e.EmberSwallower.class));
        cards.add(new SetCardInfo("Ephara's Warden", 10, Rarity.COMMON, mage.cards.e.EpharasWarden.class));
        cards.add(new SetCardInfo("Erebos, God of the Dead", 85, Rarity.MYTHIC, mage.cards.e.ErebosGodOfTheDead.class));
        cards.add(new SetCardInfo("Erebos's Emissary", 86, Rarity.UNCOMMON, mage.cards.e.ErebossEmissary.class));
        cards.add(new SetCardInfo("Evangel of Heliod", 11, Rarity.UNCOMMON, mage.cards.e.EvangelOfHeliod.class));
        cards.add(new SetCardInfo("Fabled Hero", 12, Rarity.RARE, mage.cards.f.FabledHero.class));
        cards.add(new SetCardInfo("Fade into Antiquity", 157, Rarity.COMMON, mage.cards.f.FadeIntoAntiquity.class));
        cards.add(new SetCardInfo("Fanatic of Mogis", 121, Rarity.UNCOMMON, mage.cards.f.FanaticOfMogis.class));
        cards.add(new SetCardInfo("Fate Foretold", 48, Rarity.COMMON, mage.cards.f.FateForetold.class));
        cards.add(new SetCardInfo("Favored Hoplite", 13, Rarity.UNCOMMON, mage.cards.f.FavoredHoplite.class));
        cards.add(new SetCardInfo("Felhide Minotaur", 87, Rarity.COMMON, mage.cards.f.FelhideMinotaur.class));
        cards.add(new SetCardInfo("Feral Invocation", 158, Rarity.COMMON, mage.cards.f.FeralInvocation.class));
        cards.add(new SetCardInfo("Firedrinker Satyr", 122, Rarity.RARE, mage.cards.f.FiredrinkerSatyr.class));
        cards.add(new SetCardInfo("Flamecast Wheel", 215, Rarity.UNCOMMON, mage.cards.f.FlamecastWheel.class));
        cards.add(new SetCardInfo("Flamespeaker Adept", 123, Rarity.UNCOMMON, mage.cards.f.FlamespeakerAdept.class));
        cards.add(new SetCardInfo("Fleecemane Lion", 193, Rarity.RARE, mage.cards.f.FleecemaneLion.class));
        cards.add(new SetCardInfo("Fleetfeather Sandals", 216, Rarity.COMMON, mage.cards.f.FleetfeatherSandals.class));
        cards.add(new SetCardInfo("Fleshmad Steed", 88, Rarity.COMMON, mage.cards.f.FleshmadSteed.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gainsay", 49, Rarity.UNCOMMON, mage.cards.g.Gainsay.class));
        cards.add(new SetCardInfo("Gift of Immortality", 14, Rarity.RARE, mage.cards.g.GiftOfImmortality.class));
        cards.add(new SetCardInfo("Glare of Heresy", 15, Rarity.UNCOMMON, mage.cards.g.GlareOfHeresy.class));
        cards.add(new SetCardInfo("Gods Willing", 16, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 89, Rarity.COMMON, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Griptide", 50, Rarity.COMMON, mage.cards.g.Griptide.class));
        cards.add(new SetCardInfo("Guardians of Meletis", 217, Rarity.COMMON, mage.cards.g.GuardiansOfMeletis.class));
        cards.add(new SetCardInfo("Hammer of Purphoros", 124, Rarity.RARE, mage.cards.h.HammerOfPurphoros.class));
        cards.add(new SetCardInfo("Heliod, God of the Sun", 17, Rarity.MYTHIC, mage.cards.h.HeliodGodOfTheSun.class));
        cards.add(new SetCardInfo("Heliod's Emissary", 18, Rarity.UNCOMMON, mage.cards.h.HeliodsEmissary.class));
        cards.add(new SetCardInfo("Hero's Downfall", 90, Rarity.RARE, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Hopeful Eidolon", 19, Rarity.COMMON, mage.cards.h.HopefulEidolon.class));
        cards.add(new SetCardInfo("Horizon Chimera", 194, Rarity.UNCOMMON, mage.cards.h.HorizonChimera.class));
        cards.add(new SetCardInfo("Horizon Scholar", 51, Rarity.UNCOMMON, mage.cards.h.HorizonScholar.class));
        cards.add(new SetCardInfo("Hundred-Handed One", 20, Rarity.RARE, mage.cards.h.HundredHandedOne.class));
        cards.add(new SetCardInfo("Hunt the Hunter", 159, Rarity.UNCOMMON, mage.cards.h.HuntTheHunter.class));
        cards.add(new SetCardInfo("Hythonia the Cruel", 91, Rarity.MYTHIC, mage.cards.h.HythoniaTheCruel.class));
        cards.add(new SetCardInfo("Ill-Tempered Cyclops", 125, Rarity.COMMON, mage.cards.i.IllTemperedCyclops.class));
        cards.add(new SetCardInfo("Insatiable Harpy", 92, Rarity.UNCOMMON, mage.cards.i.InsatiableHarpy.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karametra's Acolyte", 160, Rarity.UNCOMMON, mage.cards.k.KarametrasAcolyte.class));
        cards.add(new SetCardInfo("Keepsake Gorgon", 93, Rarity.UNCOMMON, mage.cards.k.KeepsakeGorgon.class));
        cards.add(new SetCardInfo("Kragma Warcaller", 195, Rarity.UNCOMMON, mage.cards.k.KragmaWarcaller.class));
        cards.add(new SetCardInfo("Labyrinth Champion", 126, Rarity.RARE, mage.cards.l.LabyrinthChampion.class));
        cards.add(new SetCardInfo("Lagonna-Band Elder", 21, Rarity.COMMON, mage.cards.l.LagonnaBandElder.class));
        cards.add(new SetCardInfo("Lash of the Whip", 94, Rarity.COMMON, mage.cards.l.LashOfTheWhip.class));
        cards.add(new SetCardInfo("Last Breath", 22, Rarity.COMMON, mage.cards.l.LastBreath.class));
        cards.add(new SetCardInfo("Leafcrown Dryad", 161, Rarity.COMMON, mage.cards.l.LeafcrownDryad.class));
        cards.add(new SetCardInfo("Leonin Snarecaster", 23, Rarity.COMMON, mage.cards.l.LeoninSnarecaster.class));
        cards.add(new SetCardInfo("Lightning Strike", 127, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Loathsome Catoblepas", 95, Rarity.COMMON, mage.cards.l.LoathsomeCatoblepas.class));
        cards.add(new SetCardInfo("Lost in a Labyrinth", 52, Rarity.COMMON, mage.cards.l.LostInALabyrinth.class));
        cards.add(new SetCardInfo("Magma Jet", 128, Rarity.UNCOMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("March of the Returned", 96, Rarity.COMMON, mage.cards.m.MarchOfTheReturned.class));
        cards.add(new SetCardInfo("Master of Waves", 53, Rarity.MYTHIC, mage.cards.m.MasterOfWaves.class));
        cards.add(new SetCardInfo("Medomai the Ageless", 196, Rarity.MYTHIC, mage.cards.m.MedomaiTheAgeless.class));
        cards.add(new SetCardInfo("Meletis Charlatan", 54, Rarity.RARE, mage.cards.m.MeletisCharlatan.class));
        cards.add(new SetCardInfo("Messenger's Speed", 129, Rarity.COMMON, mage.cards.m.MessengersSpeed.class));
        cards.add(new SetCardInfo("Minotaur Skullcleaver", 130, Rarity.COMMON, mage.cards.m.MinotaurSkullcleaver.class));
        cards.add(new SetCardInfo("Mistcutter Hydra", 162, Rarity.RARE, mage.cards.m.MistcutterHydra.class));
        cards.add(new SetCardInfo("Mnemonic Wall", 55, Rarity.COMMON, mage.cards.m.MnemonicWall.class));
        cards.add(new SetCardInfo("Mogis's Marauder", 97, Rarity.UNCOMMON, mage.cards.m.MogissMarauder.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nemesis of Mortals", 163, Rarity.UNCOMMON, mage.cards.n.NemesisOfMortals.class));
        cards.add(new SetCardInfo("Nessian Asp", 164, Rarity.COMMON, mage.cards.n.NessianAsp.class));
        cards.add(new SetCardInfo("Nessian Courser", 165, Rarity.COMMON, mage.cards.n.NessianCourser.class));
        cards.add(new SetCardInfo("Nighthowler", 98, Rarity.RARE, mage.cards.n.Nighthowler.class));
        cards.add(new SetCardInfo("Nimbus Naiad", 56, Rarity.COMMON, mage.cards.n.NimbusNaiad.class));
        cards.add(new SetCardInfo("Nykthos, Shrine to Nyx", 223, Rarity.RARE, mage.cards.n.NykthosShrineToNyx.class));
        cards.add(new SetCardInfo("Nylea, God of the Hunt", 166, Rarity.MYTHIC, mage.cards.n.NyleaGodOfTheHunt.class));
        cards.add(new SetCardInfo("Nylea's Disciple", 167, Rarity.COMMON, mage.cards.n.NyleasDisciple.class));
        cards.add(new SetCardInfo("Nylea's Emissary", 168, Rarity.UNCOMMON, mage.cards.n.NyleasEmissary.class));
        cards.add(new SetCardInfo("Nylea's Presence", 169, Rarity.COMMON, mage.cards.n.NyleasPresence.class));
        cards.add(new SetCardInfo("Observant Alseid", 24, Rarity.COMMON, mage.cards.o.ObservantAlseid.class));
        cards.add(new SetCardInfo("Omenspeaker", 57, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Opaline Unicorn", 218, Rarity.COMMON, mage.cards.o.OpalineUnicorn.class));
        cards.add(new SetCardInfo("Ordeal of Erebos", 99, Rarity.UNCOMMON, mage.cards.o.OrdealOfErebos.class));
        cards.add(new SetCardInfo("Ordeal of Heliod", 25, Rarity.UNCOMMON, mage.cards.o.OrdealOfHeliod.class));
        cards.add(new SetCardInfo("Ordeal of Nylea", 170, Rarity.UNCOMMON, mage.cards.o.OrdealOfNylea.class));
        cards.add(new SetCardInfo("Ordeal of Purphoros", 131, Rarity.UNCOMMON, mage.cards.o.OrdealOfPurphoros.class));
        cards.add(new SetCardInfo("Ordeal of Thassa", 58, Rarity.UNCOMMON, mage.cards.o.OrdealOfThassa.class));
        cards.add(new SetCardInfo("Peak Eruption", 132, Rarity.UNCOMMON, mage.cards.p.PeakEruption.class));
        cards.add(new SetCardInfo("Phalanx Leader", 26, Rarity.UNCOMMON, mage.cards.p.PhalanxLeader.class));
        cards.add(new SetCardInfo("Pharika's Cure", 100, Rarity.COMMON, mage.cards.p.PharikasCure.class));
        cards.add(new SetCardInfo("Pharika's Mender", 197, Rarity.UNCOMMON, mage.cards.p.PharikasMender.class));
        cards.add(new SetCardInfo("Pheres-Band Centaurs", 171, Rarity.COMMON, mage.cards.p.PheresBandCentaurs.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polis Crusher", 198, Rarity.RARE, mage.cards.p.PolisCrusher.class));
        cards.add(new SetCardInfo("Polukranos, World Eater", 172, Rarity.MYTHIC, mage.cards.p.PolukranosWorldEater.class));
        cards.add(new SetCardInfo("Portent of Betrayal", 133, Rarity.COMMON, mage.cards.p.PortentOfBetrayal.class));
        cards.add(new SetCardInfo("Prescient Chimera", 59, Rarity.COMMON, mage.cards.p.PrescientChimera.class));
        cards.add(new SetCardInfo("Priest of Iroas", 134, Rarity.COMMON, mage.cards.p.PriestOfIroas.class));
        cards.add(new SetCardInfo("Prognostic Sphinx", 60, Rarity.RARE, mage.cards.p.PrognosticSphinx.class));
        cards.add(new SetCardInfo("Prophet of Kruphix", 199, Rarity.RARE, mage.cards.p.ProphetOfKruphix.class));
        cards.add(new SetCardInfo("Prowler's Helm", 219, Rarity.UNCOMMON, mage.cards.p.ProwlersHelm.class));
        cards.add(new SetCardInfo("Psychic Intrusion", 200, Rarity.RARE, mage.cards.p.PsychicIntrusion.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 135, Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Purphoros's Emissary", 136, Rarity.UNCOMMON, mage.cards.p.PurphorossEmissary.class));
        cards.add(new SetCardInfo("Pyxis of Pandemonium", 220, Rarity.RARE, mage.cards.p.PyxisOfPandemonium.class));
        cards.add(new SetCardInfo("Rageblood Shaman", 138, Rarity.RARE, mage.cards.r.RagebloodShaman.class));
        cards.add(new SetCardInfo("Rage of Purphoros", 137, Rarity.COMMON, mage.cards.r.RageOfPurphoros.class));
        cards.add(new SetCardInfo("Ray of Dissolution", 27, Rarity.COMMON, mage.cards.r.RayOfDissolution.class));
        cards.add(new SetCardInfo("Read the Bones", 101, Rarity.COMMON, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Reaper of the Wilds", 201, Rarity.RARE, mage.cards.r.ReaperOfTheWilds.class));
        cards.add(new SetCardInfo("Rescue from the Underworld", 102, Rarity.UNCOMMON, mage.cards.r.RescueFromTheUnderworld.class));
        cards.add(new SetCardInfo("Returned Centaur", 103, Rarity.COMMON, mage.cards.r.ReturnedCentaur.class));
        cards.add(new SetCardInfo("Returned Phalanx", 104, Rarity.COMMON, mage.cards.r.ReturnedPhalanx.class));
        cards.add(new SetCardInfo("Reverent Hunter", 173, Rarity.RARE, mage.cards.r.ReverentHunter.class));
        cards.add(new SetCardInfo("Satyr Hedonist", 174, Rarity.COMMON, mage.cards.s.SatyrHedonist.class));
        cards.add(new SetCardInfo("Satyr Piper", 175, Rarity.UNCOMMON, mage.cards.s.SatyrPiper.class));
        cards.add(new SetCardInfo("Satyr Rambler", 139, Rarity.COMMON, mage.cards.s.SatyrRambler.class));
        cards.add(new SetCardInfo("Savage Surge", 176, Rarity.COMMON, mage.cards.s.SavageSurge.class));
        cards.add(new SetCardInfo("Scholar of Athreos", 28, Rarity.COMMON, mage.cards.s.ScholarOfAthreos.class));
        cards.add(new SetCardInfo("Scourgemark", 105, Rarity.COMMON, mage.cards.s.Scourgemark.class));
        cards.add(new SetCardInfo("Sea God's Revenge", 61, Rarity.UNCOMMON, mage.cards.s.SeaGodsRevenge.class));
        cards.add(new SetCardInfo("Sealock Monster", 62, Rarity.UNCOMMON, mage.cards.s.SealockMonster.class));
        cards.add(new SetCardInfo("Sedge Scorpion", 177, Rarity.COMMON, mage.cards.s.SedgeScorpion.class));
        cards.add(new SetCardInfo("Sentry of the Underworld", 202, Rarity.UNCOMMON, mage.cards.s.SentryOfTheUnderworld.class));
        cards.add(new SetCardInfo("Setessan Battle Priest", 29, Rarity.COMMON, mage.cards.s.SetessanBattlePriest.class));
        cards.add(new SetCardInfo("Setessan Griffin", 30, Rarity.COMMON, mage.cards.s.SetessanGriffin.class));
        cards.add(new SetCardInfo("Shipbreaker Kraken", 63, Rarity.RARE, mage.cards.s.ShipbreakerKraken.class));
        cards.add(new SetCardInfo("Shipwreck Singer", 203, Rarity.UNCOMMON, mage.cards.s.ShipwreckSinger.class));
        cards.add(new SetCardInfo("Shredding Winds", 178, Rarity.COMMON, mage.cards.s.ShreddingWinds.class));
        cards.add(new SetCardInfo("Silent Artisan", 31, Rarity.COMMON, mage.cards.s.SilentArtisan.class));
        cards.add(new SetCardInfo("Sip of Hemlock", 106, Rarity.COMMON, mage.cards.s.SipOfHemlock.class));
        cards.add(new SetCardInfo("Soldier of the Pantheon", 32, Rarity.RARE, mage.cards.s.SoldierOfThePantheon.class));
        cards.add(new SetCardInfo("Spark Jolt", 140, Rarity.COMMON, mage.cards.s.SparkJolt.class));
        cards.add(new SetCardInfo("Spear of Heliod", 33, Rarity.RARE, mage.cards.s.SpearOfHeliod.class));
        cards.add(new SetCardInfo("Spearpoint Oread", 141, Rarity.COMMON, mage.cards.s.SpearpointOread.class));
        cards.add(new SetCardInfo("Spellheart Chimera", 204, Rarity.UNCOMMON, mage.cards.s.SpellheartChimera.class));
        cards.add(new SetCardInfo("Staunch-Hearted Warrior", 179, Rarity.COMMON, mage.cards.s.StaunchHeartedWarrior.class));
        cards.add(new SetCardInfo("Steam Augury", 205, Rarity.RARE, mage.cards.s.SteamAugury.class));
        cards.add(new SetCardInfo("Stoneshock Giant", 142, Rarity.UNCOMMON, mage.cards.s.StoneshockGiant.class));
        cards.add(new SetCardInfo("Stormbreath Dragon", 143, Rarity.MYTHIC, mage.cards.s.StormbreathDragon.class));
        cards.add(new SetCardInfo("Stymied Hopes", 64, Rarity.COMMON, mage.cards.s.StymiedHopes.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swan Song", 65, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Sylvan Caryatid", 180, Rarity.RARE, mage.cards.s.SylvanCaryatid.class));
        cards.add(new SetCardInfo("Temple of Abandon", 224, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Deceit", 225, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Mystery", 226, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Silence", 227, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 228, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Thassa, God of the Sea", 66, Rarity.MYTHIC, mage.cards.t.ThassaGodOfTheSea.class));
        cards.add(new SetCardInfo("Thassa's Bounty", 67, Rarity.COMMON, mage.cards.t.ThassasBounty.class));
        cards.add(new SetCardInfo("Thassa's Emissary", 68, Rarity.UNCOMMON, mage.cards.t.ThassasEmissary.class));
        cards.add(new SetCardInfo("Thoughtseize", 107, Rarity.RARE, mage.cards.t.Thoughtseize.class));
        cards.add(new SetCardInfo("Time to Feed", 181, Rarity.COMMON, mage.cards.t.TimeToFeed.class));
        cards.add(new SetCardInfo("Titan of Eternal Fire", 144, Rarity.RARE, mage.cards.t.TitanOfEternalFire.class));
        cards.add(new SetCardInfo("Titan's Strength", 145, Rarity.COMMON, mage.cards.t.TitansStrength.class));
        cards.add(new SetCardInfo("Tormented Hero", 108, Rarity.UNCOMMON, mage.cards.t.TormentedHero.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 221, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Traveling Philosopher", 34, Rarity.COMMON, mage.cards.t.TravelingPhilosopher.class));
        cards.add(new SetCardInfo("Triad of Fates", 206, Rarity.RARE, mage.cards.t.TriadOfFates.class));
        cards.add(new SetCardInfo("Triton Fortune Hunter", 69, Rarity.UNCOMMON, mage.cards.t.TritonFortuneHunter.class));
        cards.add(new SetCardInfo("Triton Shorethief", 70, Rarity.COMMON, mage.cards.t.TritonShorethief.class));
        cards.add(new SetCardInfo("Triton Tactics", 71, Rarity.UNCOMMON, mage.cards.t.TritonTactics.class));
        cards.add(new SetCardInfo("Two-Headed Cerberus", 146, Rarity.COMMON, mage.cards.t.TwoHeadedCerberus.class));
        cards.add(new SetCardInfo("Tymaret, the Murder King", 207, Rarity.RARE, mage.cards.t.TymaretTheMurderKing.class));
        cards.add(new SetCardInfo("Underworld Cerberus", 208, Rarity.MYTHIC, mage.cards.u.UnderworldCerberus.class));
        cards.add(new SetCardInfo("Unknown Shores", 229, Rarity.COMMON, mage.cards.u.UnknownShores.class));
        cards.add(new SetCardInfo("Vanquish the Foul", 35, Rarity.UNCOMMON, mage.cards.v.VanquishTheFoul.class));
        cards.add(new SetCardInfo("Vaporkin", 72, Rarity.COMMON, mage.cards.v.Vaporkin.class));
        cards.add(new SetCardInfo("Viper's Kiss", 109, Rarity.COMMON, mage.cards.v.VipersKiss.class));
        cards.add(new SetCardInfo("Voyage's End", 73, Rarity.COMMON, mage.cards.v.VoyagesEnd.class));
        cards.add(new SetCardInfo("Voyaging Satyr", 182, Rarity.COMMON, mage.cards.v.VoyagingSatyr.class));
        cards.add(new SetCardInfo("Vulpine Goliath", 183, Rarity.COMMON, mage.cards.v.VulpineGoliath.class));
        cards.add(new SetCardInfo("Warriors' Lesson", 184, Rarity.UNCOMMON, mage.cards.w.WarriorsLesson.class));
        cards.add(new SetCardInfo("Wavecrash Triton", 74, Rarity.COMMON, mage.cards.w.WavecrashTriton.class));
        cards.add(new SetCardInfo("Whip of Erebos", 110, Rarity.RARE, mage.cards.w.WhipOfErebos.class));
        cards.add(new SetCardInfo("Wild Celebrants", 147, Rarity.COMMON, mage.cards.w.WildCelebrants.class));
        cards.add(new SetCardInfo("Wingsteed Rider", 36, Rarity.COMMON, mage.cards.w.WingsteedRider.class));
        cards.add(new SetCardInfo("Witches' Eye", 222, Rarity.UNCOMMON, mage.cards.w.WitchesEye.class));
        cards.add(new SetCardInfo("Xenagos, the Reveler", 209, Rarity.MYTHIC, mage.cards.x.XenagosTheReveler.class));
        cards.add(new SetCardInfo("Yoked Ox", 37, Rarity.COMMON, mage.cards.y.YokedOx.class));
    }
}
