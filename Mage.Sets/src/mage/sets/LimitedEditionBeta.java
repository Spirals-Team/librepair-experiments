package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class LimitedEditionBeta extends ExpansionSet {

    private static final LimitedEditionBeta instance = new LimitedEditionBeta();

    public static LimitedEditionBeta getInstance() {
        return instance;
    }

    private LimitedEditionBeta() {
        super("Limited Edition Beta", "LEB", ExpansionSet.buildDate(1993, 10, 1), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Air Elemental", 47, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ancestral Recall", 48, Rarity.RARE, mage.cards.a.AncestralRecall.class));
        cards.add(new SetCardInfo("Animate Artifact", 49, Rarity.UNCOMMON, mage.cards.a.AnimateArtifact.class));
        cards.add(new SetCardInfo("Animate Dead", 93, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Animate Wall", 1, Rarity.RARE, mage.cards.a.AnimateWall.class));
        cards.add(new SetCardInfo("Ankh of Mishra", 231, Rarity.RARE, mage.cards.a.AnkhOfMishra.class));
        cards.add(new SetCardInfo("Armageddon", 2, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Aspect of Wolf", 185, Rarity.RARE, mage.cards.a.AspectOfWolf.class));
        cards.add(new SetCardInfo("Bad Moon", 94, Rarity.RARE, mage.cards.b.BadMoon.class));
        cards.add(new SetCardInfo("Badlands", 278, Rarity.RARE, mage.cards.b.Badlands.class));
        cards.add(new SetCardInfo("Balance", 3, Rarity.RARE, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Basalt Monolith", 232, Rarity.UNCOMMON, mage.cards.b.BasaltMonolith.class));
        cards.add(new SetCardInfo("Bayou", 279, Rarity.RARE, mage.cards.b.Bayou.class));
        cards.add(new SetCardInfo("Benalish Hero", 4, Rarity.COMMON, mage.cards.b.BenalishHero.class));
        cards.add(new SetCardInfo("Berserk", 186, Rarity.UNCOMMON, mage.cards.b.Berserk.class));
        cards.add(new SetCardInfo("Birds of Paradise", 187, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Black Knight", 95, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Black Lotus", 233, Rarity.RARE, mage.cards.b.BlackLotus.class));
        cards.add(new SetCardInfo("Black Vise", 234, Rarity.UNCOMMON, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Black Ward", 5, Rarity.UNCOMMON, mage.cards.b.BlackWard.class));
        cards.add(new SetCardInfo("Blaze of Glory", 6, Rarity.RARE, mage.cards.b.BlazeOfGlory.class));
        cards.add(new SetCardInfo("Blessing", 7, Rarity.RARE, mage.cards.b.Blessing.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 50, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blue Ward", 8, Rarity.UNCOMMON, mage.cards.b.BlueWard.class));
        cards.add(new SetCardInfo("Bog Wraith", 96, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Braingeyser", 51, Rarity.RARE, mage.cards.b.Braingeyser.class));
        cards.add(new SetCardInfo("Burrowing", 139, Rarity.UNCOMMON, mage.cards.b.Burrowing.class));
        cards.add(new SetCardInfo("Camouflage", 188, Rarity.UNCOMMON, mage.cards.c.Camouflage.class));
        cards.add(new SetCardInfo("Castle", 9, Rarity.UNCOMMON, mage.cards.c.Castle.class));
        cards.add(new SetCardInfo("Celestial Prism", 235, Rarity.UNCOMMON, mage.cards.c.CelestialPrism.class));
        cards.add(new SetCardInfo("Channel", 189, Rarity.UNCOMMON, mage.cards.c.Channel.class));
        cards.add(new SetCardInfo("Chaoslace", 140, Rarity.RARE, mage.cards.c.Chaoslace.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", 10, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Blue", 11, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlue.class));
        cards.add(new SetCardInfo("Circle of Protection: Green", 12, Rarity.COMMON, mage.cards.c.CircleOfProtectionGreen.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 13, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Circle of Protection: White", 14, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Clockwork Beast", 237, Rarity.RARE, mage.cards.c.ClockworkBeast.class));
        cards.add(new SetCardInfo("Clone", 52, Rarity.UNCOMMON, mage.cards.c.Clone.class));
        cards.add(new SetCardInfo("Cockatrice", 190, Rarity.RARE, mage.cards.c.Cockatrice.class));
        cards.add(new SetCardInfo("Consecrate Land", 15, Rarity.UNCOMMON, mage.cards.c.ConsecrateLand.class));
        cards.add(new SetCardInfo("Conservator", 238, Rarity.UNCOMMON, mage.cards.c.Conservator.class));
        cards.add(new SetCardInfo("Control Magic", 53, Rarity.UNCOMMON, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Conversion", 16, Rarity.UNCOMMON, mage.cards.c.Conversion.class));
        cards.add(new SetCardInfo("Copper Tablet", 239, Rarity.UNCOMMON, mage.cards.c.CopperTablet.class));
        cards.add(new SetCardInfo("Copy Artifact", 54, Rarity.RARE, mage.cards.c.CopyArtifact.class));
        cards.add(new SetCardInfo("Counterspell", 55, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Craw Wurm", 191, Rarity.COMMON, mage.cards.c.CrawWurm.class));
        cards.add(new SetCardInfo("Creature Bond", 56, Rarity.COMMON, mage.cards.c.CreatureBond.class));
        cards.add(new SetCardInfo("Crusade", 17, Rarity.RARE, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Crystal Rod", 240, Rarity.UNCOMMON, mage.cards.c.CrystalRod.class));
        cards.add(new SetCardInfo("Cursed Land", 98, Rarity.UNCOMMON, mage.cards.c.CursedLand.class));
        cards.add(new SetCardInfo("Cyclopean Tomb", 241, Rarity.RARE, mage.cards.c.CyclopeanTomb.class));
        cards.add(new SetCardInfo("Dark Ritual", 99, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Death Ward", 18, Rarity.COMMON, mage.cards.d.DeathWard.class));
        cards.add(new SetCardInfo("Deathgrip", 101, Rarity.UNCOMMON, mage.cards.d.Deathgrip.class));
        cards.add(new SetCardInfo("Deathlace", 102, Rarity.RARE, mage.cards.d.Deathlace.class));
        cards.add(new SetCardInfo("Demonic Hordes", 104, Rarity.RARE, mage.cards.d.DemonicHordes.class));
        cards.add(new SetCardInfo("Demonic Tutor", 105, Rarity.UNCOMMON, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Dingus Egg", 242, Rarity.RARE, mage.cards.d.DingusEgg.class));
        cards.add(new SetCardInfo("Disenchant", 19, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disintegrate", 141, Rarity.COMMON, mage.cards.d.Disintegrate.class));
        cards.add(new SetCardInfo("Disrupting Scepter", 243, Rarity.RARE, mage.cards.d.DisruptingScepter.class));
        cards.add(new SetCardInfo("Dragon Whelp", 142, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Drain Life", 106, Rarity.COMMON, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 107, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Dwarven Demolition Team", 143, Rarity.UNCOMMON, mage.cards.d.DwarvenDemolitionTeam.class));
        cards.add(new SetCardInfo("Dwarven Warriors", 144, Rarity.COMMON, mage.cards.d.DwarvenWarriors.class));
        cards.add(new SetCardInfo("Earth Elemental", 145, Rarity.UNCOMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Earthbind", 146, Rarity.COMMON, mage.cards.e.Earthbind.class));
        cards.add(new SetCardInfo("Earthquake", 147, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Elvish Archers", 192, Rarity.RARE, mage.cards.e.ElvishArchers.class));
        cards.add(new SetCardInfo("Evil Presence", 108, Rarity.UNCOMMON, mage.cards.e.EvilPresence.class));
        cards.add(new SetCardInfo("False Orders", 148, Rarity.COMMON, mage.cards.f.FalseOrders.class));
        cards.add(new SetCardInfo("Farmstead", 20, Rarity.RARE, mage.cards.f.Farmstead.class));
        cards.add(new SetCardInfo("Fastbond", 193, Rarity.RARE, mage.cards.f.Fastbond.class));
        cards.add(new SetCardInfo("Fear", 109, Rarity.COMMON, mage.cards.f.Fear.class));
        cards.add(new SetCardInfo("Feedback", 58, Rarity.UNCOMMON, mage.cards.f.Feedback.class));
        cards.add(new SetCardInfo("Fire Elemental", 149, Rarity.UNCOMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fireball", 150, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebreathing", 151, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Flashfires", 152, Rarity.UNCOMMON, mage.cards.f.Flashfires.class));
        cards.add(new SetCardInfo("Flight", 59, Rarity.COMMON, mage.cards.f.Flight.class));
        cards.add(new SetCardInfo("Fog", 194, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Force of Nature", 195, Rarity.RARE, mage.cards.f.ForceOfNature.class));
        cards.add(new SetCardInfo("Forcefield", 244, Rarity.RARE, mage.cards.f.Forcefield.class));
        cards.add(new SetCardInfo("Forest", 300, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 301, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 302, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fork", 153, Rarity.RARE, mage.cards.f.Fork.class));
        cards.add(new SetCardInfo("Frozen Shade", 110, Rarity.COMMON, mage.cards.f.FrozenShade.class));
        cards.add(new SetCardInfo("Fungusaur", 196, Rarity.RARE, mage.cards.f.Fungusaur.class));
        cards.add(new SetCardInfo("Gaea's Liege", 197, Rarity.RARE, mage.cards.g.GaeasLiege.class));
        cards.add(new SetCardInfo("Gauntlet of Might", 245, Rarity.RARE, mage.cards.g.GauntletOfMight.class));
        cards.add(new SetCardInfo("Giant Growth", 198, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spider", 199, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Glasses of Urza", 246, Rarity.UNCOMMON, mage.cards.g.GlassesOfUrza.class));
        cards.add(new SetCardInfo("Gloom", 111, Rarity.UNCOMMON, mage.cards.g.Gloom.class));
        cards.add(new SetCardInfo("Goblin Balloon Brigade", 154, Rarity.UNCOMMON, mage.cards.g.GoblinBalloonBrigade.class));
        cards.add(new SetCardInfo("Goblin King", 155, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Granite Gargoyle", 156, Rarity.RARE, mage.cards.g.GraniteGargoyle.class));
        cards.add(new SetCardInfo("Gray Ogre", 157, Rarity.COMMON, mage.cards.g.GrayOgre.class));
        cards.add(new SetCardInfo("Green Ward", 21, Rarity.UNCOMMON, mage.cards.g.GreenWard.class));
        cards.add(new SetCardInfo("Grizzly Bears", 200, Rarity.COMMON, mage.cards.g.GrizzlyBears.class));
        cards.add(new SetCardInfo("Guardian Angel", 22, Rarity.COMMON, mage.cards.g.GuardianAngel.class));
        cards.add(new SetCardInfo("Healing Salve", 23, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Helm of Chatzuk", 247, Rarity.RARE, mage.cards.h.HelmOfChatzuk.class));
        cards.add(new SetCardInfo("Hill Giant", 158, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Holy Armor", 24, Rarity.COMMON, mage.cards.h.HolyArmor.class));
        cards.add(new SetCardInfo("Holy Strength", 25, Rarity.COMMON, mage.cards.h.HolyStrength.class));
        cards.add(new SetCardInfo("Howl from Beyond", 112, Rarity.COMMON, mage.cards.h.HowlFromBeyond.class));
        cards.add(new SetCardInfo("Howling Mine", 248, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Hurloon Minotaur", 159, Rarity.COMMON, mage.cards.h.HurloonMinotaur.class));
        cards.add(new SetCardInfo("Hurricane", 201, Rarity.UNCOMMON, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 113, Rarity.UNCOMMON, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Ice Storm", 202, Rarity.UNCOMMON, mage.cards.i.IceStorm.class));
        cards.add(new SetCardInfo("Icy Manipulator", 249, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Instill Energy", 203, Rarity.UNCOMMON, mage.cards.i.InstillEnergy.class));
        cards.add(new SetCardInfo("Invisibility", 60, Rarity.COMMON, mage.cards.i.Invisibility.class));
        cards.add(new SetCardInfo("Iron Star", 251, Rarity.UNCOMMON, mage.cards.i.IronStar.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 160, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Ironroot Treefolk", 204, Rarity.COMMON, mage.cards.i.IronrootTreefolk.class));
        cards.add(new SetCardInfo("Island Sanctuary", 26, Rarity.RARE, mage.cards.i.IslandSanctuary.class));
        cards.add(new SetCardInfo("Island", 291, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 292, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 293, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivory Cup", 252, Rarity.UNCOMMON, mage.cards.i.IvoryCup.class));
        cards.add(new SetCardInfo("Jade Monolith", 253, Rarity.RARE, mage.cards.j.JadeMonolith.class));
        cards.add(new SetCardInfo("Jade Statue", 254, Rarity.UNCOMMON, mage.cards.j.JadeStatue.class));
        cards.add(new SetCardInfo("Jayemdae Tome", 255, Rarity.RARE, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Juggernaut", 256, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jump", 61, Rarity.COMMON, mage.cards.j.Jump.class));
        cards.add(new SetCardInfo("Karma", 27, Rarity.UNCOMMON, mage.cards.k.Karma.class));
        cards.add(new SetCardInfo("Keldon Warlord", 161, Rarity.UNCOMMON, mage.cards.k.KeldonWarlord.class));
        cards.add(new SetCardInfo("Kormus Bell", 257, Rarity.RARE, mage.cards.k.KormusBell.class));
        cards.add(new SetCardInfo("Kudzu", 205, Rarity.RARE, mage.cards.k.Kudzu.class));
        cards.add(new SetCardInfo("Lance", 28, Rarity.UNCOMMON, mage.cards.l.Lance.class));
        cards.add(new SetCardInfo("Ley Druid", 206, Rarity.UNCOMMON, mage.cards.l.LeyDruid.class));
        cards.add(new SetCardInfo("Library of Leng", 258, Rarity.UNCOMMON, mage.cards.l.LibraryOfLeng.class));
        cards.add(new SetCardInfo("Lich", 114, Rarity.RARE, mage.cards.l.Lich.class));
        cards.add(new SetCardInfo("Lifeforce", 207, Rarity.UNCOMMON, mage.cards.l.Lifeforce.class));
        cards.add(new SetCardInfo("Lifelace", 208, Rarity.RARE, mage.cards.l.Lifelace.class));
        cards.add(new SetCardInfo("Lifetap", 62, Rarity.UNCOMMON, mage.cards.l.Lifetap.class));
        cards.add(new SetCardInfo("Lightning Bolt", 162, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Living Artifact", 209, Rarity.RARE, mage.cards.l.LivingArtifact.class));
        cards.add(new SetCardInfo("Living Lands", 210, Rarity.RARE, mage.cards.l.LivingLands.class));
        cards.add(new SetCardInfo("Living Wall", 259, Rarity.UNCOMMON, mage.cards.l.LivingWall.class));
        cards.add(new SetCardInfo("Llanowar Elves", 211, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 63, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Lord of the Pit", 115, Rarity.RARE, mage.cards.l.LordOfThePit.class));
        cards.add(new SetCardInfo("Lure", 212, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 65, Rarity.RARE, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Mana Flare", 163, Rarity.RARE, mage.cards.m.ManaFlare.class));
        cards.add(new SetCardInfo("Mana Short", 66, Rarity.RARE, mage.cards.m.ManaShort.class));
        cards.add(new SetCardInfo("Mana Vault", 260, Rarity.RARE, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Manabarbs", 164, Rarity.RARE, mage.cards.m.Manabarbs.class));
        cards.add(new SetCardInfo("Meekstone", 261, Rarity.RARE, mage.cards.m.Meekstone.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 67, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mesa Pegasus", 29, Rarity.COMMON, mage.cards.m.MesaPegasus.class));
        cards.add(new SetCardInfo("Mind Twist", 116, Rarity.RARE, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 165, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Mountain", 297, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 298, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 299, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Emerald", 262, Rarity.RARE, mage.cards.m.MoxEmerald.class));
        cards.add(new SetCardInfo("Mox Jet", 263, Rarity.RARE, mage.cards.m.MoxJet.class));
        cards.add(new SetCardInfo("Mox Pearl", 264, Rarity.RARE, mage.cards.m.MoxPearl.class));
        cards.add(new SetCardInfo("Mox Ruby", 265, Rarity.RARE, mage.cards.m.MoxRuby.class));
        cards.add(new SetCardInfo("Mox Sapphire", 266, Rarity.RARE, mage.cards.m.MoxSapphire.class));
        cards.add(new SetCardInfo("Natural Selection", 213, Rarity.RARE, mage.cards.n.NaturalSelection.class));
        cards.add(new SetCardInfo("Nether Shadow", 117, Rarity.RARE, mage.cards.n.NetherShadow.class));
        cards.add(new SetCardInfo("Nettling Imp", 118, Rarity.UNCOMMON, mage.cards.n.NettlingImp.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 267, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Nightmare", 119, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Northern Paladin", 30, Rarity.RARE, mage.cards.n.NorthernPaladin.class));
        cards.add(new SetCardInfo("Obsianus Golem", 268, Rarity.UNCOMMON, mage.cards.o.ObsianusGolem.class));
        cards.add(new SetCardInfo("Orcish Artillery", 166, Rarity.UNCOMMON, mage.cards.o.OrcishArtillery.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 167, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Paralyze", 120, Rarity.COMMON, mage.cards.p.Paralyze.class));
        cards.add(new SetCardInfo("Pearled Unicorn", 31, Rarity.COMMON, mage.cards.p.PearledUnicorn.class));
        cards.add(new SetCardInfo("Personal Incarnation", 32, Rarity.RARE, mage.cards.p.PersonalIncarnation.class));
        cards.add(new SetCardInfo("Pestilence", 121, Rarity.COMMON, mage.cards.p.Pestilence.class));
        cards.add(new SetCardInfo("Phantasmal Forces", 68, Rarity.UNCOMMON, mage.cards.p.PhantasmalForces.class));
        cards.add(new SetCardInfo("Phantasmal Terrain", 69, Rarity.COMMON, mage.cards.p.PhantasmalTerrain.class));
        cards.add(new SetCardInfo("Phantom Monster", 70, Rarity.UNCOMMON, mage.cards.p.PhantomMonster.class));
        cards.add(new SetCardInfo("Pirate Ship", 71, Rarity.RARE, mage.cards.p.PirateShip.class));
        cards.add(new SetCardInfo("Plague Rats", 122, Rarity.COMMON, mage.cards.p.PlagueRats.class));
        cards.add(new SetCardInfo("Plains", 288, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 289, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 290, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plateau", 280, Rarity.RARE, mage.cards.p.Plateau.class));
        cards.add(new SetCardInfo("Power Leak", 72, Rarity.COMMON, mage.cards.p.PowerLeak.class));
        cards.add(new SetCardInfo("Power Sink", 73, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Power Surge", 168, Rarity.RARE, mage.cards.p.PowerSurge.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 74, Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Psionic Blast", 75, Rarity.UNCOMMON, mage.cards.p.PsionicBlast.class));
        cards.add(new SetCardInfo("Psychic Venom", 76, Rarity.COMMON, mage.cards.p.PsychicVenom.class));
        cards.add(new SetCardInfo("Purelace", 33, Rarity.RARE, mage.cards.p.Purelace.class));
        cards.add(new SetCardInfo("Raging River", 169, Rarity.RARE, mage.cards.r.RagingRiver.class));
        cards.add(new SetCardInfo("Raise Dead", 123, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Red Elemental Blast", 170, Rarity.COMMON, mage.cards.r.RedElementalBlast.class));
        cards.add(new SetCardInfo("Red Ward", 34, Rarity.UNCOMMON, mage.cards.r.RedWard.class));
        cards.add(new SetCardInfo("Regeneration", 214, Rarity.COMMON, mage.cards.r.Regeneration.class));
        cards.add(new SetCardInfo("Regrowth", 215, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Resurrection", 35, Rarity.UNCOMMON, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Reverse Damage", 36, Rarity.RARE, mage.cards.r.ReverseDamage.class));
        cards.add(new SetCardInfo("Righteousness", 37, Rarity.RARE, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Roc of Kher Ridges", 171, Rarity.RARE, mage.cards.r.RocOfKherRidges.class));
        cards.add(new SetCardInfo("Rock Hydra", 172, Rarity.RARE, mage.cards.r.RockHydra.class));
        cards.add(new SetCardInfo("Rod of Ruin", 269, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Royal Assassin", 124, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Sacrifice", 125, Rarity.UNCOMMON, mage.cards.s.Sacrifice.class));
        cards.add(new SetCardInfo("Samite Healer", 38, Rarity.COMMON, mage.cards.s.SamiteHealer.class));
        cards.add(new SetCardInfo("Savannah Lions", 39, Rarity.RARE, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Savannah", 281, Rarity.RARE, mage.cards.s.Savannah.class));
        cards.add(new SetCardInfo("Scathe Zombies", 126, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Scavenging Ghoul", 127, Rarity.UNCOMMON, mage.cards.s.ScavengingGhoul.class));
        cards.add(new SetCardInfo("Scrubland", 282, Rarity.RARE, mage.cards.s.Scrubland.class));
        cards.add(new SetCardInfo("Scryb Sprites", 216, Rarity.COMMON, mage.cards.s.ScrybSprites.class));
        cards.add(new SetCardInfo("Sea Serpent", 77, Rarity.COMMON, mage.cards.s.SeaSerpent.class));
        cards.add(new SetCardInfo("Sedge Troll", 173, Rarity.RARE, mage.cards.s.SedgeTroll.class));
        cards.add(new SetCardInfo("Sengir Vampire", 128, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 40, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shanodin Dryads", 217, Rarity.COMMON, mage.cards.s.ShanodinDryads.class));
        cards.add(new SetCardInfo("Shatter", 174, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shivan Dragon", 175, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Simulacrum", 129, Rarity.UNCOMMON, mage.cards.s.Simulacrum.class));
        cards.add(new SetCardInfo("Sinkhole", 130, Rarity.COMMON, mage.cards.s.Sinkhole.class));
        cards.add(new SetCardInfo("Siren's Call", 78, Rarity.UNCOMMON, mage.cards.s.SirensCall.class));
        cards.add(new SetCardInfo("Smoke", 176, Rarity.RARE, mage.cards.s.Smoke.class));
        cards.add(new SetCardInfo("Sol Ring", 270, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Soul Net", 271, Rarity.UNCOMMON, mage.cards.s.SoulNet.class));
        cards.add(new SetCardInfo("Spell Blast", 80, Rarity.COMMON, mage.cards.s.SpellBlast.class));
        cards.add(new SetCardInfo("Stasis", 81, Rarity.RARE, mage.cards.s.Stasis.class));
        cards.add(new SetCardInfo("Steal Artifact", 82, Rarity.UNCOMMON, mage.cards.s.StealArtifact.class));
        cards.add(new SetCardInfo("Stone Giant", 177, Rarity.UNCOMMON, mage.cards.s.StoneGiant.class));
        cards.add(new SetCardInfo("Stone Rain", 178, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stream of Life", 218, Rarity.COMMON, mage.cards.s.StreamOfLife.class));
        cards.add(new SetCardInfo("Sunglasses of Urza", 272, Rarity.RARE, mage.cards.s.SunglassesOfUrza.class));
        cards.add(new SetCardInfo("Swamp", 294, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 295, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 296, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 41, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Taiga", 283, Rarity.RARE, mage.cards.t.Taiga.class));
        cards.add(new SetCardInfo("Terror", 131, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("The Hive", 273, Rarity.RARE, mage.cards.t.TheHive.class));
        cards.add(new SetCardInfo("Thicket Basilisk", 219, Rarity.UNCOMMON, mage.cards.t.ThicketBasilisk.class));
        cards.add(new SetCardInfo("Thoughtlace", 83, Rarity.RARE, mage.cards.t.Thoughtlace.class));
        cards.add(new SetCardInfo("Throne of Bone", 274, Rarity.UNCOMMON, mage.cards.t.ThroneOfBone.class));
        cards.add(new SetCardInfo("Timber Wolves", 220, Rarity.RARE, mage.cards.t.TimberWolves.class));
        cards.add(new SetCardInfo("Time Vault", 275, Rarity.RARE, mage.cards.t.TimeVault.class));
        cards.add(new SetCardInfo("Time Walk", 84, Rarity.RARE, mage.cards.t.TimeWalk.class));
        cards.add(new SetCardInfo("Timetwister", 85, Rarity.RARE, mage.cards.t.Timetwister.class));
        cards.add(new SetCardInfo("Tranquility", 221, Rarity.COMMON, mage.cards.t.Tranquility.class));
        cards.add(new SetCardInfo("Tropical Island", 284, Rarity.RARE, mage.cards.t.TropicalIsland.class));
        cards.add(new SetCardInfo("Tsunami", 222, Rarity.UNCOMMON, mage.cards.t.Tsunami.class));
        cards.add(new SetCardInfo("Tundra", 285, Rarity.RARE, mage.cards.t.Tundra.class));
        cards.add(new SetCardInfo("Tunnel", 179, Rarity.UNCOMMON, mage.cards.t.Tunnel.class));
        cards.add(new SetCardInfo("Twiddle", 86, Rarity.COMMON, mage.cards.t.Twiddle.class));
        cards.add(new SetCardInfo("Two-Headed Giant of Foriys", 180, Rarity.RARE, mage.cards.t.TwoHeadedGiantOfForiys.class));
        cards.add(new SetCardInfo("Underground Sea", 286, Rarity.RARE, mage.cards.u.UndergroundSea.class));
        cards.add(new SetCardInfo("Unholy Strength", 132, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Unsummon", 87, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Uthden Troll", 181, Rarity.UNCOMMON, mage.cards.u.UthdenTroll.class));
        cards.add(new SetCardInfo("Verduran Enchantress", 223, Rarity.RARE, mage.cards.v.VerduranEnchantress.class));
        cards.add(new SetCardInfo("Vesuvan Doppelganger", 88, Rarity.RARE, mage.cards.v.VesuvanDoppelganger.class));
        cards.add(new SetCardInfo("Veteran Bodyguard", 42, Rarity.RARE, mage.cards.v.VeteranBodyguard.class));
        cards.add(new SetCardInfo("Volcanic Eruption", 89, Rarity.RARE, mage.cards.v.VolcanicEruption.class));
        cards.add(new SetCardInfo("Volcanic Island", 287, Rarity.RARE, mage.cards.v.VolcanicIsland.class));
        cards.add(new SetCardInfo("Wall of Air", 90, Rarity.UNCOMMON, mage.cards.w.WallOfAir.class));
        cards.add(new SetCardInfo("Wall of Bone", 133, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("Wall of Brambles", 224, Rarity.UNCOMMON, mage.cards.w.WallOfBrambles.class));
        cards.add(new SetCardInfo("Wall of Fire", 182, Rarity.UNCOMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Ice", 225, Rarity.UNCOMMON, mage.cards.w.WallOfIce.class));
        cards.add(new SetCardInfo("Wall of Stone", 183, Rarity.UNCOMMON, mage.cards.w.WallOfStone.class));
        cards.add(new SetCardInfo("Wall of Swords", 43, Rarity.UNCOMMON, mage.cards.w.WallOfSwords.class));
        cards.add(new SetCardInfo("Wall of Water", 91, Rarity.UNCOMMON, mage.cards.w.WallOfWater.class));
        cards.add(new SetCardInfo("Wall of Wood", 226, Rarity.COMMON, mage.cards.w.WallOfWood.class));
        cards.add(new SetCardInfo("Wanderlust", 227, Rarity.UNCOMMON, mage.cards.w.Wanderlust.class));
        cards.add(new SetCardInfo("War Mammoth", 228, Rarity.COMMON, mage.cards.w.WarMammoth.class));
        cards.add(new SetCardInfo("Warp Artifact", 134, Rarity.RARE, mage.cards.w.WarpArtifact.class));
        cards.add(new SetCardInfo("Water Elemental", 92, Rarity.UNCOMMON, mage.cards.w.WaterElemental.class));
        cards.add(new SetCardInfo("Weakness", 135, Rarity.COMMON, mage.cards.w.Weakness.class));
        cards.add(new SetCardInfo("Web", 229, Rarity.RARE, mage.cards.w.Web.class));
        cards.add(new SetCardInfo("Wheel of Fortune", 184, Rarity.RARE, mage.cards.w.WheelOfFortune.class));
        cards.add(new SetCardInfo("White Knight", 44, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("White Ward", 45, Rarity.UNCOMMON, mage.cards.w.WhiteWard.class));
        cards.add(new SetCardInfo("Wild Growth", 230, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", 136, Rarity.RARE, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Winter Orb", 276, Rarity.RARE, mage.cards.w.WinterOrb.class));
        cards.add(new SetCardInfo("Wooden Sphere", 277, Rarity.UNCOMMON, mage.cards.w.WoodenSphere.class));
        cards.add(new SetCardInfo("Wrath of God", 46, Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Zombie Master", 138, Rarity.RARE, mage.cards.z.ZombieMaster.class));
    }
}
