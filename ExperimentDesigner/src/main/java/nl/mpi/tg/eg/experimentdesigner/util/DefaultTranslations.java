/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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

import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureText;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;

/**
 * @since Jun 19, 2017 11:49:13 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class DefaultTranslations {

//    private final TranslationRepository translationRepository;
//    public DefaultTranslations(TranslationRepository translationRepository) {
//        this.translationRepository = translationRepository;
//    }
    public Experiment applyTranslations(Experiment experiment) {
        for (PresenterScreen presenterScreen : experiment.getPresenterScreen()) {
            for (PresenterFeature presenterFeature : presenterScreen.getPresenterFeatureList()) {
                applyTranslations(presenterFeature);
            }
        }
        return experiment;
    }

    public void applyTranslations(PresenterFeature presenterFeature0) {
        if (presenterFeature0.getFeatureText() != null) {
            insertTranslations(presenterFeature0);
        }
        for (PresenterFeature presenterFeature1 : presenterFeature0.getPresenterFeatureList()) {
            applyTranslations(presenterFeature1);
        }
    }

    public void insertTranslations(PresenterFeature presenterFeature) {
        insertEnNlDeRu(presenterFeature, "Submit", "Sla op", "", "Отправить");
        insertEnNlDeRu(presenterFeature, "Quit", "Stop", "", "Стоп");
        insertEnNlDeRu(presenterFeature, "No colour", "Geen kleur", "", "нет цвета");
        insertEnNlDeRu(presenterFeature, "{0} of {1}    ", "{0} of {1}    ", "", "{0} of {1}    ");
        insertEnNlDeRu(presenterFeature, "Score: {0}", "Score: {0}", "", "счёт ");
        insertEnNlDeRu(presenterFeature, "In this test a score below 1.5 is considered synaesthetic.", "In deze test betekent een score lager dan 1.5 dat je synesthesie hebt.", "", "В данном тесте оценка ниже 1.5 классифицируется как синестезия");
        insertEnNlDeRu(presenterFeature, "OK, go to test!", "OK, ga naar de test!", "", "Отлично, начнем тест!");
        insertEnNlDeRu(presenterFeature, "Cancel", "Annuleer", "", "Отменить");
        insertEnNlDeRu(presenterFeature, "An error occurred", "Er is een fout opgetreden", "", "Произошла ошибка");
        insertEnNlDeRu(presenterFeature, "SynQuiz2", "SynQuiz2", "", "SynQuiz2");
        insertEnNlDeRu(presenterFeature, "Introduction", "Introductie", "", "введе́ние ");
        insertEnNlDeRu(presenterFeature, "Decoding the Genetics of Synaesthesia", "Het decoderen van de genetica van synesthesie", "", "Расшифровка генетических основ Синестезии.");
        insertEnNlDeRu(presenterFeature, "We are studying the genetic basis of synaesthesia, a neurological phenomenon described as a \"mixing of the senses\". To find out how our genes shape how we see the world, we are looking for people who connect letters, numbers, days of the week, or months with specific colours. This is called \"grapheme-colour\" synaesthesia. ", "Wij onderzoeken de genetische basis van synesthesie, een neurologisch fenomeen dat beschreven wordt als 'het mixen van zintuigen'. Om uit te vinden hoe onze genen vorm geven aan de manier waarop wij de wereld zien, zoeken we mensen die letters, cijfers, dagen van de week of maanden verbinden met bepaalde kleuren. Dit wordt ook wel 'grafeem-kleur' synesthesie genoemd.", "", "Мы изучаем генетические основы синестезии, неврологический феномен, часто определяемый как \"смешение чувств\". Для того чтобы узнать, как наши гены формируют то, как мы видим мир, мы ищем людей, у которых буквы, цифры, дни недели или месяцы связаны с конкретными цветами. Эта разновидность называется \"графемно-цветовая\" синестезия. ");
        insertEnNlDeRu(presenterFeature, "How our study works:", "Hoe ons onderzoek werkt:", "", "Как работает наше исследование: ");
        insertEnNlDeRu(presenterFeature, "The synaesthesia tests take about 20 minutes to complete, and you can choose the ones that apply to you. Depending on your scores, we may send you an email inviting you to participate in the genetics part of the study. There is no cost to participate, and you can do everything from home.", "The synaesthesietesten duren ongeveer 20 minuten en je kunt de testen kiezen die op jou betrekking hebben. Afhankelijk van jouw scores sturen we je een e-mail om je uit te nodigen om aan het genetische deel van ons onderzoek deel te nemen. Er zijn geen kosten verbonden aan deelname en je kunt alles thuis doen.", "", "Тесты по синестезии занимают около 20 минут, и вы можете выбрать те, которые подходят именно вам. В зависимости от результатов, мы отправляем вам электронное письмо с приглашением к участию в генетическом этапе исследования. Участие не предполагает каких-либо расходов,  так как вы проходите его, не выходя из дома. ");
        insertEnNlDeRu(presenterFeature, "Participate!", "Doe mee!", "", "Участвуйте! ");
        insertEnNlDeRu(presenterFeature, "For more information about synaesthesia, please see our ''About synaesthesia'' page. If you are not sure if you have synaesthesia, and want to find out, try our SynQuiz app or take a quick test at synesthete.org.", "Voor meer informatie over synesthesie kun je kijken naar onze 'Over synesthesie' pagina. Als je niet zeker weet of je synesthesie hebt en erachter wilt komen, dan kun je onze SynQuiz app proberen of een snelle test maken op synesthete.org.", "", "Для получения более подробной информации о синестезии, см. нашу страницу 'О синестезии'. Если Вы не уверены, что обладаете синестезией, и хотите убедиться в этом, попробуйте сделать это посредством нашего приложения SynQuiz или выполните быстрый тест на synesthete.org. ");
        insertEnNlDeRu(presenterFeature, "This project is organised and funded by the Language & Genetics Department at the Max Planck Institute for Psycholinguistics in Nijmegen in the Netherlands, directed by Prof. Dr. Simon E. Fisher. The synaesthesia studies are coordinated by Dr. Amanda Tilot and Dr. Sarah Graham. If you have any questions about our research, please contact us at <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''mailto:synaesthesia@mpi.nl'',''_system''); return false;'',''_system''); return false;\">synaesthesia@mpi.nl</a>.", "Dit project is georganiseerd en gefinancierd door het Language & Genetics Department van het Max Planck Institute for Psycholinguistics in Nijmegen, Nederland, geleid door Prof. Dr. Simon E. Fisher. De onderzoeken over synesthesie worden gecoordineerd door Dr. Amanda Tilot en Dr. Sarah Graham. Mocht je vragen hebben over ons onderzoek, stuur dan een e-mail naar <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''mailto:synaesthesia@mpi.nl'',''_system''); return false;'',''_system''); return false;\">synaesthesia@mpi.nl</a>.", "", "Этот проект организован и финансируется кафедрой языка и генетики Института психолингвистики Макса Планка в городе Неймеген в Нидерландах, которым руководит проф. Саймон Фишер. Исследования по синестезии координирует доктор наук Аманда Тайлот и доктор наук Сара Грэм. Пожалуйста, свяжитесь с нами по электронному адресу <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''mailto:synaesthesia@mpi.nl'',''_system''); return false;'',''_system''); return false;\">synaesthesia@mpi.nl</a>., если у вас есть вопросы о нашем исследовании.");
//        insertEnNlDeRu(presenterFeature, "Participant", "Deelnemer", "", "участник");
        insertEnNlDeRu(presenterFeature, "Participant", "Deelnemer", "", "участник");
        insertEnNlDeRu(presenterFeature, "Please read the <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''static/synaesthesia_info_sheet_ENGLISH_webversion.pdf'',''_system''); return false;'',''_system''); return false;\">Participant Information Sheet</a> carefully!", "Lees de <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''static/synaesthesia_info_sheet_ENGLISH_webversion.pdf'',''_system''); return false;'',''_system''); return false;\">pagina met deelnemersinformatie</a> goed door!", "", "Пожалуйста, внимательно ознакомитесь с <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''static/synaesthesia_info_sheet_ENGLISH_webversion.pdf'',''_system''); return false;'',''_system''); return false;\">информационным листом </a> участника!");
        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
//        insertEnNlDeRu(presenterFeature, "Details", "Details", "", "Дополнительная информация");
        insertEnNlDeRu(presenterFeature, "Details", "Details", "", "Дополнительная информация");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Study", "Onderzoek", "", "изуче́ние");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Study(1/8)", "Vertel ons over jouw synesthesie: Onderzoek (1/8)", "", "Расскажите о своей синестезии:");
        insertEnNlDeRu(presenterFeature, "Our study at the Max Planck Institute focuses on synaesthesia where numbers, letters, weekdays, or months cause people to have a colour experience. To someone with synaesthesia, the letter A might \"mean\" red to them, or the number \"5\" might make them experience the colour green. Please let us know if you experience any other types of synaesthesia by checking the boxes in the following screens. We may contact you in the future about studies related to these other types.", "Ons onderzoek op het Max Planck Institute houdt zich bezig met synesthesie die mensen een kleur laat ervaren door cijfers, letters, dagen van de week of maanden. Voor iemand die synesthesie heeft, kan de letter A rood 'betekenen', of  nummer '5' kan een ervaring van groen veroorzaken. Laat ons weten of je andere vormen van synesthesie ervaart door de hokjes aan te vinken op de volgende pagina's. Het kan zijn dat we in de toekomst contact met je opnemen over studies die met deze andere types van synesthesie te maken hebben.", "", "Наше исследование в Институте Макса Планка фокусируется на синестезии, при которой цифры, буквы, дни недели, месяцы связаны у людей с ощущением цвета. Например, у кого-то буква \"А\" может \"означать\" красный или число \"5\" может восприниматься окрашенной в зеленый цвет. Пожалуйста, сообщите нам, есть ли у вас ещё какие-либо другие типы синестезии, отметив поля на последующих страницах. Мы свяжемся с Вами в будущем по вопросам исследований, связанных с этими типами. ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Colour", "Kleur", "", "цвет");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Colour(2/8)", "Vertel ons over jouw synesthesie: Kleur (2/8)", "", "Расскажите о своей синестезии: цвет (2/8)");
        insertEnNlDeRu(presenterFeature, "Do any of the items below cause you to have a color experience?<br/><br/>Examples: Does the letter M \"mean\" orange to you? Or does hearing a piano being played make you perceive red?<br/><br/>", "Veroorzaken een of meer onderstaande items ervaring van kleur bij jou? <br/><br/>Voorbeeld: 'Betekent' de letter M oranje voor jou? Of ervaar je de kleur rood, als je iemand op een piano hoort spelen?<br/><br/>", "", "Вызывает ли у вас что-то из перечисленного ниже ощущение цвета? Например: означает (имеет) ли для вас буква М оранжевый цвет? Воспринимаются звуки  ортепиано как окрашенные в красный? ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Smell", "Geur", "", "Запах");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Smell(3/8)", "Vertel ons over jouw synesthesie: Geur (3/8)", "", "Расскажите о своей синестезии: Запах (3/8)");
        insertEnNlDeRu(presenterFeature, "Do any of the items below cause you to experience smells?<br/><br/>Example: Does Tuesday smell like bananas?<br/><br/>", "Veroorzaken een of meer onderstaande items geur bij jou? <br/><br/> Voorbeeld: Ruikt dinsdag naar bananen? <br/><br/>", "", "Вызывает ли у вас какой то из этих предметов (названий, пунктов) ощущение запаха? Например, пахнет ли вторник бананами? ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Sound", "Geluid", "", "звук");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Sound(4/8)", "Vertel ons over jouw synesthesie: Geluid (4/8)", "", "Расскажите о своей синестезии: звук (4/8)");
        insertEnNlDeRu(presenterFeature, "Do any of the items below cause you to experience sound?<br/><br/>Example: Do you hear a particular sound when you experience cold temperatures?<br/><br/>", "Veroorzaken een of meer onderstaande items een ervaring van geluid bij jou? <br/><br/> Voorbeeld: Hoor je een bepaald geluid als je koude temperaturen ervaart? <br/><br/>", "", "Вызывает ли у вас какой то из этих предметов ощущение звука? Например, слышите ли вы определенный звук, когда испытываете холод? ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Spatial", "Ruimtelijk", "", "Пространственный");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Spatial(5/8)", "Vertel ons over jouw synesthesie: Ruimtelijk(5/8)", "", "Расскажите о своей синестезии: простра́нственный (5/8)");
        insertEnNlDeRu(presenterFeature, "Do you experience any of the items below in a particular spatial location?<br/><br/>Example: Do you \"see\" sequences like the days of the month or numbers in physical space?<br/><br/>", "Ervaar je een of meer onderstaande items in een bepaalde ruimtelijke locatie? <br/><br/> Voorbeeld: 'Zie' je opeenvolgingen als dagen in een maand of cijfers in een fysieke ruimte? <br/><br/>", "", "Возникает ли у вас ощущение, что перечисленные предметы имеют определенное положение в пространстве? Например, \"видите\" ли вы последовательность дней месяца и чисел в физическом пространстве? ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Taste", "Smaak", "", "вкус ");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Taste(6/8)", "Расскажите о своей синестезии: вкус  (6/8)", "", "Расскажите о своей синестезии:");
        insertEnNlDeRu(presenterFeature, "Do any of the items below cause you to experience tastes?<br/><br/>Examples: \"Philip tastes of bitter oranges, while April tastes of apricots.\" \"The word ''safety'' tastes of lightly buttered toast\"<br/><br/>", "Veroorzaken een of meer onderstaande items ervaring van smaak bij jou? <br/><br/>Voorbeeld: \"Jan smaakt naar bittere sinaasappels, terwijl Heleen smaakt naar abrikozen' . ' Het woord 'veiligheid' proeft als een geroosterde boterham met boter'.\"<br/><br/>", "", "Ощущаете ли вы вкус перечисленных ниже предметов? Примеры: \"Филипп имеет вкус горьких апельсинов, у апреля вкус абрикосов\" или \"Слово \"безопасность\" имеет вкус тоста, слегка смазанного маслом\". ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Touch", "Aanraking", "", "осяза́ние");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Touch(7/8)", "Vertel ons over jouw synesthesie: Aanraking (7/8)", "", "Расскажите о своей синестезии: осяза́ние (7/8)");
        insertEnNlDeRu(presenterFeature, "Do any of the items below cause you to experience a sense of touch?<br/><br/>Example: You feel a touch on your arm when you see someone else being touched on their arm.<br/><br/>", "Veroorzaken een of meer onderstaande items ervaring van aanraking bij jou? <br/><br/> Voorbeeld: Je voelt een aanraking op je arm als je ziet dat iemand anders aangeraakt wordt op zijn/haar arm. <br/><br/>", "", "Испытываете ли вы чувство прикосновения? Например: ощущаете ли вы прикосновение к руке, когда вы видите, как кто-то прикасается к чьей-то руке. ");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Other", "Anders", "", "друго́й");
        insertEnNlDeRu(presenterFeature, "Tell us about your synaesthesia: Other(8/8)", "Vertel ons over jouw synesthesie: Anders (8/8)", "", "Расскажите о своей синестезии: друго́й (8/8)");
//        insertEnNlDeRu(presenterFeature, "Continue", "Ga door", "", "Продолжать");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Menu", "Menu", "", "меню́");
//        insertEnNlDeRu(presenterFeature, "Menu", "Menu", "", "меню");
        insertEnNlDeRu(presenterFeature, "Weekdays", "Dagen van de week", "", "дни недели");
        insertEnNlDeRu(presenterFeature, "Letters and Numbers", "Letters en cijfers", "", "буквы и цифры");
        insertEnNlDeRu(presenterFeature, "Months", "Maanden", "", "месяцы");
        insertEnNlDeRu(presenterFeature, "The tests above will ask about the colours that you associate with Weekdays, Letters and Numbers, or Months. If you do not have colour associations with one of the options, you can skip that test. After each test you can view your results.<br/><br/>When you are finished taking the tests that apply to you, please click <b>Submit my results</b> below to finish the experiment.", "In de test hierboven krijg je vragen over de kleuren die jij associeert met dagen van de week, letters, cijfers of maanden. Als je geen kleurassociatie hebt met een van deze opties, dan kun je die test overslaan. Na iedere test kun je je resultaten zien. <br/><br/> Als je klaar bent met de tests die op jou betrekking hebben, klik dan onderaan op <b>Sla mijn resultaten op</b> om het experiment te beeindigen.", "", "Далее в тесте следуют вопросы о цветах, которые ассоциируются у вас с днями недели, буквами и цифрами или месяцами. Если у вас не возникнет цветовых ассоциаций в каком-то или ни в одном из вариантов тестов, то эту часть тестирования можно пропустить. После каждого теста вы можете ознакомиться с результатами. Закончив все этапы тестирования, выбранные вами под ваши типы синестезии, пожалуйста, нажмите ниже: \"Отправить мои результаты и завершить эксперимент.");
//        insertEnNlDeRu(presenterFeature, "Weekdays", "Dagen van de week", "", "дни недели");
//        insertEnNlDeRu(presenterFeature, "Weekdays", "Dagen van de week", "", "дни недели");
        insertEnNlDeRu(presenterFeature, "<b>Instructions</b>\n<p>Select the colour that you associate with the presented character or word \n<ol>\n<li>Select the hue by tapping on the colour bar on the right </li><li>Select the shade by tapping on the square on the left </li>\n<li>When the colour of the preview rectangle matches your association, tap \"Submit\"</li>\n<li>If you have no colour association tap \"No colour\"</li>\n</ol>\n</p>", "<b>Instructies</b>\n<p>Selecteer de kleur die jij associeert met het aangeboden poppetje of woord\n<ol>\n<li>Selecteer de kleur door te klikken op de kleurbalk aan de rechterkant</li>\n<li>Selecteer de tint door te klikken op het vierkant links </li>\n<li>Als de kleur in de rechthoek rechts klopt met jouw associatie, klik op 'Sla op' </li>\n<li> Als je geen kleurassociatie hebt, klik op 'Geen kleur'</li>\n</ol>\n</p>", "", "<b>Инструкции</b>\n<p>Выберите цвет, ассоциируищийся с показанным первонажем или словом\n<ol>\n<li>Выберите тон из палитры справа.</li><li>Выберите оттенок из палитры слева. </li>\n<li>Если цвет в окне выбора совпадает с вашей ассоциацией, нажмите \"дальше\"</li>\n<li>Если у вас нет ассоциаций с определенным цветом, нажмите \"Без цвета\"</li>\n</ol>\n</p>");
        insertEnNlDeRu(presenterFeature, "OK, go to test!", "OK, ga naar de test!", "", "Отлично, начнем тест!");
//        insertEnNlDeRu(presenterFeature, "Letters and Numbers", "Letters en cijfers", "", "буквы и цифры");
        insertEnNlDeRu(presenterFeature, "LettersNumbers", "LettersCijfers", "", "буквыцифры");
        insertEnNlDeRu(presenterFeature, "<b>Instructions</b>\n<p>Select the colour that you associate with the presented character or word \n<ol>\n<li>Select the hue by tapping on the colour bar on the right </li><li>Select the shade by tapping on the square on the left </li>\n<li>When the colour of the preview rectangle matches your association, tap \"Submit\"</li>\n<li>If you have no colour association tap \"No colour\"</li>\n</ol>\n</p>", "<b>Instructies</b>\n<p>Selecteer de kleur die jij associeert met het aangeboden poppetje of woord\n<ol>\n<li>Selecteer de kleur door te klikken op de kleurbalk aan de rechterkant</li>\n<li>Selecteer de tint door te klikken op het vierkant links </li>\n<li>Als de kleur in de rechthoek rechts klopt met jouw associatie, klik op 'Sla op' </li>\n<li> Als je geen kleurassociatie hebt, klik op ' Geen kleur'</li>\n</ol>\n</p>", "", "<b>Инструкции</b>\n<p>Выберите цвет, ассоциируищийся с показанным первонажем или словом\n<ol>\n<li>Выберите тон из палитры справа.</li><li>Выберите оттенок из палитры слева. </li>\n<li>Если цвет в окне выбора совпадает с вашей ассоциацией, нажмите \"дальше\"</li>\n<li>Если у вас нет ассоциаций с определенным цветом, нажмите \"Без цвета\"</li>\n</ol>\n</p>");
        insertEnNlDeRu(presenterFeature, "OK, go to test!", "OK, ga naar de test!", "", "Отлично, начнем тест!");
        insertEnNlDeRu(presenterFeature, "Completion", "Voltooiing", "", "Закончить");
//        insertEnNlDeRu(presenterFeature, "Completion", "Voltooiing", "", "Закончить");
        insertEnNlDeRu(presenterFeature, "Thank you for participating! You may hear from us in the next few weeks to ask if you would like to participate in the genetics part of the study. Your data has been saved, and you can now close your browser. <br><br>If you have any questions about the study, you can email them to us at <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''mailto:synaesthesia@mpi.nl'',''_system''); return false;'',''_system''); return false;\">synaesthesia@mpi.nl</a>. It will be a year or more before there are results, but when we publish our study it will be posted on our <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''http://www.mpi.nl/departments/language-and-genetics/projects/decoding-the-genetics-of-synaesthesia/publications'',''_system''); return false;'',''_system''); return false;\">website</a>.<br/><br/>", "Bedankt voor het meedoen! Je zult in de komende weken een bericht van ons ontvangen met daarin de vraag of je wil meedoen aan het genetische deel van deze studie. Je data zijn opgeslagen en je kunt nu je browser sluiten. <br><br>Als je vragen hebt over dit onderzoek, kun je e-mailen naar<a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''mailto:synaesthesia@mpi.nl'',''_system''); return false;'',''_system''); return false;\">synaesthesia@mpi.nl</a>. Het zal een jaar of langer duren voordat er resultaten zijn, maar als we ons onderzoek publiceren, zullen we het ook posten op onze <a href=\"#\" onclick=\"window.open(''#\" onclick=\"window.open(''http://www.mpi.nl/departments/language-and-genetics/projects/decoding-the-genetics-of-synaesthesia/publications'',''_system''); return false;'',''_system''); return false;\">website</a>.<br/><br/>", "", "Благодарим Вас за участие! В течение нескольких недель, мы направим вам запрос о том, хотите ли вы участвовать в генетическом этапе нашего исследования. Ваши данные сохранены, и теперь браузер можно закрыть. Если у вас есть вопросы о проекте, направляйте их по адресу synaesthesia@mpi.nl.  бработка результатов займёт чуть больше года, после его результаты исследования будут опубликованы на нашем сайте. ");
        insertEnNlDeRu(presenterFeature, "Finish this experiment and start from the begining", "Maak dit experiment af en start vanaf het begin", "", "Закончить этот тест и начать сначала.");
//        insertEnNlDeRu(presenterFeature, "Could not contact the server, please check your internet connection and try again.", "Geen verbinding met de server, controleer of er internetverbinding is en probeer het dan nog een keer.", "", "Нет подключения к серверу, проверьте, что есть подключение к Интернету и повторите попытку.");
        insertEnNlDeRu(presenterFeature, "Retry", "Probeer nog een keer", "", "Попробуйте еще раз");
//        insertEnNlDeRu(presenterFeature, "Months", "Maanden", "", "месяцы");
//        insertEnNlDeRu(presenterFeature, "Months", "Maanden", "", "месяцы");
        insertEnNlDeRu(presenterFeature, "<b>Instructions</b>\n<p>Select the colour that you associate with the presented character or word \n<ol>\n<li>Select the hue by tapping on the colour bar on the right </li><li>Select the shade by tapping on the square on the left </li>\n<li>When the colour of the preview rectangle matches your association, tap \"Submit\"</li>\n<li>If you have no colour association tap \"No colour\"</li>\n</ol>\n</p>", "<b>Instructies</b>\n<p>Selecteer de kleur die jij associeert met het aangeboden letter, cijfer of woord\n<ol>\n<li>Selecteer de kleur door te klikken op de kleurbalk aan de rechterkant</li><li>Selecteer de tint door te klikken op het vierkant links </li>\n<li>Als de kleur in de rechthoek rechts klopt met jouw associatie, klik op 'Sla op' </li>\n<li> Als je geen kleurassociatie hebt, klik op ' Geen kleur'</li>\n</ol>\n</p>", "", "<b>Инструкции</b>\n<p>Выберите цвет, ассоциируищийся с показанным первонажем или словом\n<ol>\n<li>Выберите тон из палитры справа.</li><li>Выберите оттенок из палитры слева. </li>\n<li>Если цвет в окне выбора совпадает с вашей ассоциацией, нажмите \"дальше\"</li>\n<li>Если у вас нет ассоциаций с определенным цветом, нажмите \"Без цвета\"</li>\n</ol>\n</p>");
        insertEnNlDeRu(presenterFeature, "OK, go to test!", "OK, ga naar de test!", "", "Отлично, начнем тест!");
        insertEnNlDeRu(presenterFeature, "Submit my results", "Sla mijn resultaten op", "", "Отправить");
        insertEnNlDeRu(presenterFeature, "Register", "Registreer", "", "сохранить");
        insertEnNlDeRu(presenterFeature, "Error submitting data.", "Error met invoeren van data.", "", "Ошибка отправки данных.");
        insertEnNlDeRu(presenterFeature, "Report", "Reporteer", "", "отчет");
//        insertEnNlDeRu(presenterFeature, "Report", "Reporteer", "", "отчет");
        insertEnNlDeRu(presenterFeature, "About Screen", "Over Scherm", "", "Информация об окне");
//        insertEnNlDeRu(presenterFeature, "About Screen", "Over Scherm", "", "Информация об окне");
        insertEnNlDeRu(presenterFeature, "({0}) and unique id ({1})", "({0}) and unique id ({1})", "", "({0}) and unique id ({1})");
    }

    private void insertEnNlDeRu(PresenterFeature presenterFeature, String enText, String nlText, String deText, String ruText) {
        if (enText.equals(presenterFeature.getFeatureText())) {
            presenterFeature.addTranslation(new FeatureText(nlText, "nl"));
            presenterFeature.addTranslation(new FeatureText(deText, "de"));
            presenterFeature.addTranslation(new FeatureText(ruText, "ru"));
        }
        // todo: this insert method is temporary and should be replaced when the translation repository is set up
//        final Translation translation = new Translation();
//        translation.setLocaleString("en", enText);
//        translation.setLocaleString("nl", nlText);
//        translation.setLocaleString("de", deText);
//        translation.setLocaleString("ru", ruText);
//        translationRepository.save(translation);
    }
}
