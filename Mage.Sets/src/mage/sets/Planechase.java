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
public class Planechase extends ExpansionSet {

    private static final Planechase instance = new Planechase();

    public static Planechase getInstance() {
        return instance;
    }

    private Planechase() {
        super("Planechase", "HOP", ExpansionSet.buildDate(2009, 8, 4), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        cards.add(new SetCardInfo("Akroma's Vengeance", 1, Rarity.RARE, mage.cards.a.AkromasVengeance.class));
        cards.add(new SetCardInfo("Ancient Den", 130, Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Arcbound Crusher", 105, Rarity.UNCOMMON, mage.cards.a.ArcboundCrusher.class));
        cards.add(new SetCardInfo("Arcbound Slith", 106, Rarity.UNCOMMON, mage.cards.a.ArcboundSlith.class));
        cards.add(new SetCardInfo("Arc Lightning", 46, Rarity.COMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Arsenal Thresher", 96, Rarity.COMMON, mage.cards.a.ArsenalThresher.class));
        cards.add(new SetCardInfo("Ascendant Evincar", 17, Rarity.RARE, mage.cards.a.AscendantEvincar.class));
        cards.add(new SetCardInfo("Balefire Liege", 97, Rarity.RARE, mage.cards.b.BalefireLiege.class));
        cards.add(new SetCardInfo("Battlegate Mimic", 98, Rarity.COMMON, mage.cards.b.BattlegateMimic.class));
        cards.add(new SetCardInfo("Beacon of Unrest", 18, Rarity.RARE, mage.cards.b.BeaconOfUnrest.class));
        cards.add(new SetCardInfo("Beast Hunt", 68, Rarity.COMMON, mage.cards.b.BeastHunt.class));
        cards.add(new SetCardInfo("Beseech the Queen", 19, Rarity.UNCOMMON, mage.cards.b.BeseechTheQueen.class));
        cards.add(new SetCardInfo("Blaze", 47, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Bogardan Firefiend", 48, Rarity.COMMON, mage.cards.b.BogardanFirefiend.class));
        cards.add(new SetCardInfo("Bogardan Rager", 49, Rarity.COMMON, mage.cards.b.BogardanRager.class));
        cards.add(new SetCardInfo("Boros Garrison", 131, Rarity.COMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Guildmage", 99, Rarity.UNCOMMON, mage.cards.b.BorosGuildmage.class));
        cards.add(new SetCardInfo("Boros Signet", 107, Rarity.COMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Boros Swiftblade", 82, Rarity.UNCOMMON, mage.cards.b.BorosSwiftblade.class));
        cards.add(new SetCardInfo("Bosh, Iron Golem", 108, Rarity.RARE, mage.cards.b.BoshIronGolem.class));
        cards.add(new SetCardInfo("Branching Bolt", 83, Rarity.COMMON, mage.cards.b.BranchingBolt.class));
        cards.add(new SetCardInfo("Briarhorn", 69, Rarity.UNCOMMON, mage.cards.b.Briarhorn.class));
        cards.add(new SetCardInfo("Broodstar", 8, Rarity.RARE, mage.cards.b.Broodstar.class));
        cards.add(new SetCardInfo("Browbeat", 50, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Bull Cerodon", 84, Rarity.UNCOMMON, mage.cards.b.BullCerodon.class));
        cards.add(new SetCardInfo("Cabal Coffers", 132, Rarity.UNCOMMON, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Cadaverous Knight", 20, Rarity.COMMON, mage.cards.c.CadaverousKnight.class));
        cards.add(new SetCardInfo("Cerodon Yearling", 86, Rarity.COMMON, mage.cards.c.CerodonYearling.class));
        cards.add(new SetCardInfo("Cinder Elemental", 51, Rarity.UNCOMMON, mage.cards.c.CinderElemental.class));
        cards.add(new SetCardInfo("Cone of Flame", 52, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Congregate", 2, Rarity.COMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Consume Spirit", 21, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Copper Myr", 109, Rarity.COMMON, mage.cards.c.CopperMyr.class));
        cards.add(new SetCardInfo("Corpse Harvester", 22, Rarity.UNCOMMON, mage.cards.c.CorpseHarvester.class));
        cards.add(new SetCardInfo("Cranial Plating", 110, Rarity.COMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Cruel Revival", 23, Rarity.COMMON, mage.cards.c.CruelRevival.class));
        cards.add(new SetCardInfo("Dark Ritual", 24, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Darksteel Forge", 111, Rarity.RARE, mage.cards.d.DarksteelForge.class));
        cards.add(new SetCardInfo("Death Baron", 25, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Door to Nothingness", 112, Rarity.RARE, mage.cards.d.DoorToNothingness.class));
        cards.add(new SetCardInfo("Double Cleave", 100, Rarity.COMMON, mage.cards.d.DoubleCleave.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 26, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 101, Rarity.UNCOMMON, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Etched Oracle", 113, Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 70, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Fabricate", 9, Rarity.UNCOMMON, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Fertile Ground", 71, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Fertilid", 72, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Festering Goblin", 27, Rarity.COMMON, mage.cards.f.FesteringGoblin.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", 87, Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("Flamekin Harbinger", 53, Rarity.UNCOMMON, mage.cards.f.FlamekinHarbinger.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 54, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Forest", 165, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 166, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 167, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 168, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 169, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Ancient", 73, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Furnace of Rath", 55, Rarity.RARE, mage.cards.f.FurnaceOfRath.class));
        cards.add(new SetCardInfo("Glory of Warfare", 88, Rarity.RARE, mage.cards.g.GloryOfWarfare.class));
        cards.add(new SetCardInfo("Goblin Offensive", 56, Rarity.UNCOMMON, mage.cards.g.GoblinOffensive.class));
        cards.add(new SetCardInfo("Gold Myr", 114, Rarity.COMMON, mage.cards.g.GoldMyr.class));
        cards.add(new SetCardInfo("Gravedigger", 29, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Grave Pact", 28, Rarity.RARE, mage.cards.g.GravePact.class));
        cards.add(new SetCardInfo("Great Furnace", 133, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Gruul Turf", 134, Rarity.COMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Hearthfire Hobgoblin", 102, Rarity.UNCOMMON, mage.cards.h.HearthfireHobgoblin.class));
        cards.add(new SetCardInfo("Helldozer", 30, Rarity.RARE, mage.cards.h.Helldozer.class));
        cards.add(new SetCardInfo("Hideous End", 31, Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Hull Breach", 89, Rarity.COMMON, mage.cards.h.HullBreach.class));
        cards.add(new SetCardInfo("Incremental Blight", 32, Rarity.UNCOMMON, mage.cards.i.IncrementalBlight.class));
        cards.add(new SetCardInfo("Innocent Blood", 33, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Insurrection", 57, Rarity.RARE, mage.cards.i.Insurrection.class));
        cards.add(new SetCardInfo("Iron Myr", 115, Rarity.COMMON, mage.cards.i.IronMyr.class));
        cards.add(new SetCardInfo("Island", 147, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 148, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 149, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 150, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivy Elemental", 74, Rarity.RARE, mage.cards.i.IvyElemental.class));
        cards.add(new SetCardInfo("Keep Watch", 10, Rarity.COMMON, mage.cards.k.KeepWatch.class));
        cards.add(new SetCardInfo("Keldon Champion", 58, Rarity.UNCOMMON, mage.cards.k.KeldonChampion.class));
        cards.add(new SetCardInfo("Kor Sanctifiers", 3, Rarity.COMMON, mage.cards.k.KorSanctifiers.class));
        cards.add(new SetCardInfo("Leaden Myr", 116, Rarity.COMMON, mage.cards.l.LeadenMyr.class));
        cards.add(new SetCardInfo("Leechridden Swamp", 135, Rarity.UNCOMMON, mage.cards.l.LeechriddenSwamp.class));
        cards.add(new SetCardInfo("Lightning Helix", 90, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Living Hive", 75, Rarity.RARE, mage.cards.l.LivingHive.class));
        cards.add(new SetCardInfo("Lodestone Myr", 117, Rarity.RARE, mage.cards.l.LodestoneMyr.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 118, Rarity.RARE, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Mage Slayer", 91, Rarity.UNCOMMON, mage.cards.m.MageSlayer.class));
        cards.add(new SetCardInfo("Mask of Memory", 119, Rarity.UNCOMMON, mage.cards.m.MaskOfMemory.class));
        cards.add(new SetCardInfo("Master of Etherium", 11, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Menacing Ogre", 59, Rarity.RARE, mage.cards.m.MenacingOgre.class));
        cards.add(new SetCardInfo("Mountain", 156, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 157, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 158, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 159, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 160, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 161, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 162, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 163, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 164, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Enforcer", 120, Rarity.COMMON, mage.cards.m.MyrEnforcer.class));
        cards.add(new SetCardInfo("Nefashu", 34, Rarity.RARE, mage.cards.n.Nefashu.class));
        cards.add(new SetCardInfo("Noxious Ghoul", 35, Rarity.UNCOMMON, mage.cards.n.NoxiousGhoul.class));
        cards.add(new SetCardInfo("Nuisance Engine", 121, Rarity.UNCOMMON, mage.cards.n.NuisanceEngine.class));
        cards.add(new SetCardInfo("Oblivion Ring", 4, Rarity.COMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Order // Chaos", 104, Rarity.UNCOMMON, mage.cards.o.OrderChaos.class));
        cards.add(new SetCardInfo("Orim's Thunder", 5, Rarity.COMMON, mage.cards.o.OrimsThunder.class));
        cards.add(new SetCardInfo("Pentad Prism", 122, Rarity.COMMON, mage.cards.p.PentadPrism.class));
        cards.add(new SetCardInfo("Pentavus", 123, Rarity.RARE, mage.cards.p.Pentavus.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 36, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 37, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class));
        cards.add(new SetCardInfo("Plains", 142, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 143, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 144, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 145, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 146, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prison Term", 6, Rarity.UNCOMMON, mage.cards.p.PrisonTerm.class));
        cards.add(new SetCardInfo("Profane Command", 38, Rarity.RARE, mage.cards.p.ProfaneCommand.class));
        cards.add(new SetCardInfo("Pyrotechnics", 60, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Qumulox", 12, Rarity.UNCOMMON, mage.cards.q.Qumulox.class));
        cards.add(new SetCardInfo("Rampant Growth", 76, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Razia, Boros Archangel", 92, Rarity.RARE, mage.cards.r.RaziaBorosArchangel.class));
        cards.add(new SetCardInfo("Reckless Charge", 61, Rarity.COMMON, mage.cards.r.RecklessCharge.class));
        cards.add(new SetCardInfo("Relentless Assault", 62, Rarity.RARE, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 124, Rarity.COMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Rockslide Elemental", 63, Rarity.UNCOMMON, mage.cards.r.RockslideElemental.class));
        cards.add(new SetCardInfo("Rolling Thunder", 64, Rarity.COMMON, mage.cards.r.RollingThunder.class));
        cards.add(new SetCardInfo("Rorix Bladewing", 65, Rarity.RARE, mage.cards.r.RorixBladewing.class));
        cards.add(new SetCardInfo("Rotting Rats", 39, Rarity.COMMON, mage.cards.r.RottingRats.class));
        cards.add(new SetCardInfo("Rumbling Slum", 93, Rarity.RARE, mage.cards.r.RumblingSlum.class));
        cards.add(new SetCardInfo("Sarcomite Myr", 13, Rarity.COMMON, mage.cards.s.SarcomiteMyr.class));
        cards.add(new SetCardInfo("Savage Twister", 94, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Search for Tomorrow", 77, Rarity.COMMON, mage.cards.s.SearchForTomorrow.class));
        cards.add(new SetCardInfo("Seat of the Synod", 136, Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class));
        cards.add(new SetCardInfo("Serum Tank", 125, Rarity.UNCOMMON, mage.cards.s.SerumTank.class));
        cards.add(new SetCardInfo("Shepherd of Rot", 40, Rarity.COMMON, mage.cards.s.ShepherdOfRot.class));
        cards.add(new SetCardInfo("Shivan Oasis", 137, Rarity.UNCOMMON, mage.cards.s.ShivanOasis.class));
        cards.add(new SetCardInfo("Silverglade Elemental", 78, Rarity.COMMON, mage.cards.s.SilvergladeElemental.class));
        cards.add(new SetCardInfo("Silver Myr", 126, Rarity.COMMON, mage.cards.s.SilverMyr.class));
        cards.add(new SetCardInfo("Skeleton Shard", 127, Rarity.UNCOMMON, mage.cards.s.SkeletonShard.class));
        cards.add(new SetCardInfo("Sludge Strider", 95, Rarity.UNCOMMON, mage.cards.s.SludgeStrider.class));
        cards.add(new SetCardInfo("Smokebraider", 66, Rarity.COMMON, mage.cards.s.Smokebraider.class));
        cards.add(new SetCardInfo("Soulless One", 41, Rarity.UNCOMMON, mage.cards.s.SoullessOne.class));
        cards.add(new SetCardInfo("Soul Warden", 7, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Sunhome, Fortress of the Legion", 138, Rarity.UNCOMMON, mage.cards.s.SunhomeFortressOfTheLegion.class));
        cards.add(new SetCardInfo("Suntouched Myr", 128, Rarity.COMMON, mage.cards.s.SuntouchedMyr.class));
        cards.add(new SetCardInfo("Swamp", 151, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 152, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 153, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 154, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 155, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syphon Mind", 42, Rarity.COMMON, mage.cards.s.SyphonMind.class));
        cards.add(new SetCardInfo("Syphon Soul", 43, Rarity.COMMON, mage.cards.s.SyphonSoul.class));
        cards.add(new SetCardInfo("Taurean Mauler", 67, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 139, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 14, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Tornado Elemental", 79, Rarity.RARE, mage.cards.t.TornadoElemental.class));
        cards.add(new SetCardInfo("Tree of Tales", 140, Rarity.COMMON, mage.cards.t.TreeOfTales.class));
        cards.add(new SetCardInfo("Tribal Unity", 80, Rarity.UNCOMMON, mage.cards.t.TribalUnity.class));
        cards.add(new SetCardInfo("Undead Warchief", 44, Rarity.UNCOMMON, mage.cards.u.UndeadWarchief.class));
        cards.add(new SetCardInfo("Vault of Whispers", 141, Rarity.COMMON, mage.cards.v.VaultOfWhispers.class));
        cards.add(new SetCardInfo("Vedalken Engineer", 15, Rarity.COMMON, mage.cards.v.VedalkenEngineer.class));
        cards.add(new SetCardInfo("Verdant Force", 81, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Whiplash Trap", 16, Rarity.COMMON, mage.cards.w.WhiplashTrap.class));
        cards.add(new SetCardInfo("Withered Wretch", 45, Rarity.UNCOMMON, mage.cards.w.WitheredWretch.class));
        cards.add(new SetCardInfo("Wizard Replica", 129, Rarity.COMMON, mage.cards.w.WizardReplica.class));
    }

}
