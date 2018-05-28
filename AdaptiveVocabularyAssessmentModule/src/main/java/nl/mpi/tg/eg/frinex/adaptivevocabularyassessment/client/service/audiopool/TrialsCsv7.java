package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool;

/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/**
 *
 * @author olhshk
 */
public class TrialsCsv7 {
    
    public static String CSV_CONTENT="Nr;Word;Target_nonword;Syllables;Condition;Length_list;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Noise_level;Position_foil;\n" +
"3001;kruis;fluis_1.wav;1;Target-only;6 words;viet.wav;knaf.wav;fluis_2.wav;eidind.wav;gruton.wav;goeling.wav;3;min20db;0;\n" +
"3002;slang;krang_1.wav;1;Target-only;6 words;sproor.wav;dapter.wav;koomlan.wav;krang_2.wav;goem.wav;afschaag.wav;4;min20db;0;\n" +
"3003;fiets;fuits_1.wav;1;Target-only;6 words;klork.wav;oorwag.wav;klies.wav;fuits_2.wav;pernoen.wav;voorboons.wav;4;min20db;0;\n" +
"3004;plein;ploen_1.wav;1;Target-only;6 words;roekwom.wav;hoel.wav;inbods.wav;ploen_2.wav;gik.wav;kievaar.wav;4;min20db;0;\n" +
"3005;kaas;kaat_1.wav;1;Target-only;6 words;menk.wav;waalteed.wav;voorboorn.wav;kaat_2.wav;mouning.wav;deu.wav;4;min20db;0;\n" +
"3006;tol;gol_1.wav;1;Target-only;6 words;wodel.wav;glimlilg.wav;wuisderf.wav;hoot.wav;gol_2.wav;blenk.wav;5;min20db;0;\n" +
"3007;bril;slil_1.wav;1;Target-only;6 words;oorgeek.wav;agbies.wav;blijn.wav;paldij.wav;slil_2.wav;loe.wav;5;min20db;0;\n" +
"3008;oor;oop_1.wav;1;Target-only;6 words;roord.wav;korg.wav;boodsprop.wav;woettaan.wav;oop_2.wav;klungan.wav;5;min20db;0;\n" +
"3009;touw;tomp_1.wav;1;Target-only;6 words;oormoel.wav;karp.wav;retnin.wav;alveid.wav;tomp_2.wav;penk.wav;5;min20db;0;\n" +
"3010;heks;hoks_1.wav;1;Target-only;6 words;schieling.wav;sprork.wav;baarwuig.wav;tat.wav;hoks_2.wav;loevas.wav;5;min20db;0;\n" +
"3011;bord;bops_1.wav;1;Target+Foil;3 words;bolgwap.wav;bops_2.wav;ziep.wav;;;;2;min20db;1;\n" +
"3012;aap;ijp_1.wav;1;Target+Foil;3 words;ijnrorf.wav;ijp_2.wav;wuis.wav;;;;2;min20db;1;\n" +
"3013;vlieg;vloeg_1.wav;1;Target+Foil;3 words;vloefpaak.wav;vloeg_2.wav;halp.wav;;;;2;min20db;1;\n" +
"3014;kast;kang_1.wav;1;Target+Foil;3 words;kachboop.wav;kang_2.wav;vars.wav;;;;2;min20db;1;\n" +
"3015;brug;tuch_1.wav;1;Target+Foil;3 words;tungbog.wav;tuch_2.wav;wag.wav;;;;2;min20db;1;\n" +
"3016;fles;knes_1.wav;1;Target+Foil;3 words;knerwolp.wav;knes_2.wav;gops.wav;;;;2;min20db;1;\n" +
"3017;mes;mas_1.wav;1;Target+Foil;3 words;marmon.wav;mas_2.wav;niel.wav;;;;2;min20db;1;\n" +
"3018;jas;nas_1.wav;1;Target+Foil;3 words;nafpes.wav;nas_2.wav;roor.wav;;;;2;min20db;1;\n" +
"3019;vis;jis_1.wav;1;Target+Foil;3 words;jilgruik.wav;jis_2.wav;remp.wav;;;;2;min20db;1;\n" +
"3020;voet;vuit_1.wav;1;Target+Foil;3 words;vuikschoop.wav;vuit_2.wav;naf.wav;;;;2;min20db;1;\n" +
"3021;stoel;stuil_1.wav;1;Target+Foil;3 words;stuindreek.wav;stuil_2.wav;keis.wav;;;;2;min20db;1;\n" +
"3022;bom;rom_1.wav;1;Target+Foil;3 words;rofreun.wav;rom_2.wav;zamp.wav;;;;2;min20db;1;\n" +
"3023;boom;baam_1.wav;1;Target+Foil;3 words;baagfolp.wav;baam_2.wav;stid.wav;;;;2;min20db;1;\n" +
"3024;trap;zwap_1.wav;1;Target+Foil;3 words;zwantaar.wav;zwap_2.wav;heern.wav;;;;2;min20db;1;\n" +
"3025;ring;ling_1.wav;1;Target+Foil;3 words;lixkroei.wav;ling_2.wav;daul.wav;;;;2;min20db;1;\n" +
"3026;dak;nak_1.wav;1;Target+Foil;3 words;napbeig.wav;nak_2.wav;mel.wav;;;;2;min20db;1;\n" +
"3027;glas;sjas_1.wav;1;Target+Foil;3 words;sjatmeep.wav;sjas_2.wav;voen.wav;;;;2;min20db;1;\n" +
"3028;bus;bru_1.wav;1;Target+Foil;3 words;brozil.wav;bru_2.wav;zek.wav;;;;2;min20db;1;\n" +
"3029;taart;toort_1.wav;1;Target+Foil;4 words;toolgmong.wav;toort_2.wav;veepsel.wav;sies.wav;;;2;min20db;1;\n" +
"3030;muur;scru_1.wav;1;Target+Foil;4 words;scriraal.wav;scru_2.wav;boekon.wav;oes.wav;;;2;min20db;1;\n" +
"3031;broek;groek_1.wav;1;Target+Foil;4 words;groedmiel.wav;groek_2.wav;voorwoer.wav;ses.wav;;;2;min20db;1;\n" +
"3032;oog;ood_1.wav;1;Target+Foil;4 words;ooptaag.wav;ood_2.wav;vernee.wav;krel.wav;;;2;min20db;1;\n" +
"3033;brood;bried_1.wav;1;Target+Foil;4 words;snes.wav;briepkimp.wav;bried_2.wav;jutleen.wav;;;3;min20db;2;\n" +
"3034;zon;erm_1.wav;1;Target+Foil;4 words;kangoer.wav;elgpaun.wav;erm_2.wav;mip.wav;;;3;min20db;2;\n" +
"3035;neus;kels_1.wav;1;Target+Foil;4 words;uitvong.wav;kerplem.wav;kels_2.wav;pans.wav;;;3;min20db;2;\n" +
"3036;raam;bap_1.wav;1;Target+Foil;4 words;woors.wav;bandars.wav;bap_2.wav;olvin.wav;;;3;min20db;2;\n" +
"3037;trein;paaks_1.wav;1;Target+Foil;4 words;ner.wav;paandvelm.wav;paaks_2.wav;pernijn.wav;;;3;min20db;2;\n" +
"3038;hoed;koed_1.wav;1;Target+Foil;4 words;koegpaat.wav;momp.wav;koed_2.wav;noger.wav;;;3;min20db;1;\n" +
"3039;blik;drik_1.wav;1;Target+Foil;4 words;dritmeek.wav;stetjel.wav;drik_2.wav;tar_1.wav;;;3;min20db;1;\n" +
"3040;arm;von_1.wav;1;Target+Foil;4 words;vormerp.wav;moen.wav;von_2.wav;merbog.wav;;;3;min20db;1;\n" +
"3041;kerk;nerg_1.wav;1;Target+Foil;4 words;nelktoes.wav;smoek.wav;nerg_2.wav;larnies.wav;;;3;min20db;1;\n" +
"3042;bal;laam_1.wav;1;Target+Foil;4 words;laakbuim.wav;staks.wav;laam_2.wav;scheennoon.wav;;;3;min20db;1;\n" +
"3043;paard;slein_1.wav;1;Target+Foil;4 words;sleitsog.wav;toebong.wav;slein_2.wav;luif.wav;;;3;min20db;1;\n" +
"3044;bank;benk_1.wav;1;Target+Foil;4 words;belfnast.wav;akkum.wav;benk_2.wav;vag.wav;;;3;min20db;1;\n" +
"3045;boot;book_1.wav;1;Target+Foil;4 words;boopdijs.wav;olburcht.wav;book_2.wav;gip.wav;;;3;min20db;1;\n" +
"3046;zak;zik_1.wav;1;Target+Foil;4 words;zifknil.wav;rar.wav;zik_2.wav;olmeer.wav;;;3;min20db;1;\n" +
"3047;vuur;fjon_1.wav;1;Target+Foil;5 words;fjodschelg.wav;fjon_2.wav;wisdaag.wav;tuik.wav;poks.wav;;2;min20db;1;\n" +
"3048;kip;kep_1.wav;1;Target+Foil;5 words;vui.wav;kekmieg.wav;kep_2.wav;peek.wav;tukliek.wav;;3;min20db;2;\n" +
"3049;hoef;hogt_1.wav;1;Target+Foil;5 words;ontpad.wav;holmdrins.wav;hogt_2.wav;toor.wav;roog.wav;;3;min20db;2;\n" +
"3050;stuur;hies_1.wav;1;Target+Foil;5 words;vaatlal.wav;nos.wav;hiemrief.wav;hies_2.wav;fots.wav;;4;min20db;3;\n" +
"3051;boek;zan_1.wav;1;Target+Foil;5 words;dauk.wav;andoer.wav;zallalk.wav;zan_2.wav;slon.wav;;4;min20db;3;\n" +
"3052;doos;duis_1.wav;1;Target+Foil;5 words;duigjooi.wav;fruik.wav;duis_2.wav;eels.wav;apder.wav;;3;min20db;1;\n" +
"3053;hond;ked_1.wav;1;Target+Foil;5 words;kergeuk.wav;vierlan.wav;ked_2.wav;dif.wav;volp.wav;;3;min20db;1;\n" +
"3054;hart;teur_1.wav;1;Target+Foil;5 words;teumsnaf.wav;dorts.wav;teur_2.wav;geegheid.wav;hyk.wav;;3;min20db;1;\n" +
"3055;hand;kem_1.wav;1;Target+Foil;5 words;guil.wav;kedlim.wav;sorbuin.wav;kem_2.wav;vep.wav;;4;min20db;2;\n" +
"3056;bed;zarp_1.wav;1;Target+Foil;5 words;vleek.wav;zanskolm.wav;kelf.wav;zarp_2.wav;bolpes.wav;;4;min20db;2;\n" +
"3057;deur;koef_1.wav;1;Target+Foil;5 words;raandog.wav;koepteik.wav;nocht.wav;koef_2.wav;womp.wav;;4;min20db;2;\n" +
"3058;tent;teng_1.wav;1;Target+Foil;5 words;texbuif.wav;moop.wav;faan.wav;teng_2.wav;zajukt.wav;;4;min20db;1;\n" +
"3059;huis;chlon_1.wav;1;Target+Foil;5 words;chlokhien.wav;doon.wav;keeg.wav;chlon_2.wav;vijnwel.wav;;4;min20db;1;\n" +
"3060;man;bauk_1.wav;1;Target+Foil;5 words;bauswor.wav;aaks.wav;paldur.wav;bauk_2.wav;schreerp.wav;;4;min20db;1;\n" +
"3061;kom;hats_1.wav;1;Target+Foil;5 words;halgherm.wav;badsies.wav;goos.wav;hats_2.wav;traap.wav;;4;min20db;1;\n" +
"3062;zalf;haps_1.wav;1;Target+Foil;5 words;harfbijg.wav;gruif.wav;naaglot.wav;haps_2.wav;juit.wav;;4;min20db;1;\n" +
"3063;trouw;trops_1.wav;1;Target+Foil;5 words;trolkdoef.wav;weepbuis.wav;kogs.wav;trops_2.wav;waaf.wav;;4;min20db;1;\n" +
"3064;maan;maap_1.wav;1;Target+Foil;6 words;maafnoep.wav;maap_2.wav;rargen.wav;kons.wav;wop.wav;reveer.wav;2;min20db;1;\n" +
"3065;velg;duil_1.wav;1;Target+Foil;6 words;gangkeer.wav;duiskalp.wav;duil_2.wav;opvek.wav;jeel.wav;snaag.wav;3;min20db;2;\n" +
"3066;streep;wuik_1.wav;1;Target+Foil;6 words;lendaar.wav;grem.wav;wuigkroer.wav;wuik_2.wav;koereek.wav;kork.wav;4;min20db;3;\n" +
"3067;paars;breeg_1.wav;1;Target+Foil;6 words;hoortbijn.wav;zel.wav;grubond.wav;breelmeeg.wav;breeg_2.wav;scherg.wav;5;min20db;4;\n" +
"3068;gans;gret_1.wav;1;Target+Foil;6 words;gremdoep.wav;snuim.wav;gret_2.wav;wuil.wav;warnis.wav;winkheek.wav;3;min20db;1;\n" +
"3069;kuil;baust_1.wav;1;Target+Foil;6 words;baaf.wav;baundleu.wav;foestvag.wav;baust_2.wav;tan.wav;rornas.wav;4;min20db;2;\n" +
"3070;bad;bam_1.wav;1;Target+Foil;6 words;wirums.wav;mefdel.wav;bafruip.wav;lir.wav;bam_2.wav;lank.wav;5;min20db;3;\n" +
"3071;klas;roen_1.wav;1;Target+Foil;6 words;zeut.wav;glank.wav;roegtaus.wav;lentar.wav;roen_2.wav;rielak.wav;5;min20db;3;\n" +
"3072;grot;vlaad_1.wav;1;Target+Foil;6 words;vlaasrig.wav;kalvoon.wav;voornil.wav;vlaad_2.wav;bruip.wav;bax.wav;4;min20db;1;\n" +
"3073;buik;veus_1.wav;1;Target+Foil;6 words;veuphalk.wav;ollusk.wav;gaam.wav;veus_2.wav;ijn.wav;zeiming.wav;4;min20db;1;\n" +
"3074;beest;klag_1.wav;1;Target+Foil;6 words;aalpeis.wav;klanwuist.wav;sjoek.wav;snerm.wav;klag_2.wav;arrui.wav;5;min20db;2;\n" +
"3075;vloed;pras_1.wav;1;Target+Foil;6 words;kroen.wav;prambeup.wav;sidraal.wav;beuting.wav;pras_2.wav;relp.wav;5;min20db;2;\n" +
"3076;ster;krer_1.wav;1;Target+Foil;6 words;kremgof.wav;pef.wav;monksel.wav;muig.wav;krer_2.wav;deper.wav;5;min20db;1;\n" +
"3077;steeg;strijp_1.wav;1;Target+Foil;6 words;strijmrin.wav;doog.wav;handreer.wav;zordoer.wav;strijp_2.wav;sleer.wav;5;min20db;1;\n" +
"3078;zoen;poers_1.wav;1;Target+Foil;6 words;poentflins.wav;uitmed.wav;baag.wav;vir.wav;poers_2.wav;ierwofs.wav;5;min20db;1;\n" +
"3079;klad;gars_1.wav;1;Target+Foil;6 words;galtvits.wav;raan.wav;seipoon.wav;baaps.wav;gars_2.wav;aanmed.wav;5;min20db;1;\n" +
"3080;jaar;jaaf_1.wav;1;Target+Foil;6 words;jaadmers.wav;lijngaals.wav;kral.wav;vammoer.wav;jaaf_2.wav;spool.wav;5;min20db;1;\n" +
"3081;loep;luip.wav;1;NoTarget;3 words;kleis.wav;heerting.wav;zwins.wav;;;;0;min20db;0;\n" +
"3082;doof;moof.wav;1;NoTarget;3 words;zolp.wav;heeg.wav;jasween.wav;;;;0;min20db;0;\n" +
"3083;nier;rier.wav;1;NoTarget;3 words;zuif.wav;voormool.wav;slig.wav;;;;0;min20db;0;\n" +
"3084;schuim;schijm.wav;1;NoTarget;3 words;amnaar.wav;woe.wav;trar.wav;;;;0;min20db;0;\n" +
"3085;stift;stins.wav;1;NoTarget;3 words;prok.wav;din.wav;rieding.wav;;;;0;min20db;0;\n" +
"3086;tong;tonk.wav;1;NoTarget;3 words;zandog.wav;dijg.wav;vets.wav;;;;0;min20db;0;\n" +
"3087;roer;doer.wav;1;NoTarget;3 words;staas.wav;uitbag.wav;maars.wav;;;;0;min20db;0;\n" +
"3088;week;woek.wav;1;NoTarget;3 words;vool.wav;jal.wav;tuzi.wav;;;;0;min20db;0;\n" +
"3089;zorg;zolg.wav;1;NoTarget;3 words;naap.wav;mig.wav;piroet.wav;;;;0;min20db;0;\n" +
"3090;veer;veep.wav;1;NoTarget;3 words;zaam.wav;tikaat.wav;kijs.wav;;;;0;min20db;0;\n" +
"3091;smaak;smijk.wav;1;NoTarget;3 words;veg.wav;wieel.wav;loor.wav;;;;0;min20db;0;\n" +
"3092;rots;rets.wav;1;NoTarget;3 words;gipjet.wav;wiem.wav;nauk.wav;;;;0;min20db;0;\n" +
"3093;mouw;molp.wav;1;NoTarget;3 words;cibuur.wav;rool.wav;tieg.wav;;;;0;min20db;0;\n" +
"3094;lijm;kijm.wav;1;NoTarget;3 words;pidel.wav;raun.wav;rox.wav;;;;0;min20db;0;\n" +
"3095;moord;moops.wav;1;NoTarget;4 words;fluik.wav;ontseet.wav;hook.wav;gebeeg.wav;;;0;min20db;0;\n" +
"3096;baas;baap.wav;1;NoTarget;4 words;rer.wav;bem.wav;foersmaal.wav;dadroek.wav;;;0;min20db;0;\n" +
"3097;poot;peit.wav;1;NoTarget;4 words;nal.wav;guiping.wav;buip.wav;herbing.wav;;;0;min20db;0;\n" +
"3098;blaar;slaar.wav;1;NoTarget;4 words;naags.wav;onliel.wav;zaklaak.wav;raf.wav;;;0;min20db;0;\n" +
"3099;doek;deek.wav;1;NoTarget;4 words;eerkuid.wav;jor.wav;vas.wav;wijtoeg.wav;;;0;min20db;0;\n" +
"3100;wand;wamp.wav;1;NoTarget;4 words;aanmil.wav;wons.wav;toezol.wav;fap.wav;;;0;min20db;0;\n" +
"3101;pop;lop.wav;1;NoTarget;4 words;voorserm.wav;muiland.wav;fraal.wav;kijn.wav;;;0;min20db;0;\n" +
"3102;grijs;drijs.wav;1;NoTarget;4 words;sal.wav;veis.wav;wireun.wav;pente.wav;;;0;min20db;0;\n" +
"3103;slank;slamp.wav;1;NoTarget;4 words;luin.wav;adbie.wav;lides.wav;nif.wav;;;0;min20db;0;\n" +
"3104;taal;laal.wav;1;NoTarget;4 words;glibuck.wav;rijl.wav;zas.wav;kotken.wav;;;0;min20db;0;\n" +
"3105;roem;loem.wav;1;NoTarget;4 words;salder.wav;voes.wav;guitel.wav;swi.wav;;;0;min20db;0;\n" +
"3106;bier;biem.wav;1;NoTarget;4 words;straap.wav;gijmel.wav;nil.wav;velmen.wav;;;0;min20db;0;\n" +
"3107;zoon;zoot.wav;1;NoTarget;4 words;loedes.wav;broep.wav;muip.wav;perlein.wav;;;0;min20db;0;\n" +
"3108;riem;diem.wav;1;NoTarget;4 words;girter.wav;medder.wav;biek.wav;joom.wav;;;0;min20db;0;\n" +
"3109;vocht;gocht.wav;1;NoTarget;5 words;inbong.wav;gop.wav;dorm.wav;wiffer.wav;blem.wav;;0;min20db;0;\n" +
"3110;wet;wot.wav;1;NoTarget;5 words;bijs.wav;stror.wav;spen.wav;taapman.wav;piktoor.wav;;0;min20db;0;\n" +
"3111;troon;groon.wav;1;NoTarget;5 words;woos.wav;aanbees.wav;stiem.wav;roon.wav;gardeen.wav;;0;min20db;0;\n" +
"3112;roos;ries.wav;1;NoTarget;5 words;kreen.wav;omrong.wav;aannok.wav;toots.wav;klil.wav;;0;min20db;0;\n" +
"3113;broer;troer.wav;1;NoTarget;5 words;voortegt.wav;brof.wav;blook.wav;spaag.wav;afbuip.wav;;0;min20db;0;\n" +
"3114;blaas;draas.wav;1;NoTarget;5 words;aanbag.wav;smap.wav;tetjel.wav;gog.wav;pag.wav;;0;min20db;0;\n" +
"3115;vet;vek.wav;1;NoTarget;5 words;vank.wav;linier.wav;varn.wav;lorte.wav;gruid.wav;;0;min20db;0;\n" +
"3116;rol;rof.wav;1;NoTarget;5 words;bir.wav;schroer.wav;mipper.wav;kluzel.wav;steit.wav;;0;min20db;0;\n" +
"3117;mol;mil.wav;1;NoTarget;5 words;deg.wav;spuwel.wav;prier.wav;drok.wav;ledder.wav;;0;min20db;0;\n" +
"3118;been;bien.wav;1;NoTarget;5 words;drepier.wav;pals.wav;hader.wav;bruif.wav;smoop.wav;;0;min20db;0;\n" +
"3119;gat;gar.wav;1;NoTarget;5 words;lomel.wav;strieg.wav;snaan.wav;sloog.wav;rirte.wav;;0;min20db;0;\n" +
"3120;haas;haap.wav;1;NoTarget;5 words;kloor.wav;meffie.wav;brelp.wav;mepel.wav;grak.wav;;0;min20db;0;\n" +
"3121;kaal;kaaf.wav;1;NoTarget;5 words;fieker.wav;migder.wav;schaan.wav;krelg.wav;strep.wav;;0;min20db;0;\n" +
"3122;lok;lox.wav;1;NoTarget;5 words;troof.wav;striek.wav;vlaf.wav;schroener.wav;poven.wav;;0;min20db;0;\n" +
"3123;krans;krons.wav;1;NoTarget;6 words;milfoon.wav;wiks.wav;braal.wav;palfzer.wav;kijder.wav;grui.wav;0;min20db;0;\n" +
"3124;cel;woen.wav;1;NoTarget;6 words;kaans.wav;noet.wav;woger.wav;spaper.wav;zook.wav;tanel.wav;0;min20db;0;\n" +
"3125;bloed;zwoed.wav;1;NoTarget;6 words;liem.wav;watse.wav;kieg.wav;firp.wav;bioel.wav;gebog.wav;0;min20db;0;\n" +
"3126;hoek;hauk.wav;1;NoTarget;6 words;dipon.wav;krets.wav;rahel.wav;noek.wav;ronoen.wav;druid.wav;0;min20db;0;\n" +
"3127;boer;goer.wav;1;NoTarget;6 words;uik.wav;tolas.wav;gepij.wav;lanhaap.wav;vres.wav;grank.wav;0;min20db;0;\n" +
"3128;grens;spens.wav;1;NoTarget;6 words;oening.wav;zilms.wav;lits.wav;kietsoor.wav;huipoord.wav;zimp.wav;0;min20db;0;\n" +
"3129;knop;smop.wav;1;NoTarget;6 words;loenak.wav;wandstrap.wav;ples.wav;liel.wav;jeen.wav;vergpuis.wav;0;min20db;0;\n" +
"3130;geel;geek.wav;1;NoTarget;6 words;styk.wav;deiver.wav;spok.wav;lieken.wav;drelg.wav;zesel.wav;0;min20db;0;\n" +
"3131;peen;peef.wav;1;NoTarget;6 words;hesk.wav;kraap.wav;rades.wav;garker.wav;redel.wav;stor.wav;0;min20db;0;\n" +
"3132;sik;jik.wav;1;NoTarget;6 words;ots.wav;ploots.wav;fiek.wav;keder.wav;laben.wav;faper.wav;0;min20db;0;\n" +
"3133;sop;sor.wav;1;NoTarget;6 words;raber.wav;hieg.wav;brots.wav;wref.wav;ledoer.wav;meusel.wav;0;min20db;0;\n" +
"3134;vak;vaf.wav;1;NoTarget;6 words;zapel.wav;gesplik.wav;vrops.wav;tar_2.wav;vorn.wav;linsel.wav;0;min20db;0;\n" +
"3135;was;wes.wav;1;NoTarget;6 words;colfres.wav;zalper.wav;blojerp.wav;smat.wav;rax.wav;spoeg.wav;0;min20db;0;\n" +
"3136;wol;pra.wav;1;NoTarget;6 words;reuwel.wav;wog.wav;consmilp.wav;leskert.wav;mels.wav;dwaat.wav;0;min20db;0;";

   
}
