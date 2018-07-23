

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.context.TryContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static STATES.ACCEPT;
import static STATES.CHECK;
import static STATES.COOK;
import static STATES.OFF;
import static STATES.WAIT;


class Automata {
    private double cash;

    private STATES state;

    private String choice;

    private static HashMap<String, Double> jsonParseMap;

    private Set<Map.Entry<String, Double>> menu;

    static {
        Automata.jsonParseMap = new HashMap<>();
        CallChecker.varAssign(Automata.jsonParseMap, "Automata.jsonParseMap", 27, 574, 604);
    }

    public Automata() {
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(Automata.class, 30, 617, 669);
        try {
            state = OFF;
            CallChecker.varAssign(this.state, "this.state", 31, 645, 663);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    private void parseMenu() {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 34, 676, 1422);
        try {
            CallChecker.varInit(this, "this", 34, 676, 1422);
            CallChecker.varInit(this.menu, "menu", 34, 676, 1422);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 34, 676, 1422);
            CallChecker.varInit(this.choice, "choice", 34, 676, 1422);
            CallChecker.varInit(this.state, "state", 34, 676, 1422);
            CallChecker.varInit(this.cash, "cash", 34, 676, 1422);
            final String PATH = CallChecker.varInit("Java-lab-2\\src\\main\\resources\\main_menu.json", "PATH", 35, 711, 781);
            JSONParser parser = CallChecker.varInit(new JSONParser(), "parser", 36, 791, 827);
            TryContext _bcornu_try_context_1 = new TryContext(1, Automata.class, "java.lang.Exception");
            try {
                JSONArray jsonArray = CallChecker.init(JSONArray.class);
                if (CallChecker.beforeDeref(parser, JSONParser.class, 38, 889, 894)) {
                    parser = CallChecker.beforeCalled(parser, JSONParser.class, 38, 889, 894);
                    jsonArray = ((JSONArray) (CallChecker.isCalled(parser, JSONParser.class, 38, 889, 894).parse(new FileReader(PATH))));
                    CallChecker.varAssign(jsonArray, "jsonArray", 38, 889, 894);
                }
                JSONObject jsonObject = CallChecker.init(JSONObject.class);
                Iterator<JSONObject> iterator = CallChecker.init(Iterator.class);
                if (CallChecker.beforeDeref(jsonArray, JSONArray.class, 40, 1004, 1012)) {
                    jsonArray = CallChecker.beforeCalled(jsonArray, JSONArray.class, 40, 1004, 1012);
                    iterator = CallChecker.isCalled(jsonArray, JSONArray.class, 40, 1004, 1012).iterator();
                    CallChecker.varAssign(iterator, "iterator", 40, 1004, 1012);
                }
                iterator = CallChecker.beforeCalled(iterator, Iterator.class, 41, 1044, 1051);
                while (CallChecker.isCalled(iterator, Iterator.class, 41, 1044, 1051).hasNext()) {
                    if (CallChecker.beforeDeref(iterator, Iterator.class, 42, 1095, 1102)) {
                        iterator = CallChecker.beforeCalled(iterator, Iterator.class, 42, 1095, 1102);
                        jsonObject = CallChecker.isCalled(iterator, Iterator.class, 42, 1095, 1102).next();
                        CallChecker.varAssign(jsonObject, "jsonObject", 42, 1082, 1110);
                    }
                    if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 43, 1145, 1154)) {
                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 43, 1145, 1154);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 43, 1145, 1154).get("caption"), Object.class, 43, 1145, 1169)) {
                            if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 44, 1226, 1235)) {
                                jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 44, 1226, 1235);
                                if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 44, 1226, 1235).get("price"), Object.class, 44, 1226, 1248)) {
                                    if (CallChecker.beforeDeref(Automata.jsonParseMap, HashMap.class, 43, 1128, 1139)) {
                                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 43, 1145, 1154);
                                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 44, 1226, 1235);
                                        CallChecker.isCalled(Automata.jsonParseMap, HashMap.class, 43, 1128, 1139).put(CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 43, 1145, 1154).get("caption"), Object.class, 43, 1145, 1169).toString(), Double.parseDouble(CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 44, 1226, 1235).get("price"), Object.class, 44, 1226, 1248).toString()));
                                    }
                                }
                            }
                        }
                    }
                } 
                if (CallChecker.beforeDeref(Automata.jsonParseMap, HashMap.class, 46, 1297, 1308)) {
                    menu = CallChecker.isCalled(Automata.jsonParseMap, HashMap.class, 46, 1297, 1308).entrySet();
                    CallChecker.varAssign(this.menu, "this.menu", 46, 1290, 1320);
                }
            } catch (IOException | ParseException ex) {
                _bcornu_try_context_1.catchStart(1);
                ex.printStackTrace();
            } finally {
                _bcornu_try_context_1.finallyStart(1);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public void on() {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 53, 1429, 1537);
        try {
            CallChecker.varInit(this, "this", 53, 1429, 1537);
            CallChecker.varInit(this.menu, "menu", 53, 1429, 1537);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 53, 1429, 1537);
            CallChecker.varInit(this.choice, "choice", 53, 1429, 1537);
            CallChecker.varInit(this.state, "state", 53, 1429, 1537);
            CallChecker.varInit(this.cash, "cash", 53, 1429, 1537);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 54, 1471, 1490);
            if (CallChecker.beforeDeref(System.out, PrintStream.class, 55, 1500, 1509)) {
                CallChecker.isCalled(System.out, PrintStream.class, 55, 1500, 1509).println(printMenu());
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    public void off() {
        MethodContext _bcornu_methode_context3 = new MethodContext(void.class, 59, 1544, 1612);
        try {
            CallChecker.varInit(this, "this", 59, 1544, 1612);
            CallChecker.varInit(this.menu, "menu", 59, 1544, 1612);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 59, 1544, 1612);
            CallChecker.varInit(this.choice, "choice", 59, 1544, 1612);
            CallChecker.varInit(this.state, "state", 59, 1544, 1612);
            CallChecker.varInit(this.cash, "cash", 59, 1544, 1612);
            state = OFF;
            CallChecker.varAssign(this.state, "this.state", 60, 1588, 1606);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    public void coin(double cash) throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context4 = new MethodContext(void.class, 64, 1619, 1998);
        try {
            CallChecker.varInit(this, "this", 64, 1619, 1998);
            CallChecker.varInit(cash, "cash", 64, 1619, 1998);
            CallChecker.varInit(this.menu, "menu", 64, 1619, 1998);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 64, 1619, 1998);
            CallChecker.varInit(this.choice, "choice", 64, 1619, 1998);
            CallChecker.varInit(this.state, "state", 64, 1619, 1998);
            CallChecker.varInit(this.cash, "cash", 64, 1619, 1998);
            if (CallChecker.beforeDeref(state, STATES.class, 65, 1717, 1721)) {
                state = CallChecker.beforeCalled(state, STATES.class, 65, 1717, 1721);
                if ((!(CallChecker.isCalled(state, STATES.class, 65, 1717, 1721).equals(OFF))) && (cash != 0)) {
                    state = ACCEPT;
                    CallChecker.varAssign(this.state, "this.state", 66, 1770, 1791);
                    this.cash += (Math.rint((cash * 100))) / 100;
                    CallChecker.varAssign(this.cash, "this.cash", 67, 1805, 1845);
                    if (CallChecker.beforeDeref(System.out, PrintStream.class, 68, 1859, 1868)) {
                        CallChecker.isCalled(System.out, PrintStream.class, 68, 1859, 1868).println((("Your cash: " + (getCash())) + "$"));
                    }
                }else {
                    throw new UnsupportedOperationException();
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    public String printMenu() {
        MethodContext _bcornu_methode_context5 = new MethodContext(String.class, 74, 2005, 2484);
        try {
            CallChecker.varInit(this, "this", 74, 2005, 2484);
            CallChecker.varInit(this.menu, "menu", 74, 2005, 2484);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 74, 2005, 2484);
            CallChecker.varInit(this.choice, "choice", 74, 2005, 2484);
            CallChecker.varInit(this.state, "state", 74, 2005, 2484);
            CallChecker.varInit(this.cash, "cash", 74, 2005, 2484);
            if (CallChecker.beforeDeref(state, STATES.class, 75, 2044, 2048)) {
                state = CallChecker.beforeCalled(state, STATES.class, 75, 2044, 2048);
                if (CallChecker.isCalled(state, STATES.class, 75, 2044, 2048).equals(WAIT)) {
                    parseMenu();
                    StringBuilder sb = CallChecker.varInit(new StringBuilder(), "sb", 77, 2110, 2148);
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 78, 2162, 2163)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 78, 2162, 2163);
                        CallChecker.isCalled(sb, StringBuilder.class, 78, 2162, 2163).append("----MENU----");
                    }
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 79, 2201, 2202)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 79, 2201, 2202);
                        CallChecker.isCalled(sb, StringBuilder.class, 79, 2201, 2202).append('\n');
                    }
                    if (CallChecker.beforeDeref(menu, String.class, 80, 2263, 2266)) {
                        for (Map.Entry<String, Double> arr : menu) {
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 81, 2297, 2299)) {
                                if (CallChecker.beforeDeref(sb, StringBuilder.class, 81, 2287, 2288)) {
                                    sb = CallChecker.beforeCalled(sb, StringBuilder.class, 81, 2287, 2288);
                                    CallChecker.isCalled(sb, StringBuilder.class, 81, 2287, 2288).append(((CallChecker.isCalled(arr, Map.Entry.class, 81, 2297, 2299).getKey()) + ": "));
                                }
                            }
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 82, 2345, 2347)) {
                                if (CallChecker.beforeDeref(sb, StringBuilder.class, 82, 2335, 2336)) {
                                    sb = CallChecker.beforeCalled(sb, StringBuilder.class, 82, 2335, 2336);
                                    CallChecker.isCalled(sb, StringBuilder.class, 82, 2335, 2336).append(((CallChecker.isCalled(arr, Map.Entry.class, 82, 2345, 2347).getValue()) + "$\n"));
                                }
                            }
                        }
                    }
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 85, 2404, 2405)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 85, 2404, 2405);
                        return CallChecker.isCalled(sb, StringBuilder.class, 85, 2404, 2405).toString();
                    }else
                        throw new AbnormalExecutionError();

                }
            }else
                throw new AbnormalExecutionError();

            throw new UnsupportedOperationException();
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    private void updateJsonMenu(String s) {
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 90, 2491, 3182);
        try {
            CallChecker.varInit(this, "this", 90, 2491, 3182);
            CallChecker.varInit(s, "s", 90, 2491, 3182);
            CallChecker.varInit(this.menu, "menu", 90, 2491, 3182);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 90, 2491, 3182);
            CallChecker.varInit(this.choice, "choice", 90, 2491, 3182);
            CallChecker.varInit(this.state, "state", 90, 2491, 3182);
            CallChecker.varInit(this.cash, "cash", 90, 2491, 3182);
            final String PATH = CallChecker.varInit("Java-lab-2\\src\\main\\resources\\main_menu.json", "PATH", 91, 2539, 2609);
            JSONParser parser = CallChecker.varInit(new JSONParser(), "parser", 92, 2619, 2655);
            TryContext _bcornu_try_context_2 = new TryContext(2, Automata.class, "java.lang.Exception");
            try {
                JSONArray jsonArray = CallChecker.init(JSONArray.class);
                if (CallChecker.beforeDeref(parser, JSONParser.class, 94, 2717, 2722)) {
                    parser = CallChecker.beforeCalled(parser, JSONParser.class, 94, 2717, 2722);
                    jsonArray = ((JSONArray) (CallChecker.isCalled(parser, JSONParser.class, 94, 2717, 2722).parse(new FileReader(PATH))));
                    CallChecker.varAssign(jsonArray, "jsonArray", 94, 2717, 2722);
                }
                JSONObject jsonObject = CallChecker.init(JSONObject.class);
                Iterator<JSONObject> iterator = CallChecker.init(Iterator.class);
                if (CallChecker.beforeDeref(jsonArray, JSONArray.class, 96, 2832, 2840)) {
                    jsonArray = CallChecker.beforeCalled(jsonArray, JSONArray.class, 96, 2832, 2840);
                    iterator = CallChecker.isCalled(jsonArray, JSONArray.class, 96, 2832, 2840).iterator();
                    CallChecker.varAssign(iterator, "iterator", 96, 2832, 2840);
                }
                iterator = CallChecker.beforeCalled(iterator, Iterator.class, 97, 2872, 2879);
                while (CallChecker.isCalled(iterator, Iterator.class, 97, 2872, 2879).hasNext()) {
                    if (CallChecker.beforeDeref(iterator, Iterator.class, 98, 2923, 2930)) {
                        iterator = CallChecker.beforeCalled(iterator, Iterator.class, 98, 2923, 2930);
                        jsonObject = CallChecker.isCalled(iterator, Iterator.class, 98, 2923, 2930).next();
                        CallChecker.varAssign(jsonObject, "jsonObject", 98, 2910, 2938);
                    }
                    if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 99, 2959, 2968)) {
                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 99, 2959, 2968);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 99, 2959, 2968).get("caption"), Object.class, 99, 2959, 2983)) {
                            jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 99, 2959, 2968);
                            if (CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 99, 2959, 2968).get("caption"), Object.class, 99, 2959, 2983).equals(s)) {
                                if (CallChecker.beforeDeref(System.out, PrintStream.class, 100, 3018, 3027)) {
                                    CallChecker.isCalled(System.out, PrintStream.class, 100, 3018, 3027).println(jsonObject);
                                }
                            }
                        }
                    }
                } 
            } catch (IOException | ParseException ex) {
                _bcornu_try_context_2.catchStart(2);
                ex.printStackTrace();
            } finally {
                _bcornu_try_context_2.finallyStart(2);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    public STATES printState() {
        MethodContext _bcornu_methode_context7 = new MethodContext(STATES.class, 108, 3189, 3244);
        try {
            CallChecker.varInit(this, "this", 108, 3189, 3244);
            CallChecker.varInit(this.menu, "menu", 108, 3189, 3244);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 108, 3189, 3244);
            CallChecker.varInit(this.choice, "choice", 108, 3189, 3244);
            CallChecker.varInit(this.state, "state", 108, 3189, 3244);
            CallChecker.varInit(this.cash, "cash", 108, 3189, 3244);
            return state;
        } catch (ForceReturn _bcornu_return_t) {
            return ((STATES) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    public String choice(String choice) throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context8 = new MethodContext(String.class, 112, 3251, 3789);
        try {
            CallChecker.varInit(this, "this", 112, 3251, 3789);
            CallChecker.varInit(choice, "choice", 112, 3251, 3789);
            CallChecker.varInit(this.menu, "menu", 112, 3251, 3789);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 112, 3251, 3789);
            CallChecker.varInit(this.choice, "choice", 112, 3251, 3789);
            CallChecker.varInit(this.state, "state", 112, 3251, 3789);
            CallChecker.varInit(this.cash, "cash", 112, 3251, 3789);
            state = CHECK;
            CallChecker.varAssign(this.state, "this.state", 113, 3334, 3354);
            if (CallChecker.beforeDeref(menu, String.class, 114, 3397, 3400)) {
                for (Map.Entry<String, Double> arr : menu) {
                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 115, 3421, 3423)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 115, 3421, 3423).getKey(), String.class, 115, 3421, 3432)) {
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 115, 3458, 3460)) {
                                if ((CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 115, 3421, 3423).getKey(), String.class, 115, 3421, 3432).equals(choice)) && (check(CallChecker.isCalled(arr, Map.Entry.class, 115, 3458, 3460).getValue()))) {
                                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 116, 3507, 3509)) {
                                        this.choice = CallChecker.isCalled(arr, Map.Entry.class, 116, 3507, 3509).getKey();
                                        CallChecker.varAssign(this.choice, "this.choice", 116, 3493, 3519);
                                    }
                                }else
                                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 117, 3543, 3545)) {
                                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 117, 3543, 3545).getKey(), String.class, 117, 3543, 3554)) {
                                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 117, 3582, 3584)) {
                                                if ((CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 117, 3543, 3545).getKey(), String.class, 117, 3543, 3554).equals(choice)) && (!(check(CallChecker.isCalled(arr, Map.Entry.class, 117, 3582, 3584).getValue())))) {
                                                    this.choice = null;
                                                    CallChecker.varAssign(this.choice, "this.choice", 118, 3618, 3636);
                                                    throw new UnsupportedOperationException("Not enough money for this product.");
                                                }
                                            }else
                                                throw new AbnormalExecutionError();

                                        }else
                                            throw new AbnormalExecutionError();

                                    }else
                                        throw new AbnormalExecutionError();


                            }else
                                throw new AbnormalExecutionError();

                        }else
                            throw new AbnormalExecutionError();

                    }else
                        throw new AbnormalExecutionError();

                }
            }
            return this.choice;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    private boolean check(double price) {
        MethodContext _bcornu_methode_context9 = new MethodContext(boolean.class, 125, 3796, 3938);
        try {
            CallChecker.varInit(this, "this", 125, 3796, 3938);
            CallChecker.varInit(price, "price", 125, 3796, 3938);
            CallChecker.varInit(this.menu, "menu", 125, 3796, 3938);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 125, 3796, 3938);
            CallChecker.varInit(this.choice, "choice", 125, 3796, 3938);
            CallChecker.varInit(this.state, "state", 125, 3796, 3938);
            CallChecker.varInit(this.cash, "cash", 125, 3796, 3938);
            this.state = CHECK;
            CallChecker.varAssign(this.state, "this.state", 126, 3842, 3867);
            if ((cash) < price)
                return false;
            else
                return true;

        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }

    public double getCash() throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context10 = new MethodContext(double.class, 131, 3945, 4143);
        try {
            CallChecker.varInit(this, "this", 131, 3945, 4143);
            CallChecker.varInit(this.menu, "menu", 131, 3945, 4143);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 131, 3945, 4143);
            CallChecker.varInit(this.choice, "choice", 131, 3945, 4143);
            CallChecker.varInit(this.state, "state", 131, 3945, 4143);
            CallChecker.varInit(this.cash, "cash", 131, 3945, 4143);
            if (CallChecker.beforeDeref(state, STATES.class, 132, 4020, 4024)) {
                state = CallChecker.beforeCalled(state, STATES.class, 132, 4020, 4024);
                if (!(CallChecker.isCalled(state, STATES.class, 132, 4020, 4024).equals(OFF)))
                    return cash;
                else
                    throw new UnsupportedOperationException();

            }else
                throw new AbnormalExecutionError();

        } catch (ForceReturn _bcornu_return_t) {
            return ((Double) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context10.methodEnd();
        }
    }

    public double cancel() {
        MethodContext _bcornu_methode_context11 = new MethodContext(double.class, 138, 4150, 4306);
        try {
            CallChecker.varInit(this, "this", 138, 4150, 4306);
            CallChecker.varInit(this.menu, "menu", 138, 4150, 4306);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 138, 4150, 4306);
            CallChecker.varInit(this.choice, "choice", 138, 4150, 4306);
            CallChecker.varInit(this.state, "state", 138, 4150, 4306);
            CallChecker.varInit(this.cash, "cash", 138, 4150, 4306);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 139, 4183, 4202);
            if ((cash) != 0) {
                return cash;
            }else {
                return 0;
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Double) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context11.methodEnd();
        }
    }

    public String cook() throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context12 = new MethodContext(String.class, 147, 4313, 4930);
        try {
            CallChecker.varInit(this, "this", 147, 4313, 4930);
            CallChecker.varInit(this.menu, "menu", 147, 4313, 4930);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 147, 4313, 4930);
            CallChecker.varInit(this.choice, "choice", 147, 4313, 4930);
            CallChecker.varInit(this.state, "state", 147, 4313, 4930);
            CallChecker.varInit(this.cash, "cash", 147, 4313, 4930);
            state = COOK;
            CallChecker.varAssign(this.state, "this.state", 148, 4381, 4400);
            String item = CallChecker.varInit("", "item", 149, 4410, 4426);
            if (CallChecker.beforeDeref(menu, String.class, 150, 4469, 4472)) {
                for (Map.Entry<String, Double> arr : menu) {
                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 151, 4493, 4495)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 151, 4493, 4495).getKey(), String.class, 151, 4493, 4504)) {
                            if (CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 151, 4493, 4495).getKey(), String.class, 151, 4493, 4504).equals(choice)) {
                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 152, 4547, 4549)) {
                                    item = CallChecker.isCalled(arr, Map.Entry.class, 152, 4547, 4549).getKey();
                                    CallChecker.varAssign(item, "item", 152, 4540, 4559);
                                }
                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 153, 4587, 4589)) {
                                    if (check(CallChecker.isCalled(arr, Map.Entry.class, 153, 4587, 4589).getValue())) {
                                        if (CallChecker.beforeDeref(arr, Map.Entry.class, 154, 4634, 4636)) {
                                            if (CallChecker.beforeDeref(((Double) (CallChecker.isCalled(arr, Map.Entry.class, 154, 4634, 4636).getValue())), double.class, 154, 4634, 4647)) {
                                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 154, 4634, 4636)) {
                                                    cash -= CallChecker.isCalled(((Double) (CallChecker.isCalled(arr, Map.Entry.class, 154, 4634, 4636).getValue())), double.class, 154, 4634, 4647);
                                                    CallChecker.varAssign(this.cash, "this.cash", 154, 4626, 4648);
                                                }
                                            }
                                        }
                                    }else {
                                        throw new UnsupportedOperationException();
                                    }
                                }else
                                    throw new AbnormalExecutionError();

                            }
                        }else
                            throw new AbnormalExecutionError();

                    }else
                        throw new AbnormalExecutionError();

                }
            }
            cash = (Math.rint((100.0 * (cash)))) / 100.0;
            CallChecker.varAssign(this.cash, "this.cash", 160, 4788, 4826);
            if (CallChecker.beforeDeref(System.out, PrintStream.class, 161, 4836, 4845)) {
                CallChecker.isCalled(System.out, PrintStream.class, 161, 4836, 4845).println((("Remains: " + (getCash())) + "$"));
            }
            finish();
            return item;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context12.methodEnd();
        }
    }

    public void finish() {
        MethodContext _bcornu_methode_context13 = new MethodContext(void.class, 166, 4937, 4993);
        try {
            CallChecker.varInit(this, "this", 166, 4937, 4993);
            CallChecker.varInit(this.menu, "menu", 166, 4937, 4993);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 166, 4937, 4993);
            CallChecker.varInit(this.choice, "choice", 166, 4937, 4993);
            CallChecker.varInit(this.state, "state", 166, 4937, 4993);
            CallChecker.varInit(this.cash, "cash", 166, 4937, 4993);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 167, 4968, 4987);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context13.methodEnd();
        }
    }
}

