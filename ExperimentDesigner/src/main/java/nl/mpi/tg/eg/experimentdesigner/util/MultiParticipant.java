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
import nl.mpi.tg.eg.experimentdesigner.model.DataChannel;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMultiParticipantGroupFormationScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMultiParticipantScreen;

/**
 * @since Oct 21, 2016 11:52:03 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MultiParticipant {

    private final WizardController wizardController = new WizardController();
    // @todo: scoring for each participant 
    // @todo: the allocated member id must maintained thoughout the experiment
    // @todo: show the experiment total score not the participants score
    // @todo: round 0 is the naming screen and does not collect guesses and therefore no scores
    // @todo: make the stimuli list common between all group members
    //  @todo: limit the communication to within each channel
    // @todo: add the group data collection
    // @todo: add the group data CSV output
    // @todo: add an option in the admin reporting app to "hide ugly details" which hides strings like UUID and browser version strings

//,Round,Dyad,Game.no,Item.ID,Shape,Size,RawSize,ItemCurrentAge,Producer,Word,ACC
//165,1,AB,1,10,2,small,2.74,2,A,flup,0
//167,1,CD,1,10,2,small,2.74,2,C,mozel,1
//65,1,AB,2,5,1,small,2.94,2,B,mozel,1
//67,1,CD,2,5,1,small,2.94,2,D,tjalp,1
//297,1,AB,3,19,4,medium,4.4,2,A,flolp,1
//299,1,CD,3,19,4,medium,3.95,2,C,vlolp,1
//147,1,AB,4,9,2,medium,4.51,2,B,kimi,0
//149,1,CD,4,9,2,medium,4.51,2,D,vlalp,0
//85,1,AB,5,6,2,big,7.6,2,A,dauft,0
//87,1,CD,5,6,2,big,7.6,2,C,potmik,1
//    int[] possibleAngles = new int[]{30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};   
//    private String[] getStimuli(){
//        for (int textureIndex = 1; textureIndex < 29; textureIndex++) {
//            
//        }
//    }
//    Dutch Instruction (13 JULY 2017)
//Introductie / welcome screen
    // todo: change this intro screen to a group screen that when not connected shows info like the "This is a prototype multiparticipant experiment." screen was. Only when connected does the text below display--
    final private String welcomeScreen = "Welkom bij het fantasietaal spel!<br/><br/>" + "In dit experiment ga je een speciale taal creëren "
            + "om verschillende soorten \"scènes\" te beschrijven. In deze scènes zijn verschillende voorwerpen te zien die in "
            + "verschillende richtingen bewegen.<br/><br/>" + "Daarna ga je deze taal gebruiken om te communiceren met andere leden van "
            + "je groep tijdens het spelen van het spel op de laptops. Je speelt in tweetallen en na elke ronde wissel je van partner.<br/><br/>"
            + "De bedoeling is dat de communicatie tussen jou en je partner zo succesvol mogelijk verloopt. Je krijgt 1 punt voor elke "
            + "geslaagde beurt. Nadat we alle groepjes in ons experiment getest hebben (kan even duren), krijgt de groep met de hoogste "
            + "gemiddelde score een extra prijs. Dit betekent dat je moet proberen om zoveel mogelijk punten te verdienen door zo goed "
            + "mogelijk met elkaar te communiceren - communicatie is de enige manier om punten te verdienen!<br/><br/>" + "Je mag geen Nederlands "
            + "of andere bestaande talen gebruiken tijdens deze taak. Gebruik ook niets dat heel erg op het Nederlands of andere talen lijkt. "
            + "Als je bijvoorbeeld een scène met een blauw voorwerp er in wilt beschrijven, gebruik dan niet een andere variatie van het "
            + "woord blauw zoals blaw, blu, blieblie of zelfs een ander woord beginnend met dezelfde letter. Het gebruik van afkortingen "
            + "is ook niet toegestaan. Probeer creatief te zijn en niet terug te vallen op je kennis van het Nederlands of andere talen. "
            + "Als je niet zeker weet of een woord is toegestaan, vraag ons dit dan van tevoren. Het doel van het spel is goed te "
            + "communiceren in een nieuwe fantasietaal. Daarom moeten we helaas de hele groep uitsluiten van het spel als er een woord "
            + "gebruikt wordt dat niet is toegestaan.<br/><br/>" + "Verder mag je niet praten, gebaren of wijzen tijdens het experiment, "
            + "je communiceert enkel door beschrijvingen te typen.<br/><br/>" + "Veel succes!<br/><br/>";
    final private String ronde0NAMING = "Om het spel te "
            + "kunnen spelen moet je eerst acht beschrijvingen voor acht scènes in de fantasietaal creëren. <br/><br/>" + "Gebruik geen "
            + "bestaande dingen in het Nederlands of in een andere taal - probeer creatief te zijn en gebruik je fantasie om een taal "
            + "te creëren. Je kun de spatiebalk gebruiken om beschrijvingen te maken met meerdere woorden. Maar let op, je kunt niet "
            + "alle letters gebruiken. Alleen de letters die zichtbaar zijn op het keyboard kunnen gebruikt worden. Check dus eerst of "
            + "je de beschrijving die je in gedachten hebt, kunt typen voor je verder gaat. De backspace toets is bruikbaar als je de "
            + "beschrijving wilt aanpassen of herschrijven. <br/><br/>" + "Proefpersoon 1 begint. Hij/zij zal een beschrijving geven aan de eerste "
            + "scène op het scherm. Toon de beschrijving eerst aan de onderzoeker voor je verder gaat. Omdat dit de eerste ronde is van "
            + "het spel willen we even checken of het aan de eisen voldoet. Als de beschrijving goed is, zal hij zeven seconden samen "
            + "met de scène op het scherm getoond worden aan alle deelnemers van de groep.<br/><br/>" + "Daarna benoemt proefpersoon 2 de tweede "
            + "scène en zo verder.<br/><br/>" + "Wanneer alle scènes benoemd zijn, krijg je ze nog twee keer te zien. Probeer de beschrijvingen "
            + "en de bijhorende scènes zo goed mogelijk te onthouden zodat je beter kunt communiceren in de volgende rondes.<br/><br/>";
    final private String ronde0REPETITION1 = "Je krijgt de scenes nog twee keer te zien.<br/><br/>" + "Probeer de beschrijvingen en de scenes "
            + "die ze beschrijven zo goed mogelijk te onthouden.<br/><br/>" + "Druk op de knop of Enter om verder te gaan.<br/><br/>";
    final private String ronde0REPETITION2 = "Je krijgt de scenes nog één keer te zien.<br/><br/>"
            + "Probeer de beschrijvingen en de scenes die ze beschrijven zo goed mogelijk te onthouden.<br/><br/>"
            + "Druk op de knop of Enter om verder te gaan.<br/><br/>";
    final private String ronde1 = "Tijd om te spelen!<br/><br/>"
            + "Nu verdelen we jullie in paren. In deze ronde zul je het spel spelen met een andere proefpersoon.<br/><br/>"
            + "In elke beurt krijgt een van de spelers één scène te zien, de ander speler krijgt acht scènes te zien. "
            + "De speler met één scène bedenkt een beschrijving voor die scène en toont die beschrijving aan de andere speler. "
            + "De ander speler moet dan raden om welk van de acht scènes op zijn/haar scherm het gaat.<br/><br/>"
            + "Als het jouw beurt is om een beschrijving te bedenken is het doel ervoor te zorgen dat je medespeler je zo "
            + "goed mogelijk begrijpt zodat die het juiste scène uit de reeks scènes zal kiezen. Je mag beschrijvingen gebruiken "
            + "die je in een eerdere ronde hebt geleerd of je mag een nieuwe beschrijving bedenken. Dit is geen geheugen test, "
            + "dus als je denkt dat je een beter beschrijving weet schroom dan niet om die te gebruiken. Als je het niet goed weet, "
            + "probeer dan een beschrijving te bedenken met de kennis die je tot dan toe hebt opgedaan over de fantasietaal. "
            + "Onthoud dat als je scènes benoemt, je enkel de zichtbare letters kunt gebruiken. Als je een beschrijving bedacht hebt, "
            + "kun je op Enter drukken. <br/><br/>" + "De andere speler kan nu uit de acht scènes kiezen welke scène hij/zij denkt dat je bedoelt.<br/><br/>"
            + "Als het jouw beurt is om te kiezen welke scène bedoeld wordt met de beschrijving, doe je dit door op een van de nummers (1-8) te "
            + "drukken (kan zowel bovenaan als rechts) of op het juiste plaatje te klikken. <br/><br/>"
            + "Nadat er een scène gekozen is, krijg je een feedback scherm waarop 2 scènes komen te staan; de gekozen "
            + "scène en de scène die het had moeten zijn. Als de gekozen scène correct was, krijg je een punt. Goed gedaan! "
            + "Zo niet, dan kun je de feedback gebruiken om het volgende ronde beter te doen.  <br/><br/>"
            + "De volgende beurt keren jullie de rollen om. 1 ronde bestaat uit 23 beurten. Veel succes!<br/><br/>";
    final private String ronde2tm7 = "Tijd om te spelen!<br/><br/>"
            + "Deze ronde werkt precies hetzelfde als de vorige ronde, maar je werkt nu samen met een andere partner en er worden "
            + "wat nieuwe scènes aan het spel toegevoegd. Als je zo'n scène ziet, probeer die dan een beschrijving te geven met de kennis "
            + "die je tot dan toe hebt opgedaan over de fantasietaal.<br/><br/>"
            //            + "In deze ronde zul je het spel spelen met proefpersoon X.<br/><br/>"
            + "Onthoud: het doel is om zo goed mogelijk te communiceren met elkaar en om zoveel mogelijk punten te verdienen! ";
    final private String ronde1tm7PRODUCERSCREEN = "Typ je beschrijving voor de scène: <br/><br/>";
    final private String waitingScreenForGuesser = "Een moment alsjeblieft. Je partner is een beschrijving aan het bedenken.";
    final private String ronde1tm7GUESSERSCREEN = "Kies de scène die bij de beschrijving hoort: <br/><br/>";
    final private String waitingScreenForProducer = "Een moment alsjeblieft. Je partner is een scène aan het kiezen.";
    final private String namingAndExposure = "Kijk naar de beschrijving van de scène in fantasietaal:<br/><br/>";

    final private String ronde1tm7FEEDBACKSCREEN = null;
    final private String ronde1tm7FeedbackCorrect = "<br/>Goed gedaan! Jij en je partner hebben een punt verdiend voor jullie groep!";
    final private String ronde1tm7FeedbackIncorrect = "<br/>Helaas hebben jij en je partner elkaar niet goed begrepen. Volgende keer beter!";
    final private String ronde1tm7ENDSCREEN = "De huidige ronde is klaar! Goed gedaan!<br/><br/>"
            + "Jij en je partner hebben <channelScore> punten verdiend voor jullie groep!<br/><br/>"
            + "Jullie groep heeft nu een totaal van <groupScore> punten. <br/><br/>"
            + "Dit zijn de scores van deze ronde:<br/><br/>"
            + "<channelLoop>Duo <channelLabel> heeft <channelScore> punten.<br/><br/></channelLoop>";
//            + "Duo X-X heeft XX punten.<br/><br/>"
//            + "Duo X-X heeft XX punten.<br/><br/>"
//            + "Duo X-X heeft XX punten.";
    final private String ronde8test1 = "Als laatste willen we graag weten wat je kennis van de taal is. Je krijgt telkens een van de scènes "
            + "uit het spel te zien. Typ de  beschrijving waarvan je denkt dat die de scène beschrijft in de fantasietaal. "
            + "Dit doe je zonder te overleggen.";

    final private String finalSCREEN = "Geweldig! Jullie groep heeft een totaal van <groupScore> punten verdiend in dit spel!<br/><br/>"
            + "Hartelijk dank voor het meespelen!";
    final int numberOfStimuli = 23;
    final int repeatCountStimuli = 3;
    final int randomWindowStimuli = 6;

    private final String[] stimuliArray = new String[]{
        "2.png:shape1:version1:quadrant3:moveRotated270",
        "6.png:shape1:version1:quadrant4:moveRotated300",
        "27.png:shape4:version1:quadrant2:moveRotated120",
        "24.png:shape4:version1round2:version1round3:quadrant2:moveRotated180",
        "12.png:shape2:version1round2:version1round3:quadrant1:moveRotated30",
        "23.png:shape4:version1round2:extra:quadrant4:moveRotated360",
        "12.png:shape2:version1:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated30",
        "24.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version4:quadrant2:moveRotated180",
        "3.png:shape1:version1:version1round3:version1round4:version1round5:quadrant1:moveRotated90",
        "11.png:shape2:version1:version1round3:version1round4:version1round5:quadrant4:moveRotated330",
        "20.png:shape3:version1:version1round3:version1round4:version1round5:quadrant3:moveRotated240",
        "3.png:shape1:version1round3:quadrant1:moveRotated90",
        "11.png:shape2:version1round3:quadrant4:moveRotated330",
        "20.png:shape3:version1round3:quadrant3:moveRotated240",
        "14.png:shape2:version1:version1round4:version1round5:quadrant2:moveRotated120",
        "21.png:shape3:version1:version1round4:version1round5:quadrant4:moveRotated360",
        "4.png:shape1:version1:version1round4:version1round5:version5:version5zero:quadrant4:moveRotated330",
        "14.png:shape2:version1round4:quadrant2:moveRotated120",
        "4.png:shape1:version1round4:version5:version5zero:quadrant4:moveRotated330",
        "25.png:shape4:version1round5:quadrant4:moveRotated315",
        "16.png:shape3:version1round5:version4:version4zero:quadrant1:moveRotated45",
        "1.png:shape1:version1round5:version5:quadrant3:moveRotated210",
        "25.png:shape4:version1:version1round5:quadrant4:moveRotated315",
        "16.png:shape3:version1:version1round5:version4:version4zero:quadrant1:moveRotated45",
        "1.png:shape1:version1:version1round5:version5:quadrant3:moveRotated210",
        "7.png:shape1:version1round2:quadrant2:moveRotated135",
        "23.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version5:quadrant4:moveRotated360",
        "9.png:shape2:version1round2:quadrant3:moveRotated270",
        "28.png:shape4:version1round2:version1round3:quadrant3:moveRotated225",
        "19.png:shape3:version1round2:quadrant2:moveRotated135",
        "10.png:shape2:version1round4:quadrant2:moveRotated150",
        "22.png:shape4:version1round2:quadrant1:moveRotated60",
        "5.png:shape1:version1round2:version6:quadrant1:moveRotated30",
        "17.png:shape3:version1round2:version2:quadrant4:moveRotated315",
        "9.png:shape2:version1round3:quadrant3:moveRotated270",
        "7.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135",
        "10.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated150",
        "22.png:shape4:version1round4:quadrant1:moveRotated60",
        "7.png:shape1:version1round4:quadrant2:moveRotated135",
        "19.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135",
        "22.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated60",
        "9.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated270",
        "23.png:shape4:version1round2:version1round3:quadrant4:moveRotated360",
        "10.png:shape2:version1round2:quadrant2:moveRotated150",
        "5.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version6:quadrant1:moveRotated30",
        "21.png:shape3:version1round4:quadrant4:moveRotated360",
        "28.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated225",
        "17.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version2:quadrant4:moveRotated315",
        "5.png:shape1:version1round3:quadrant1:moveRotated30"
    };

    // @todo: server shared variables to be used in animations and interactions concurrently displayed on multiple users devices 
    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("MultiParticipant");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);
        wizardData.setTextFontSize(24);

        final WizardMultiParticipantGroupFormationScreen wizardAgreementScreen = new WizardMultiParticipantGroupFormationScreen("Introductie", welcomeScreen,
                "Volgende");
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen(true);
        wizardAboutScreen.setScreenText("<b>This is a prototype multiparticipant experiment.</b><br/><br/>"
                + "With this prototype you can:<br/>"
                + "<li>view the group activity and add dummy users to a group for testing purposes <a href='/multiparticipant/grouptestpage.html'>/multiparticipant/grouptestpage.html</a>.</li>"
                + "<li>erase the local data for this browser <a href='/multiparticipant?debug'>/multiparticipant?debug</a></li>"
                + "<li>random data can be generated with the testing robot <a href='/multiparticipant/TestingFrame.html'>/multiparticipant/TestingFrame.html</a></li><br/>"
                + "<li>eight users intracting can be randomly simulated testing robot <a href='/multiparticipant/grouptestframes.html'>/multiparticipant/grouptestframes.html</a></li><br/>"
                + "The group name must be allocated with the following with <a href='/multiparticipant/?group=a_group_name'>/multiparticipant/?group=a_group_name</a> where a_group_name should be replaced with a suitable string. A second user can be tested on one computer via the incognito browser window with this link, providing the group name matches.<br/><br/>"
                + "There needs to be eight users connected for the group process to begin. Once a group is full, any subsequent users will need to be allocated a different group via the a_group_name parameter.<br/><br/><br/>"
                + "You can view the collected group data with <a href='/multiparticipant-admin/groupdataviewer'>/multiparticipant-admin/groupdataviewer</a> with the user and password you have been supplied.<br/><br/><br/>");
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Volgende");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
        wizardEditUserScreen.setAgeField();
//        wizardEditUserScreen.setFirstNameField(); 
        wizardEditUserScreen.getWizardScreenData().getMetadataFields().add(new Metadata("firstName", "First name", ".'{'1,'}'", "Please enter at least three letters or numbers.", false, null));
        wizardEditUserScreen.setMandatoryGenderField();
        wizardEditUserScreen.setWorkerIdField();

        final String groupMembers4 = "A,B,C,D";
        final String groupMembers8 = "A,B,C,D,E,F,G,H";

        WizardMenuScreen menuScreen4or8Members = new WizardMenuScreen("GroupSizeMenu", "GroupSizeMenu", "GroupSizeMenu");
        menuScreen4or8Members.setBranchOnGetParam(true, "A choice must be provided out of the following:<br/>");
        WizardCompletionScreen completionScreen = new WizardCompletionScreen("Einde van het experiment", false, false,
                //                                "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                "Einde van het experiment1",
                "Opnieuw beginnen",
                "Einde van het experiment2",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");

        wizardData.addScreen(wizardAgreementScreen);
        wizardData.addScreen(wizardEditUserScreen);
        wizardData.addScreen(menuScreen4or8Members);

        String[][] groupOfFourCommunicationChannels = new String[][]{
            {"0", "A,B,C,D", "naming", "version1zero", ronde0NAMING, null, RANDOMISE},
            {"0.1", "A,B,C,D", "naming", "version1zero", ronde0REPETITION1, null, RANDOMISE},
            {"0.2", "A,B,C,D", "naming", "version1zero", ronde0REPETITION2, null, RANDOMISE},
            // todo: for the various communication channels, we can add a get param based branch to different play screen sets
            {"1", "A,B|D,C", "play", "version1zero", ronde1, ronde1tm7ENDSCREEN, RANDOMISE},
            {"2", "C,A|D,B", "play", "version1round2", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"3", "B,C|A,D", "play", "version1round3", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"4", "B,A|C,D", "play", "version1round4", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"5", "A,C|B,D", "play", "version1round5", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"6", "C,B|D,A", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"7", "A,B|D,C", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            // todo: after the play screens all communication channel versions go to the same test screen
            {"8", "A|B|C|D", "test", "version1", ronde8test1, finalSCREEN, RANDOMISE}
        };
        WizardMultiParticipantScreen roundOfFourScreenOuter = null;
        for (String[] currentChannel : groupOfFourCommunicationChannels) {
            final WizardMultiParticipantScreen roundScreen;
            if ("naming".equals(currentChannel[2])) {
                roundScreen = getNamingRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1],
                        "A:-:-:B:-:-:C:-:-:D:-:-", "B,C,D:B,C,D:-:A,C,D:A,C,D:-:B,A,D:B,A,D:-:B,C,A:B,C,A:-", "-:-:A,B,C,D:-:-:A,B,C,D:-:-:A,B,C,D:-:-:A,B,C,D",
                        "-:A:-:-:B:-:-:C:-:-:D:-", currentChannel[4], currentChannel[5]);
            } else if ("test".equals(currentChannel[2])) {
                roundScreen = getTestRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1], "A,B,C,D:-", "-:A,B,C,D", currentChannel[4], currentChannel[5]);
            } else {
                roundScreen = getPlayingRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1], currentChannel[4], currentChannel[5]);
                roundScreen.setGroupTitle("(<groupMemberCode> speelt met <channelOtherMemberCodes>)");
            }
            roundScreen.setRandomiseStimuli(currentChannel[6].equals(RANDOMISE));
            roundScreen.setStimuliSet(stimuliArray);
            roundScreen.getWizardScreenData().setStimuliRandomTags(new String[]{currentChannel[3]});
            roundScreen.setStimulusFreeText(true, "[wetuiopasfghknm ]{2,}", "Sorry, dit teken is niet toegestaan in de Fantasietaal.", " "/*, "The key '<keycode>' is not allowed."*/);
            roundScreen.setAllowedCharCodes("wetuiopasfghknm ");
            wizardData.addScreen(roundScreen);
            if (roundOfFourScreenOuter == null) {
                menuScreen4or8Members.addTargetScreen(roundScreen);
            } else {
                roundOfFourScreenOuter.setNextWizardScreen(roundScreen);
            }
//            roundScreen.setBackWizardScreen(wizardAgreementScreen);
            roundOfFourScreenOuter = roundScreen;
        }
        if (roundOfFourScreenOuter != null) {
            roundOfFourScreenOuter.setNextWizardScreen(completionScreen);
        }
        String[][] groupOfEightCommunicationChannels = new String[][]{
            {"0", "A,B,C,D,E,F,G,H", "naming", "version1zero", ronde0NAMING, null, RANDOMISE},
            {"0.1", "A,B,C,D,E,F,G,H", "naming", "version1zero", ronde0REPETITION1, null, RANDOMISE},
            {"0.2", "A,B,C,D,E,F,G,H", "naming", "version1zero", ronde0REPETITION2, null, RANDOMISE},
            // todo: for the various communication channels, we can add a get param based branch to different play screen sets
            {"1", "A,B|C,D|E,F|G,H", "play", "version1zero", ronde1, ronde1tm7ENDSCREEN, RANDOMISE},
            {"2", "H,A|D,E|F,C|B,G", "play", "version1round2", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"3", "A,D|G,F|E,B|C,H", "play", "version1round3", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"4", "C,A|B,D|E,G|F,H", "play", "version1round4", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"5", "A,E|D,H|B,F|G,C", "play", "version1round5", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"6", "F,A|G,D|C,B|H,E", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"7", "A,G|H,B|E,C|D,F", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            // todo: after the play screens all communication channel versions go to the same test screen
            {"8", "A|B|C|D|E|F|G|H", "test", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            // todo: for the various communication channels, we can add a get param based branch to different play screen sets
            {"9", "B,A|D,C|F,E|H,G", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"10", "A,H|E,D|C,F|G,B", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"11", "D,A|F,G|B,E|H,C", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"12", "A,C|D,B|G,E|H,F", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"13", "E,A|H,D|F,B|C,G", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"14", "A,F|D,G|B,C|E,H", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            {"15", "G,A|B,H|C,E|F,D", "play", "version1", ronde2tm7, ronde1tm7ENDSCREEN, RANDOMISE},
            // todo: after the play screens all communication channel versions go to the same test screen
            {"16", "A|B|C|D|E|F|G|H", "test", "version1", ronde8test1, finalSCREEN, RANDOMISE}
        };
        WizardMultiParticipantScreen roundOfEightScreenOuter = null;
        for (String[] currentChannel : groupOfEightCommunicationChannels) {
            final WizardMultiParticipantScreen roundScreen;
            if ("naming".equals(currentChannel[2])) {
                roundScreen = getNamingRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1],
                        "A:-:-:B:-:-:C:-:-:D:-:-:E:-:-:F:-:-:G:-:-:H:-:-",
                        "B,C,D,E,F,G,H:B,C,D,E,F,G,H:-:A,C,D,E,F,G,H:A,C,D,E,F,G,H:-:B,A,D,E,F,G,H:B,A,D,E,F,G,H:-:B,C,A,E,F,G,H:B,C,A,E,F,G,H:-:B,C,D,A,F,G,H:B,C,D,A,F,G,H:-:B,C,D,E,A,G,H:B,C,D,E,A,G,H:-:B,C,D,E,F,A,H:B,C,D,E,F,A,H:-:B,C,D,E,F,G,A:B,C,D,E,F,G,A:-",
                        "-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H",
                        "-:A:-:-:B:-:-:C:-:-:D:-:-:E:-:-:F:-:-:G:-:-:H:-", currentChannel[4], currentChannel[5]);
            } else if ("test".equals(currentChannel[2])) {
                roundScreen = getTestRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1], "A,B,C,D,E,F,G,H:-", "-:A,B,C,D,E,F,G,H", currentChannel[4], currentChannel[5]);
            } else {
                roundScreen = getPlayingRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1], currentChannel[4], currentChannel[5]);
                roundScreen.setGroupTitle("(<groupMemberCode> playing with <channelOtherMemberCodes>)");
            }
            roundScreen.setRandomiseStimuli(currentChannel[6].equals(RANDOMISE));
            roundScreen.setStimuliSet(stimuliArray);
            roundScreen.getWizardScreenData().setStimuliRandomTags(new String[]{currentChannel[3]});
            roundScreen.setStimulusFreeText(true, "[wetuiopasfghknm ]{2,}", "Sorry, dit teken is niet toegestaan in de Fantasietaal.", " "/*, "The key '<keycode>' is not allowed."*/);
            roundScreen.setAllowedCharCodes("wetuiopasfghknm ");
            wizardData.addScreen(roundScreen);
            if (roundOfEightScreenOuter == null) {
                menuScreen4or8Members.addTargetScreen(roundScreen);
            } else {
                roundOfEightScreenOuter.setNextWizardScreen(roundScreen);
            }
//            roundScreen.setBackWizardScreen(wizardAgreementScreen);
            roundOfEightScreenOuter = roundScreen;
        }
        if (roundOfEightScreenOuter != null) {
            roundOfEightScreenOuter.setNextWizardScreen(completionScreen);
        }

        wizardData.addScreen(wizardAboutScreen);
        wizardData.addScreen(completionScreen);

        wizardAgreementScreen.setNextWizardScreen(wizardEditUserScreen);
        wizardEditUserScreen.setBackWizardScreen(wizardAgreementScreen);
        wizardEditUserScreen.setNextWizardScreen(menuScreen4or8Members);

//        endTextScreen.setNextWizardScreen(wizardAboutScreen);
        wizardAboutScreen.setBackWizardScreen(wizardAgreementScreen);

        return wizardData;
    }
    private static final String SEQUENTIAL = "sequential";
    private static final String RANDOMISE = "randomise";

    protected WizardMultiParticipantScreen getTestRound(final String screenName, final String groupMembers4, final String communicationChannels,
            final String textEntryPhaseRoles, final String groupRecordSubmitionPhaseRoles, final String preStimuliText, final String postStimuliText) {
        // done: test round needs to submit dat to the group table even though its not a group interaction
        // done: in the testing round there are cases where the entered text is not recorded but the interaction is!!!
        return new WizardMultiParticipantScreen(screenName,
                groupMembers4, 2,
                communicationChannels, textEntryPhaseRoles,
                "&nbsp;",
                "",
                true,
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                groupRecordSubmitionPhaseRoles,
                preStimuliText, postStimuliText,
                numberOfStimuli, repeatCountStimuli, randomWindowStimuli,
                0, 0,
                0, 0, null
        );
    }

    protected WizardMultiParticipantScreen getNamingRound(final String screenName, final String groupMembers, final String communicationChannels,
            final String textEntryPhaseRoles, final String waitingForProducerPhaseRoles, final String outcomeDisplayedPhaseRoles,
            final String groupRecordSubmitionPhaseRoles, final String preStimuliText, final String postStimuliText) {
        final WizardMultiParticipantScreen wizardMultiParticipantScreen = new WizardMultiParticipantScreen(screenName,
                groupMembers, 3,
                communicationChannels, textEntryPhaseRoles,
                ronde1tm7PRODUCERSCREEN, waitingForProducerPhaseRoles,
                true,
                waitingScreenForGuesser,
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "",
                "",
                "This phase is not used in this screen", outcomeDisplayedPhaseRoles,
                namingAndExposure,
                "",
                groupRecordSubmitionPhaseRoles,
                preStimuliText, postStimuliText,
                numberOfStimuli, 1, randomWindowStimuli,
                7000, 2000,
                0, 0, null);
        wizardMultiParticipantScreen.setUseDictionary(true);
        return wizardMultiParticipantScreen;
    }

    protected WizardMultiParticipantScreen getPlayingRound(final String screenName, final String groupMembers, final String communicationChannels,
            final String preStimuliText, final String postStimuliText) {
        final String textEntryPhaseText = "&nbsp;";
        final String textWaitPhaseText = waitingScreenForGuesser;
        final String gridWaitPhaseText = waitingScreenForProducer;
        final String responseGridPhaseText = "&nbsp;";
        final String mutualFeedbackPhaseText = "&nbsp;";
        final int timerCountDownProducerMs = 30 * 1000;
        final int timerCountDownGuesserMs = 20 * 1000;
        final String timerCountDownLabel = "Time is up! Play now!";

        String phaseRoleA = "";
        String phaseRoleB = "";
        for (final String channel : communicationChannels.split("\\|")) {
//            System.out.println("channel:" + channel);
            final String[] members = channel.split(",");
            for (int index = 0; index < members.length; index += 2) {
                if (!phaseRoleA.isEmpty()) {
                    phaseRoleA += ",";
                }
                if (!phaseRoleB.isEmpty()) {
                    phaseRoleB += ",";
                }
                phaseRoleA += members[index];
                phaseRoleB += members[index + 1];
            }
        }
        final String responseGridPhaseRoles = "-:" + phaseRoleB + ":-:-:" + phaseRoleA + ":-";
        final String mutualFeedbackPhaseRoles = "-:-:" + phaseRoleA + "," + phaseRoleB + ":-:-:" + phaseRoleA + "," + phaseRoleB;
        final String textWaitPhaseRoles = phaseRoleB + ":-:-:" + phaseRoleA + ":-:-";
        final String textEntryPhaseRoles = phaseRoleA + ":-:-:" + phaseRoleB + ":-:-";
        final String gridWaitPhaseRoles = "-:" + phaseRoleA + ":-:-:" + phaseRoleB + ":-";

        return new WizardMultiParticipantScreen(screenName,
                groupMembers, 3,
                communicationChannels,
                textEntryPhaseRoles,
                ronde1tm7PRODUCERSCREEN,
                textWaitPhaseRoles,
                false,
                textWaitPhaseText,
                gridWaitPhaseRoles,
                gridWaitPhaseText,
                responseGridPhaseRoles,
                ronde1tm7GUESSERSCREEN,
                mutualFeedbackPhaseRoles,
                ronde1tm7FEEDBACKSCREEN,
                ronde1tm7FeedbackCorrect,
                ronde1tm7FeedbackIncorrect,
                "",
                "This phase is not used in this screen",
                "",
                "",
                preStimuliText, postStimuliText,
                numberOfStimuli, repeatCountStimuli, randomWindowStimuli,
                0, 0,
                timerCountDownProducerMs, timerCountDownGuesserMs, timerCountDownLabel);
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        experiment.getDataChannels().add(new DataChannel(3, "groupNetworkActivity", false));
        experiment.setIsScalable(false);
        experiment.setDefaultScale(1.1f);
        return experiment;
    }
}
