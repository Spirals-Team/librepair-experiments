/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
 * @author fireshoes
 */
public class ModernMasters2017 extends ExpansionSet {

    private static final ModernMasters2017 instance = new ModernMasters2017();

    public static ModernMasters2017 getInstance() {
        return instance;
    }

    private ModernMasters2017() {
        super("Modern Masters 2017", "MM3", ExpansionSet.buildDate(2017, 3, 17), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abrupt Decay", 146, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Abyssal Specter", 59, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class));
        cards.add(new SetCardInfo("Advent of the Wurm", 147, Rarity.RARE, mage.cards.a.AdventOfTheWurm.class));
        cards.add(new SetCardInfo("Aethermage's Touch", 148, Rarity.RARE, mage.cards.a.AethermagesTouch.class));
        cards.add(new SetCardInfo("Aethertow", 205, Rarity.COMMON, mage.cards.a.Aethertow.class));
        cards.add(new SetCardInfo("Agent of Masks", 149, Rarity.UNCOMMON, mage.cards.a.AgentOfMasks.class));
        cards.add(new SetCardInfo("Agony Warp", 150, Rarity.COMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Ancient Grudge", 88, Rarity.UNCOMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Arachnus Spinner", 117, Rarity.UNCOMMON, mage.cards.a.ArachnusSpinner.class));
        cards.add(new SetCardInfo("Arachnus Web", 118, Rarity.COMMON, mage.cards.a.ArachnusWeb.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 228, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Arid Mesa", 229, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Attended Knight", 1, Rarity.COMMON, mage.cards.a.AttendedKnight.class));
        cards.add(new SetCardInfo("Auger Spree", 151, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Augur of Bolas", 30, Rarity.COMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 119, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 230, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Azorius Signet", 215, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Azure Mage", 31, Rarity.UNCOMMON, mage.cards.a.AzureMage.class));
        cards.add(new SetCardInfo("Baloth Cage Trap", 120, Rarity.UNCOMMON, mage.cards.b.BalothCageTrap.class));
        cards.add(new SetCardInfo("Banishing Stroke", 2, Rarity.UNCOMMON, mage.cards.b.BanishingStroke.class));
        cards.add(new SetCardInfo("Basilisk Collar", 216, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 89, Rarity.COMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Blade Splicer", 3, Rarity.RARE, mage.cards.b.BladeSplicer.class));
        cards.add(new SetCardInfo("Blood Moon", 90, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Bone Splinters", 60, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bonfire of the Damned", 91, Rarity.MYTHIC, mage.cards.b.BonfireOfTheDamned.class));
        cards.add(new SetCardInfo("Boros Guildgate", 231, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Boros Reckoner", 206, Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Boros Signet", 217, Rarity.UNCOMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Bronzebeak Moa", 152, Rarity.UNCOMMON, mage.cards.b.BronzebeakMoa.class));
        cards.add(new SetCardInfo("Broodmate Dragon", 153, Rarity.RARE, mage.cards.b.BroodmateDragon.class));
        cards.add(new SetCardInfo("Burning-Tree Emissary", 207, Rarity.COMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Cackling Counterpart", 32, Rarity.RARE, mage.cards.c.CacklingCounterpart.class));
        cards.add(new SetCardInfo("Call of the Conclave", 154, Rarity.COMMON, mage.cards.c.CallOfTheConclave.class));
        cards.add(new SetCardInfo("Call of the Herd", 121, Rarity.RARE, mage.cards.c.CallOfTheHerd.class));
        cards.add(new SetCardInfo("Carnage Gladiator", 155, Rarity.UNCOMMON, mage.cards.c.CarnageGladiator.class));
        cards.add(new SetCardInfo("Cavern of Souls", 232, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Centaur Healer", 156, Rarity.COMMON, mage.cards.c.CentaurHealer.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 92, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Coiling Oracle", 157, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Compulsive Research", 33, Rarity.UNCOMMON, mage.cards.c.CompulsiveResearch.class));
        cards.add(new SetCardInfo("Cower in Fear", 62, Rarity.COMMON, mage.cards.c.CowerInFear.class));
        cards.add(new SetCardInfo("Corpse Connoisseur", 61, Rarity.UNCOMMON, mage.cards.c.CorpseConnoisseur.class));
        cards.add(new SetCardInfo("Craterhoof Behemoth", 122, Rarity.MYTHIC, mage.cards.c.CraterhoofBehemoth.class));
        cards.add(new SetCardInfo("Crippling Chill", 34, Rarity.COMMON, mage.cards.c.CripplingChill.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 158, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 233, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Cyclonic Rift", 35, Rarity.RARE, mage.cards.c.CyclonicRift.class));
        cards.add(new SetCardInfo("Damnation", 63, Rarity.RARE, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Damping Matrix", 218, Rarity.RARE, mage.cards.d.DampingMatrix.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", 123, Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Death's Shadow", 64, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Deadeye Navigator", 36, Rarity.RARE, mage.cards.d.DeadeyeNavigator.class));
        cards.add(new SetCardInfo("Delirium Skeins", 65, Rarity.COMMON, mage.cards.d.DeliriumSkeins.class));
        cards.add(new SetCardInfo("Deputy of Acquittals", 159, Rarity.COMMON, mage.cards.d.DeputyOfAcquittals.class));
        cards.add(new SetCardInfo("Desecration Demon", 66, Rarity.RARE, mage.cards.d.DesecrationDemon.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 234, Rarity.COMMON, mage.cards.d.DimirGuildgate.class));
        cards.add(new SetCardInfo("Dimir Signet", 219, Rarity.UNCOMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Dinrova Horror", 160, Rarity.COMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Domri Rade", 161, Rarity.MYTHIC, mage.cards.d.DomriRade.class));
        cards.add(new SetCardInfo("Dragon Fodder", 93, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 67, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Druid's Deliverance", 124, Rarity.COMMON, mage.cards.d.DruidsDeliverance.class));
        cards.add(new SetCardInfo("Dynacharge", 94, Rarity.COMMON, mage.cards.d.Dynacharge.class));
        cards.add(new SetCardInfo("Entomber Exarch", 68, Rarity.UNCOMMON, mage.cards.e.EntomberExarch.class));
        cards.add(new SetCardInfo("Entreat the Angels", 4, Rarity.MYTHIC, mage.cards.e.EntreatTheAngels.class));
        cards.add(new SetCardInfo("Evil Twin", 162, Rarity.RARE, mage.cards.e.EvilTwin.class));
        cards.add(new SetCardInfo("Explore", 125, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Extractor Demon", 69, Rarity.RARE, mage.cards.e.ExtractorDemon.class));
        cards.add(new SetCardInfo("Eyes in the Skies", 5, Rarity.COMMON, mage.cards.e.EyesInTheSkies.class));
        cards.add(new SetCardInfo("Falkenrath Aristocrat", 163, Rarity.RARE, mage.cards.f.FalkenrathAristocrat.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 70, Rarity.COMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Familiar's Ruse", 37, Rarity.UNCOMMON, mage.cards.f.FamiliarsRuse.class));
        cards.add(new SetCardInfo("Fiery Justice", 164, Rarity.RARE, mage.cards.f.FieryJustice.class));
        cards.add(new SetCardInfo("Fists of Ironwood", 126, Rarity.COMMON, mage.cards.f.FistsOfIronwood.class));
        cards.add(new SetCardInfo("Flickerwisp", 6, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 38, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Gaea's Anthem", 127, Rarity.UNCOMMON, mage.cards.g.GaeasAnthem.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 165, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Ghostly Flicker", 39, Rarity.COMMON, mage.cards.g.GhostlyFlicker.class));
        cards.add(new SetCardInfo("Giantbaiting", 208, Rarity.COMMON, mage.cards.g.Giantbaiting.class));
        cards.add(new SetCardInfo("Gideon's Lawkeeper", 7, Rarity.COMMON, mage.cards.g.GideonsLawkeeper.class));
        cards.add(new SetCardInfo("Gift of Orzhova", 209, Rarity.COMMON, mage.cards.g.GiftOfOrzhova.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 40, Rarity.RARE, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Gnawing Zombie", 71, Rarity.COMMON, mage.cards.g.GnawingZombie.class));
        cards.add(new SetCardInfo("Goblin Assault", 95, Rarity.UNCOMMON, mage.cards.g.GoblinAssault.class));
        cards.add(new SetCardInfo("Goblin Electromancer", 166, Rarity.COMMON, mage.cards.g.GoblinElectromancer.class));
        cards.add(new SetCardInfo("Goblin Guide", 96, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Golgari Germination", 167, Rarity.UNCOMMON, mage.cards.g.GolgariGermination.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 235, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Golgari Rotwurm", 168, Rarity.COMMON, mage.cards.g.GolgariRotwurm.class));
        cards.add(new SetCardInfo("Golgari Signet", 220, Rarity.UNCOMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Graceful Reprieve", 8, Rarity.COMMON, mage.cards.g.GracefulReprieve.class));
        cards.add(new SetCardInfo("Grafdigger's Cage", 221, Rarity.RARE, mage.cards.g.GrafdiggersCage.class));
        cards.add(new SetCardInfo("Grasp of Phantoms", 41, Rarity.COMMON, mage.cards.g.GraspOfPhantoms.class));
        cards.add(new SetCardInfo("Griselbrand", 72, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Grisly Spectacle", 73, Rarity.COMMON, mage.cards.g.GrislySpectacle.class));
        cards.add(new SetCardInfo("Grixis Slavedriver", 74, Rarity.COMMON, mage.cards.g.GrixisSlavedriver.class));
        cards.add(new SetCardInfo("Ground Assault", 169, Rarity.COMMON, mage.cards.g.GroundAssault.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 236, Rarity.COMMON, mage.cards.g.GruulGuildgate.class));
        cards.add(new SetCardInfo("Gruul Signet", 222, Rarity.UNCOMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Gruul War Chant", 170, Rarity.UNCOMMON, mage.cards.g.GruulWarChant.class));
        cards.add(new SetCardInfo("Hanweir Lancer", 97, Rarity.COMMON, mage.cards.h.HanweirLancer.class));
        cards.add(new SetCardInfo("Harmonize", 128, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Hellrider", 98, Rarity.RARE, mage.cards.h.Hellrider.class));
        cards.add(new SetCardInfo("Hungry Spriggan", 129, Rarity.COMMON, mage.cards.h.HungrySpriggan.class));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 75, Rarity.UNCOMMON, mage.cards.i.InquisitionOfKozilek.class));
        cards.add(new SetCardInfo("Intangible Virtue", 9, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Izzet Charm", 171, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Izzet Guildgate", 237, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class));
        cards.add(new SetCardInfo("Izzet Signet", 223, Rarity.UNCOMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Jungle Shrine", 238, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Kathari Bomber", 172, Rarity.COMMON, mage.cards.k.KathariBomber.class));
        cards.add(new SetCardInfo("Kor Hookmaster", 10, Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 11, Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Kraken Hatchling", 42, Rarity.COMMON, mage.cards.k.KrakenHatchling.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 76, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Lingering Souls", 12, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Linvala, Keeper of Silence", 13, Rarity.MYTHIC, mage.cards.l.LinvalaKeeperOfSilence.class));
        cards.add(new SetCardInfo("Lone Missionary", 14, Rarity.COMMON, mage.cards.l.LoneMissionary.class));
        cards.add(new SetCardInfo("Madcap Skills", 99, Rarity.COMMON, mage.cards.m.MadcapSkills.class));
        cards.add(new SetCardInfo("Magma Jet", 100, Rarity.COMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Marsh Flats", 239, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Master Splicer", 15, Rarity.UNCOMMON, mage.cards.m.MasterSplicer.class));
        cards.add(new SetCardInfo("Might of Old Krosa", 130, Rarity.UNCOMMON, mage.cards.m.MightOfOldKrosa.class));
        cards.add(new SetCardInfo("Mind Shatter", 77, Rarity.RARE, mage.cards.m.MindShatter.class));
        cards.add(new SetCardInfo("Mist Raven", 43, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Misty Rainforest", 240, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 210, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Mizzium Mortars", 101, Rarity.RARE, mage.cards.m.MizziumMortars.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 102, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Molten Rain", 103, Rarity.UNCOMMON, mage.cards.m.MoltenRain.class));
        cards.add(new SetCardInfo("Momentary Blink", 16, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Moroii", 173, Rarity.UNCOMMON, mage.cards.m.Moroii.class));
        cards.add(new SetCardInfo("Mortician Beetle", 78, Rarity.COMMON, mage.cards.m.MorticianBeetle.class));
        cards.add(new SetCardInfo("Mudbutton Torchrunner", 104, Rarity.COMMON, mage.cards.m.MudbuttonTorchrunner.class));
        cards.add(new SetCardInfo("Mystic Genesis", 174, Rarity.UNCOMMON, mage.cards.m.MysticGenesis.class));
        cards.add(new SetCardInfo("Mystical Teachings", 44, Rarity.COMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Night Terrors", 79, Rarity.COMMON, mage.cards.n.NightTerrors.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Dracogenius", 175, Rarity.RARE, mage.cards.n.NivMizzetDracogenius.class));
        cards.add(new SetCardInfo("Obzedat, Ghost Council", 176, Rarity.RARE, mage.cards.o.ObzedatGhostCouncil.class));
        cards.add(new SetCardInfo("Ogre Jailbreaker", 80, Rarity.COMMON, mage.cards.o.OgreJailbreaker.class));
        cards.add(new SetCardInfo("Olivia Voldaren", 177, Rarity.MYTHIC, mage.cards.o.OliviaVoldaren.class));
        cards.add(new SetCardInfo("Opportunity", 45, Rarity.UNCOMMON, mage.cards.o.Opportunity.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 241, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Orzhov Signet", 224, Rarity.UNCOMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Past in Flames", 105, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Path to Exile", 17, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Penumbra Spider", 131, Rarity.COMMON, mage.cards.p.PenumbraSpider.class));
        cards.add(new SetCardInfo("Phantasmal Image", 46, Rarity.RARE, mage.cards.p.PhantasmalImage.class));
        cards.add(new SetCardInfo("Pilfered Plans", 178, Rarity.COMMON, mage.cards.p.PilferedPlans.class));
        cards.add(new SetCardInfo("Pit Keeper", 81, Rarity.COMMON, mage.cards.p.PitKeeper.class));
        cards.add(new SetCardInfo("Pitfall Trap", 18, Rarity.COMMON, mage.cards.p.PitfallTrap.class));
        cards.add(new SetCardInfo("Primal Command", 132, Rarity.RARE, mage.cards.p.PrimalCommand.class));
        cards.add(new SetCardInfo("Putrefy", 179, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyrewild Shaman", 106, Rarity.UNCOMMON, mage.cards.p.PyrewildShaman.class));
        cards.add(new SetCardInfo("Pyroclasm", 107, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Pyromancer Ascension", 108, Rarity.RARE, mage.cards.p.PyromancerAscension.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 242, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class));
        cards.add(new SetCardInfo("Rakdos Signet", 225, Rarity.UNCOMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Ranger of Eos", 19, Rarity.RARE, mage.cards.r.RangerOfEos.class));
        cards.add(new SetCardInfo("Recover", 82, Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Restoration Angel", 20, Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Revive", 133, Rarity.COMMON, mage.cards.r.Revive.class));
        cards.add(new SetCardInfo("Rewind", 47, Rarity.COMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Rhox War Monk", 180, Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Rootborn Defenses", 21, Rarity.COMMON, mage.cards.r.RootbornDefenses.class));
        cards.add(new SetCardInfo("Rubblebelt Maaka", 109, Rarity.COMMON, mage.cards.r.RubblebeltMaaka.class));
        cards.add(new SetCardInfo("Savage Lands", 243, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scalding Tarn", 244, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 134, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Scorched Rusalka", 110, Rarity.COMMON, mage.cards.s.ScorchedRusalka.class));
        cards.add(new SetCardInfo("Scourge Devil", 111, Rarity.COMMON, mage.cards.s.ScourgeDevil.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 48, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Seal of Doom", 83, Rarity.UNCOMMON, mage.cards.s.SealOfDoom.class));
        cards.add(new SetCardInfo("Seal of Primordium", 135, Rarity.COMMON, mage.cards.s.SealOfPrimordium.class));
        cards.add(new SetCardInfo("Seance", 22, Rarity.RARE, mage.cards.s.Seance.class));
        cards.add(new SetCardInfo("Seaside Citadel", 245, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Sedraxis Specter", 181, Rarity.UNCOMMON, mage.cards.s.SedraxisSpecter.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 246, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Selesnya Signet", 226, Rarity.UNCOMMON, mage.cards.s.SelesnyaSignet.class));
        cards.add(new SetCardInfo("Sensor Splicer", 23, Rarity.COMMON, mage.cards.s.SensorSplicer.class));
        cards.add(new SetCardInfo("Serum Visions", 49, Rarity.UNCOMMON, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Sever the Bloodline", 84, Rarity.RARE, mage.cards.s.SeverTheBloodline.class));
        cards.add(new SetCardInfo("Shimmering Grotto", 247, Rarity.COMMON, mage.cards.s.ShimmeringGrotto.class));
        cards.add(new SetCardInfo("Simic Guildgate", 248, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Simic Signet", 227, Rarity.UNCOMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Simic Sky Swallower", 182, Rarity.RARE, mage.cards.s.SimicSkySwallower.class));
        cards.add(new SetCardInfo("Sin Collector", 183, Rarity.UNCOMMON, mage.cards.s.SinCollector.class));
        cards.add(new SetCardInfo("Skirsdag Cultist", 112, Rarity.UNCOMMON, mage.cards.s.SkirsdagCultist.class));
        cards.add(new SetCardInfo("Skyknight Legionnaire", 184, Rarity.COMMON, mage.cards.s.SkyknightLegionnaire.class));
        cards.add(new SetCardInfo("Slaughterhorn", 136, Rarity.COMMON, mage.cards.s.Slaughterhorn.class));
        cards.add(new SetCardInfo("Slime Molding", 137, Rarity.COMMON, mage.cards.s.SlimeMolding.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 50, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Sphinx's Revelation", 187, Rarity.MYTHIC, mage.cards.s.SphinxsRevelation.class));
        cards.add(new SetCardInfo("Soul Manipulation", 185, Rarity.UNCOMMON, mage.cards.s.SoulManipulation.class));
        cards.add(new SetCardInfo("Soul Ransom", 186, Rarity.UNCOMMON, mage.cards.s.SoulRansom.class));
        cards.add(new SetCardInfo("Soul Warden", 24, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Spell Pierce", 51, Rarity.COMMON, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Spike Jester", 188, Rarity.COMMON, mage.cards.s.SpikeJester.class));
        cards.add(new SetCardInfo("Spire Monitor", 52, Rarity.COMMON, mage.cards.s.SpireMonitor.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 189, Rarity.UNCOMMON, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Stoic Angel", 190, Rarity.RARE, mage.cards.s.StoicAngel.class));
        cards.add(new SetCardInfo("Stony Silence", 25, Rarity.RARE, mage.cards.s.StonySilence.class));
        cards.add(new SetCardInfo("Strength in Numbers", 138, Rarity.COMMON, mage.cards.s.StrengthInNumbers.class));
        cards.add(new SetCardInfo("Summoning Trap", 139, Rarity.RARE, mage.cards.s.SummoningTrap.class));
        cards.add(new SetCardInfo("Sundering Growth", 211, Rarity.COMMON, mage.cards.s.SunderingGrowth.class));
        cards.add(new SetCardInfo("Sunhome Guildmage", 191, Rarity.UNCOMMON, mage.cards.s.SunhomeGuildmage.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 140, Rarity.COMMON, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Talon Trooper", 192, Rarity.COMMON, mage.cards.t.TalonTrooper.class));
        cards.add(new SetCardInfo("Tandem Lookout", 53, Rarity.COMMON, mage.cards.t.TandemLookout.class));
        cards.add(new SetCardInfo("Tarmogoyf", 141, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tattermunge Witch", 212, Rarity.UNCOMMON, mage.cards.t.TattermungeWitch.class));
        cards.add(new SetCardInfo("Teleportal", 193, Rarity.UNCOMMON, mage.cards.t.Teleportal.class));
        cards.add(new SetCardInfo("Temporal Mastery", 54, Rarity.MYTHIC, mage.cards.t.TemporalMastery.class));
        cards.add(new SetCardInfo("Terminate", 194, Rarity.UNCOMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terminus", 26, Rarity.RARE, mage.cards.t.Terminus.class));
        cards.add(new SetCardInfo("Thornscape Battlemage", 142, Rarity.UNCOMMON, mage.cards.t.ThornscapeBattlemage.class));
        cards.add(new SetCardInfo("Thragtusk", 143, Rarity.RARE, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Thunderous Wrath", 113, Rarity.COMMON, mage.cards.t.ThunderousWrath.class));
        cards.add(new SetCardInfo("Thundersong Trumpeter", 195, Rarity.UNCOMMON, mage.cards.t.ThundersongTrumpeter.class));
        cards.add(new SetCardInfo("Torrent of Souls", 213, Rarity.UNCOMMON, mage.cards.t.TorrentOfSouls.class));
        cards.add(new SetCardInfo("Traitorous Instinct", 114, Rarity.COMMON, mage.cards.t.TraitorousInstinct.class));
        cards.add(new SetCardInfo("Ulvenwald Tracker", 144, Rarity.RARE, mage.cards.u.UlvenwaldTracker.class));
        cards.add(new SetCardInfo("Unburial Rites", 85, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
        cards.add(new SetCardInfo("Unflinching Courage", 197, Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Urban Evolution", 198, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Urbis Protector", 27, Rarity.UNCOMMON, mage.cards.u.UrbisProtector.class));
        cards.add(new SetCardInfo("Vampire Aristocrat", 86, Rarity.COMMON, mage.cards.v.VampireAristocrat.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 87, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vital Splicer", 145, Rarity.UNCOMMON, mage.cards.v.VitalSplicer.class));
        cards.add(new SetCardInfo("Tower Gargoyle", 196, Rarity.UNCOMMON, mage.cards.t.TowerGargoyle.class));
        cards.add(new SetCardInfo("Vanish into Memory", 199, Rarity.UNCOMMON, mage.cards.v.VanishIntoMemory.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 55, Rarity.RARE, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 249, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
        cards.add(new SetCardInfo("Vithian Stinger", 115, Rarity.UNCOMMON, mage.cards.v.VithianStinger.class));
        cards.add(new SetCardInfo("Voice of Resurgence", 200, Rarity.MYTHIC, mage.cards.v.VoiceOfResurgence.class));
        cards.add(new SetCardInfo("Wake the Reflections", 28, Rarity.COMMON, mage.cards.w.WakeTheReflections.class));
        cards.add(new SetCardInfo("Wall of Denial", 201, Rarity.UNCOMMON, mage.cards.w.WallOfDenial.class));
        cards.add(new SetCardInfo("Wall of Frost", 56, Rarity.UNCOMMON, mage.cards.w.WallOfFrost.class));
        cards.add(new SetCardInfo("Wayfaring Temple", 202, Rarity.UNCOMMON, mage.cards.w.WayfaringTemple.class));
        cards.add(new SetCardInfo("Wing Splicer", 57, Rarity.UNCOMMON, mage.cards.w.WingSplicer.class));
        cards.add(new SetCardInfo("Wingcrafter", 58, Rarity.COMMON, mage.cards.w.Wingcrafter.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 203, Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Wort, the Raidmother", 214, Rarity.RARE, mage.cards.w.WortTheRaidmother.class));
        cards.add(new SetCardInfo("Youthful Knight", 29, Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
        cards.add(new SetCardInfo("Zealous Conscripts", 116, Rarity.RARE, mage.cards.z.ZealousConscripts.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 204, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class));
    }

}
