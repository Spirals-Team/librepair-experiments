package de.swt.inf.controler;

import de.swt.inf.database.*;
import de.swt.inf.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

@Controller
public class FragekatalogController {

    private UserPreferences userPreference = Main.user.getUserPreferences();

    private final int KEY_MUSIC_PREF = 0;
    private final int KEY_GENDER_PREF = 1;
    private final int KEY_COURSE_PREF = 2;
    private final int KEY_PROVINCE_PREF = 3;
    private final int KEY_UNIVERSITY_PREF = 4;
    private final int KEY_AGE_PREF = 5;
    private final int KEY_SEMESTER_PREF = 6;


    /**
     * 1. Prüft ob User eingeloggt.
     * 2. Überprüft ob beim User schon irgendwelche Preferenzen vorliegen und fügt sie dem Model hinzu, damit diese vorausgewählt werden.
     * 3. Alle Enums werden geladen.
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/dashboard/fragekatalog", method = RequestMethod.GET)
    public String fragekatalog(HttpServletRequest request, Model model){

        userPreference = (userPreference == null ? new UserPreferences() : userPreference);

        if(LoginController.isUserLoggedIn(request.getCookies())){
            // Falls der Fragekatalog schon einmal verwendet wurde, werden die alten Preferenzen vorausgewählt
            if(userPreference.getMusicPreferencesEnum() != null){
                MusicPreferencesEnum musicSelected = userPreference.getMusicPreferencesEnum();
                model.addAttribute("musicSelected", musicSelected);
            }
            if(userPreference.getProvince() != null){
                ProvinceEnum provinceSelected = userPreference.getProvince();
                model.addAttribute("provinceSelected", provinceSelected);
            }
            if(userPreference.getCourseEnum() != null){
                CourseEnum courseSelected = userPreference.getCourseEnum();
                model.addAttribute("courseSelected", courseSelected);
            }
            if(userPreference.getGenderEnum() != null){
                GenderEnum genderSelected = userPreference.getGenderEnum();
                model.addAttribute("genderSelected", genderSelected);
            }
            if(userPreference.getUniversityEnum() != null){
                UniversityEnum universitySelected = userPreference.getUniversityEnum();
                model.addAttribute("universitySelected", universitySelected);
            }
            if(userPreference.getAge() != null){
                String ageSelected = userPreference.getAge();
                model.addAttribute("ageSelected", ageSelected);
            }
            if(userPreference.getSemester() != null){
                String semesterSelected = userPreference.getSemester();
                model.addAttribute("semesterSelected", semesterSelected);
            }



            //Enums einlesen
            List<MusicPreferencesEnum> musicEnums = Arrays.asList(MusicPreferencesEnum.values());
            List<GenderEnum> genderEnums = Arrays.asList(GenderEnum.values());
            List<CourseEnum> courseEnums = Arrays.asList(CourseEnum.values());
            List<ProvinceEnum> provinceEnums = Arrays.asList(ProvinceEnum.values());
            List<UniversityEnum> universityEnums = Arrays.asList(UniversityEnum.values());

            //Alters- und Semestergruppierung einlesen
            //Anschließend dem Model hinzufügen wenn nicht null
            List<String> ageEnums = loadOtherPreferences(KEY_AGE_PREF);
            List<String> semesterEnums = loadOtherPreferences(KEY_SEMESTER_PREF);

            model.addAttribute("age", ageEnums);
            model.addAttribute("semester", semesterEnums);

            //Enums dem Model hinzufügen
            model.addAttribute("music", musicEnums);
            model.addAttribute("gender", genderEnums);
            model.addAttribute("course", courseEnums);
            model.addAttribute("province", provinceEnums);
            model.addAttribute("university", universityEnums);

            return "fragekatalog";
        }else{
            return "redictDashboard";
        }

    }

    /**
     * 1. Liest die ausgewählten Preferenzen aus dem Fragekatalog aus.
     * 2. Sammelt alle möglichen Termine mit Hilfe von <k>loadTermine(PREFERENZ_KONSTANT)</k>.
     * 3. Alle Termine, die schon in der Datenbank vorhanden sind, werden aus der Liste wieder entfernt.
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/dashboard/fragekatalog", method = RequestMethod.POST)
    public String addFragekatalog(HttpServletRequest request, Model model){
        TerminDao terminDao = DaoFactory.getTerminDao();
        List<Termin> alteTermine = terminDao.getAllTermine();
        List<Termin> neueTermine = new LinkedList<Termin>();
        Map<Integer, String> allSelections = new HashMap<Integer, String>();

        //MusicPreferences
        allSelections.put(KEY_MUSIC_PREF, request.getParameter("music"));

        //GenderPreferences
        allSelections.put(KEY_GENDER_PREF, request.getParameter("gender"));

        //CoursePreferences
        allSelections.put(KEY_COURSE_PREF, request.getParameter("course"));

        //ProvincePreferences
        allSelections.put(KEY_PROVINCE_PREF, request.getParameter("province"));

        //UniversityPreferences
        allSelections.put(KEY_UNIVERSITY_PREF, request.getParameter("university"));

        //AgePreferences
        allSelections.put(KEY_AGE_PREF, request.getParameter("age"));

        //SemesterPreferences
        allSelections.put(KEY_SEMESTER_PREF, request.getParameter("semester"));


        int i = 0;
        for(Map.Entry e : allSelections.entrySet()){
            if(!e.getValue().equals("")){
                switch (i){
                    //MusicPreferences
                    case KEY_MUSIC_PREF:
                        userPreference.setMusic(MusicPreferencesEnum.valueOf(e.getValue().toString()));
                        neueTermine.addAll(loadTermine(i));
                        break;

                    //GenderPreferences
                    case KEY_GENDER_PREF:
                        userPreference.setGender(GenderEnum.valueOf(e.getValue().toString()));
                        neueTermine.addAll(loadTermine(i));
                        break;

                    //CoursePreferences
                    case KEY_COURSE_PREF:
                        userPreference.setCourse(CourseEnum.valueOf(e.getValue().toString()));
                        neueTermine.addAll(loadTermine(i));
                        break;

                    //ProvincePreferences
                    case KEY_PROVINCE_PREF:
                        userPreference.setProvince(ProvinceEnum.valueOf(e.getValue().toString()));
                        neueTermine.addAll(loadTermine(i));
                        break;

                    //UniversityPreferences
                    case KEY_UNIVERSITY_PREF:
                        userPreference.setUniversity(UniversityEnum.valueOf(e.getValue().toString()));
                        neueTermine.addAll(loadTermine(i));
                        break;

                    //AgePreferences
                    case KEY_AGE_PREF:
                        userPreference.setAge(e.getValue().toString());
                        neueTermine.addAll(loadTermine(i));
                        break;

                    case KEY_SEMESTER_PREF:
                        userPreference.setSemester(e.getValue().toString());
                        neueTermine.addAll(loadTermine(i));
                        break;
                }
            }
            i++;
        }

        //Alle Termine, die noch nicht im aktuellen Kalender vorhanden sind, werden nun hinzugefügt.
        boolean found = false;
        if(neueTermine != null){
            for(Termin tNeu : neueTermine){
                for(Termin tAlt : alteTermine){
                    if( (tNeu.getName().equals(tAlt.getName())) && (tNeu.getStart().equals(tAlt.getStart())) &&
                            (tNeu.getEnd().equals(tAlt.getEnd()))){
                        found = true;
                    }
                }
                if(!found){
                    terminDao.addTermin(tNeu);
                }else{
                    found = false;
                }
            }
        }




        return "redictDashboard";
    }

    /**
     * Beinhaltet alle Termine, die durch den Fragekatalog erzeugt werden können.
     * @param i Key der Konstanten, siehe oben
     * @return eine Liste aller Termine der Preferenz <b>i</b>
     */
    private List<Termin> loadTermine(int i){
        Termin temp;
        List<Termin> neueTermine = new LinkedList<Termin>();
        Calendar calendar = new GregorianCalendar();
        int aktuellesJahr = calendar.get(Calendar.YEAR);

        //Nur für den Test
        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        switch (i) {
            //MusicPreferences
            case KEY_MUSIC_PREF:
                MusicPreferencesEnum tempMusic = userPreference.getMusicPreferencesEnum();
                switch (tempMusic) {
                    case EDM:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Deichbrand Festival", df.format(c.getTime())/*"2018-07-19"*/, df.format(c.getTime())/*"2018-07-22"*/, true,
                                        "10:00", "22:00");
                                neueTermine.add(temp);

                            case 2019:
                                temp = new Termin("Deichbrand Festival", df.format(c.getTime())/*"2019-07-19"*/, df.format(c.getTime())/*"2019-07-22"*/, true,
                                        "11:00", "23:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Pop:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Haldern Pop", "2018-08-09", "2018-08-11", true,
                                        "13:00", "21:30");
                                neueTermine.add(temp);

                            case 2019:
                                temp = new Termin("Haldern Pop", "2019-08-09", "2019-08-11", true,
                                        "14:00", "22:30");
                                neueTermine.add(temp);
                        }
                        break;

                    case RnB:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Splash! Festival", "2018-07-06", "2018-07-08", true,
                                        "10:00", "22:00");
                                neueTermine.add(temp);

                            case 2019:
                                temp = new Termin("Splash! Festival", "2019-07-06", "2019-07-08", true,
                                        "11:00", "23:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Rock:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Rock am Ring", "2018-06-01", "2018-06-03", true,
                                        "10:00", "22:00");
                                neueTermine.add(temp);

                            case 2019:
                                temp = new Termin("Rock am Ring", "2019-06-01", "2019-06-03", true,
                                        "11:00", "23:00");
                                neueTermine.add(temp);
                        }
                        break;
                }
                break;

            //GenderPreferences
            case KEY_GENDER_PREF:
                GenderEnum tempGender = userPreference.getGenderEnum();
                switch (tempGender) {
                    case Female:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Weltfrauentag", "2018-03-08", "2018-03-08", true,
                                        "01:00", "22:59");
                                neueTermine.add(temp);
                            case 2019:
                                temp = new Termin("Weltfrauentag", "2019-03-08", "2019-03-08", true,
                                        "02:00", "23:59");
                                neueTermine.add(temp);
                        }
                        break;

                    case Male:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Herrentag", "2018-05-30", "2018-05-30", true,
                                        "01:00", "22:59");
                                neueTermine.add(temp);
                            case 2019:
                                temp = new Termin("Herrentag", "2019-05-30", "2019-05-30", true,
                                        "02:00", "23:59");
                                neueTermine.add(temp);
                        }
                        break;

                    case Intersexuell:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Welttag der Intersexualität", "2018-08-26", "2018-08-26", true,
                                        "01:00", "23:59");
                                neueTermine.add(temp);
                        }
                        break;
                }
                break;

            //CoursePreferences
            case KEY_COURSE_PREF:
                CourseEnum tempCourse = userPreference.getCourseEnum();
                switch (tempCourse) {
                    case Informatik_I:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Informatik_I", "2018-06-11", "2018-06-11", false,
                                        "08:15", "09:45");
                                neueTermine.add(temp);
                        }
                        break;

                    case Informatik_II:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Informatik_II", "2018-06-11", "2018-06-11", false,
                                        "10:00", "11:30");
                                neueTermine.add(temp);
                        }
                        break;

                    case Softwaretechnik_I:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Softwaretechnik_I", "2018-06-11", "2018-06-11", false,
                                        "12:00", "13:30");
                                neueTermine.add(temp);
                        }
                        break;

                    case Softwaretechnik_II:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Softwaretechnik_II", "2018-06-11", "2018-06-11", false,
                                        "14:30", "16:00");
                                neueTermine.add(temp);
                        }
                        break;
                }
                break;

            //ProvincePreferences
            case KEY_PROVINCE_PREF:
                ProvinceEnum tempProvince = userPreference.getProvince();
                switch (tempProvince) {
                    case Schleswig_Holstein:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Schleswig Holstein", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Hansestadt_Hamburg:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Hansestadt Hamburg", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Mecklenburg_Vorpommern:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Mecklenburg Vorpommern", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;
                }
                break;

            //UniversityPreferences
            case KEY_UNIVERSITY_PREF:
                UniversityEnum tempUniversity = userPreference.getUniversityEnum();
                switch (tempUniversity) {
                    case FH_Luebeck:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("FH Luebeck", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Uni_Luebeck:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Uni Luebeck", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case FH_Hamburg:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("FH Hamburg", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case Uni_Hamburg:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("Uni Hamburg", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;

                    case FH_Wismar:
                        switch (aktuellesJahr) {
                            case 2018:
                                temp = new Termin("FH Wismar", "2018-08-02", "2018-08-02", false,
                                        "07:00", "08:00");
                                neueTermine.add(temp);
                        }
                        break;
                }
                break;

            //AgePreferences
            case KEY_AGE_PREF:
                String tempAge = userPreference.getAge();
                if(tempAge.equals("unter 18")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("unter 18", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                }else if(tempAge.equals("unter 30")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("unter 30", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempAge.equals("über 30")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("über 30", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                }
                break;

            //SemesterPreferences
            case KEY_SEMESTER_PREF:
                String tempSemester = userPreference.getSemester();
                if(tempSemester.equals("1")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("1. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("2")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("2. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("3")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("3. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("4")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("4. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("5")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("5. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("6")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("6. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("7")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("7. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                }  else if(tempSemester.equals("8")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("8. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals("9")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("9. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                }  else if(tempSemester.equals("10")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("10. Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                } else if(tempSemester.equals(">10")){
                    switch (aktuellesJahr) {
                        case 2018:
                            temp = new Termin("(10+) Semester", "2018-08-02", "2018-08-02", false,
                                    "07:00", "08:00");
                            neueTermine.add(temp);
                    }
                }
        }


        return neueTermine;
    }

    /**
     * Beinhaltet die Alters- und Semesterpreferenz
     * @param key Key der Konstanten, siehe oben
     * @return eine Liste der unterschiedlichen Unterteilungen innerhalb einer Preferenz
     */
    private List<String> loadOtherPreferences(int key){
        switch (key){
            case 5:
                List<String> altersgruppen = new LinkedList<String>();
                altersgruppen.add("unter 18");
                altersgruppen.add("unter 30");
                altersgruppen.add("über 30");
                return altersgruppen;
            case 6:
                List<String> semester = new LinkedList<String>();
                semester.add("1");
                semester.add("2");
                semester.add("3");
                semester.add("4");
                semester.add("5");
                semester.add("6");
                semester.add("7");
                semester.add("8");
                semester.add("9");
                semester.add("10");
                semester.add(">10");
                return semester;
        }
        return null;
    }

}
