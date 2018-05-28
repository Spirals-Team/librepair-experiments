/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.util;

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardRandomStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSubmitDataScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;

/**
 * @since Mar 16, 2016 2:35:56 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 *
 * Prose text in storyTexts are copyright Martin Rombouts used by permission
 */
public class FactOrFiction {

    private final WizardController wizardController = new WizardController();
    String[] images = new String[]{
        "Emotioneel1_1stperson.png",
        "Emotioneel2_3rdperson.png",
        "Koffiemolen2_3rdperson.png",
        "Matroesjka1_3rdperson.png",
        "Meesterwerk1_1stperson.png",
        "Meesterwerk2_3rdperson.png",
        "Emotioneel1_3rdperson.png",
        "Koffiemoeln_3rdperson.png",
        "Koffiemolen_1stperson_V2.png",
        "Matroesjka2_1stperson.png",
        "Meesterwerk1_3rdperson.png",
        "Emotioneel2_1stperson.png",
        "Koffiemolen1_1stperson.png",
        "Matroesjka1_1stperson.png",
        "Matroesjka2_3rdperson.png",
        "Meesterwerk2_1stperson.png"
    };
    String[] servey1Stimuli = new String[]{
        //    String[] attentionStimuli = new String[]{
        "A1:Tijdens het lezen van het verhaal vergat ik de tijd",
        "A2:Tijdens het lezen van het verhaal was ik geconcentreerd op wat er in het verhaal gebeurde",
        "A3:Ik voelde mij geabsorbeerd in het verhaal",
        "A4:Het verhaal pakte me zo dat ik me kon afsluiten voor wat er om mij heen gebeurde",
        "A5:Ik was zo geconcentreerd aan het lezen dat ik de wereld om mij heen even was vergeten",
        //    };
        //    String[] transportationStimuli = new String[]{
        "T1:Tijdens het lezen van het verhaal leek het soms alsof ik zelf ook in de wereld van het verhaal was",
        "T2:Tijdens het lezen van het verhaal waren er momenten waarop de wereld van het verhaal leek te overlappen met mijn eigen wereld",
        "T3:De wereld van het verhaal voelde tijdens het lezen soms dichterbij dan de wereld om mij heen",
        "T4:Toen ik klaar was met lezen van het verhaal voelde het alsof ik net een uitstapje had gemaakt naar de wereld van het verhaal",
        "T5:Omdat al mijn aandacht uit ging naar het verhaal, leek het soms alsof ik niet meer los van het verhaal bestond",
        //    };
        //    String[] emotionalEngagement = new String[]{
        "EE1: Ik kon me tijdens het lezen van het verhaal voorstellen hoe het zou zijn om in de schoenen van de hoofdpersoon te staan",
        "EE2:Ik voelde met de hoofdpersoon in het verhaal mee",
        "EE3:Ik voelde me verbonden met de hoofdpersoon in dit verhaal",
        "EE4:Ik voelde mij hoe de hoofdpersoon zich voelde",
        "EE5:Ik leefde mee met wat er gebeurde in het verhaal",
        //    };
        //    String[] mentalImagery = new String[]{
        "MS1: Tijdens het lezen van dit verhaal had ik een beeld van de hoofdpersoon voor mijn ogen",
        "MS2: Tijdens het lezen van dit verhaal kon ik de situaties die beschreven werden voor me zien",
        "MS3: Ik kon me voorstellen hoe de omgeving waarin het verhaal zich afspeelt eruit zag",
        //    };
        //    String[] perspective = new String[]{
        "P1:Soms had ik het gevoel door de ogen van de hoofdpersoon te kunnen zien",
        "P2: Tijdens het lezen zag ik situaties die beschreven werden voor me alsof ik een stille toeschouwer was"
    };

    String multipleChoiceText = "1= niet zo graag, 7 = heel graag (same as survey 1 and 3)";
    String[] readingBehavior = new String[]{
        "RB1:Leest u graag fictie? <br/>(1=niet zo graag, 7=heel graag)",
        "RB2:Komt u wel eens in aanraking met  andere soorten fictie (b.v. films of series, stripboeken etc.)<br/>(1=niet zo vaak, 7=heel vaak)",
        "RB3:Hoe vaak komt u in aanraking met fictie?<br/>(1=nooit, 7=dagelijks)",
        "RB4:Leest u graag waargebeurde verhalen?<br/>(1=niet zo graag, 7=heel graag)",
        "RB5:Leest u vaak non-fictie (bijvoorbeeld nieuwsartikelen of (auto-)biografieen), of komt u vaak op andere manieren in aanraking met non-fictie, bijvoorbeeld op tv of op de radio (bijvoorbeeld nieuws- of wetenschapsprogramma’s)?<br/>(1=niet zo vaak, 7=heel vaak)",
        "RB6:Hoe vaak komt u in aanraking met non-fictie?<br/>(1=nooit, 7=dagelijks)"
    };
    final String[] storyTexts = new String[]{
        "Emotioneel_hij:<b>Emotioneel</b><br/><br/>"
        + "Zijn oma lag op sterven, en omdat zijn moeder hem eerder op de avond had verweten dat hij niet in contact stond met zijn gevoel, gaf hij zijn muziekspeler de opdracht alleen trieste muziek zijn oren in te laten afspelen. Daar was op zijn muziekspeler een speciale instelling voor: SensMe Emotioneel; en dus kostte het hem geen moeite. Hij kon gewoon doorgaan met denken, terwijl hij wachtte op het bevestigende telefoontje.<br/>"
        + "<br/>"
        + "Twintig, dacht hij, is ook wel een goede leeftijd om je grootouders te hebben sterven. Zoals vijftig dat is voor je ouders. <br/>"
        + "De eerste tien jaar van je leven ben je van ze afhankelijk; hun laatste tien levensjaren zijn zij dat van jou. <br/>"
        + "De tweede tien jaar ben je ze afgunstig (zij zijn volwassen en mogen alles; jij alles niet); in hun op tien na laatste tien jaar (wanneer hun oogleden voor hun pupillen beginnen te hangen) zijn zij het jou.<br/>"
        + "Maar de 30 jaar daartussen leef je in harmonie. Zij koppelen hun levensgeluk aan jouw succes; jij geeft ze kleinkinderen waarin ze hun jeugdfoto’s herkennen.<br/>"
        + "Symmetrie. Evenwicht. Gelijkheid. Ouder dan zeventig hoefden zijn ouders niet te worden.<br/>"
        + "Maar waar was hij? Hij was in zijn kamer. Hij lag op bed en keek naar de vochtvlekken en spinnenwebben op het plafond, en luisterde naar de droevige muziek, zo lang als het wachten duurde. Dat was langer dan verwacht, en toen zijn moeder hem belde was het al half 4 ‘s nachts, en hij was al zeker 8 uur Emotioneel. Hij kon er zelfs om huilen, en trots tranend dacht hij: Goed zo, je staat in goed contact met je gevoel. <br/>"
        + "Met zijn muziekspeler nog aan viel hij in slaap, zijn hoofd op een nat kussen.<br/>"
        + "<br/>"
        + "Hij kan je niet zeggen of, en als: waarover, hij droomde die nacht. Hij weet het niet meer. <br/>"
        + "Maar wat hij wel weet is dat, toen hij de volgende ochtend wakker werd en de muziekspeler omschakelde van SensMe Emotioneel naar SensMe Uitbundig, zijn gevoel toen maar niet mee terug wilde veranderen.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Emotioneel_ik:<b>Emotioneel</b><br/><br/>"
        + "Mijn oma lag op sterven, en omdat mijn moeder me eerder op de avond had verweten dat ik niet in contact stond met mijn gevoel, gaf ik mijn muziekspeler de opdracht alleen trieste muziek mijn oren in te laten afspelen. Daar was op mijn muziekspeler een speciale instelling voor: SensMe Emotioneel; en dus kostte het me geen moeite. Ik kon gewoon doorgaan met denken, terwijl ik wachtte op het bevestigende belletje.<br/>"
        + "<br/>"
        + "Twintig, dacht ik, is ook wel een goede leeftijd om je grootouders te hebben sterven. Zoals vijftig dat is voor je ouders. <br/>"
        + "De eerste tien jaar van je leven ben je van ze afhankelijk; hun laatste tien levensjaren zijn zij dat van jou. <br/>"
        + "De tweede tien jaar ben je ze afgunstig (zij zijn volwassen en mogen alles; jij alles niet); in hun op tien na laatste tien jaar (wanneer hun oogleden voor hun pupillen beginnen te hangen) zijn zij het jou.<br/>"
        + "Maar de 30 jaar daartussen leef je in harmonie. Zij koppelen hun levensgeluk aan jouw succes; jij geeft ze kleinkinderen waarin ze hun jeugdfoto’s herkennen.<br/>"
        + "Symmetrie. Evenwicht. Gelijkheid. Ouder dan zeventig hoeven mijn ouders niet te worden.<br/>"
        + "Maar waar was ik? Ik was in mijn kamer. Ik lag op bed, en keek naar de vochtvlekken en spinnenwebben op het plafond, en luisterde naar de droevige muziek zo lang als het wachten duurde. Dat was langer dan verwacht en toen mijn moeder belde was het al half 4 ‘s nachts, en ik was al zeker 8 uur Emotioneel. Ik kon er zelfs om huilen, en trots tranend dacht ik: Goed zo, je staat in goedcontact met je gevoel. <br/>"
        + "Met mijn muziekspeler nog aan viel ik in slaap, mijn hoofd op een nat kussen.<br/>"
        + "<br/>"
        + "Ik kan je niet zeggen of, en als: waarover, ik droomde die nacht. Ik weet het niet meer. <br/>"
        + "Maar wat ik wel weet is dat, toen ik de volgende ochtend wakker werd en de muziekspeler omschakelde van SensMe Emotioneel naar SensMe Uitbundig, mijn gevoel toen maar niet mee terug wilde veranderen."
        + "<br/><br/><br/><br/><br/><br/>",
        "Koffiemolen_hij:<b>Koffiemolen.</b><br/><br/>"
        + "In de keuken van haar Utrechtse studentenhuis staan zeven koffiezetapparaten.<br/>"
        + "<br/>"
        + "Dubbele punt:<br/>"
        + "- Twee filterkoffieapparaten (oud) (vies) (plakkerige stoflaag) (gebruikt niemand) (doen ze het nog?).<br/>"
        + "- Drie percolators (kleintjes) (voor op vakantie, voor op het campinggasje?) (alle drie brandschoon) (nooit gebruikt?).<br/>"
        + "- Een Philips Senseo II Senseoaparaat (uit de tijd dat er nog mooie mensen in de koffiepadreclames zaten) (gebruikt zij).<br/>"
        + "- Een Krups Fastspresso Nespressoapparaat (What else?) (met van die cupjes) (gebruiken de anderen).<br/>"
        + " - en Een echt espressoapparaat (met jaren ’70 uiterlijk) (en een wijzertje dat aangeeft hoe hoog de druk is ofzo) (je moet de koffie in zo’n los handvat doen en precies hard genoeg aandrukken met een soort grote, glimmende schaakpion) (zegt Naut, die als enige weet hoe hij werkt).<br/>"
        + "Iedere maand probeert Naut het wel een keer. Dan zitten ze ’s ochtends met z’n allen aan de keukentafel, en dan zit zij de krant te lezen, en dan pakt ze zonder te kijken haar kopje en dan neemt ze een slokje en dan: is het opeens andere koffie.<br/>"
        + "Ze laat haar krant zakken en dan is het gezicht van Naut daar: ‘Dat is schrikken dat verschil hè? Die rommel of een espresso lungo van Guatemalteekse arabicabonen. Dat verschil proef je wel even, hè?’<br/>"
        + "‘Ja,’ zegt ze dan, ‘dat verschil proef ik wel, maar ik proef gewoon geen dertig cent verschil. Snap je?’ en dan kijk hij haar aan met een soort meewarig treurige blik. Alsof ze hem net verteld heeft dat ze niet in God gelooft, en hij haar in eeuwig vuur brandend voor zich ziet.<br/>"
        + "<br/>"
        + "Om Naut vrolijk te maken, en hem te laten zien dat ze echt niet onverschillig staat tegenover zijn grote liefde, kocht ze een paar weken terug op een rommelmarkt in Rotterdam voor zeven euro een antiek uitziende en bij ronddraaien luid piep- en knarsende koffiemolen.<br/>"
        + "De verkoper vroeg er tien euro’s voor, maar toen ze zei dat ze maar zeven op zak had hij geknikt en ‘is ook goed’ gezegd.<br/>"
        + "Ze zeggen wel eens dat na goede onderhandelingen beide partijen het gevoel hebben de ander te hebben opgelicht. Vermoedelijk was dat toen heel erg aan de hand, want de man maakte er geen enkel probleem van dat ze met een twintig eurobiljet betaalde. Thuis, bij nadere inspectie van haar aankoop, ontdekte ze dat het luide piep- en knarsen werd veroorzaakt door een plakkerige laag groene schimmel die het maalwerk van het molentje bekleedde. En dat het bijna onmogelijk zou zijn die schimmel te verwijderen, omdat de enige toegangsweg de smalle gleuf voor het koffieopvanglaatje was.<br/>"
        + "<br/>"
        + "Die avond had ze haar nicht Katinkel over de keukenvloer voor Japanse zoutjes en wijn en gezelligheid enzo. Toen ze terugkwam van het uit haar kamer halen van een nieuwe fles Aldi-shiraz – ze moesten nog uit – zag ze haar nicht gebiologeerd naar haar geflopte aankoop staren. Ze vroeg waarom ze een koffiemolen had. Kort vertelde ze over het piepen en knarsen, en over de schimmel die daarvan de oorzaak was. <br/>"
        + "Katinkel luisterde en draaide.<br/>"
        + "Het piep- en knarste inderdaad. <br/>"
        + "Katinkel pakte een zoutje. ‘Mag ik een pinda malen?’<br/>"
        + "‘Wat?’<br/>"
        + "Ze brak de pinda uit zijn Japanse jasje. ‘In je molen? Een pinda.’<br/>"
        + "‘Je doet maar.’<br/>"
        + "Onder luid gepiep- en knars draaide Katinkel de molen. Daarna keek ze in het laatje: fijn gemalen pindabrokjes vermengd met groene puntjes schimmel. En daarna brak ze weer een pinda vrij, en haalde ook die door de molen; daarna deed ze dat nog een keer. En daarna deed ze dat nog een keer, en daarna deed ze er twee. Daarna zelfs drie en vier. En toen zei haar nicht dat ze ook wilde.<br/>"
        + "Vijf tegelijk gooide ze in de molen, en zelfs zonder er het rijstjasje vanaf te pellen. Het paste maar net. Het molentje piep- en knarste verschrikkelijk, nog veel luider dan daarvoor. Blijkbaar zelfs zo luid dat het geluid door het plafond heen tot in de kamer van Naut reikte, die de trap af de keuken in kwam rennen om te kijken wat er in godsnaam aan de hand was. En daar zag hij zijn huisgenote die in een kreunende antieke koffiemolen handjes Japanse rijstzoutjes probeerde te vermalen.<br/>"
        + "‘O jij vuile Senseodrinker,’ zei Naut. ‘Barbaar! Jij hebt geen hart, jij. Ik wist het wel.’ Hij pakte de koffiemolen vast en trok hem met zoveel kracht uit haar handen dat het koffie-opvanglaatje uit zijn gleuf losraakte en door de keuken vloog, de met schimmel vermengde zoutjeskruimels over de keukenvloer uitzaaiend. ‘Nooit, nooit geef ik je meer echte koffie.’ Hij rende de trap op naar boven, naar zijn kamer; trok met een harde klap de deur dicht.<br/>"
        + "<br/>"
        + "Ze heeft het geprobeerd aan hem uit te leggen – via sms, Facebook, zelfs voicemail. Ze vertelde van de schimmel, van de smalle gleuf en het gesprek met de marktkoopman. Ze heeft zelfs alle pindakruimels van de keukenvloer gestofzuigd, maar hij weigert nog steeds met haar te praten. Op het mededelingenbord schreef hij dat hij de koffiemolen tegen haar in bescherming heeft genomen.<br/>"
        + "<br/>"
        + "Laatst, toen hij een weekend van huis was, wilde ze in zijn kamer op zoek te gaan, maar hij bleek zijn kamerdeur plots op slot te houden. Van de reservesleutels in het bakje in de kast mist alleen de zijne.<br/>"
        + "Ze heeft het opvanglaatje maar aan zijn deurklink gehangen.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Koffiemolen_ik:<b>Koffiemolen</b><br/><br/>"
        + "In de keuken van mijn Utrechtse studentenhuis staan zeven koffiezetapparaten.<br/>"
        + "<br/>"
        + "Dubbele punt:<br/>"
        + "- Twee filterkoffieapparaten (oud) (vies) (plakkerige stoflaag) (gebruikt niemand) (doen ze het nog?).<br/>"
        + "- Drie percolators (kleintjes) (voor op vakantie, voor op het campinggasje?) (alle drie brandschoon) (nooit gebruikt?).<br/>"
        + "- Een Philips Senseo II Senseoaparaat (uit de tijd dat er nog mooie mensen in de koffiepadreclames zaten) (gebruik ik).<br/>"
        + "- Een Krups Fastspresso Nespressoapparaat (What else?) (met van die cupjes) (gebruiken de anderen).<br/>"
        + " - en Een echt espressoapparaat (met jaren ’70 uiterlijk) (en een wijzertje dat aangeeft hoe hoog de druk is ofzo) (je moet de koffie in zo’n los handvat doen en precies hard genoeg aandrukken met een soort grote, glimmende schaakpion) (zegt Naut, die als enige weet hoe hij werkt).<br/>"
        + "Iedere maand probeert Naut het wel een keer. Dan zitten we ’s ochtends met z’n allen aan de keukentafel, en dan zit ik de krant te lezen, en dan pak ik zonder te kijken mijn kopje en dan neem ik een slokje, en dan is het opeens andere koffie.<br/>"
        + "Ik laat mijn krant zakken en dan is het gezicht van Naut daar: ‘Dat is schrikken dat verschil hè? Die rommel of een espresso lungo van Guatemalteekse arabicabonen. Dat verschil proef je wel even, hè?’<br/>"
        + "‘Ja,’ zeg ik dan, ‘dat verschil proef ik wel, maar ik proef gewoon geen dertig cent verschil. Snap je?’ en dan kijk hij me aan met een soort meewarig treurige blik. Alsof ik hem net verteld heb dat ik niet in God geloof, en hij mij in eeuwig vuur brandend voor zich ziet.<br/>"
        + " <br/>"
        + "Om Naut vrolijk te maken, en hem te laten zien dat ik echt niet onverschillig sta tegenover zijn grote liefde, kocht ik een paar weken terug op een rommelmarkt in Rotterdam voor zeven euro een antiek uitziende en bij ronddraaien luid piep- en knarsende koffiemolen.<br/>"
        + "De verkoper vroeg er tien euro’s voor, maar toen ik zei dat ik maar zeven op zak had hij geknikt en ‘is ook goed’ gezegd.<br/>"
        + "Ze zeggen wel eens dat na goede onderhandelingen beide partijen het gevoel hebben de ander te hebben opgelicht. Ik denk dat dat toen heel erg aan de hand was, want de man maakte er geen probleem van dat ik met een twintig eurobiljet betaalde. Thuis, bij nadere inspectie van mijn aankoop, ontdekte ik dat dat het luide piep- en knarsen werd veroorzaakt door een plakkerige laag groene schimmel die het maalwerk van het molentje bekleedde. En dat het bijna onmogelijk zou zijn die schimmel te verwijderen, omdat de enige toegangsweg de smalle gleuf voor het koffieopvanglaatje was.<br/>"
        + "<br/>"
        + "Die avond had ik mijn nicht Katinkel over de keukenvloer voor Japanse zoutjes en wijn en gezelligheid enzo. Toen ik terugkwam van het uit mijn kamer halen van een tweede fles Aldi-shiraz – we moesten nog uit – zag ik haar gebiologeerd naar mijn geflopte aankoop staren. Ze vroeg waarom ik een koffiemolen had. Kort vertelde ik over het piepen en knarsen, en over de schimmel die daarvan de oorzaak was. <br/>"
        + "Katinkel luisterde en draaide. <br/>"
        + "Het piep- en knarste inderdaad. <br/>"
        + "Katinkel pakte een zoutje. ‘Mag ik een pinda malen?’<br/>"
        + "‘Wat?’ vroeg ik.<br/>"
        + "Ze brak de pinda uit zijn Japanse jasje. ‘In je molen? Een pinda.’<br/>"
        + "‘Je doet maar.’<br/>"
        + "Onder luid gepiep- en knars draaide Katinkel de molen. Daarna keek ze in het laatje: fijn gemalen pindabrokjes vermengd met groene puntjes schimmel. En daarna brak ze weer een pinda vrij, en haalde ook die door de molen; daarna deed ze dat nog een keer. En daarna deed ze dat nog een keer, en daarna deed ze er twee. Daarna zelfs drie en vier. En toen zei ik dat ik ook wilde.<br/>"
        + "Vijf tegelijk gooide ik in de molen, zelfs zonder het jasje van ze af te pellen. Het paste maar net. Het molentje piep- en knarste verschrikkelijk, nog veel luider dan daarvoor. Blijkbaar zelfs zo luid dat het geluid door het plafond heen tot in de kamer van Naut reikte, die de trap af de keuken in kwam rennen om te kijken wat er in godsnaam aan de hand was. En daar zag hij mij die in een kreunende antieke koffiemolen handjes Japanse rijstzoutjes probeerde te vermalen.<br/>"
        + "‘O jij vuile Senseodrinker,’ zei Naut. ‘Barbaar! Jij hebt geen hart, jij. Ik wist het wel.’ Hij pakte de koffiemolen vast en trok hem met zoveel kracht uit mijn handen dat het koffie-opvanglaatje uit zijn gleuf losraakte en door de keuken vloog, de met schimmel vermengde zoutjeskruimels over onze keukenvloer uitzaaiend. ‘Nooit, nooit geef ik je meer echte koffie.’ Hij rende de trap op naar boven, naar zijn kamer; trok met een harde klap de deur dicht.<br/>"
        + "<br/>"
        + "Ik heb het geprobeerd aan hem uit te leggen – via sms, Facebook, zelfs voicemail. Ik vertelde van de schimmel, van de smalle gleuf en het gesprek met de marktkoopman. Ik heb zelfs alle pindakruimels van de keukenvloer gestofzuigd, maar hij weigert nog steeds met me te praten. Op het mededelingenbord in de keuken schreef hij dat hij de koffiemolen tegen mij in bescherming heeft genomen.<br/>"
        + "<br/>"
        + "Laatst, toen hij een weekend van huis was, wilde ik in zijn kamer op zoek te gaan, maar hij bleek zijn kamerdeur plots op slot te houden. Van de reservesleutels in het bakje in de kast mist alleen de zijne.<br/>"
        + "Ik heb het opvanglaatje maar aan zijn deurklink gehangen.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Matroesjka_hij:<b>Matroesjka</b><br/><br/>"
        + "‘Wat lijkt ze toch op oma,’ zeiden ze met Pasen, verjaardagen en Pinksteren, als ze zijn zusje zagen spelen in de tuin. Ze droeg de oude rokken en jurkjes van haar nichten, die ook op oma leken, maar niet zoveel als zij. Er is een foto in een fotoboek in de archiefkast boven, waarop alle nichtjes in op elkaar lijkende blauwe jurkjes rond oma zitten. Op oma’s verjaardag gemaakt. Alle meisjes kijken netjes zoals het hoort snoezig in de camera. Cheese. <br/>"
        + "Maar zij en oma niet. Zij kijken ondeugend, alsof oma haar net heeft verteld dat ze ontdekt heeft waar opa zijn snoeppot bewaart. Twee paar rechte neuzen met vlak voor het puntje een klein dalletje, vier helderblauwe ogen, oma’s witte haren die onder haar hoofddoekje uit piepen, haar mond een klein beetje open, nog geen idee van wat poseren is, twee paar appelwangetjes, gloeiend als luciferkopjes. Ze herinnert zich het niet meer, ze was pas drie toen oma stierf en van voor je vierde herinner je je niks, las hij laatst. Maar hij snapt het gewoon niet, als hij naar die foto kijkt snapt hij het gewoon niet. Oma zo mooi, jij zo mooi – jullie waren anders, bijzonder.<br/>"
        + "Toch is hij trots op haar, zijn zusje. Ze besloot het en deed het; viel af als een matroesjka. Maar het lijkt wel, alsof ieder beetje vet dat haar lichaam uit verdwijnt, wat meeneemt van wat haar zo haar maakt. Ze verandert. Toen papa en mama laatst een weekend weg waren, vroeg ze bij de snackbar of ze niet ook salade hebben. ’s Avonds keek ze op haar kamer film, met een vriend. Hij hoorde ze door de muur heen lachen. Haar stem is hoger tegenwoordig. <br/>"
        + "Toen ze met Pasen met z’n allen eieren zochten in oma’s – nu tante Liesbeths – tuin, gaf ze alle eitjes die ze vond aan hem. Zelfs haar lievelings, de witte met praline.<br/>"
        + "‘Jij kan het wél hebben,’ zei ze, toen ze ze in zijn handen stopte.<br/>"
        + "En voordat hij het wist was het eruit: ‘Je bent normaal geworden, zusje.’<br/>"
        + "Ze liep op hem af en sloeg haar armen om hem heen. <br/>"
        + "Hij voelde hoe ze hem probeerde te knuffelen, zoals vroeger. Maar waar ze vroeger zacht was, voelde hij nu randen, botten: haar schouders, heupen, sleutelbeenderen; hoe haar ribben ritmisch tegen zijn borstkas zweefden; haar beha, en hoe die indeukte.<br/>"
        + "‘Dankjewel, broer,’ zei zij.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Matroesjka_ik:<b>Matroesjka</b><br/><br/>"
        + "‘Wat lijkt ze toch op oma,’ zeiden ze met Pasen, verjaardagen en Pinksteren, als ze je zagen spelen in de tuin. Je droeg de oude rokken en jurkjes van je nichten, die ook op oma leken, maar niet zoveel als jij. Er is een foto in een fotoboek in de archiefkast boven, waarop alle nichtjes in op elkaar lijkende blauwe jurkjes rondom oma zitten. Op haar verjaardag gemaakt. Alle meisjes kijken netjes zoals het hoort snoezig in de camera. Cheese.<br/>"
        + "Maar jij en oma niet. Jullie kijken ondeugend, alsof oma je net heeft verteld dat ze ontdekt heeft waar opa zijn snoeppot bewaart. Twee paar rechte neuzen met vlak voor het puntje een klein dalletje, vier helderblauwe ogen, oma’s witte haren die onder haar hoofddoekje uit piepen, jouw mond een klein beetje open, nog geen idee van wat poseren is, twee paar appelwangetjes, gloeiend als luciferkopjes. Je herinnert je het niet meer, je was pas drie toen oma stierf en van voor je vierde herinner je je niks, las ik laatst. Maar ik snap het gewoon niet, als ik naar die foto kijk snap ik het gewoon niet. Oma zo mooi, jij zo mooi – jullie waren anders, bijzonder.<br/>"
        + "Toch ben ik trots op je, zusje. Je besloot het en deed het; viel af als een matroesjka. Maar het lijkt wel alsof ieder beetje vet dat jouw lichaam uit verdwijnt wat meeneemt van wat jou zo jou maakt. Je verandert. Toen papa en mama laatst een weekend weg waren, vroeg je bij de snackbar of ze niet ook salade hebben. ’s Avonds keek je op jouw kamer film, met een vriend. Ik hoorde jullie door de muur heen lachen. Je stem is hoger tegenwoordig. <br/>"
        + "Toen we met Pasen met z’n allen eieren zochten in oma’s – nu tante Liesbeths – tuin, gaf je alle eitjes die je vond aan mij. Zelfs je lievelings, de witte met praline.<br/>"
        + "‘Jij kan het wél hebben,’ zei je, toen je ze in mijn handen stopte.<br/>"
        + "En voordat ik het wist was het eruit: ‘Je bent normaal geworden, zusje.’<br/>"
        + "Je liep op me af en sloeg je armen om me heen. <br/>"
        + "Ik voelde hoe je me probeerde te knuffelen, zoals vroeger. Maar waar je vroeger zacht was, voelde ik nu randen, botten: je schouders, heupen, sleutelbeenderen; hoe je ribben ritmisch tegen mijn borstkas zweefden; je beha, en hoe die indeukte.<br/>"
        + "‘Dankjewel, broer,’ zei jij.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Meesterwerk_hij:<b>Meesterwerk</b><br/><br/>"
        + "Op de tast, met zijn ogen gesloten opent hij het raam in de serre van zijn royale buitenwoning. Door het bloed in zijn oogleden ziet hij rood. Zouden er wolken zijn? Zal hij in de wolken zijn gemoed herkennen? Langs hem waait een briesje wind naar binnen. Hij snuift het op. Zou weer een geur hebben? En zou dit dan zijn hoe stapelwolken ruiken? Hij slaat zijn handen voor zijn ogen en gaat steviger staan. Nu ziet hij zwart. Dan ontspant hij zijn schouders. Ze waren gespannen merkt hij.<br/>"
        + "Hij ademt in. Uit. En als hij weer inademt, doet hij het: hij trekt zijn handen weg en opent zijn ogen zo ver als hij kan. <br/>"
        + "Het brandt, hij ziet alleen maar wit, maar hij zet door. Tot hij alles ziet, en het raam weer sluit.<br/>"
        + "De telefoon gaat en hij voelt zich schuldig. Niet omdat hij de telefoon niet opneemt maar omdat hij zich heeft laten teleurstellen. Hij had er ook niet zoveel van mogen verwachten, misschien. Maar moet hij het dan maar gewoon nemen zoals het is, allemaal? Mag hij dan niet hopen?! Mag hij er dan niet iets van proberen te maken, van een nieuwe dag?! Woedend is hij, en hij smijt het raam dicht, het gordijn er weer voor. Er walmt een wolk stof uit. Dat gaat hij Anastasia laten weten de volgende keer. Wanneer kwam ze ook alweer? Wat voor dag is het vandaag eigenlijk?<br/>"
        + "De telefoon gaat nog een keer.<br/>"
        + "<br/>"
        + "- Hallo, zegt hij.<br/>"
        + "- Waar ben je?<br/>"
        + "- Gewoon, zegt hij. Waar ik ben.<br/>"
        + "- Ben je thuis?<br/>"
        + "- Achter mijn bureau.<br/>"
        + "- We hadden …<br/>"
        + "- Mijn mooie bureau, niet dat lelijke ding.<br/>"
        + "- … We hadden afgesproken. Je wilde me vandaag nog spreken.<br/>"
        + "- Suusje, ik wilde zoveel voor je. Hoe kon ik weten dat je nu opeens wél naar me zou luisteren?<br/>"
        + "- Ach man, niet dat weer.<br/>"
        + "- Vader, Suus. Ik ben je vader, niet je man.<br/>"
        + "- Váder. Ze zeggen dat ze je hier in geen weken gezien hebben. Dat Johannes nu de beslissingen neemt.<br/>"
        + "- Johannes, dat is een wijs man.<br/>"
        + "- Je háát Johannes.<br/>"
        + "- Hij had mijn schoonzoon kunnen zijn, Suus. Weet je nog?<br/>"
        + "- Dat is … - Páp. Wat wil je nou? … Is er iets gebeurd? Ben je op vakantie of wat …<br/>"
        + "- Suus, ik ben kunstschilder geworden.<br/>"
        + "<br/>"
        + "Gedecideerd legt hij de hoorn op de haak, en loopt weg van het bureau. Naar de hoek met het mooiste licht, vlak voor het raam. Vanaf daar kijkt hij naar het werk op de ezel, zijn creatie. Hij weet dat ze hem buiten voor gek aan het verklaren waren, maar wat zal het, laat ze buiten blijven. Hun letters en cijfers op papier, hun overlegcultuur, hun belastingstrategieën, dat is allemaal niet waar het om gaat. Dit, wat hij maakt, dáár gaat het om, dít. Wit op wit, op wit doek. Om het reliëf. Hoogteverschillen. Om verflaag op verflaag op verflaag.<br/>"
        + "Hij buigt voorover, brengt zijn gezicht tot dicht bij het doek tot hij alleen nog maar wit ziet. Hij sluit zijn ogen en hij snuift. Het ruikt goed.<br/>"
        + "Dan draait hij zijn hoofd en komt nog dichterbij. Hij gaat met zijn wang langs het doek, met zijn baard, en voelt met de haren die hij zich nooit eerder gepermitteerd heeft dat het goed is. Hij maakt zich klaar om een nieuwe laag aan te brengen.<br/>"
        + "<br/><br/><br/><br/><br/><br/>",
        "Meesterwerk_ik:<b>Meesterwerk</b><br/><br/>"
        + "Op de tast, met mijn ogen gesloten open ik het raam in de serre van mijn royale buitenwoning. Door het bloed in mijn oogleden zie ik rood. Zouden er wolken zijn? Zal ik in de wolken mijn gemoed herkennen? Langs me waait een briesje wind naar binnen. Ik snuif het op. Zou weer een geur hebben? En zou dit dan zijn hoe stapelwolken ruiken? Ik sla mijn handen voor mijn ogen en ga steviger staan. Nu zie ik zwart. Dan ontspan ik mijn schouders. Ze waren gespannen merk ik.<br/>"
        + "Ik adem in. Uit. En als ik weer inadem, doe ik het: ik trek mijn handen weg en open mijn ogen zo ver als ik kan.<br/>"
        + "Het brandt, ik zie alleen maar wit, maar ik zet door. Tot ik alles zie, en het raam weer sluit.<br/>"
        + "De telefoon gaat, en ik voel me schuldig. Niet omdat ik de telefoon niet opneem maar omdat ik me heb laten teleurstellen. Ik had er ook niet zoveel van mogen verwachten, misschien. Maar moet ik het dan maar gewoon nemen zoals het is, allemaal? Mag ik dan niet hopen?! Mag ik er dan niet iets van proberen te maken, van een nieuwe dag?!<br/>"
        + "Woedend ben ik, en ik gooi het raam weer dicht, het gordijn er weer voor. Er walmt een wolk stof uit. Dat ga ik Anastasia laten weten de volgende keer. Wanneer kwam ze ook alweer? Wat voor dag is het vandaag eigenlijk?<br/>"
        + "De telefoon gaat nog een keer<br/>"
        + "<br/>"
        + "- Hallo, zeg ik.<br/>"
        + "- Waar ben je?<br/>"
        + "- Gewoon, zeg ik. Waar ik ben.<br/>"
        + "- Ben je thuis?<br/>"
        + "- Achter mijn bureau.<br/>"
        + "- We hadden …<br/>"
        + "- Mijn mooie bureau, niet dat lelijke ding.<br/>"
        + "- … We hadden afgesproken. Je wilde me vandaag nog spreken.<br/>"
        + "- Suusje, ik wilde zoveel voor je. Hoe kon ik weten dat je nu opeens wél naar me zou luisteren?<br/>"
        + "- Ach man, niet dat weer.<br/>"
        + "- Vader, Suus. Ik ben je vader, niet je man.<br/>"
        + "- Váder. Ze zeggen dat ze je hier in geen weken gezien hebben. Dat Johannes nu de beslissingen neemt.<br/>"
        + "- Johannes, dat is een wijs man.<br/>"
        + "- Je háát Johannes.<br/>"
        + "- Hij had mijn schoonzoon kunnen zijn, Suus. Weet je nog?<br/>"
        + "- Dat is … - Páp. Wat wil je nou? … Is er iets gebeurd? Ben je op vakantie of wat …<br/>"
        + "- Suus, ik ben kunstschilder geworden.<br/>"
        + "<br/>"
        + "Gedecideerd leg ik de hoorn op de haak, en loop weg van het bureau. Naar de hoek met het mooiste licht, vlak voor het raam. Vanaf daar kijk ik naar het werk op de ezel, mijn creatie. <br/>"
        + "Ik weet dat ze me buiten voor gek aan het verklaren zijn, maar wat zal het, laat ze buiten blijven. Hun letters en cijfers op papier, hun overlegcultuur, hun belastingstrategieën, dat is allemaal niet waar het om gaat. Dit, wat ik maak, dáár gaat het om. Wit op wit, op wit doek. Om het reliëf. Hoogteverschillen. Om verflaag op verflaag op verflaag.<br/>"
        + "Ik buig voorover, breng mijn gezicht tot dicht bij het doek tot ik alleen nog maar wit zie. Ik sluit mijn ogen. Ik snuif. Het ruikt goed.<br/>"
        + "Dan draai ik mijn hoofd en kom nog dichterbij. Ik ga met mijn wang langs het doek, met mijn baard, en voel met de haren die ik me nooit eerder gepermitteerd heb dat het goed is. Ik maak me klaar om een nieuwe laag aan te brengen."
        + "<br/><br/><br/><br/><br/><br/>"
    };

    final String completionScreenText = "Hartelijk bedankt voor uw deelname aan ons experiment.<br/><br/><br/>"
            + "<b>De schrijver</b><br/>"
            + "Martin Rombouts (Rotterdam, 1992) studeerde Humanistiek aan de Universiteit voor Humanistiek in Utrecht en is derdejaars Creative Writing aan ArtEZ Hogeschool voor de kunsten in Arnhem. Hij schreef voor <a href=\"http://www.abcyourself.nl/author/martin/\">ABCyourself</a> en de blog van de Wintertuin. Hij  trad op op het Wintertuinfestival, Festival Hongerige Wolf en de Kunstbende Masterclass. Martin heeft een grote liefde voor stadsvogels, mensen en conceptuele kunst, en schrijft alles wat er maar te schrijven valt: van voordrachten tot gedichten, van verhalen tot een radioprogramma; zijn Love Radio is tot half april nog te zien als het randprogramma van de dansvoorstelling <a href=\"http://www.jensvandaele.com/nl/voorstellingen/over-onze-vader\">Over Onze Vader</a>, van Jens van Daele.<br/>"
            + "Volg Martin op twitter <a href=\"https://twitter.com/martinrombouts\">@MartinRombouts</a> of <a href=\"https://www.facebook.com/martin.rombouts28\">Facebook</a>. Of stuur hem een e-mail: martin[at]martinrombouts.nl.<br/>"
            + "<br/>"
            + "<b>De onderzoeker</b><br/>"
            + "Franziska Hartung is promovenda in het Neurobiology of Language department van Peter Hagoort op het Donders Instituut voor Hersenen, Cognitie en Gedrag en op het Max Planck Instituut voor Psycholinguïstiek. Samen met Dr. Roel Willems doet ze onderzoek naar hoe ons hersenen verhalen beleven. Meer informatie over ons onderzoek kunt u vinden op de website van <a href=\"http://www.mpi.nl/people/hartung-franziska\">Franziska Hartung</a> en van <a href=\"http://www.rmwillems.nl/index.html\">Roel Willems</a>. Voor vragen over dit experiment kunt u een email sturen naar franziska.hartung@mpi.nl<br/>"
            + " <br/>"
            + "Als u ons onderzoek interessant vindt kunt u hier meer informatie vinden over eerdere studies naar lezen, gedaan door onze afdeling: <br/>"
            + "<a href=\"https://taalenhersenen.wordpress.com/2014/06/06/hoe-komt-het-dat-we-ons-echt-de-held-uit-het-verhaal-voelen/\">Hoe komt het dat we ons écht de held uit het verhaal voelen?</a><br/>"
            + "<a href=\"https://goo.gl/KLlo30\">Hersenonderzoek naar fictie: hoe begrijpen we een verhaal?</a><br/>"
            + "<a href=\"https://goo.gl/Zv41vK\">Hoe onze hersenen ervoor zorgen dat we meeleven met fictieve personages</a><br/>"
            + "<a href=\"https://goo.gl/f34iUW\">Literatuur dwingt ons soms langzamer te lezen</a><br/>"
            + "<a href=\"https://goo.gl/op1oNs\">Word je een beter mens van boeken lezen?</a><br/>"
            + "<br/>"
            + "Als u graag meer wilt weten over het onderzoek uit het Neurobiology of Language department van Peter Hagoort kunt u kijken op <a href=\"http://hettaligebrein.nl/\">hettaligebrein</a> of <a href=\"http://www.mpi.nl/departments/neurobiology-of-language\">neurobiology-of-language</a>. <br/>"
            + "<br/>"
            + "<b>Software ontwikkelaar</b><br/>"
            + "Dit experiment is gemaakt met Frinex. Frinex is software in ontwikkeling voor interactieve experimenten ontwikkeld door Peter Withers. Het project is gefinancierd door het Max-Planck Instituut voor Psycholinguïstiek. Voor vragen kunt u contact opnemen met peter.withers@mpi.nl."
            + "<br/><br/>"
            + "Wilt u de resultaten van deze studie ontvangen? Vul dan hier uw email adres in:";

    final String agreementScreenText = "Welkom! <br/>"
            + "In deze studie onderzoeken we hoe mensen verhalen beleven. Door deel te nemen aan dit onderzoek helpt u ons inzicht te krijgen in verschillen en overeenkomsten tussen lezers.<br/>"
            + "U zult eerst een kort verhaal lezen, en daarna enkele vragen beantwoorden over uw leeservaring. Daarna doet u een korte reactietest. In totaal duurt het experiment ongeveer 10 minuten. <br/>"
            + "Door verder te gaan verklaart u deel te nemen aan het experiment en gaat u akkoord met het gebruik van de verzamelde geanonimiseerde data voor onderzoeksdoeleinden. U kunt op ieder moment stoppen met het experiment door het scherm te sluiten.<br/>"
            + "Dit experiment is goedgekeurd door de ethische toetsingscommissie van de faculteit sociale wetenschappen van de Radboud Universiteit (referentienummer ECG2013-1308-120).";

    final String[] stimuliGroupAorB = new String[]{
        "IntroductionAFact:U gaat zo een tekst lezen van Martin Rombouts. Hij is een jonge Nederlandse columnist. Hij schrijft over alledaagse dingen die hij meemaakt, altijd geïnspireerd door een waargebeurd verhaal.",
        "IntroductionBFiction:U gaat zo een tekst lezen van Martin Rombouts. Hij is een jonge Nederlandse schrijver. Hij schrijft korte fictieve verhalen die voortkomen uit zijn creatieve gedachtenwereld. "
    };

    public WizardData getWizardData() {

        WizardData wizardData = new WizardData();
        wizardData.setAppName("Leeservaring");
        wizardData.setShowMenuBar(false);
        wizardData.setObfuscateScreenNames(true);
        wizardData.setTextFontSize(22);

//        wizardData.setAgeField(true);
//        wizardData.setGenderField(true);
//        wizardData.setCustomTextField("level of proficiency in Dutch");
        WizardAgreementScreen wizardAgreementScreen = new WizardAgreementScreen("Toestemming", agreementScreenText, "Akkoord");
//        wizardAgreementScreen.setGenerateCompletionCode(Boolean.FALSE);
        wizardData.addScreen(wizardAgreementScreen);
//        final PresenterScreen agreementScreen = wizardAgreementScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 1);
//agreementScreen.setBackWizardScreen(new AbstractWizardScreen() {
//            @Override
//            public PresenterScreen getPresenterScreen() {
//                return welcomePresenter;
//            }
//
//            @Override
//            public String getScreenTag() {
//                return welcomePresenter.getSelfPresenterTag();
//            }
//
//        });
//        agreementScreen.setNextWizardScreen(new AbstractWizardScreen() {
//            @Override
//            public PresenterScreen getPresenterScreen() {
//                return welcomePresenter;
//            }
//
//            @Override
//            public String getScreenTag() {
//                return welcomePresenter.getSelfPresenterTag();
//            }
//
//        });
        final String[] medataFields = new String[]{
            "leeftijd:Leeftijd:[0-9]+:Voer een getal.",
            "geslacht:Geslacht:|man|vrouw|anders:.",
            "opleidingsniveau:Opleidingsniveau:primair onderwijs (basisschool)|voortgezet onderwijs|middelbaar beroepsonderwijs (MBO)|hoger onderwijs (HBO, universiteit)|anders:.",
            "nederlandsMoedertaal:Nederlands is mijn moedertaal:true|false:.",
            "hoeveelJaarNederlands:Als Nederlands niet uw moedertaal is, hoeveel jaar leert u al Nederlands?:[0-9]*:Voer een getal in."
        };
        final String could_not_contact_the_server_please_check = "De server is niet bereikbaar. Controleer de internetverbinding en probeer opnieuw.";
        final WizardEditUserScreen editUserScreen = new WizardEditUserScreen("Participant", "Participant", null, "Volgende", null, null, null, true, false, could_not_contact_the_server_please_check);
        editUserScreen.setCustomFields(medataFields);
        final WizardRandomStimulusScreen groupAorBScreen = new WizardRandomStimulusScreen("Introduction", false, stimuliGroupAorB,
                new String[]{"IntroductionAFact", "IntroductionBFiction"}, 1, true, null, 0, 0, null, null, null, null, "volgende [ spatiebalk ]");
//        final PresenterScreen storyScreen = new WizardRandomStimulusScreen(experiment, null, 4, "StoryPresentation", false, storyTexts, new String[]{"Emotioneel_hij", "Emotioneel_ik", "Koffiemolen_hij", "Koffiemolen_ik", "Matroesjka_hij", "Matroesjka_ik", "Meesterwerk_hij", "Meesterwerk_ik"}, 1, null, 0, 0, null, null, null, null, wizardData.isObfuscateScreenNames());
        final WizardRandomStimulusScreen storyScreen = new WizardRandomStimulusScreen("StoryPresentation", false, storyTexts, new String[]{"Emotioneel_hij", "Emotioneel_ik", "Koffiemolen_hij", "Koffiemolen_ik", "Matroesjka_hij", "Matroesjka_ik", "Meesterwerk_hij", "Meesterwerk_ik"}, 1, true, null, 0, 0, null, null, null, null, "volgende [ spatiebalk ]");
        final WizardRandomStimulusScreen survey1Screen = new WizardRandomStimulusScreen("Survey1", servey1Stimuli, 1000, true, "1,2,3,4,5,6,7", "helemaal niet van toepassing", "helemaal van toepassing");
        WizardTextScreen survey1InstructionsScreen = new WizardTextScreen("Survey1Instructions", "<b>U krijgt nu enkele stellingen te zien over uw ervaringen tijdens het lezen van het voorgaande verhaal. Geef aan in hoeverre de stellingen van toepassing zijn op uw ervaring tijdens het lezen van dit verhaal.</b>", "volgende [ spatiebalk ]");
        WizardTextScreen survey2InstructionsScreen = new WizardTextScreen("Survey2Instructions", "<b>U krijgt nu enkele woorden te zien. Uw taak is om aan te geven in hoeverre de woorden van toepassing zijn op uw ervaring tijdens het lezen van het verhaal.</b>", "volgende [ spatiebalk ]");
        final WizardRandomStimulusScreen survey2Screen = new WizardRandomStimulusScreen("Survey2", new String[]{"Interessant:interessant", "goedgeschreven:goed geschreven", "vanhogeliterairekwaliteit:van hoge literaire kwaliteit", "makkelijktebegrijpen:makkelijk te begrijpen", "toegankelijk:toegankelijk", "spannend:spannend", "mooi:mooi", "boeiend:boeiend", "emotioneel:emotioneel", "saai:saai"}, 1000, true, "1,2,3,4,5,6,7", "helemaal niet van toepassing", "helemaal van toepassing");
        WizardTextScreen pictureInstructionsScreen = new WizardTextScreen("PictureInstructions", "<b>U zult zo enkele afbeeldingen zien die situaties tonen. Uw taak is te kiezen of de afgebeelde acties overeenkomen met acties in het verhaal dat u net las, of niet. Als het beeld een actie toont die in het verhaal voorkwam, toetst u “Z”, als de actie niet in het verhaal voorkwam toetst u “.”. Probeer zo snel mogelijk te reageren.", "volgende [ spatiebalk ]</b>");
        final WizardRandomStimulusScreen pictureTaskScreen = new WizardRandomStimulusScreen("PictureTask", images, 1000, true, "ja [ z ],nee [ . ]", "", "");
        WizardTextScreen readingBehaviorInstructionsScreen = new WizardTextScreen("ReadingBehaviorInstructions", "<b>Het experiment is bijna klaar. We hebben nog 6 korte vragen aan u.</b>", "volgende [ spatiebalk ]");
        final WizardRandomStimulusScreen readingBehaviorScreen = new WizardRandomStimulusScreen("ReadingBehavior", readingBehavior, 1000, false, "1,2,3,4,5,6,7", "", "");
        final String retry = "Probeer opnieuw";
        final WizardSubmitDataScreen submitDataScreen = new WizardSubmitDataScreen("SubmitData", could_not_contact_the_server_please_check, retry);
        final String wil_nog_iemand_op_dit_apparaat_deelnemen_ = "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.";
        final WizardCompletionScreen restartScreen = new WizardCompletionScreen(wil_nog_iemand_op_dit_apparaat_deelnemen_, true, true, null, "Opnieuw beginnen", "Completion", could_not_contact_the_server_please_check, retry);
        final WizardCompletionScreen registeredScreen = new WizardCompletionScreen("Leuk dat u geïnteresseerd bent. " + wil_nog_iemand_op_dit_apparaat_deelnemen_, true, true, null, "Opnieuw beginnen", "Registered", could_not_contact_the_server_please_check, retry);
        final String nog_een_keer_meedoen = wil_nog_iemand_op_dit_apparaat_deelnemen_; //"Nog een keer meedoen?";
        final WizardEditUserScreen completionScreen = new WizardEditUserScreen("Bedankt", "Bedankt", completionScreenText, "Registreren", nog_een_keer_meedoen, restartScreen, "Opnieuw beginnen", true, false, could_not_contact_the_server_please_check);
        completionScreen.setCustomFields(new String[]{"emaill::^[^@]+@[^@]+$:Geef een geldig e-mailadres."});
//        completionScreen.setBackPresenter(restartScreen);
        restartScreen.setNextWizardScreen(wizardAgreementScreen);
        registeredScreen.setNextWizardScreen(wizardAgreementScreen);
        completionScreen.setNextWizardScreen(registeredScreen);
        wizardAgreementScreen.setNextWizardScreen(editUserScreen);
        editUserScreen.setNextWizardScreen(groupAorBScreen);
        groupAorBScreen.setNextWizardScreen(storyScreen);
        storyScreen.setNextWizardScreen(survey1InstructionsScreen);
        survey1InstructionsScreen.setNextWizardScreen(survey1Screen);
        survey1Screen.setNextWizardScreen(survey2InstructionsScreen);
        survey2InstructionsScreen.setNextWizardScreen(survey2Screen);
        survey2Screen.setNextWizardScreen(pictureInstructionsScreen);
        pictureInstructionsScreen.setNextWizardScreen(pictureTaskScreen);
        pictureTaskScreen.setNextWizardScreen(readingBehaviorInstructionsScreen);
        readingBehaviorInstructionsScreen.setNextWizardScreen(readingBehaviorScreen);
        readingBehaviorScreen.setNextWizardScreen(submitDataScreen);
        submitDataScreen.setNextWizardScreen(completionScreen);

        wizardData.addScreen(editUserScreen);
        wizardData.addScreen(groupAorBScreen);
        wizardData.addScreen(storyScreen);
        wizardData.addScreen(survey1Screen);
        wizardData.addScreen(survey2Screen);
        wizardData.addScreen(pictureInstructionsScreen);
        wizardData.addScreen(survey1InstructionsScreen);
        wizardData.addScreen(survey2InstructionsScreen);
        wizardData.addScreen(pictureTaskScreen);
        wizardData.addScreen(readingBehaviorInstructionsScreen);
        wizardData.addScreen(readingBehaviorScreen);
        wizardData.addScreen(submitDataScreen);
        wizardData.addScreen(completionScreen);
        wizardData.addScreen(restartScreen);
        wizardData.addScreen(registeredScreen);
//        wizardAgreementScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 1);
//        editUserScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 2);
//        groupAorBScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 3);
//        storyScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 4);
//        survey1InstructionsScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 15);
//        survey1Screen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 5);
//        survey2InstructionsScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 16);
//        survey2Screen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 6);
//        pictureInstructionsScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 14);
//        pictureTaskScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 17);
//        readingBehaviorInstructionsScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 18);
//        readingBehaviorScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 19);
//        submitDataScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 20);
//        completionScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 21);
//        restartScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 22);
//        registeredScreen.populatePresenterScreen(experiment, wizardData.isObfuscateScreenNames(), 23);
        return wizardData;
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        return experiment;
    }
}
