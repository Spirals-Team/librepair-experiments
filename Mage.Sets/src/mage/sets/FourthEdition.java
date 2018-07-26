
package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.n.NafsAsp;
import mage.cards.p.Piety;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public final class FourthEdition extends ExpansionSet {

    private static final FourthEdition instance = new FourthEdition();

    public static FourthEdition getInstance() {
        return instance;
    }

    private FourthEdition() {
        super("Fourth Edition", "4ED", ExpansionSet.buildDate(1995, 3, 1), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Abomination", 117, Rarity.UNCOMMON, mage.cards.a.Abomination.class));
        cards.add(new SetCardInfo("Air Elemental", 59, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Alabaster Potion", 1, Rarity.COMMON, mage.cards.a.AlabasterPotion.class));
        cards.add(new SetCardInfo("Aladdin's Lamp", 291, Rarity.RARE, mage.cards.a.AladdinsLamp.class));
        cards.add(new SetCardInfo("Aladdin's Ring", 292, Rarity.RARE, mage.cards.a.AladdinsRing.class));
        cards.add(new SetCardInfo("Ali Baba", 175, Rarity.UNCOMMON, mage.cards.a.AliBaba.class));
        cards.add(new SetCardInfo("Amrou Kithkin", 2, Rarity.COMMON, mage.cards.a.AmrouKithkin.class));
        cards.add(new SetCardInfo("Amulet of Kroog", 293, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class));
        cards.add(new SetCardInfo("Angry Mob", 3, Rarity.UNCOMMON, mage.cards.a.AngryMob.class));
        cards.add(new SetCardInfo("Animate Artifact", 60, Rarity.UNCOMMON, mage.cards.a.AnimateArtifact.class));
        cards.add(new SetCardInfo("Animate Dead", 118, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Animate Wall", 4, Rarity.RARE, mage.cards.a.AnimateWall.class));
        cards.add(new SetCardInfo("Ankh of Mishra", 294, Rarity.RARE, mage.cards.a.AnkhOfMishra.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 61, Rarity.COMMON, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Armageddon Clock", 295, Rarity.RARE, mage.cards.a.ArmageddonClock.class));
        cards.add(new SetCardInfo("Armageddon", 5, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 119, Rarity.UNCOMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 296, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class));
        cards.add(new SetCardInfo("Aspect of Wolf", 233, Rarity.RARE, mage.cards.a.AspectOfWolf.class));
        cards.add(new SetCardInfo("Backfire", 62, Rarity.UNCOMMON, mage.cards.b.Backfire.class));
        cards.add(new SetCardInfo("Bad Moon", 120, Rarity.RARE, mage.cards.b.BadMoon.class));
        cards.add(new SetCardInfo("Balance", 6, Rarity.RARE, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Ball Lightning", 176, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Battering Ram", 297, Rarity.COMMON, mage.cards.b.BatteringRam.class));
        cards.add(new SetCardInfo("Benalish Hero", 7, Rarity.COMMON, mage.cards.b.BenalishHero.class));
        cards.add(new SetCardInfo("Bird Maiden", 177, Rarity.COMMON, mage.cards.b.BirdMaiden.class));
        cards.add(new SetCardInfo("Birds of Paradise", 234, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Black Knight", 121, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Black Mana Battery", 298, Rarity.RARE, mage.cards.b.BlackManaBattery.class));
        cards.add(new SetCardInfo("Black Vise", 299, Rarity.UNCOMMON, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Black Ward", 8, Rarity.UNCOMMON, mage.cards.b.BlackWard.class));
        cards.add(new SetCardInfo("Blessing", 9, Rarity.RARE, mage.cards.b.Blessing.class));
        cards.add(new SetCardInfo("Blight", 122, Rarity.UNCOMMON, mage.cards.b.Blight.class));
        cards.add(new SetCardInfo("Blood Lust", 178, Rarity.COMMON, mage.cards.b.BloodLust.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 63, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blue Mana Battery", 300, Rarity.RARE, mage.cards.b.BlueManaBattery.class));
        cards.add(new SetCardInfo("Blue Ward", 10, Rarity.UNCOMMON, mage.cards.b.BlueWard.class));
        cards.add(new SetCardInfo("Bog Imp", 123, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Wraith", 124, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Bottle of Suleiman", 301, Rarity.RARE, mage.cards.b.BottleOfSuleiman.class));
        cards.add(new SetCardInfo("Brainwash", 11, Rarity.COMMON, mage.cards.b.Brainwash.class));
        cards.add(new SetCardInfo("Brass Man", 302, Rarity.UNCOMMON, mage.cards.b.BrassMan.class));
        cards.add(new SetCardInfo("Brothers of Fire", 179, Rarity.COMMON, mage.cards.b.BrothersOfFire.class));
        cards.add(new SetCardInfo("Burrowing", 180, Rarity.UNCOMMON, mage.cards.b.Burrowing.class));
        cards.add(new SetCardInfo("Carnivorous Plant", 235, Rarity.COMMON, mage.cards.c.CarnivorousPlant.class));
        cards.add(new SetCardInfo("Carrion Ants", 125, Rarity.UNCOMMON, mage.cards.c.CarrionAnts.class));
        cards.add(new SetCardInfo("Castle", 12, Rarity.UNCOMMON, mage.cards.c.Castle.class));
        cards.add(new SetCardInfo("Cave People", 181, Rarity.UNCOMMON, mage.cards.c.CavePeople.class));
        cards.add(new SetCardInfo("Celestial Prism", 304, Rarity.UNCOMMON, mage.cards.c.CelestialPrism.class));
        cards.add(new SetCardInfo("Channel", 236, Rarity.UNCOMMON, mage.cards.c.Channel.class));
        cards.add(new SetCardInfo("Chaoslace", 182, Rarity.RARE, mage.cards.c.Chaoslace.class));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 13, Rarity.COMMON, mage.cards.c.CircleOfProtectionArtifacts.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", 14, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Blue", 15, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlue.class));
        cards.add(new SetCardInfo("Circle of Protection: Green", 16, Rarity.COMMON, mage.cards.c.CircleOfProtectionGreen.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 17, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Circle of Protection: White", 18, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Clay Statue", 305, Rarity.COMMON, mage.cards.c.ClayStatue.class));
        cards.add(new SetCardInfo("Clockwork Avian", 306, Rarity.RARE, mage.cards.c.ClockworkAvian.class));
        cards.add(new SetCardInfo("Clockwork Beast", 307, Rarity.RARE, mage.cards.c.ClockworkBeast.class));
        cards.add(new SetCardInfo("Cockatrice", 237, Rarity.RARE, mage.cards.c.Cockatrice.class));
        cards.add(new SetCardInfo("Colossus of Sardia", 308, Rarity.RARE, mage.cards.c.ColossusOfSardia.class));
        cards.add(new SetCardInfo("Conservator", 309, Rarity.UNCOMMON, mage.cards.c.Conservator.class));
        cards.add(new SetCardInfo("Control Magic", 64, Rarity.UNCOMMON, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Conversion", 19, Rarity.UNCOMMON, mage.cards.c.Conversion.class));
        cards.add(new SetCardInfo("Coral Helm", 310, Rarity.RARE, mage.cards.c.CoralHelm.class));
        cards.add(new SetCardInfo("Cosmic Horror", 126, Rarity.RARE, mage.cards.c.CosmicHorror.class));
        cards.add(new SetCardInfo("Counterspell", 65, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Craw Wurm", 238, Rarity.COMMON, mage.cards.c.CrawWurm.class));
        cards.add(new SetCardInfo("Creature Bond", 66, Rarity.COMMON, mage.cards.c.CreatureBond.class));
        cards.add(new SetCardInfo("Crimson Manticore", 183, Rarity.RARE, mage.cards.c.CrimsonManticore.class));
        cards.add(new SetCardInfo("Crumble", 239, Rarity.UNCOMMON, mage.cards.c.Crumble.class));
        cards.add(new SetCardInfo("Crusade", 20, Rarity.RARE, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Crystal Rod", 311, Rarity.UNCOMMON, mage.cards.c.CrystalRod.class));
        cards.add(new SetCardInfo("Cursed Land", 127, Rarity.UNCOMMON, mage.cards.c.CursedLand.class));
        cards.add(new SetCardInfo("Cursed Rack", 312, Rarity.UNCOMMON, mage.cards.c.CursedRack.class));
        cards.add(new SetCardInfo("Cyclopean Mummy", 128, Rarity.COMMON, mage.cards.c.CyclopeanMummy.class));
        cards.add(new SetCardInfo("Dancing Scimitar", 313, Rarity.RARE, mage.cards.d.DancingScimitar.class));
        cards.add(new SetCardInfo("Dark Ritual", 129, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Death Ward", 21, Rarity.COMMON, mage.cards.d.DeathWard.class));
        cards.add(new SetCardInfo("Deathgrip", 130, Rarity.UNCOMMON, mage.cards.d.Deathgrip.class));
        cards.add(new SetCardInfo("Deathlace", 131, Rarity.RARE, mage.cards.d.Deathlace.class));
        cards.add(new SetCardInfo("Desert Twister", 240, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Detonate", 184, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Diabolic Machine", 314, Rarity.UNCOMMON, mage.cards.d.DiabolicMachine.class));
        cards.add(new SetCardInfo("Dingus Egg", 315, Rarity.RARE, mage.cards.d.DingusEgg.class));
        cards.add(new SetCardInfo("Disenchant", 22, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disintegrate", 185, Rarity.COMMON, mage.cards.d.Disintegrate.class));
        cards.add(new SetCardInfo("Disrupting Scepter", 316, Rarity.RARE, mage.cards.d.DisruptingScepter.class));
        cards.add(new SetCardInfo("Divine Transformation", 23, Rarity.UNCOMMON, mage.cards.d.DivineTransformation.class));
        cards.add(new SetCardInfo("Dragon Engine", 317, Rarity.RARE, mage.cards.d.DragonEngine.class));
        cards.add(new SetCardInfo("Dragon Whelp", 186, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Drain Life", 132, Rarity.COMMON, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Drain Power", 67, Rarity.RARE, mage.cards.d.DrainPower.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 133, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Durkwood Boars", 241, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Dwarven Warriors", 187, Rarity.COMMON, mage.cards.d.DwarvenWarriors.class));
        cards.add(new SetCardInfo("Earth Elemental", 188, Rarity.UNCOMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Earthquake", 189, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Ebony Horse", 318, Rarity.RARE, mage.cards.e.EbonyHorse.class));
        cards.add(new SetCardInfo("El-Hajjaj", 134, Rarity.RARE, mage.cards.e.ElHajjaj.class));
        cards.add(new SetCardInfo("Elder Land Wurm", 24, Rarity.RARE, mage.cards.e.ElderLandWurm.class));
        cards.add(new SetCardInfo("Elven Riders", 242, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Elvish Archers", 243, Rarity.RARE, mage.cards.e.ElvishArchers.class));
        cards.add(new SetCardInfo("Energy Flux", 68, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Energy Tap", 69, Rarity.COMMON, mage.cards.e.EnergyTap.class));
        cards.add(new SetCardInfo("Erg Raiders", 135, Rarity.COMMON, mage.cards.e.ErgRaiders.class));
        cards.add(new SetCardInfo("Erosion", 70, Rarity.COMMON, mage.cards.e.Erosion.class));
        cards.add(new SetCardInfo("Eternal Warrior", 190, Rarity.COMMON, mage.cards.e.EternalWarrior.class));
        cards.add(new SetCardInfo("Evil Presence", 136, Rarity.UNCOMMON, mage.cards.e.EvilPresence.class));
        cards.add(new SetCardInfo("Eye for an Eye", 25, Rarity.RARE, mage.cards.e.EyeForAnEye.class));
        cards.add(new SetCardInfo("Fear", 137, Rarity.COMMON, mage.cards.f.Fear.class));
        cards.add(new SetCardInfo("Feedback", 71, Rarity.UNCOMMON, mage.cards.f.Feedback.class));
        cards.add(new SetCardInfo("Fellwar Stone", 319, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fire Elemental", 191, Rarity.UNCOMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fireball", 192, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebreathing", 193, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Fissure", 194, Rarity.COMMON, mage.cards.f.Fissure.class));
        cards.add(new SetCardInfo("Flashfires", 195, Rarity.UNCOMMON, mage.cards.f.Flashfires.class));
        cards.add(new SetCardInfo("Flight", 72, Rarity.COMMON, mage.cards.f.Flight.class));
        cards.add(new SetCardInfo("Flood", 73, Rarity.COMMON, mage.cards.f.Flood.class));
        cards.add(new SetCardInfo("Flying Carpet", 320, Rarity.RARE, mage.cards.f.FlyingCarpet.class));
        cards.add(new SetCardInfo("Fog", 244, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Force of Nature", 245, Rarity.RARE, mage.cards.f.ForceOfNature.class));
        cards.add(new SetCardInfo("Forest", 376, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 377, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 378, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortified Area", 26, Rarity.COMMON, mage.cards.f.FortifiedArea.class));
        cards.add(new SetCardInfo("Frozen Shade", 138, Rarity.COMMON, mage.cards.f.FrozenShade.class));
        cards.add(new SetCardInfo("Fungusaur", 246, Rarity.RARE, mage.cards.f.Fungusaur.class));
        cards.add(new SetCardInfo("Gaea's Liege", 247, Rarity.RARE, mage.cards.g.GaeasLiege.class));
        cards.add(new SetCardInfo("Gaseous Form", 74, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Ghost Ship", 75, Rarity.UNCOMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Giant Growth", 248, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spider", 249, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Giant Strength", 196, Rarity.COMMON, mage.cards.g.GiantStrength.class));
        cards.add(new SetCardInfo("Giant Tortoise", 76, Rarity.COMMON, mage.cards.g.GiantTortoise.class));
        cards.add(new SetCardInfo("Glasses of Urza", 321, Rarity.UNCOMMON, mage.cards.g.GlassesOfUrza.class));
        cards.add(new SetCardInfo("Gloom", 139, Rarity.UNCOMMON, mage.cards.g.Gloom.class));
        cards.add(new SetCardInfo("Goblin Balloon Brigade", 197, Rarity.UNCOMMON, mage.cards.g.GoblinBalloonBrigade.class));
        cards.add(new SetCardInfo("Goblin King", 198, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Goblin Rock Sled", 199, Rarity.COMMON, mage.cards.g.GoblinRockSled.class));
        cards.add(new SetCardInfo("Grapeshot Catapult", 322, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class));
        cards.add(new SetCardInfo("Gray Ogre", 200, Rarity.COMMON, mage.cards.g.GrayOgre.class));
        cards.add(new SetCardInfo("Greed", 140, Rarity.RARE, mage.cards.g.Greed.class));
        cards.add(new SetCardInfo("Green Mana Battery", 323, Rarity.RARE, mage.cards.g.GreenManaBattery.class));
        cards.add(new SetCardInfo("Green Ward", 27, Rarity.UNCOMMON, mage.cards.g.GreenWard.class));
        cards.add(new SetCardInfo("Grizzly Bears", 250, Rarity.COMMON, mage.cards.g.GrizzlyBears.class));
        cards.add(new SetCardInfo("Healing Salve", 28, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Helm of Chatzuk", 324, Rarity.RARE, mage.cards.h.HelmOfChatzuk.class));
        cards.add(new SetCardInfo("Hill Giant", 201, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Holy Armor", 29, Rarity.COMMON, mage.cards.h.HolyArmor.class));
        cards.add(new SetCardInfo("Holy Strength", 30, Rarity.COMMON, mage.cards.h.HolyStrength.class));
        cards.add(new SetCardInfo("Howl from Beyond", 141, Rarity.COMMON, mage.cards.h.HowlFromBeyond.class));
        cards.add(new SetCardInfo("Howling Mine", 325, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Hurkyl's Recall", 77, Rarity.RARE, mage.cards.h.HurkylsRecall.class));
        cards.add(new SetCardInfo("Hurloon Minotaur", 202, Rarity.COMMON, mage.cards.h.HurloonMinotaur.class));
        cards.add(new SetCardInfo("Hurr Jackal", 203, Rarity.RARE, mage.cards.h.HurrJackal.class));
        cards.add(new SetCardInfo("Hurricane", 251, Rarity.UNCOMMON, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 142, Rarity.UNCOMMON, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Immolation", 204, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Inferno", 205, Rarity.RARE, mage.cards.i.Inferno.class));
        cards.add(new SetCardInfo("Instill Energy", 252, Rarity.UNCOMMON, mage.cards.i.InstillEnergy.class));
        cards.add(new SetCardInfo("Iron Star", 326, Rarity.UNCOMMON, mage.cards.i.IronStar.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 206, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Ironroot Treefolk", 253, Rarity.COMMON, mage.cards.i.IronrootTreefolk.class));
        cards.add(new SetCardInfo("Island Fish Jasconius", 78, Rarity.RARE, mage.cards.i.IslandFishJasconius.class));
        cards.add(new SetCardInfo("Island Sanctuary", 31, Rarity.RARE, mage.cards.i.IslandSanctuary.class));
        cards.add(new SetCardInfo("Island", 367, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 369, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivory Cup", 327, Rarity.UNCOMMON, mage.cards.i.IvoryCup.class));
        cards.add(new SetCardInfo("Ivory Tower", 328, Rarity.RARE, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jade Monolith", 329, Rarity.RARE, mage.cards.j.JadeMonolith.class));
        cards.add(new SetCardInfo("Jandor's Saddlebags", 330, Rarity.RARE, mage.cards.j.JandorsSaddlebags.class));
        cards.add(new SetCardInfo("Jayemdae Tome", 331, Rarity.RARE, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Jump", 79, Rarity.COMMON, mage.cards.j.Jump.class));
        cards.add(new SetCardInfo("Jun?n Efreet", 143, Rarity.UNCOMMON, mage.cards.j.JununEfreet.class));
        cards.add(new SetCardInfo("Karma", 32, Rarity.UNCOMMON, mage.cards.k.Karma.class));
        cards.add(new SetCardInfo("Keldon Warlord", 207, Rarity.UNCOMMON, mage.cards.k.KeldonWarlord.class));
        cards.add(new SetCardInfo("Killer Bees", 254, Rarity.UNCOMMON, mage.cards.k.KillerBees.class));
        cards.add(new SetCardInfo("Kismet", 33, Rarity.UNCOMMON, mage.cards.k.Kismet.class));
        cards.add(new SetCardInfo("Kormus Bell", 332, Rarity.RARE, mage.cards.k.KormusBell.class));
        cards.add(new SetCardInfo("Land Leeches", 255, Rarity.COMMON, mage.cards.l.LandLeeches.class));
        cards.add(new SetCardInfo("Land Tax", 34, Rarity.RARE, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Leviathan", 80, Rarity.RARE, mage.cards.l.Leviathan.class));
        cards.add(new SetCardInfo("Ley Druid", 256, Rarity.UNCOMMON, mage.cards.l.LeyDruid.class));
        cards.add(new SetCardInfo("Library of Leng", 333, Rarity.UNCOMMON, mage.cards.l.LibraryOfLeng.class));
        cards.add(new SetCardInfo("Lifeforce", 257, Rarity.UNCOMMON, mage.cards.l.Lifeforce.class));
        cards.add(new SetCardInfo("Lifelace", 258, Rarity.RARE, mage.cards.l.Lifelace.class));
        cards.add(new SetCardInfo("Lifetap", 81, Rarity.UNCOMMON, mage.cards.l.Lifetap.class));
        cards.add(new SetCardInfo("Lightning Bolt", 208, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Living Artifact", 259, Rarity.RARE, mage.cards.l.LivingArtifact.class));
        cards.add(new SetCardInfo("Living Lands", 260, Rarity.RARE, mage.cards.l.LivingLands.class));
        cards.add(new SetCardInfo("Llanowar Elves", 261, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 82, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Lord of the Pit", 144, Rarity.RARE, mage.cards.l.LordOfThePit.class));
        cards.add(new SetCardInfo("Lost Soul", 145, Rarity.COMMON, mage.cards.l.LostSoul.class));
        cards.add(new SetCardInfo("Lure", 262, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Magnetic Mountain", 209, Rarity.RARE, mage.cards.m.MagneticMountain.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 84, Rarity.RARE, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Mana Clash", 210, Rarity.RARE, mage.cards.m.ManaClash.class));
        cards.add(new SetCardInfo("Mana Flare", 211, Rarity.RARE, mage.cards.m.ManaFlare.class));
        cards.add(new SetCardInfo("Mana Short", 85, Rarity.RARE, mage.cards.m.ManaShort.class));
        cards.add(new SetCardInfo("Mana Vault", 334, Rarity.RARE, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Manabarbs", 212, Rarity.RARE, mage.cards.m.Manabarbs.class));
        cards.add(new SetCardInfo("Marsh Gas", 146, Rarity.COMMON, mage.cards.m.MarshGas.class));
        cards.add(new SetCardInfo("Marsh Viper", 263, Rarity.COMMON, mage.cards.m.MarshViper.class));
        cards.add(new SetCardInfo("Meekstone", 335, Rarity.RARE, mage.cards.m.Meekstone.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 86, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mesa Pegasus", 35, Rarity.COMMON, mage.cards.m.MesaPegasus.class));
        cards.add(new SetCardInfo("Millstone", 336, Rarity.RARE, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mind Bomb", 87, Rarity.UNCOMMON, mage.cards.m.MindBomb.class));
        cards.add(new SetCardInfo("Mind Twist", 147, Rarity.RARE, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("Mishra's Factory", 361, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Mishra's War Machine", 337, Rarity.RARE, mage.cards.m.MishrasWarMachine.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 213, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Morale", 36, Rarity.COMMON, mage.cards.m.Morale.class));
        cards.add(new SetCardInfo("Mountain", 373, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 374, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 375, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murk Dwellers", 148, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Nafs Asp", 264, Rarity.COMMON, NafsAsp.class));
        cards.add(new SetCardInfo("Nether Shadow", 149, Rarity.RARE, mage.cards.n.NetherShadow.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 338, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Nightmare", 150, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Northern Paladin", 37, Rarity.RARE, mage.cards.n.NorthernPaladin.class));
        cards.add(new SetCardInfo("Oasis", 362, Rarity.UNCOMMON, mage.cards.o.Oasis.class));
        cards.add(new SetCardInfo("Obsianus Golem", 339, Rarity.UNCOMMON, mage.cards.o.ObsianusGolem.class));
        cards.add(new SetCardInfo("Onulet", 340, Rarity.RARE, mage.cards.o.Onulet.class));
        cards.add(new SetCardInfo("Orcish Artillery", 214, Rarity.UNCOMMON, mage.cards.o.OrcishArtillery.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 215, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Ornithopter", 341, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Osai Vultures", 38, Rarity.UNCOMMON, mage.cards.o.OsaiVultures.class));
        cards.add(new SetCardInfo("Paralyze", 151, Rarity.COMMON, mage.cards.p.Paralyze.class));
        cards.add(new SetCardInfo("Pearled Unicorn", 39, Rarity.COMMON, mage.cards.p.PearledUnicorn.class));
        cards.add(new SetCardInfo("Personal Incarnation", 40, Rarity.RARE, mage.cards.p.PersonalIncarnation.class));
        cards.add(new SetCardInfo("Pestilence", 152, Rarity.COMMON, mage.cards.p.Pestilence.class));
        cards.add(new SetCardInfo("Phantasmal Forces", 88, Rarity.UNCOMMON, mage.cards.p.PhantasmalForces.class));
        cards.add(new SetCardInfo("Phantasmal Terrain", 89, Rarity.COMMON, mage.cards.p.PhantasmalTerrain.class));
        cards.add(new SetCardInfo("Phantom Monster", 90, Rarity.UNCOMMON, mage.cards.p.PhantomMonster.class));
        cards.add(new SetCardInfo("Piety", 41, Rarity.COMMON, Piety.class));
        cards.add(new SetCardInfo("Pikemen", 42, Rarity.COMMON, mage.cards.p.Pikemen.class));
        cards.add(new SetCardInfo("Pirate Ship", 91, Rarity.RARE, mage.cards.p.PirateShip.class));
        cards.add(new SetCardInfo("Pit Scorpion", 153, Rarity.COMMON, mage.cards.p.PitScorpion.class));
        cards.add(new SetCardInfo("Plague Rats", 154, Rarity.COMMON, mage.cards.p.PlagueRats.class));
        cards.add(new SetCardInfo("Plains", 364, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 365, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 366, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Leak", 92, Rarity.COMMON, mage.cards.p.PowerLeak.class));
        cards.add(new SetCardInfo("Power Sink", 93, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Power Surge", 216, Rarity.RARE, mage.cards.p.PowerSurge.class));
        cards.add(new SetCardInfo("Pradesh Gypsies", 265, Rarity.COMMON, mage.cards.p.PradeshGypsies.class));
        cards.add(new SetCardInfo("Primal Clay", 342, Rarity.RARE, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 94, Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Psionic Entity", 95, Rarity.RARE, mage.cards.p.PsionicEntity.class));
        cards.add(new SetCardInfo("Psychic Venom", 96, Rarity.COMMON, mage.cards.p.PsychicVenom.class));
        cards.add(new SetCardInfo("Purelace", 43, Rarity.RARE, mage.cards.p.Purelace.class));
        cards.add(new SetCardInfo("Pyrotechnics", 217, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Radjan Spirit", 266, Rarity.UNCOMMON, mage.cards.r.RadjanSpirit.class));
        cards.add(new SetCardInfo("Rag Man", 155, Rarity.RARE, mage.cards.r.RagMan.class));
        cards.add(new SetCardInfo("Raise Dead", 156, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Red Elemental Blast", 218, Rarity.COMMON, mage.cards.r.RedElementalBlast.class));
        cards.add(new SetCardInfo("Red Mana Battery", 343, Rarity.RARE, mage.cards.r.RedManaBattery.class));
        cards.add(new SetCardInfo("Red Ward", 44, Rarity.UNCOMMON, mage.cards.r.RedWard.class));
        cards.add(new SetCardInfo("Regeneration", 268, Rarity.COMMON, mage.cards.r.Regeneration.class));
        cards.add(new SetCardInfo("Relic Bind", 97, Rarity.RARE, mage.cards.r.RelicBind.class));
        cards.add(new SetCardInfo("Reverse Damage", 45, Rarity.RARE, mage.cards.r.ReverseDamage.class));
        cards.add(new SetCardInfo("Righteousness", 46, Rarity.RARE, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Rod of Ruin", 344, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Royal Assassin", 157, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Samite Healer", 47, Rarity.COMMON, mage.cards.s.SamiteHealer.class));
        cards.add(new SetCardInfo("Sandstorm", 269, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Savannah Lions", 48, Rarity.RARE, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scathe Zombies", 158, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Scavenging Ghoul", 159, Rarity.UNCOMMON, mage.cards.s.ScavengingGhoul.class));
        cards.add(new SetCardInfo("Scryb Sprites", 270, Rarity.COMMON, mage.cards.s.ScrybSprites.class));
        cards.add(new SetCardInfo("Sea Serpent", 98, Rarity.COMMON, mage.cards.s.SeaSerpent.class));
        cards.add(new SetCardInfo("Seeker", 49, Rarity.COMMON, mage.cards.s.Seeker.class));
        cards.add(new SetCardInfo("Segovian Leviathan", 99, Rarity.UNCOMMON, mage.cards.s.SegovianLeviathan.class));
        cards.add(new SetCardInfo("Sengir Vampire", 160, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 50, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shanodin Dryads", 271, Rarity.COMMON, mage.cards.s.ShanodinDryads.class));
        cards.add(new SetCardInfo("Shapeshifter", 345, Rarity.UNCOMMON, mage.cards.s.Shapeshifter.class));
        cards.add(new SetCardInfo("Shatter", 219, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shivan Dragon", 220, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Simulacrum", 161, Rarity.UNCOMMON, mage.cards.s.Simulacrum.class));
        cards.add(new SetCardInfo("Sindbad", 100, Rarity.UNCOMMON, mage.cards.s.Sindbad.class));
        cards.add(new SetCardInfo("Siren's Call", 101, Rarity.UNCOMMON, mage.cards.s.SirensCall.class));
        cards.add(new SetCardInfo("Sisters of the Flame", 221, Rarity.COMMON, mage.cards.s.SistersOfTheFlame.class));
        cards.add(new SetCardInfo("Smoke", 222, Rarity.RARE, mage.cards.s.Smoke.class));
        cards.add(new SetCardInfo("Sorceress Queen", 162, Rarity.RARE, mage.cards.s.SorceressQueen.class));
        cards.add(new SetCardInfo("Soul Net", 346, Rarity.UNCOMMON, mage.cards.s.SoulNet.class));
        cards.add(new SetCardInfo("Spell Blast", 103, Rarity.COMMON, mage.cards.s.SpellBlast.class));
        cards.add(new SetCardInfo("Spirit Link", 51, Rarity.UNCOMMON, mage.cards.s.SpiritLink.class));
        cards.add(new SetCardInfo("Spirit Shackle", 163, Rarity.UNCOMMON, mage.cards.s.SpiritShackle.class));
        cards.add(new SetCardInfo("Stasis", 104, Rarity.RARE, mage.cards.s.Stasis.class));
        cards.add(new SetCardInfo("Steal Artifact", 105, Rarity.UNCOMMON, mage.cards.s.StealArtifact.class));
        cards.add(new SetCardInfo("Stone Giant", 223, Rarity.UNCOMMON, mage.cards.s.StoneGiant.class));
        cards.add(new SetCardInfo("Stone Rain", 224, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stream of Life", 272, Rarity.COMMON, mage.cards.s.StreamOfLife.class));
        cards.add(new SetCardInfo("Strip Mine", 363, Rarity.UNCOMMON, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Sunglasses of Urza", 347, Rarity.RARE, mage.cards.s.SunglassesOfUrza.class));
        cards.add(new SetCardInfo("Sunken City", 106, Rarity.COMMON, mage.cards.s.SunkenCity.class));
        cards.add(new SetCardInfo("Swamp", 370, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 371, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 372, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 52, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Library", 273, Rarity.RARE, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Tawnos's Wand", 348, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 349, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class));
        cards.add(new SetCardInfo("Terror", 164, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Tetravus", 350, Rarity.RARE, mage.cards.t.Tetravus.class));
        cards.add(new SetCardInfo("The Brute", 226, Rarity.COMMON, mage.cards.t.TheBrute.class));
        cards.add(new SetCardInfo("The Hive", 351, Rarity.RARE, mage.cards.t.TheHive.class));
        cards.add(new SetCardInfo("The Rack", 352, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
        cards.add(new SetCardInfo("Thicket Basilisk", 274, Rarity.UNCOMMON, mage.cards.t.ThicketBasilisk.class));
        cards.add(new SetCardInfo("Thoughtlace", 107, Rarity.RARE, mage.cards.t.Thoughtlace.class));
        cards.add(new SetCardInfo("Throne of Bone", 353, Rarity.UNCOMMON, mage.cards.t.ThroneOfBone.class));
        cards.add(new SetCardInfo("Timber Wolves", 275, Rarity.RARE, mage.cards.t.TimberWolves.class));
        cards.add(new SetCardInfo("Time Elemental", 108, Rarity.RARE, mage.cards.t.TimeElemental.class));
        cards.add(new SetCardInfo("Titania's Song", 276, Rarity.RARE, mage.cards.t.TitaniasSong.class));
        cards.add(new SetCardInfo("Tranquility", 277, Rarity.COMMON, mage.cards.t.Tranquility.class));
        cards.add(new SetCardInfo("Triskelion", 354, Rarity.RARE, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Tsunami", 278, Rarity.UNCOMMON, mage.cards.t.Tsunami.class));
        cards.add(new SetCardInfo("Tundra Wolves", 53, Rarity.COMMON, mage.cards.t.TundraWolves.class));
        cards.add(new SetCardInfo("Tunnel", 227, Rarity.UNCOMMON, mage.cards.t.Tunnel.class));
        cards.add(new SetCardInfo("Twiddle", 109, Rarity.COMMON, mage.cards.t.Twiddle.class));
        cards.add(new SetCardInfo("Uncle Istvan", 165, Rarity.UNCOMMON, mage.cards.u.UncleIstvan.class));
        cards.add(new SetCardInfo("Unholy Strength", 166, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Unstable Mutation", 110, Rarity.COMMON, mage.cards.u.UnstableMutation.class));
        cards.add(new SetCardInfo("Unsummon", 111, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Untamed Wilds", 279, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Urza's Avenger", 355, Rarity.RARE, mage.cards.u.UrzasAvenger.class));
        cards.add(new SetCardInfo("Uthden Troll", 228, Rarity.UNCOMMON, mage.cards.u.UthdenTroll.class));
        cards.add(new SetCardInfo("Vampire Bats", 167, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Venom", 280, Rarity.COMMON, mage.cards.v.Venom.class));
        cards.add(new SetCardInfo("Verduran Enchantress", 281, Rarity.RARE, mage.cards.v.VerduranEnchantress.class));
        cards.add(new SetCardInfo("Visions", 54, Rarity.UNCOMMON, mage.cards.v.Visions.class));
        cards.add(new SetCardInfo("Volcanic Eruption", 112, Rarity.RARE, mage.cards.v.VolcanicEruption.class));
        cards.add(new SetCardInfo("Wall of Air", 113, Rarity.UNCOMMON, mage.cards.w.WallOfAir.class));
        cards.add(new SetCardInfo("Wall of Bone", 168, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("Wall of Brambles", 282, Rarity.UNCOMMON, mage.cards.w.WallOfBrambles.class));
        cards.add(new SetCardInfo("Wall of Dust", 229, Rarity.UNCOMMON, mage.cards.w.WallOfDust.class));
        cards.add(new SetCardInfo("Wall of Fire", 230, Rarity.UNCOMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Ice", 283, Rarity.UNCOMMON, mage.cards.w.WallOfIce.class));
        cards.add(new SetCardInfo("Wall of Spears", 356, Rarity.COMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Wall of Stone", 231, Rarity.UNCOMMON, mage.cards.w.WallOfStone.class));
        cards.add(new SetCardInfo("Wall of Swords", 55, Rarity.UNCOMMON, mage.cards.w.WallOfSwords.class));
        cards.add(new SetCardInfo("Wall of Water", 114, Rarity.UNCOMMON, mage.cards.w.WallOfWater.class));
        cards.add(new SetCardInfo("Wall of Wood", 284, Rarity.COMMON, mage.cards.w.WallOfWood.class));
        cards.add(new SetCardInfo("Wanderlust", 285, Rarity.UNCOMMON, mage.cards.w.Wanderlust.class));
        cards.add(new SetCardInfo("War Mammoth", 286, Rarity.COMMON, mage.cards.w.WarMammoth.class));
        cards.add(new SetCardInfo("Warp Artifact", 169, Rarity.RARE, mage.cards.w.WarpArtifact.class));
        cards.add(new SetCardInfo("Water Elemental", 115, Rarity.UNCOMMON, mage.cards.w.WaterElemental.class));
        cards.add(new SetCardInfo("Weakness", 170, Rarity.COMMON, mage.cards.w.Weakness.class));
        cards.add(new SetCardInfo("Web", 287, Rarity.RARE, mage.cards.w.Web.class));
        cards.add(new SetCardInfo("Whirling Dervish", 288, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class));
        cards.add(new SetCardInfo("White Knight", 56, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("White Mana Battery", 357, Rarity.RARE, mage.cards.w.WhiteManaBattery.class));
        cards.add(new SetCardInfo("White Ward", 57, Rarity.UNCOMMON, mage.cards.w.WhiteWard.class));
        cards.add(new SetCardInfo("Wild Growth", 289, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", 171, Rarity.RARE, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Winds of Change", 232, Rarity.RARE, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Winter Blast", 290, Rarity.UNCOMMON, mage.cards.w.WinterBlast.class));
        cards.add(new SetCardInfo("Winter Orb", 358, Rarity.RARE, mage.cards.w.WinterOrb.class));
        cards.add(new SetCardInfo("Wooden Sphere", 359, Rarity.UNCOMMON, mage.cards.w.WoodenSphere.class));
        cards.add(new SetCardInfo("Word of Binding", 172, Rarity.COMMON, mage.cards.w.WordOfBinding.class));
        cards.add(new SetCardInfo("Wrath of God", 58, Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Xenic Poltergeist", 173, Rarity.RARE, mage.cards.x.XenicPoltergeist.class));
        cards.add(new SetCardInfo("Yotian Soldier", 360, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
        cards.add(new SetCardInfo("Zephyr Falcon", 116, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class));
        cards.add(new SetCardInfo("Zombie Master", 174, Rarity.RARE, mage.cards.z.ZombieMaster.class));
    }
}
