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
 * @author BetaSteward_at_googlemail.com
 */
public class Magic2010 extends ExpansionSet {

    private static final Magic2010 instance = new Magic2010();

    public static Magic2010 getInstance() {
        return instance;
    }

    private Magic2010() {
        super("Magic 2010", "M10", ExpansionSet.buildDate(2009, 7, 17), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Acidic Slime", 165, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Acolyte of Xathrid", 83, Rarity.COMMON, mage.cards.a.AcolyteOfXathrid.class));
        cards.add(new SetCardInfo("Act of Treason", 124, Rarity.UNCOMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Air Elemental", 42, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ajani Goldmane", 1, Rarity.MYTHIC, mage.cards.a.AjaniGoldmane.class));
        cards.add(new SetCardInfo("Alluring Siren", 43, Rarity.UNCOMMON, mage.cards.a.AlluringSiren.class));
        cards.add(new SetCardInfo("Angel's Feather", 206, Rarity.UNCOMMON, mage.cards.a.AngelsFeather.class));
        cards.add(new SetCardInfo("Angel's Mercy", 2, Rarity.COMMON, mage.cards.a.AngelsMercy.class));
        cards.add(new SetCardInfo("Ant Queen", 166, Rarity.RARE, mage.cards.a.AntQueen.class));
        cards.add(new SetCardInfo("Armored Ascension", 3, Rarity.UNCOMMON, mage.cards.a.ArmoredAscension.class));
        cards.add(new SetCardInfo("Assassinate", 84, Rarity.COMMON, mage.cards.a.Assassinate.class));
        cards.add(new SetCardInfo("Awakener Druid", 167, Rarity.UNCOMMON, mage.cards.a.AwakenerDruid.class));
        cards.add(new SetCardInfo("Ball Lightning", 125, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 4, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Berserkers of Blood Ridge", 126, Rarity.COMMON, mage.cards.b.BerserkersOfBloodRidge.class));
        cards.add(new SetCardInfo("Birds of Paradise", 168, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Black Knight", 85, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Blinding Mage", 5, Rarity.COMMON, mage.cards.b.BlindingMage.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 127, Rarity.MYTHIC, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Bog Wraith", 86, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Borderland Ranger", 169, Rarity.COMMON, mage.cards.b.BorderlandRanger.class));
        cards.add(new SetCardInfo("Bountiful Harvest", 170, Rarity.COMMON, mage.cards.b.BountifulHarvest.class));
        cards.add(new SetCardInfo("Bramble Creeper", 171, Rarity.COMMON, mage.cards.b.BrambleCreeper.class));
        cards.add(new SetCardInfo("Burning Inquiry", 128, Rarity.COMMON, mage.cards.b.BurningInquiry.class));
        cards.add(new SetCardInfo("Burst of Speed", 129, Rarity.COMMON, mage.cards.b.BurstOfSpeed.class));
        cards.add(new SetCardInfo("Cancel", 44, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Canyon Minotaur", 130, Rarity.COMMON, mage.cards.c.CanyonMinotaur.class));
        cards.add(new SetCardInfo("Capricious Efreet", 131, Rarity.RARE, mage.cards.c.CapriciousEfreet.class));
        cards.add(new SetCardInfo("Captain of the Watch", 6, Rarity.RARE, mage.cards.c.CaptainOfTheWatch.class));
        cards.add(new SetCardInfo("Celestial Purge", 7, Rarity.UNCOMMON, mage.cards.c.CelestialPurge.class));
        cards.add(new SetCardInfo("Cemetery Reaper", 87, Rarity.RARE, mage.cards.c.CemeteryReaper.class));
        cards.add(new SetCardInfo("Centaur Courser", 172, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Chandra Nalaar", 132, Rarity.MYTHIC, mage.cards.c.ChandraNalaar.class));
        cards.add(new SetCardInfo("Child of Night", 88, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Clone", 45, Rarity.RARE, mage.cards.c.Clone.class));
        cards.add(new SetCardInfo("Coat of Arms", 207, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Consume Spirit", 89, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Convincing Mirage", 46, Rarity.COMMON, mage.cards.c.ConvincingMirage.class));
        cards.add(new SetCardInfo("Coral Merfolk", 47, Rarity.COMMON, mage.cards.c.CoralMerfolk.class));
        cards.add(new SetCardInfo("Craw Wurm", 173, Rarity.COMMON, mage.cards.c.CrawWurm.class));
        cards.add(new SetCardInfo("Cudgel Troll", 174, Rarity.UNCOMMON, mage.cards.c.CudgelTroll.class));
        cards.add(new SetCardInfo("Darksteel Colossus", 208, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Deadly Recluse", 175, Rarity.COMMON, mage.cards.d.DeadlyRecluse.class));
        cards.add(new SetCardInfo("Deathmark", 90, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Demon's Horn", 209, Rarity.UNCOMMON, mage.cards.d.DemonsHorn.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 91, Rarity.UNCOMMON, mage.cards.d.DiabolicTutor.class));
        cards.add(new SetCardInfo("Disentomb", 92, Rarity.COMMON, mage.cards.d.Disentomb.class));
        cards.add(new SetCardInfo("Disorient", 48, Rarity.COMMON, mage.cards.d.Disorient.class));
        cards.add(new SetCardInfo("Divination", 49, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Divine Verdict", 8, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Djinn of Wishes", 50, Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Doom Blade", 93, Rarity.COMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Dragon's Claw", 210, Rarity.UNCOMMON, mage.cards.d.DragonsClaw.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 223, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dragon Whelp", 133, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Dread Warlock", 94, Rarity.COMMON, mage.cards.d.DreadWarlock.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 224, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 95, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Duress", 96, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Earthquake", 134, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Elite Vanguard", 9, Rarity.UNCOMMON, mage.cards.e.EliteVanguard.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 176, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Piper", 177, Rarity.RARE, mage.cards.e.ElvishPiper.class));
        cards.add(new SetCardInfo("Elvish Visionary", 178, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Emerald Oryx", 179, Rarity.COMMON, mage.cards.e.EmeraldOryx.class));
        cards.add(new SetCardInfo("Enormous Baloth", 180, Rarity.UNCOMMON, mage.cards.e.EnormousBaloth.class));
        cards.add(new SetCardInfo("Entangling Vines", 181, Rarity.COMMON, mage.cards.e.EntanglingVines.class));
        cards.add(new SetCardInfo("Essence Scatter", 51, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Excommunicate", 10, Rarity.COMMON, mage.cards.e.Excommunicate.class));
        cards.add(new SetCardInfo("Fabricate", 52, Rarity.UNCOMMON, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Fiery Hellhound", 135, Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Fireball", 136, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebreathing", 137, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Flashfreeze", 53, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Fog", 182, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gargoyle Castle", 225, Rarity.RARE, mage.cards.g.GargoyleCastle.class));
        cards.add(new SetCardInfo("Garruk Wildspeaker", 183, Rarity.MYTHIC, mage.cards.g.GarrukWildspeaker.class));
        cards.add(new SetCardInfo("Giant Growth", 184, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spider", 185, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Glacial Fortress", 226, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Glorious Charge", 11, Rarity.COMMON, mage.cards.g.GloriousCharge.class));
        cards.add(new SetCardInfo("Goblin Artillery", 138, Rarity.UNCOMMON, mage.cards.g.GoblinArtillery.class));
        cards.add(new SetCardInfo("Goblin Chieftain", 139, Rarity.RARE, mage.cards.g.GoblinChieftain.class));
        cards.add(new SetCardInfo("Goblin Piker", 140, Rarity.COMMON, mage.cards.g.GoblinPiker.class));
        cards.add(new SetCardInfo("Gorgon Flail", 211, Rarity.UNCOMMON, mage.cards.g.GorgonFlail.class));
        cards.add(new SetCardInfo("Gravedigger", 97, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Great Sable Stag", 186, Rarity.RARE, mage.cards.g.GreatSableStag.class));
        cards.add(new SetCardInfo("Griffin Sentinel", 12, Rarity.COMMON, mage.cards.g.GriffinSentinel.class));
        cards.add(new SetCardInfo("Guardian Seraph", 13, Rarity.RARE, mage.cards.g.GuardianSeraph.class));
        cards.add(new SetCardInfo("Harm's Way", 14, Rarity.UNCOMMON, mage.cards.h.HarmsWay.class));
        cards.add(new SetCardInfo("Haunting Echoes", 98, Rarity.RARE, mage.cards.h.HauntingEchoes.class));
        cards.add(new SetCardInfo("Hive Mind", 54, Rarity.RARE, mage.cards.h.HiveMind.class));
        cards.add(new SetCardInfo("Holy Strength", 15, Rarity.COMMON, mage.cards.h.HolyStrength.class));
        cards.add(new SetCardInfo("Honor of the Pure", 16, Rarity.RARE, mage.cards.h.HonorOfThePure.class));
        cards.add(new SetCardInfo("Horned Turtle", 55, Rarity.COMMON, mage.cards.h.HornedTurtle.class));
        cards.add(new SetCardInfo("Howling Banshee", 99, Rarity.UNCOMMON, mage.cards.h.HowlingBanshee.class));
        cards.add(new SetCardInfo("Howling Mine", 212, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Howl of the Night Pack", 187, Rarity.UNCOMMON, mage.cards.h.HowlOfTheNightPack.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 100, Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Ice Cage", 56, Rarity.COMMON, mage.cards.i.IceCage.class));
        cards.add(new SetCardInfo("Ignite Disorder", 141, Rarity.UNCOMMON, mage.cards.i.IgniteDisorder.class));
        cards.add(new SetCardInfo("Illusionary Servant", 57, Rarity.COMMON, mage.cards.i.IllusionaryServant.class));
        cards.add(new SetCardInfo("Indestructibility", 17, Rarity.RARE, mage.cards.i.Indestructibility.class));
        cards.add(new SetCardInfo("Inferno Elemental", 142, Rarity.UNCOMMON, mage.cards.i.InfernoElemental.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace Beleren", 58, Rarity.MYTHIC, mage.cards.j.JaceBeleren.class));
        cards.add(new SetCardInfo("Jackal Familiar", 143, Rarity.COMMON, mage.cards.j.JackalFamiliar.class));
        cards.add(new SetCardInfo("Jump", 59, Rarity.COMMON, mage.cards.j.Jump.class));
        cards.add(new SetCardInfo("Kalonian Behemoth", 188, Rarity.RARE, mage.cards.k.KalonianBehemoth.class));
        cards.add(new SetCardInfo("Kelinore Bat", 101, Rarity.COMMON, mage.cards.k.KelinoreBat.class));
        cards.add(new SetCardInfo("Kindled Fury", 144, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Kraken's Eye", 213, Rarity.UNCOMMON, mage.cards.k.KrakensEye.class));
        cards.add(new SetCardInfo("Lava Axe", 145, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Levitation", 60, Rarity.UNCOMMON, mage.cards.l.Levitation.class));
        cards.add(new SetCardInfo("Lifelink", 18, Rarity.COMMON, mage.cards.l.Lifelink.class));
        cards.add(new SetCardInfo("Lightning Bolt", 146, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lightning Elemental", 147, Rarity.COMMON, mage.cards.l.LightningElemental.class));
        cards.add(new SetCardInfo("Lightwielder Paladin", 19, Rarity.RARE, mage.cards.l.LightwielderPaladin.class));
        cards.add(new SetCardInfo("Liliana Vess", 102, Rarity.MYTHIC, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Llanowar Elves", 189, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Looming Shade", 103, Rarity.COMMON, mage.cards.l.LoomingShade.class));
        cards.add(new SetCardInfo("Lurking Predators", 190, Rarity.RARE, mage.cards.l.LurkingPredators.class));
        cards.add(new SetCardInfo("Magebane Armor", 214, Rarity.RARE, mage.cards.m.MagebaneArmor.class));
        cards.add(new SetCardInfo("Magma Phoenix", 148, Rarity.RARE, mage.cards.m.MagmaPhoenix.class));
        cards.add(new SetCardInfo("Manabarbs", 149, Rarity.RARE, mage.cards.m.Manabarbs.class));
        cards.add(new SetCardInfo("Master of the Wild Hunt", 191, Rarity.MYTHIC, mage.cards.m.MasterOfTheWildHunt.class));
        cards.add(new SetCardInfo("Megrim", 104, Rarity.UNCOMMON, mage.cards.m.Megrim.class));
        cards.add(new SetCardInfo("Merfolk Looter", 61, Rarity.COMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Merfolk Sovereign", 62, Rarity.RARE, mage.cards.m.MerfolkSovereign.class));
        cards.add(new SetCardInfo("Mesa Enchantress", 20, Rarity.RARE, mage.cards.m.MesaEnchantress.class));
        cards.add(new SetCardInfo("Might of Oaks", 192, Rarity.RARE, mage.cards.m.MightOfOaks.class));
        cards.add(new SetCardInfo("Mind Control", 63, Rarity.UNCOMMON, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Mind Rot", 105, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Shatter", 106, Rarity.RARE, mage.cards.m.MindShatter.class));
        cards.add(new SetCardInfo("Mind Spring", 64, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Mirror of Fate", 215, Rarity.RARE, mage.cards.m.MirrorOfFate.class));
        cards.add(new SetCardInfo("Mist Leopard", 193, Rarity.COMMON, mage.cards.m.MistLeopard.class));
        cards.add(new SetCardInfo("Mold Adder", 194, Rarity.UNCOMMON, mage.cards.m.MoldAdder.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Naturalize", 195, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nature's Spiral", 196, Rarity.UNCOMMON, mage.cards.n.NaturesSpiral.class));
        cards.add(new SetCardInfo("Negate", 65, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nightmare", 107, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Oakenform", 197, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Open the Vaults", 21, Rarity.RARE, mage.cards.o.OpenTheVaults.class));
        cards.add(new SetCardInfo("Ornithopter", 216, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Overrun", 198, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Pacifism", 22, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Palace Guard", 23, Rarity.COMMON, mage.cards.p.PalaceGuard.class));
        cards.add(new SetCardInfo("Panic Attack", 150, Rarity.COMMON, mage.cards.p.PanicAttack.class));
        cards.add(new SetCardInfo("Phantom Warrior", 66, Rarity.UNCOMMON, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Pithing Needle", 217, Rarity.RARE, mage.cards.p.PithingNeedle.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Cleansing", 24, Rarity.RARE, mage.cards.p.PlanarCleansing.class));
        cards.add(new SetCardInfo("Platinum Angel", 218, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Polymorph", 67, Rarity.RARE, mage.cards.p.Polymorph.class));
        cards.add(new SetCardInfo("Ponder", 68, Rarity.COMMON, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Prized Unicorn", 199, Rarity.UNCOMMON, mage.cards.p.PrizedUnicorn.class));
        cards.add(new SetCardInfo("Prodigal Pyromancer", 151, Rarity.UNCOMMON, mage.cards.p.ProdigalPyromancer.class));
        cards.add(new SetCardInfo("Protean Hydra", 200, Rarity.MYTHIC, mage.cards.p.ProteanHydra.class));
        cards.add(new SetCardInfo("Pyroclasm", 152, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Raging Goblin", 153, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Rampant Growth", 201, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Razorfoot Griffin", 25, Rarity.COMMON, mage.cards.r.RazorfootGriffin.class));
        cards.add(new SetCardInfo("Regenerate", 202, Rarity.COMMON, mage.cards.r.Regenerate.class));
        cards.add(new SetCardInfo("Relentless Rats", 108, Rarity.UNCOMMON, mage.cards.r.RelentlessRats.class));
        cards.add(new SetCardInfo("Rhox Pikemaster", 26, Rarity.UNCOMMON, mage.cards.r.RhoxPikemaster.class));
        cards.add(new SetCardInfo("Righteousness", 27, Rarity.UNCOMMON, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Rise from the Grave", 109, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Rod of Ruin", 219, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Rootbound Crag", 227, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Royal Assassin", 110, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Runeclaw Bear", 203, Rarity.COMMON, mage.cards.r.RuneclawBear.class));
        cards.add(new SetCardInfo("Safe Passage", 28, Rarity.COMMON, mage.cards.s.SafePassage.class));
        cards.add(new SetCardInfo("Sage Owl", 69, Rarity.COMMON, mage.cards.s.SageOwl.class));
        cards.add(new SetCardInfo("Sanguine Bond", 111, Rarity.RARE, mage.cards.s.SanguineBond.class));
        cards.add(new SetCardInfo("Seismic Strike", 154, Rarity.COMMON, mage.cards.s.SeismicStrike.class));
        cards.add(new SetCardInfo("Serpent of the Endless Sea", 70, Rarity.COMMON, mage.cards.s.SerpentOfTheEndlessSea.class));
        cards.add(new SetCardInfo("Serra Angel", 29, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shatter", 155, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shivan Dragon", 156, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 157, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Siege Mastodon", 30, Rarity.COMMON, mage.cards.s.SiegeMastodon.class));
        cards.add(new SetCardInfo("Sign in Blood", 112, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Silence", 31, Rarity.RARE, mage.cards.s.Silence.class));
        cards.add(new SetCardInfo("Silvercoat Lion", 32, Rarity.COMMON, mage.cards.s.SilvercoatLion.class));
        cards.add(new SetCardInfo("Sleep", 71, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Snapping Drake", 72, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Solemn Offering", 33, Rarity.COMMON, mage.cards.s.SolemnOffering.class));
        cards.add(new SetCardInfo("Soul Bleed", 113, Rarity.COMMON, mage.cards.s.SoulBleed.class));
        cards.add(new SetCardInfo("Soul Warden", 34, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Sparkmage Apprentice", 158, Rarity.COMMON, mage.cards.s.SparkmageApprentice.class));
        cards.add(new SetCardInfo("Spellbook", 220, Rarity.UNCOMMON, mage.cards.s.Spellbook.class));
        cards.add(new SetCardInfo("Sphinx Ambassador", 73, Rarity.MYTHIC, mage.cards.s.SphinxAmbassador.class));
        cards.add(new SetCardInfo("Stampeding Rhino", 204, Rarity.COMMON, mage.cards.s.StampedingRhino.class));
        cards.add(new SetCardInfo("Stone Giant", 159, Rarity.UNCOMMON, mage.cards.s.StoneGiant.class));
        cards.add(new SetCardInfo("Stormfront Pegasus", 35, Rarity.COMMON, mage.cards.s.StormfrontPegasus.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 228, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Telepathy", 74, Rarity.UNCOMMON, mage.cards.t.Telepathy.class));
        cards.add(new SetCardInfo("Tempest of Light", 36, Rarity.UNCOMMON, mage.cards.t.TempestOfLight.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", 114, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 229, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Time Warp", 75, Rarity.MYTHIC, mage.cards.t.TimeWarp.class));
        cards.add(new SetCardInfo("Tome Scour", 76, Rarity.COMMON, mage.cards.t.TomeScour.class));
        cards.add(new SetCardInfo("Traumatize", 77, Rarity.RARE, mage.cards.t.Traumatize.class));
        cards.add(new SetCardInfo("Trumpet Blast", 160, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Twincast", 78, Rarity.RARE, mage.cards.t.Twincast.class));
        cards.add(new SetCardInfo("Undead Slayer", 37, Rarity.UNCOMMON, mage.cards.u.UndeadSlayer.class));
        cards.add(new SetCardInfo("Underworld Dreams", 115, Rarity.RARE, mage.cards.u.UnderworldDreams.class));
        cards.add(new SetCardInfo("Unholy Strength", 116, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Unsummon", 79, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire Aristocrat", 117, Rarity.COMMON, mage.cards.v.VampireAristocrat.class));
        cards.add(new SetCardInfo("Vampire Nocturnus", 118, Rarity.MYTHIC, mage.cards.v.VampireNocturnus.class));
        cards.add(new SetCardInfo("Veteran Armorsmith", 38, Rarity.COMMON, mage.cards.v.VeteranArmorsmith.class));
        cards.add(new SetCardInfo("Veteran Swordsmith", 39, Rarity.COMMON, mage.cards.v.VeteranSwordsmith.class));
        cards.add(new SetCardInfo("Viashino Spearhunter", 161, Rarity.COMMON, mage.cards.v.ViashinoSpearhunter.class));
        cards.add(new SetCardInfo("Wall of Bone", 119, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("Wall of Faith", 40, Rarity.COMMON, mage.cards.w.WallOfFaith.class));
        cards.add(new SetCardInfo("Wall of Fire", 162, Rarity.UNCOMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Frost", 80, Rarity.UNCOMMON, mage.cards.w.WallOfFrost.class));
        cards.add(new SetCardInfo("Warpath Ghoul", 120, Rarity.COMMON, mage.cards.w.WarpathGhoul.class));
        cards.add(new SetCardInfo("Warp World", 163, Rarity.RARE, mage.cards.w.WarpWorld.class));
        cards.add(new SetCardInfo("Weakness", 121, Rarity.COMMON, mage.cards.w.Weakness.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 221, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("White Knight", 41, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("Wind Drake", 81, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Windstorm", 205, Rarity.UNCOMMON, mage.cards.w.Windstorm.class));
        cards.add(new SetCardInfo("Wurm's Tooth", 222, Rarity.UNCOMMON, mage.cards.w.WurmsTooth.class));
        cards.add(new SetCardInfo("Xathrid Demon", 122, Rarity.MYTHIC, mage.cards.x.XathridDemon.class));
        cards.add(new SetCardInfo("Yawning Fissure", 164, Rarity.COMMON, mage.cards.y.YawningFissure.class));
        cards.add(new SetCardInfo("Zephyr Sprite", 82, Rarity.COMMON, mage.cards.z.ZephyrSprite.class));
        cards.add(new SetCardInfo("Zombie Goliath", 123, Rarity.COMMON, mage.cards.z.ZombieGoliath.class));
    }

}
