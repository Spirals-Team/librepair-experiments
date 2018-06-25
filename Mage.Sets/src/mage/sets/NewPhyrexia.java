package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class NewPhyrexia extends ExpansionSet {

    private static final NewPhyrexia instance = new NewPhyrexia();

    public static NewPhyrexia getInstance() {
        return instance;
    }

    private NewPhyrexia() {
        super("New Phyrexia", "NPH", ExpansionSet.buildDate(2011, 4, 4), SetType.EXPANSION);
        this.blockName = "Scars of Mirrodin";
        this.parentSet = ScarsOfMirrodin.getInstance();
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Act of Aggression", 78, Rarity.UNCOMMON, mage.cards.a.ActOfAggression.class));
        cards.add(new SetCardInfo("Alloy Myr", 129, Rarity.UNCOMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Apostle's Blessing", 2, Rarity.COMMON, mage.cards.a.ApostlesBlessing.class));
        cards.add(new SetCardInfo("Argent Mutation", 27, Rarity.UNCOMMON, mage.cards.a.ArgentMutation.class));
        cards.add(new SetCardInfo("Arm with Aether", 28, Rarity.UNCOMMON, mage.cards.a.ArmWithAether.class));
        cards.add(new SetCardInfo("Artillerize", 79, Rarity.COMMON, mage.cards.a.Artillerize.class));
        cards.add(new SetCardInfo("Auriok Survivors", 3, Rarity.UNCOMMON, mage.cards.a.AuriokSurvivors.class));
        cards.add(new SetCardInfo("Batterskull", 130, Rarity.MYTHIC, mage.cards.b.Batterskull.class));
        cards.add(new SetCardInfo("Beast Within", 103, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Birthing Pod", 104, Rarity.RARE, mage.cards.b.BirthingPod.class));
        cards.add(new SetCardInfo("Blade Splicer", 4, Rarity.RARE, mage.cards.b.BladeSplicer.class));
        cards.add(new SetCardInfo("Blighted Agent", 29, Rarity.COMMON, mage.cards.b.BlightedAgent.class));
        cards.add(new SetCardInfo("Blinding Souleater", 131, Rarity.COMMON, mage.cards.b.BlindingSouleater.class));
        cards.add(new SetCardInfo("Blind Zealot", 52, Rarity.COMMON, mage.cards.b.BlindZealot.class));
        cards.add(new SetCardInfo("Bludgeon Brawl", 80, Rarity.RARE, mage.cards.b.BludgeonBrawl.class));
        cards.add(new SetCardInfo("Brutalizer Exarch", 105, Rarity.UNCOMMON, mage.cards.b.BrutalizerExarch.class));
        cards.add(new SetCardInfo("Caged Sun", 132, Rarity.RARE, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Caress of Phyrexia", 53, Rarity.UNCOMMON, mage.cards.c.CaressOfPhyrexia.class));
        cards.add(new SetCardInfo("Cathedral Membrane", 5, Rarity.UNCOMMON, mage.cards.c.CathedralMembrane.class));
        cards.add(new SetCardInfo("Chained Throatseeker", 30, Rarity.COMMON, mage.cards.c.ChainedThroatseeker.class));
        cards.add(new SetCardInfo("Chancellor of the Annex", 6, Rarity.RARE, mage.cards.c.ChancellorOfTheAnnex.class));
        cards.add(new SetCardInfo("Chancellor of the Dross", 54, Rarity.RARE, mage.cards.c.ChancellorOfTheDross.class));
        cards.add(new SetCardInfo("Chancellor of the Forge", 81, Rarity.RARE, mage.cards.c.ChancellorOfTheForge.class));
        cards.add(new SetCardInfo("Chancellor of the Spires", 31, Rarity.RARE, mage.cards.c.ChancellorOfTheSpires.class));
        cards.add(new SetCardInfo("Chancellor of the Tangle", 106, Rarity.RARE, mage.cards.c.ChancellorOfTheTangle.class));
        cards.add(new SetCardInfo("Conversion Chamber", 133, Rarity.UNCOMMON, mage.cards.c.ConversionChamber.class));
        cards.add(new SetCardInfo("Corrosive Gale", 107, Rarity.UNCOMMON, mage.cards.c.CorrosiveGale.class));
        cards.add(new SetCardInfo("Corrupted Resolve", 32, Rarity.UNCOMMON, mage.cards.c.CorruptedResolve.class));
        cards.add(new SetCardInfo("Darksteel Relic", 134, Rarity.UNCOMMON, mage.cards.d.DarksteelRelic.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", 108, Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Deceiver Exarch", 33, Rarity.UNCOMMON, mage.cards.d.DeceiverExarch.class));
        cards.add(new SetCardInfo("Defensive Stance", 34, Rarity.COMMON, mage.cards.d.DefensiveStance.class));
        cards.add(new SetCardInfo("Dementia Bat", 55, Rarity.COMMON, mage.cards.d.DementiaBat.class));
        cards.add(new SetCardInfo("Despise", 56, Rarity.UNCOMMON, mage.cards.d.Despise.class));
        cards.add(new SetCardInfo("Dismember", 57, Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Dispatch", 7, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Due Respect", 8, Rarity.UNCOMMON, mage.cards.d.DueRespect.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 9, Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Enslave", 58, Rarity.UNCOMMON, mage.cards.e.Enslave.class));
        cards.add(new SetCardInfo("Entomber Exarch", 59, Rarity.UNCOMMON, mage.cards.e.EntomberExarch.class));
        cards.add(new SetCardInfo("Etched Monstrosity", 135, Rarity.MYTHIC, mage.cards.e.EtchedMonstrosity.class));
        cards.add(new SetCardInfo("Evil Presence", 60, Rarity.COMMON, mage.cards.e.EvilPresence.class));
        cards.add(new SetCardInfo("Exclusion Ritual", 10, Rarity.UNCOMMON, mage.cards.e.ExclusionRitual.class));
        cards.add(new SetCardInfo("Fallen Ferromancer", 82, Rarity.UNCOMMON, mage.cards.f.FallenFerromancer.class));
        cards.add(new SetCardInfo("Flameborn Viron", 83, Rarity.COMMON, mage.cards.f.FlamebornViron.class));
        cards.add(new SetCardInfo("Forced Worship", 11, Rarity.COMMON, mage.cards.f.ForcedWorship.class));
        cards.add(new SetCardInfo("Forest", 174, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 175, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fresh Meat", 109, Rarity.RARE, mage.cards.f.FreshMeat.class));
        cards.add(new SetCardInfo("Furnace Scamp", 84, Rarity.COMMON, mage.cards.f.FurnaceScamp.class));
        cards.add(new SetCardInfo("Geosurge", 85, Rarity.UNCOMMON, mage.cards.g.Geosurge.class));
        cards.add(new SetCardInfo("Geth's Verdict", 61, Rarity.COMMON, mage.cards.g.GethsVerdict.class));
        cards.add(new SetCardInfo("Gitaxian Probe", 35, Rarity.COMMON, mage.cards.g.GitaxianProbe.class));
        cards.add(new SetCardInfo("Glissa's Scorn", 110, Rarity.COMMON, mage.cards.g.GlissasScorn.class));
        cards.add(new SetCardInfo("Glistener Elf", 111, Rarity.COMMON, mage.cards.g.GlistenerElf.class));
        cards.add(new SetCardInfo("Glistening Oil", 62, Rarity.RARE, mage.cards.g.GlisteningOil.class));
        cards.add(new SetCardInfo("Greenhilt Trainee", 112, Rarity.UNCOMMON, mage.cards.g.GreenhiltTrainee.class));
        cards.add(new SetCardInfo("Gremlin Mine", 136, Rarity.COMMON, mage.cards.g.GremlinMine.class));
        cards.add(new SetCardInfo("Grim Affliction", 63, Rarity.COMMON, mage.cards.g.GrimAffliction.class));
        cards.add(new SetCardInfo("Gut Shot", 86, Rarity.UNCOMMON, mage.cards.g.GutShot.class));
        cards.add(new SetCardInfo("Hex Parasite", 137, Rarity.RARE, mage.cards.h.HexParasite.class));
        cards.add(new SetCardInfo("Hovermyr", 138, Rarity.COMMON, mage.cards.h.Hovermyr.class));
        cards.add(new SetCardInfo("Ichor Explosion", 64, Rarity.UNCOMMON, mage.cards.i.IchorExplosion.class));
        cards.add(new SetCardInfo("Immolating Souleater", 139, Rarity.COMMON, mage.cards.i.ImmolatingSouleater.class));
        cards.add(new SetCardInfo("Impaler Shrike", 36, Rarity.COMMON, mage.cards.i.ImpalerShrike.class));
        cards.add(new SetCardInfo("Inquisitor Exarch", 12, Rarity.UNCOMMON, mage.cards.i.InquisitorExarch.class));
        cards.add(new SetCardInfo("Insatiable Souleater", 140, Rarity.COMMON, mage.cards.i.InsatiableSouleater.class));
        cards.add(new SetCardInfo("Invader Parasite", 87, Rarity.RARE, mage.cards.i.InvaderParasite.class));
        cards.add(new SetCardInfo("Island", 168, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 169, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolation Cell", 141, Rarity.UNCOMMON, mage.cards.i.IsolationCell.class));
        cards.add(new SetCardInfo("Jin-Gitaxias, Core Augur", 37, Rarity.MYTHIC, mage.cards.j.JinGitaxiasCoreAugur.class));
        cards.add(new SetCardInfo("Jor Kadeen, the Prevailer", 128, Rarity.RARE, mage.cards.j.JorKadeenThePrevailer.class));
        cards.add(new SetCardInfo("Karn Liberated", 1, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Kiln Walker", 142, Rarity.UNCOMMON, mage.cards.k.KilnWalker.class));
        cards.add(new SetCardInfo("Lashwrithe", 143, Rarity.RARE, mage.cards.l.Lashwrithe.class));
        cards.add(new SetCardInfo("Leeching Bite", 113, Rarity.COMMON, mage.cards.l.LeechingBite.class));
        cards.add(new SetCardInfo("Life's Finale", 65, Rarity.RARE, mage.cards.l.LifesFinale.class));
        cards.add(new SetCardInfo("Lost Leonin", 13, Rarity.COMMON, mage.cards.l.LostLeonin.class));
        cards.add(new SetCardInfo("Loxodon Convert", 14, Rarity.COMMON, mage.cards.l.LoxodonConvert.class));
        cards.add(new SetCardInfo("Marrow Shards", 15, Rarity.UNCOMMON, mage.cards.m.MarrowShards.class));
        cards.add(new SetCardInfo("Master Splicer", 16, Rarity.UNCOMMON, mage.cards.m.MasterSplicer.class));
        cards.add(new SetCardInfo("Maul Splicer", 114, Rarity.COMMON, mage.cards.m.MaulSplicer.class));
        cards.add(new SetCardInfo("Melira, Sylvok Outcast", 115, Rarity.RARE, mage.cards.m.MeliraSylvokOutcast.class));
        cards.add(new SetCardInfo("Mental Misstep", 38, Rarity.UNCOMMON, mage.cards.m.MentalMisstep.class));
        cards.add(new SetCardInfo("Mindcrank", 144, Rarity.UNCOMMON, mage.cards.m.Mindcrank.class));
        cards.add(new SetCardInfo("Mindculling", 39, Rarity.UNCOMMON, mage.cards.m.Mindculling.class));
        cards.add(new SetCardInfo("Moltensteel Dragon", 88, Rarity.RARE, mage.cards.m.MoltensteelDragon.class));
        cards.add(new SetCardInfo("Mortis Dogs", 66, Rarity.COMMON, mage.cards.m.MortisDogs.class));
        cards.add(new SetCardInfo("Mountain", 172, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 173, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutagenic Growth", 116, Rarity.COMMON, mage.cards.m.MutagenicGrowth.class));
        cards.add(new SetCardInfo("Mycosynth Fiend", 117, Rarity.UNCOMMON, mage.cards.m.MycosynthFiend.class));
        cards.add(new SetCardInfo("Mycosynth Wellspring", 145, Rarity.COMMON, mage.cards.m.MycosynthWellspring.class));
        cards.add(new SetCardInfo("Myr Superion", 146, Rarity.RARE, mage.cards.m.MyrSuperion.class));
        cards.add(new SetCardInfo("Necropouncer", 147, Rarity.UNCOMMON, mage.cards.n.Necropouncer.class));
        cards.add(new SetCardInfo("Norn's Annex", 17, Rarity.RARE, mage.cards.n.NornsAnnex.class));
        cards.add(new SetCardInfo("Noxious Revival", 118, Rarity.UNCOMMON, mage.cards.n.NoxiousRevival.class));
        cards.add(new SetCardInfo("Numbing Dose", 40, Rarity.COMMON, mage.cards.n.NumbingDose.class));
        cards.add(new SetCardInfo("Ogre Menial", 89, Rarity.COMMON, mage.cards.o.OgreMenial.class));
        cards.add(new SetCardInfo("Omen Machine", 148, Rarity.RARE, mage.cards.o.OmenMachine.class));
        cards.add(new SetCardInfo("Parasitic Implant", 67, Rarity.COMMON, mage.cards.p.ParasiticImplant.class));
        cards.add(new SetCardInfo("Pestilent Souleater", 149, Rarity.COMMON, mage.cards.p.PestilentSouleater.class));
        cards.add(new SetCardInfo("Phyrexian Hulk", 150, Rarity.COMMON, mage.cards.p.PhyrexianHulk.class));
        cards.add(new SetCardInfo("Phyrexian Ingester", 41, Rarity.RARE, mage.cards.p.PhyrexianIngester.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 42, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Phyrexian Obliterator", 68, Rarity.MYTHIC, mage.cards.p.PhyrexianObliterator.class));
        cards.add(new SetCardInfo("Phyrexian Swarmlord", 119, Rarity.RARE, mage.cards.p.PhyrexianSwarmlord.class));
        cards.add(new SetCardInfo("Phyrexian Unlife", 18, Rarity.RARE, mage.cards.p.PhyrexianUnlife.class));
        cards.add(new SetCardInfo("Phyrexia's Core", 165, Rarity.UNCOMMON, mage.cards.p.PhyrexiasCore.class));
        cards.add(new SetCardInfo("Pith Driller", 69, Rarity.COMMON, mage.cards.p.PithDriller.class));
        cards.add(new SetCardInfo("Plains", 166, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 167, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Porcelain Legionnaire", 19, Rarity.COMMON, mage.cards.p.PorcelainLegionnaire.class));
        cards.add(new SetCardInfo("Postmortem Lunge", 70, Rarity.UNCOMMON, mage.cards.p.PostmortemLunge.class));
        cards.add(new SetCardInfo("Praetor's Grasp", 71, Rarity.RARE, mage.cards.p.PraetorsGrasp.class));
        cards.add(new SetCardInfo("Priest of Urabrask", 90, Rarity.UNCOMMON, mage.cards.p.PriestOfUrabrask.class));
        cards.add(new SetCardInfo("Pristine Talisman", 151, Rarity.COMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Psychic Barrier", 43, Rarity.COMMON, mage.cards.p.PsychicBarrier.class));
        cards.add(new SetCardInfo("Psychic Surgery", 44, Rarity.RARE, mage.cards.p.PsychicSurgery.class));
        cards.add(new SetCardInfo("Puresteel Paladin", 20, Rarity.RARE, mage.cards.p.PuresteelPaladin.class));
        cards.add(new SetCardInfo("Rage Extractor", 91, Rarity.UNCOMMON, mage.cards.r.RageExtractor.class));
        cards.add(new SetCardInfo("Razor Swine", 92, Rarity.COMMON, mage.cards.r.RazorSwine.class));
        cards.add(new SetCardInfo("Reaper of Sheoldred", 72, Rarity.UNCOMMON, mage.cards.r.ReaperOfSheoldred.class));
        cards.add(new SetCardInfo("Remember the Fallen", 21, Rarity.COMMON, mage.cards.r.RememberTheFallen.class));
        cards.add(new SetCardInfo("Rotted Hystrix", 120, Rarity.COMMON, mage.cards.r.RottedHystrix.class));
        cards.add(new SetCardInfo("Ruthless Invasion", 93, Rarity.COMMON, mage.cards.r.RuthlessInvasion.class));
        cards.add(new SetCardInfo("Scrapyard Salvo", 94, Rarity.COMMON, mage.cards.s.ScrapyardSalvo.class));
        cards.add(new SetCardInfo("Sensor Splicer", 22, Rarity.COMMON, mage.cards.s.SensorSplicer.class));
        cards.add(new SetCardInfo("Shattered Angel", 23, Rarity.UNCOMMON, mage.cards.s.ShatteredAngel.class));
        cards.add(new SetCardInfo("Sheoldred, Whispering One", 73, Rarity.MYTHIC, mage.cards.s.SheoldredWhisperingOne.class));
        cards.add(new SetCardInfo("Shriek Raptor", 24, Rarity.COMMON, mage.cards.s.ShriekRaptor.class));
        cards.add(new SetCardInfo("Shrine of Boundless Growth", 152, Rarity.UNCOMMON, mage.cards.s.ShrineOfBoundlessGrowth.class));
        cards.add(new SetCardInfo("Shrine of Burning Rage", 153, Rarity.UNCOMMON, mage.cards.s.ShrineOfBurningRage.class));
        cards.add(new SetCardInfo("Shrine of Limitless Power", 154, Rarity.UNCOMMON, mage.cards.s.ShrineOfLimitlessPower.class));
        cards.add(new SetCardInfo("Shrine of Loyal Legions", 155, Rarity.UNCOMMON, mage.cards.s.ShrineOfLoyalLegions.class));
        cards.add(new SetCardInfo("Shrine of Piercing Vision", 156, Rarity.UNCOMMON, mage.cards.s.ShrineOfPiercingVision.class));
        cards.add(new SetCardInfo("Sickleslicer", 157, Rarity.UNCOMMON, mage.cards.s.Sickleslicer.class));
        cards.add(new SetCardInfo("Slag Fiend", 95, Rarity.RARE, mage.cards.s.SlagFiend.class));
        cards.add(new SetCardInfo("Slash Panther", 96, Rarity.COMMON, mage.cards.s.SlashPanther.class));
        cards.add(new SetCardInfo("Soul Conduit", 158, Rarity.RARE, mage.cards.s.SoulConduit.class));
        cards.add(new SetCardInfo("Spellskite", 159, Rarity.RARE, mage.cards.s.Spellskite.class));
        cards.add(new SetCardInfo("Spinebiter", 121, Rarity.UNCOMMON, mage.cards.s.Spinebiter.class));
        cards.add(new SetCardInfo("Spined Thopter", 45, Rarity.COMMON, mage.cards.s.SpinedThopter.class));
        cards.add(new SetCardInfo("Spire Monitor", 46, Rarity.COMMON, mage.cards.s.SpireMonitor.class));
        cards.add(new SetCardInfo("Surge Node", 160, Rarity.UNCOMMON, mage.cards.s.SurgeNode.class));
        cards.add(new SetCardInfo("Surgical Extraction", 74, Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
        cards.add(new SetCardInfo("Suture Priest", 25, Rarity.COMMON, mage.cards.s.SuturePriest.class));
        cards.add(new SetCardInfo("Swamp", 170, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 171, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of War and Peace", 161, Rarity.MYTHIC, mage.cards.s.SwordOfWarAndPeace.class));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 47, Rarity.UNCOMMON, mage.cards.t.TezzeretsGambit.class));
        cards.add(new SetCardInfo("Thundering Tanadon", 122, Rarity.COMMON, mage.cards.t.ThunderingTanadon.class));
        cards.add(new SetCardInfo("Tormentor Exarch", 97, Rarity.UNCOMMON, mage.cards.t.TormentorExarch.class));
        cards.add(new SetCardInfo("Torpor Orb", 162, Rarity.RARE, mage.cards.t.TorporOrb.class));
        cards.add(new SetCardInfo("Toxic Nim", 75, Rarity.COMMON, mage.cards.t.ToxicNim.class));
        cards.add(new SetCardInfo("Trespassing Souleater", 163, Rarity.COMMON, mage.cards.t.TrespassingSouleater.class));
        cards.add(new SetCardInfo("Triumph of the Hordes", 123, Rarity.UNCOMMON, mage.cards.t.TriumphOfTheHordes.class));
        cards.add(new SetCardInfo("Unwinding Clock", 164, Rarity.RARE, mage.cards.u.UnwindingClock.class));
        cards.add(new SetCardInfo("Urabrask the Hidden", 98, Rarity.MYTHIC, mage.cards.u.UrabraskTheHidden.class));
        cards.add(new SetCardInfo("Vapor Snag", 48, Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Vault Skirge", 76, Rarity.COMMON, mage.cards.v.VaultSkirge.class));
        cards.add(new SetCardInfo("Victorious Destruction", 99, Rarity.COMMON, mage.cards.v.VictoriousDestruction.class));
        cards.add(new SetCardInfo("Viral Drake", 49, Rarity.UNCOMMON, mage.cards.v.ViralDrake.class));
        cards.add(new SetCardInfo("Viridian Betrayers", 124, Rarity.COMMON, mage.cards.v.ViridianBetrayers.class));
        cards.add(new SetCardInfo("Viridian Harvest", 125, Rarity.COMMON, mage.cards.v.ViridianHarvest.class));
        cards.add(new SetCardInfo("Vital Splicer", 126, Rarity.UNCOMMON, mage.cards.v.VitalSplicer.class));
        cards.add(new SetCardInfo("Volt Charge", 100, Rarity.COMMON, mage.cards.v.VoltCharge.class));
        cards.add(new SetCardInfo("Vorinclex, Voice of Hunger", 127, Rarity.MYTHIC, mage.cards.v.VorinclexVoiceOfHunger.class));
        cards.add(new SetCardInfo("Vulshok Refugee", 101, Rarity.UNCOMMON, mage.cards.v.VulshokRefugee.class));
        cards.add(new SetCardInfo("War Report", 26, Rarity.COMMON, mage.cards.w.WarReport.class));
        cards.add(new SetCardInfo("Whipflare", 102, Rarity.UNCOMMON, mage.cards.w.Whipflare.class));
        cards.add(new SetCardInfo("Whispering Specter", 77, Rarity.UNCOMMON, mage.cards.w.WhisperingSpecter.class));
        cards.add(new SetCardInfo("Wing Splicer", 50, Rarity.UNCOMMON, mage.cards.w.WingSplicer.class));
        cards.add(new SetCardInfo("Xenograft", 51, Rarity.RARE, mage.cards.x.Xenograft.class));
    }
}
