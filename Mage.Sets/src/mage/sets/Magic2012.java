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
import mage.cards.g.GoblinGrenade;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Magic2012 extends ExpansionSet {

    private static final Magic2012 instance = new Magic2012();

    public static Magic2012 getInstance() {
        return instance;
    }

    private Magic2012() {
        super("Magic 2012", "M12", ExpansionSet.buildDate(2011, 7, 15), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Acidic Slime", 161, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Act of Treason", 121, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Adaptive Automaton", 201, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class));
        cards.add(new SetCardInfo("Aegis Angel", 1, Rarity.RARE, mage.cards.a.AegisAngel.class));
        cards.add(new SetCardInfo("Aether Adept", 41, Rarity.COMMON, mage.cards.a.AetherAdept.class));
        cards.add(new SetCardInfo("Alabaster Mage", 2, Rarity.UNCOMMON, mage.cards.a.AlabasterMage.class));
        cards.add(new SetCardInfo("Alluring Siren", 42, Rarity.UNCOMMON, mage.cards.a.AlluringSiren.class));
        cards.add(new SetCardInfo("Amphin Cutthroat", 43, Rarity.COMMON, mage.cards.a.AmphinCutthroat.class));
        cards.add(new SetCardInfo("Angelic Destiny", 3, Rarity.MYTHIC, mage.cards.a.AngelicDestiny.class));
        cards.add(new SetCardInfo("Angel's Feather", 202, Rarity.UNCOMMON, mage.cards.a.AngelsFeather.class));
        cards.add(new SetCardInfo("Angel's Mercy", 4, Rarity.COMMON, mage.cards.a.AngelsMercy.class));
        cards.add(new SetCardInfo("Arachnus Spinner", 162, Rarity.RARE, mage.cards.a.ArachnusSpinner.class));
        cards.add(new SetCardInfo("Arachnus Web", 163, Rarity.COMMON, mage.cards.a.ArachnusWeb.class));
        cards.add(new SetCardInfo("Arbalest Elite", 5, Rarity.UNCOMMON, mage.cards.a.ArbalestElite.class));
        cards.add(new SetCardInfo("Archon of Justice", 6, Rarity.RARE, mage.cards.a.ArchonOfJustice.class));
        cards.add(new SetCardInfo("Armored Warhorse", 7, Rarity.COMMON, mage.cards.a.ArmoredWarhorse.class));
        cards.add(new SetCardInfo("Assault Griffin", 8, Rarity.COMMON, mage.cards.a.AssaultGriffin.class));
        cards.add(new SetCardInfo("Auramancer", 9, Rarity.COMMON, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Autumn's Veil", 164, Rarity.UNCOMMON, mage.cards.a.AutumnsVeil.class));
        cards.add(new SetCardInfo("Aven Fleetwing", 44, Rarity.COMMON, mage.cards.a.AvenFleetwing.class));
        cards.add(new SetCardInfo("Azure Mage", 45, Rarity.UNCOMMON, mage.cards.a.AzureMage.class));
        cards.add(new SetCardInfo("Belltower Sphinx", 46, Rarity.UNCOMMON, mage.cards.b.BelltowerSphinx.class));
        cards.add(new SetCardInfo("Benalish Veteran", 10, Rarity.COMMON, mage.cards.b.BenalishVeteran.class));
        cards.add(new SetCardInfo("Birds of Paradise", 165, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Bloodlord of Vaasgoth", 82, Rarity.MYTHIC, mage.cards.b.BloodlordOfVaasgoth.class));
        cards.add(new SetCardInfo("Blood Ogre", 122, Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Bloodrage Vampire", 83, Rarity.COMMON, mage.cards.b.BloodrageVampire.class));
        cards.add(new SetCardInfo("Blood Seeker", 81, Rarity.COMMON, mage.cards.b.BloodSeeker.class));
        cards.add(new SetCardInfo("Bonebreaker Giant", 123, Rarity.COMMON, mage.cards.b.BonebreakerGiant.class));
        cards.add(new SetCardInfo("Bountiful Harvest", 166, Rarity.COMMON, mage.cards.b.BountifulHarvest.class));
        cards.add(new SetCardInfo("Brindle Boar", 167, Rarity.COMMON, mage.cards.b.BrindleBoar.class));
        cards.add(new SetCardInfo("Brink of Disaster", 84, Rarity.COMMON, mage.cards.b.BrinkOfDisaster.class));
        cards.add(new SetCardInfo("Buried Ruin", 224, Rarity.UNCOMMON, mage.cards.b.BuriedRuin.class));
        cards.add(new SetCardInfo("Call to the Grave", 85, Rarity.RARE, mage.cards.c.CallToTheGrave.class));
        cards.add(new SetCardInfo("Cancel", 47, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Carnage Wurm", 168, Rarity.UNCOMMON, mage.cards.c.CarnageWurm.class));
        cards.add(new SetCardInfo("Celestial Purge", 11, Rarity.UNCOMMON, mage.cards.c.CelestialPurge.class));
        cards.add(new SetCardInfo("Cemetery Reaper", 86, Rarity.RARE, mage.cards.c.CemeteryReaper.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 125, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chandra's Phoenix", 126, Rarity.RARE, mage.cards.c.ChandrasPhoenix.class));
        cards.add(new SetCardInfo("Chandra, the Firebrand", 124, Rarity.MYTHIC, mage.cards.c.ChandraTheFirebrand.class));
        cards.add(new SetCardInfo("Chasm Drake", 48, Rarity.COMMON, mage.cards.c.ChasmDrake.class));
        cards.add(new SetCardInfo("Child of Night", 87, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Circle of Flame", 127, Rarity.UNCOMMON, mage.cards.c.CircleOfFlame.class));
        cards.add(new SetCardInfo("Combust", 128, Rarity.COMMON, mage.cards.c.Combust.class));
        cards.add(new SetCardInfo("Consume Spirit", 88, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Coral Merfolk", 49, Rarity.COMMON, mage.cards.c.CoralMerfolk.class));
        cards.add(new SetCardInfo("Crimson Mage", 129, Rarity.UNCOMMON, mage.cards.c.CrimsonMage.class));
        cards.add(new SetCardInfo("Crown of Empires", 203, Rarity.UNCOMMON, mage.cards.c.CrownOfEmpires.class));
        cards.add(new SetCardInfo("Crumbling Colossus", 204, Rarity.UNCOMMON, mage.cards.c.CrumblingColossus.class));
        cards.add(new SetCardInfo("Cudgel Troll", 169, Rarity.UNCOMMON, mage.cards.c.CudgelTroll.class));
        cards.add(new SetCardInfo("Dark Favor", 89, Rarity.COMMON, mage.cards.d.DarkFavor.class));
        cards.add(new SetCardInfo("Day of Judgment", 12, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Deathmark", 90, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Demon's Horn", 205, Rarity.UNCOMMON, mage.cards.d.DemonsHorn.class));
        cards.add(new SetCardInfo("Demystify", 13, Rarity.COMMON, mage.cards.d.Demystify.class));
        cards.add(new SetCardInfo("Devouring Swarm", 91, Rarity.COMMON, mage.cards.d.DevouringSwarm.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 92, Rarity.UNCOMMON, mage.cards.d.DiabolicTutor.class));
        cards.add(new SetCardInfo("Disentomb", 93, Rarity.COMMON, mage.cards.d.Disentomb.class));
        cards.add(new SetCardInfo("Distress", 94, Rarity.COMMON, mage.cards.d.Distress.class));
        cards.add(new SetCardInfo("Divination", 50, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Divine Favor", 14, Rarity.COMMON, mage.cards.d.DivineFavor.class));
        cards.add(new SetCardInfo("Djinn of Wishes", 51, Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Doom Blade", 95, Rarity.COMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Doubling Chant", 170, Rarity.RARE, mage.cards.d.DoublingChant.class));
        cards.add(new SetCardInfo("Dragon's Claw", 206, Rarity.UNCOMMON, mage.cards.d.DragonsClaw.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 225, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Drifting Shade", 96, Rarity.COMMON, mage.cards.d.DriftingShade.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 226, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Druidic Satchel", 207, Rarity.RARE, mage.cards.d.DruidicSatchel.class));
        cards.add(new SetCardInfo("Dungrove Elder", 171, Rarity.RARE, mage.cards.d.DungroveElder.class));
        cards.add(new SetCardInfo("Duskhunter Bat", 97, Rarity.COMMON, mage.cards.d.DuskhunterBat.class));
        cards.add(new SetCardInfo("Elite Vanguard", 15, Rarity.UNCOMMON, mage.cards.e.EliteVanguard.class));
        cards.add(new SetCardInfo("Elixir of Immortality", 208, Rarity.UNCOMMON, mage.cards.e.ElixirOfImmortality.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 172, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Fiery Hellhound", 130, Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Fireball", 131, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebreathing", 132, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Flameblast Dragon", 133, Rarity.RARE, mage.cards.f.FlameblastDragon.class));
        cards.add(new SetCardInfo("Flashfreeze", 52, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Flight", 53, Rarity.COMMON, mage.cards.f.Flight.class));
        cards.add(new SetCardInfo("Fling", 134, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Fog", 173, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frost Breath", 54, Rarity.COMMON, mage.cards.f.FrostBreath.class));
        cards.add(new SetCardInfo("Frost Titan", 55, Rarity.MYTHIC, mage.cards.f.FrostTitan.class));
        cards.add(new SetCardInfo("Furyborn Hellkite", 135, Rarity.MYTHIC, mage.cards.f.FurybornHellkite.class));
        cards.add(new SetCardInfo("Garruk, Primal Hunter", 174, Rarity.MYTHIC, mage.cards.g.GarrukPrimalHunter.class));
        cards.add(new SetCardInfo("Garruk's Companion", 175, Rarity.COMMON, mage.cards.g.GarruksCompanion.class));
        cards.add(new SetCardInfo("Garruk's Horde", 176, Rarity.RARE, mage.cards.g.GarruksHorde.class));
        cards.add(new SetCardInfo("Giant Spider", 177, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gideon Jura", 16, Rarity.MYTHIC, mage.cards.g.GideonJura.class));
        cards.add(new SetCardInfo("Gideon's Avenger", 17, Rarity.RARE, mage.cards.g.GideonsAvenger.class));
        cards.add(new SetCardInfo("Gideon's Lawkeeper", 18, Rarity.COMMON, mage.cards.g.GideonsLawkeeper.class));
        cards.add(new SetCardInfo("Glacial Fortress", 227, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Gladecover Scout", 178, Rarity.COMMON, mage.cards.g.GladecoverScout.class));
        cards.add(new SetCardInfo("Goblin Arsonist", 136, Rarity.COMMON, mage.cards.g.GoblinArsonist.class));
        cards.add(new SetCardInfo("Goblin Bangchuckers", 137, Rarity.UNCOMMON, mage.cards.g.GoblinBangchuckers.class));
        cards.add(new SetCardInfo("Goblin Chieftain", 138, Rarity.RARE, mage.cards.g.GoblinChieftain.class));
        cards.add(new SetCardInfo("Goblin Fireslinger", 139, Rarity.COMMON, mage.cards.g.GoblinFireslinger.class));
        cards.add(new SetCardInfo("Goblin Grenade", 140, Rarity.UNCOMMON, GoblinGrenade.class));
        cards.add(new SetCardInfo("Goblin Piker", 141, Rarity.COMMON, mage.cards.g.GoblinPiker.class));
        cards.add(new SetCardInfo("Goblin Tunneler", 142, Rarity.COMMON, mage.cards.g.GoblinTunneler.class));
        cards.add(new SetCardInfo("Goblin War Paint", 143, Rarity.COMMON, mage.cards.g.GoblinWarPaint.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 144, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Grand Abolisher", 19, Rarity.RARE, mage.cards.g.GrandAbolisher.class));
        cards.add(new SetCardInfo("Gravedigger", 99, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Grave Titan", 98, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Greater Basilisk", 179, Rarity.COMMON, mage.cards.g.GreaterBasilisk.class));
        cards.add(new SetCardInfo("Greatsword", 209, Rarity.UNCOMMON, mage.cards.g.Greatsword.class));
        cards.add(new SetCardInfo("Griffin Rider", 20, Rarity.COMMON, mage.cards.g.GriffinRider.class));
        cards.add(new SetCardInfo("Griffin Sentinel", 21, Rarity.COMMON, mage.cards.g.GriffinSentinel.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 145, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Guardians' Pledge", 22, Rarity.COMMON, mage.cards.g.GuardiansPledge.class));
        cards.add(new SetCardInfo("Harbor Serpent", 56, Rarity.COMMON, mage.cards.h.HarborSerpent.class));
        cards.add(new SetCardInfo("Hideous Visage", 100, Rarity.COMMON, mage.cards.h.HideousVisage.class));
        cards.add(new SetCardInfo("Honor of the Pure", 23, Rarity.RARE, mage.cards.h.HonorOfThePure.class));
        cards.add(new SetCardInfo("Hunter's Insight", 180, Rarity.UNCOMMON, mage.cards.h.HuntersInsight.class));
        cards.add(new SetCardInfo("Ice Cage", 57, Rarity.COMMON, mage.cards.i.IceCage.class));
        cards.add(new SetCardInfo("Incinerate", 146, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Inferno Titan", 147, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Memory Adept", 58, Rarity.MYTHIC, mage.cards.j.JaceMemoryAdept.class));
        cards.add(new SetCardInfo("Jace's Archivist", 59, Rarity.RARE, mage.cards.j.JacesArchivist.class));
        cards.add(new SetCardInfo("Jace's Erasure", 60, Rarity.COMMON, mage.cards.j.JacesErasure.class));
        cards.add(new SetCardInfo("Jade Mage", 181, Rarity.UNCOMMON, mage.cards.j.JadeMage.class));
        cards.add(new SetCardInfo("Kite Shield", 210, Rarity.UNCOMMON, mage.cards.k.KiteShield.class));
        cards.add(new SetCardInfo("Kraken's Eye", 211, Rarity.UNCOMMON, mage.cards.k.KrakensEye.class));
        cards.add(new SetCardInfo("Lava Axe", 148, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Levitation", 61, Rarity.UNCOMMON, mage.cards.l.Levitation.class));
        cards.add(new SetCardInfo("Lifelink", 24, Rarity.COMMON, mage.cards.l.Lifelink.class));
        cards.add(new SetCardInfo("Lightning Elemental", 149, Rarity.COMMON, mage.cards.l.LightningElemental.class));
        cards.add(new SetCardInfo("Llanowar Elves", 182, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lord of the Unreal", 62, Rarity.RARE, mage.cards.l.LordOfTheUnreal.class));
        cards.add(new SetCardInfo("Lure", 183, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Lurking Crocodile", 184, Rarity.COMMON, mage.cards.l.LurkingCrocodile.class));
        cards.add(new SetCardInfo("Manabarbs", 150, Rarity.RARE, mage.cards.m.Manabarbs.class));
        cards.add(new SetCardInfo("Mana Leak", 63, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Manalith", 212, Rarity.COMMON, mage.cards.m.Manalith.class));
        cards.add(new SetCardInfo("Manic Vandal", 151, Rarity.COMMON, mage.cards.m.ManicVandal.class));
        cards.add(new SetCardInfo("Master Thief", 64, Rarity.UNCOMMON, mage.cards.m.MasterThief.class));
        cards.add(new SetCardInfo("Merfolk Looter", 65, Rarity.COMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Merfolk Mesmerist", 66, Rarity.COMMON, mage.cards.m.MerfolkMesmerist.class));
        cards.add(new SetCardInfo("Mesa Enchantress", 25, Rarity.RARE, mage.cards.m.MesaEnchantress.class));
        cards.add(new SetCardInfo("Mighty Leap", 26, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mind Control", 67, Rarity.UNCOMMON, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Mind Rot", 101, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Unbound", 68, Rarity.RARE, mage.cards.m.MindUnbound.class));
        cards.add(new SetCardInfo("Monomania", 102, Rarity.RARE, mage.cards.m.Monomania.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Naturalize", 185, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Negate", 69, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Oblivion Ring", 27, Rarity.UNCOMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Onyx Mage", 103, Rarity.UNCOMMON, mage.cards.o.OnyxMage.class));
        cards.add(new SetCardInfo("Overrun", 186, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Pacifism", 28, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pentavus", 213, Rarity.RARE, mage.cards.p.Pentavus.class));
        cards.add(new SetCardInfo("Peregrine Griffin", 29, Rarity.COMMON, mage.cards.p.PeregrineGriffin.class));
        cards.add(new SetCardInfo("Personal Sanctuary", 30, Rarity.RARE, mage.cards.p.PersonalSanctuary.class));
        cards.add(new SetCardInfo("Phantasmal Bear", 70, Rarity.COMMON, mage.cards.p.PhantasmalBear.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", 71, Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Phantasmal Image", 72, Rarity.RARE, mage.cards.p.PhantasmalImage.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 187, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Ponder", 73, Rarity.COMMON, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Pride Guardian", 31, Rarity.COMMON, mage.cards.p.PrideGuardian.class));
        cards.add(new SetCardInfo("Primeval Titan", 188, Rarity.MYTHIC, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Primordial Hydra", 189, Rarity.MYTHIC, mage.cards.p.PrimordialHydra.class));
        cards.add(new SetCardInfo("Quicksilver Amulet", 214, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class));
        cards.add(new SetCardInfo("Rampant Growth", 190, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 104, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reclaim", 191, Rarity.COMMON, mage.cards.r.Reclaim.class));
        cards.add(new SetCardInfo("Redirect", 74, Rarity.RARE, mage.cards.r.Redirect.class));
        cards.add(new SetCardInfo("Reverberate", 152, Rarity.RARE, mage.cards.r.Reverberate.class));
        cards.add(new SetCardInfo("Rites of Flourishing", 192, Rarity.RARE, mage.cards.r.RitesOfFlourishing.class));
        cards.add(new SetCardInfo("Roc Egg", 32, Rarity.UNCOMMON, mage.cards.r.RocEgg.class));
        cards.add(new SetCardInfo("Rootbound Crag", 228, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Royal Assassin", 105, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Runeclaw Bear", 193, Rarity.COMMON, mage.cards.r.RuneclawBear.class));
        cards.add(new SetCardInfo("Rune-Scarred Demon", 106, Rarity.RARE, mage.cards.r.RuneScarredDemon.class));
        cards.add(new SetCardInfo("Rusted Sentinel", 215, Rarity.UNCOMMON, mage.cards.r.RustedSentinel.class));
        cards.add(new SetCardInfo("Sacred Wolf", 194, Rarity.COMMON, mage.cards.s.SacredWolf.class));
        cards.add(new SetCardInfo("Scepter of Empires", 216, Rarity.UNCOMMON, mage.cards.s.ScepterOfEmpires.class));
        cards.add(new SetCardInfo("Scrambleverse", 153, Rarity.RARE, mage.cards.s.Scrambleverse.class));
        cards.add(new SetCardInfo("Sengir Vampire", 107, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 33, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shock", 154, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Siege Mastodon", 34, Rarity.COMMON, mage.cards.s.SiegeMastodon.class));
        cards.add(new SetCardInfo("Skinshifter", 195, Rarity.RARE, mage.cards.s.Skinshifter.class));
        cards.add(new SetCardInfo("Skywinder Drake", 75, Rarity.COMMON, mage.cards.s.SkywinderDrake.class));
        cards.add(new SetCardInfo("Slaughter Cry", 155, Rarity.COMMON, mage.cards.s.SlaughterCry.class));
        cards.add(new SetCardInfo("Smallpox", 108, Rarity.UNCOMMON, mage.cards.s.Smallpox.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 217, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sorin Markov", 109, Rarity.MYTHIC, mage.cards.s.SorinMarkov.class));
        cards.add(new SetCardInfo("Sorin's Thirst", 110, Rarity.COMMON, mage.cards.s.SorinsThirst.class));
        cards.add(new SetCardInfo("Sorin's Vengeance", 111, Rarity.RARE, mage.cards.s.SorinsVengeance.class));
        cards.add(new SetCardInfo("Sphinx of Uthuun", 76, Rarity.RARE, mage.cards.s.SphinxOfUthuun.class));
        cards.add(new SetCardInfo("Spirit Mantle", 35, Rarity.UNCOMMON, mage.cards.s.SpiritMantle.class));
        cards.add(new SetCardInfo("Stampeding Rhino", 196, Rarity.COMMON, mage.cards.s.StampedingRhino.class));
        cards.add(new SetCardInfo("Stave Off", 36, Rarity.COMMON, mage.cards.s.StaveOff.class));
        cards.add(new SetCardInfo("Stingerfling Spider", 197, Rarity.UNCOMMON, mage.cards.s.StingerflingSpider.class));
        cards.add(new SetCardInfo("Stonehorn Dignitary", 37, Rarity.COMMON, mage.cards.s.StonehornDignitary.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 156, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Stormfront Pegasus", 38, Rarity.COMMON, mage.cards.s.StormfrontPegasus.class));
        cards.add(new SetCardInfo("Sundial of the Infinite", 218, Rarity.RARE, mage.cards.s.SundialOfTheInfinite.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 229, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Sun Titan", 39, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Sutured Ghoul", 112, Rarity.RARE, mage.cards.s.SuturedGhoul.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftfoot Boots", 219, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Taste of Blood", 113, Rarity.COMMON, mage.cards.t.TasteOfBlood.class));
        cards.add(new SetCardInfo("Tectonic Rift", 157, Rarity.UNCOMMON, mage.cards.t.TectonicRift.class));
        cards.add(new SetCardInfo("Thran Golem", 220, Rarity.UNCOMMON, mage.cards.t.ThranGolem.class));
        cards.add(new SetCardInfo("Throne of Empires", 221, Rarity.RARE, mage.cards.t.ThroneOfEmpires.class));
        cards.add(new SetCardInfo("Timely Reinforcements", 40, Rarity.UNCOMMON, mage.cards.t.TimelyReinforcements.class));
        cards.add(new SetCardInfo("Time Reversal", 77, Rarity.MYTHIC, mage.cards.t.TimeReversal.class));
        cards.add(new SetCardInfo("Titanic Growth", 198, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tormented Soul", 114, Rarity.COMMON, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Trollhide", 199, Rarity.COMMON, mage.cards.t.Trollhide.class));
        cards.add(new SetCardInfo("Turn to Frog", 78, Rarity.UNCOMMON, mage.cards.t.TurnToFrog.class));
        cards.add(new SetCardInfo("Unsummon", 79, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire Outcasts", 115, Rarity.UNCOMMON, mage.cards.v.VampireOutcasts.class));
        cards.add(new SetCardInfo("Vastwood Gorger", 200, Rarity.COMMON, mage.cards.v.VastwoodGorger.class));
        cards.add(new SetCardInfo("Vengeful Pharaoh", 116, Rarity.RARE, mage.cards.v.VengefulPharaoh.class));
        cards.add(new SetCardInfo("Visions of Beyond", 80, Rarity.RARE, mage.cards.v.VisionsOfBeyond.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 158, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Wall of Torches", 159, Rarity.COMMON, mage.cards.w.WallOfTorches.class));
        cards.add(new SetCardInfo("Warpath Ghoul", 117, Rarity.COMMON, mage.cards.w.WarpathGhoul.class));
        cards.add(new SetCardInfo("Warstorm Surge", 160, Rarity.RARE, mage.cards.w.WarstormSurge.class));
        cards.add(new SetCardInfo("Worldslayer", 222, Rarity.RARE, mage.cards.w.Worldslayer.class));
        cards.add(new SetCardInfo("Wring Flesh", 118, Rarity.COMMON, mage.cards.w.WringFlesh.class));
        cards.add(new SetCardInfo("Wurm's Tooth", 223, Rarity.UNCOMMON, mage.cards.w.WurmsTooth.class));
        cards.add(new SetCardInfo("Zombie Goliath", 119, Rarity.COMMON, mage.cards.z.ZombieGoliath.class));
        cards.add(new SetCardInfo("Zombie Infestation", 120, Rarity.UNCOMMON, mage.cards.z.ZombieInfestation.class));
    }
}
