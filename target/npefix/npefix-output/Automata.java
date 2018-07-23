

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
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 34, 676, 1417);
        try {
            CallChecker.varInit(this, "this", 34, 676, 1417);
            CallChecker.varInit(this.menu, "menu", 34, 676, 1417);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 34, 676, 1417);
            CallChecker.varInit(this.choice, "choice", 34, 676, 1417);
            CallChecker.varInit(this.state, "state", 34, 676, 1417);
            CallChecker.varInit(this.cash, "cash", 34, 676, 1417);
            final String PATH = CallChecker.varInit("Java-lab-2\\target\\classes\\main_menu.json", "PATH", 35, 711, 776);
            JSONParser parser = CallChecker.varInit(new JSONParser(), "parser", 36, 786, 822);
            TryContext _bcornu_try_context_1 = new TryContext(1, Automata.class, "java.lang.Exception");
            try {
                JSONArray jsonArray = CallChecker.init(JSONArray.class);
                if (CallChecker.beforeDeref(parser, JSONParser.class, 38, 884, 889)) {
                    parser = CallChecker.beforeCalled(parser, JSONParser.class, 38, 884, 889);
                    jsonArray = ((JSONArray) (CallChecker.isCalled(parser, JSONParser.class, 38, 884, 889).parse(new FileReader(PATH))));
                    CallChecker.varAssign(jsonArray, "jsonArray", 38, 884, 889);
                }
                JSONObject jsonObject = CallChecker.init(JSONObject.class);
                Iterator<JSONObject> iterator = CallChecker.init(Iterator.class);
                if (CallChecker.beforeDeref(jsonArray, JSONArray.class, 40, 999, 1007)) {
                    jsonArray = CallChecker.beforeCalled(jsonArray, JSONArray.class, 40, 999, 1007);
                    iterator = CallChecker.isCalled(jsonArray, JSONArray.class, 40, 999, 1007).iterator();
                    CallChecker.varAssign(iterator, "iterator", 40, 999, 1007);
                }
                iterator = CallChecker.beforeCalled(iterator, Iterator.class, 41, 1039, 1046);
                while (CallChecker.isCalled(iterator, Iterator.class, 41, 1039, 1046).hasNext()) {
                    if (CallChecker.beforeDeref(iterator, Iterator.class, 42, 1090, 1097)) {
                        iterator = CallChecker.beforeCalled(iterator, Iterator.class, 42, 1090, 1097);
                        jsonObject = CallChecker.isCalled(iterator, Iterator.class, 42, 1090, 1097).next();
                        CallChecker.varAssign(jsonObject, "jsonObject", 42, 1077, 1105);
                    }
                    if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 43, 1140, 1149)) {
                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 43, 1140, 1149);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 43, 1140, 1149).get("caption"), Object.class, 43, 1140, 1164)) {
                            if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 44, 1221, 1230)) {
                                jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 44, 1221, 1230);
                                if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 44, 1221, 1230).get("price"), Object.class, 44, 1221, 1243)) {
                                    if (CallChecker.beforeDeref(Automata.jsonParseMap, HashMap.class, 43, 1123, 1134)) {
                                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 43, 1140, 1149);
                                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 44, 1221, 1230);
                                        CallChecker.isCalled(Automata.jsonParseMap, HashMap.class, 43, 1123, 1134).put(CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 43, 1140, 1149).get("caption"), Object.class, 43, 1140, 1164).toString(), Double.parseDouble(CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 44, 1221, 1230).get("price"), Object.class, 44, 1221, 1243).toString()));
                                    }
                                }
                            }
                        }
                    }
                } 
                if (CallChecker.beforeDeref(Automata.jsonParseMap, HashMap.class, 46, 1292, 1303)) {
                    menu = CallChecker.isCalled(Automata.jsonParseMap, HashMap.class, 46, 1292, 1303).entrySet();
                    CallChecker.varAssign(this.menu, "this.menu", 46, 1285, 1315);
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
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 53, 1424, 1532);
        try {
            CallChecker.varInit(this, "this", 53, 1424, 1532);
            CallChecker.varInit(this.menu, "menu", 53, 1424, 1532);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 53, 1424, 1532);
            CallChecker.varInit(this.choice, "choice", 53, 1424, 1532);
            CallChecker.varInit(this.state, "state", 53, 1424, 1532);
            CallChecker.varInit(this.cash, "cash", 53, 1424, 1532);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 54, 1466, 1485);
            if (CallChecker.beforeDeref(System.out, PrintStream.class, 55, 1495, 1504)) {
                CallChecker.isCalled(System.out, PrintStream.class, 55, 1495, 1504).println(printMenu());
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    public void off() {
        MethodContext _bcornu_methode_context3 = new MethodContext(void.class, 59, 1539, 1607);
        try {
            CallChecker.varInit(this, "this", 59, 1539, 1607);
            CallChecker.varInit(this.menu, "menu", 59, 1539, 1607);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 59, 1539, 1607);
            CallChecker.varInit(this.choice, "choice", 59, 1539, 1607);
            CallChecker.varInit(this.state, "state", 59, 1539, 1607);
            CallChecker.varInit(this.cash, "cash", 59, 1539, 1607);
            state = OFF;
            CallChecker.varAssign(this.state, "this.state", 60, 1583, 1601);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    public void coin(double cash) throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context4 = new MethodContext(void.class, 64, 1614, 1993);
        try {
            CallChecker.varInit(this, "this", 64, 1614, 1993);
            CallChecker.varInit(cash, "cash", 64, 1614, 1993);
            CallChecker.varInit(this.menu, "menu", 64, 1614, 1993);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 64, 1614, 1993);
            CallChecker.varInit(this.choice, "choice", 64, 1614, 1993);
            CallChecker.varInit(this.state, "state", 64, 1614, 1993);
            CallChecker.varInit(this.cash, "cash", 64, 1614, 1993);
            if (CallChecker.beforeDeref(state, STATES.class, 65, 1712, 1716)) {
                state = CallChecker.beforeCalled(state, STATES.class, 65, 1712, 1716);
                if ((!(CallChecker.isCalled(state, STATES.class, 65, 1712, 1716).equals(OFF))) && (cash != 0)) {
                    state = ACCEPT;
                    CallChecker.varAssign(this.state, "this.state", 66, 1765, 1786);
                    this.cash += (Math.rint((cash * 100))) / 100;
                    CallChecker.varAssign(this.cash, "this.cash", 67, 1800, 1840);
                    if (CallChecker.beforeDeref(System.out, PrintStream.class, 68, 1854, 1863)) {
                        CallChecker.isCalled(System.out, PrintStream.class, 68, 1854, 1863).println((("Your cash: " + (getCash())) + "$"));
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
        MethodContext _bcornu_methode_context5 = new MethodContext(String.class, 74, 2000, 2479);
        try {
            CallChecker.varInit(this, "this", 74, 2000, 2479);
            CallChecker.varInit(this.menu, "menu", 74, 2000, 2479);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 74, 2000, 2479);
            CallChecker.varInit(this.choice, "choice", 74, 2000, 2479);
            CallChecker.varInit(this.state, "state", 74, 2000, 2479);
            CallChecker.varInit(this.cash, "cash", 74, 2000, 2479);
            if (CallChecker.beforeDeref(state, STATES.class, 75, 2039, 2043)) {
                state = CallChecker.beforeCalled(state, STATES.class, 75, 2039, 2043);
                if (CallChecker.isCalled(state, STATES.class, 75, 2039, 2043).equals(WAIT)) {
                    parseMenu();
                    StringBuilder sb = CallChecker.varInit(new StringBuilder(), "sb", 77, 2105, 2143);
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 78, 2157, 2158)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 78, 2157, 2158);
                        CallChecker.isCalled(sb, StringBuilder.class, 78, 2157, 2158).append("----MENU----");
                    }
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 79, 2196, 2197)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 79, 2196, 2197);
                        CallChecker.isCalled(sb, StringBuilder.class, 79, 2196, 2197).append('\n');
                    }
                    if (CallChecker.beforeDeref(menu, String.class, 80, 2258, 2261)) {
                        for (Map.Entry<String, Double> arr : menu) {
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 81, 2292, 2294)) {
                                if (CallChecker.beforeDeref(sb, StringBuilder.class, 81, 2282, 2283)) {
                                    sb = CallChecker.beforeCalled(sb, StringBuilder.class, 81, 2282, 2283);
                                    CallChecker.isCalled(sb, StringBuilder.class, 81, 2282, 2283).append(((CallChecker.isCalled(arr, Map.Entry.class, 81, 2292, 2294).getKey()) + ": "));
                                }
                            }
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 82, 2340, 2342)) {
                                if (CallChecker.beforeDeref(sb, StringBuilder.class, 82, 2330, 2331)) {
                                    sb = CallChecker.beforeCalled(sb, StringBuilder.class, 82, 2330, 2331);
                                    CallChecker.isCalled(sb, StringBuilder.class, 82, 2330, 2331).append(((CallChecker.isCalled(arr, Map.Entry.class, 82, 2340, 2342).getValue()) + "$\n"));
                                }
                            }
                        }
                    }
                    if (CallChecker.beforeDeref(sb, StringBuilder.class, 85, 2399, 2400)) {
                        sb = CallChecker.beforeCalled(sb, StringBuilder.class, 85, 2399, 2400);
                        return CallChecker.isCalled(sb, StringBuilder.class, 85, 2399, 2400).toString();
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
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 90, 2486, 3172);
        try {
            CallChecker.varInit(this, "this", 90, 2486, 3172);
            CallChecker.varInit(s, "s", 90, 2486, 3172);
            CallChecker.varInit(this.menu, "menu", 90, 2486, 3172);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 90, 2486, 3172);
            CallChecker.varInit(this.choice, "choice", 90, 2486, 3172);
            CallChecker.varInit(this.state, "state", 90, 2486, 3172);
            CallChecker.varInit(this.cash, "cash", 90, 2486, 3172);
            final String PATH = CallChecker.varInit("Java-lab-2\\target\\classes\\main_menu.json", "PATH", 91, 2534, 2599);
            JSONParser parser = CallChecker.varInit(new JSONParser(), "parser", 92, 2609, 2645);
            TryContext _bcornu_try_context_2 = new TryContext(2, Automata.class, "java.lang.Exception");
            try {
                JSONArray jsonArray = CallChecker.init(JSONArray.class);
                if (CallChecker.beforeDeref(parser, JSONParser.class, 94, 2707, 2712)) {
                    parser = CallChecker.beforeCalled(parser, JSONParser.class, 94, 2707, 2712);
                    jsonArray = ((JSONArray) (CallChecker.isCalled(parser, JSONParser.class, 94, 2707, 2712).parse(new FileReader(PATH))));
                    CallChecker.varAssign(jsonArray, "jsonArray", 94, 2707, 2712);
                }
                JSONObject jsonObject = CallChecker.init(JSONObject.class);
                Iterator<JSONObject> iterator = CallChecker.init(Iterator.class);
                if (CallChecker.beforeDeref(jsonArray, JSONArray.class, 96, 2822, 2830)) {
                    jsonArray = CallChecker.beforeCalled(jsonArray, JSONArray.class, 96, 2822, 2830);
                    iterator = CallChecker.isCalled(jsonArray, JSONArray.class, 96, 2822, 2830).iterator();
                    CallChecker.varAssign(iterator, "iterator", 96, 2822, 2830);
                }
                iterator = CallChecker.beforeCalled(iterator, Iterator.class, 97, 2862, 2869);
                while (CallChecker.isCalled(iterator, Iterator.class, 97, 2862, 2869).hasNext()) {
                    if (CallChecker.beforeDeref(iterator, Iterator.class, 98, 2913, 2920)) {
                        iterator = CallChecker.beforeCalled(iterator, Iterator.class, 98, 2913, 2920);
                        jsonObject = CallChecker.isCalled(iterator, Iterator.class, 98, 2913, 2920).next();
                        CallChecker.varAssign(jsonObject, "jsonObject", 98, 2900, 2928);
                    }
                    if (CallChecker.beforeDeref(jsonObject, JSONObject.class, 99, 2949, 2958)) {
                        jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 99, 2949, 2958);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(jsonObject, JSONObject.class, 99, 2949, 2958).get("caption"), Object.class, 99, 2949, 2973)) {
                            jsonObject = CallChecker.beforeCalled(jsonObject, JSONObject.class, 99, 2949, 2958);
                            if (CallChecker.isCalled(CallChecker.isCalled(jsonObject, JSONObject.class, 99, 2949, 2958).get("caption"), Object.class, 99, 2949, 2973).equals(s)) {
                                if (CallChecker.beforeDeref(System.out, PrintStream.class, 100, 3008, 3017)) {
                                    CallChecker.isCalled(System.out, PrintStream.class, 100, 3008, 3017).println(jsonObject);
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
        MethodContext _bcornu_methode_context7 = new MethodContext(STATES.class, 108, 3179, 3234);
        try {
            CallChecker.varInit(this, "this", 108, 3179, 3234);
            CallChecker.varInit(this.menu, "menu", 108, 3179, 3234);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 108, 3179, 3234);
            CallChecker.varInit(this.choice, "choice", 108, 3179, 3234);
            CallChecker.varInit(this.state, "state", 108, 3179, 3234);
            CallChecker.varInit(this.cash, "cash", 108, 3179, 3234);
            return state;
        } catch (ForceReturn _bcornu_return_t) {
            return ((STATES) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    public String choice(String choice) throws UnsupportedOperationException {
        MethodContext _bcornu_methode_context8 = new MethodContext(String.class, 112, 3241, 3779);
        try {
            CallChecker.varInit(this, "this", 112, 3241, 3779);
            CallChecker.varInit(choice, "choice", 112, 3241, 3779);
            CallChecker.varInit(this.menu, "menu", 112, 3241, 3779);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 112, 3241, 3779);
            CallChecker.varInit(this.choice, "choice", 112, 3241, 3779);
            CallChecker.varInit(this.state, "state", 112, 3241, 3779);
            CallChecker.varInit(this.cash, "cash", 112, 3241, 3779);
            state = CHECK;
            CallChecker.varAssign(this.state, "this.state", 113, 3324, 3344);
            if (CallChecker.beforeDeref(menu, String.class, 114, 3387, 3390)) {
                for (Map.Entry<String, Double> arr : menu) {
                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 115, 3411, 3413)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 115, 3411, 3413).getKey(), String.class, 115, 3411, 3422)) {
                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 115, 3448, 3450)) {
                                if ((CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 115, 3411, 3413).getKey(), String.class, 115, 3411, 3422).equals(choice)) && (check(CallChecker.isCalled(arr, Map.Entry.class, 115, 3448, 3450).getValue()))) {
                                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 116, 3497, 3499)) {
                                        this.choice = CallChecker.isCalled(arr, Map.Entry.class, 116, 3497, 3499).getKey();
                                        CallChecker.varAssign(this.choice, "this.choice", 116, 3483, 3509);
                                    }
                                }else
                                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 117, 3533, 3535)) {
                                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 117, 3533, 3535).getKey(), String.class, 117, 3533, 3544)) {
                                            if (CallChecker.beforeDeref(arr, Map.Entry.class, 117, 3572, 3574)) {
                                                if ((CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 117, 3533, 3535).getKey(), String.class, 117, 3533, 3544).equals(choice)) && (!(check(CallChecker.isCalled(arr, Map.Entry.class, 117, 3572, 3574).getValue())))) {
                                                    this.choice = null;
                                                    CallChecker.varAssign(this.choice, "this.choice", 118, 3608, 3626);
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
        MethodContext _bcornu_methode_context9 = new MethodContext(boolean.class, 125, 3786, 3928);
        try {
            CallChecker.varInit(this, "this", 125, 3786, 3928);
            CallChecker.varInit(price, "price", 125, 3786, 3928);
            CallChecker.varInit(this.menu, "menu", 125, 3786, 3928);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 125, 3786, 3928);
            CallChecker.varInit(this.choice, "choice", 125, 3786, 3928);
            CallChecker.varInit(this.state, "state", 125, 3786, 3928);
            CallChecker.varInit(this.cash, "cash", 125, 3786, 3928);
            this.state = CHECK;
            CallChecker.varAssign(this.state, "this.state", 126, 3832, 3857);
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
        MethodContext _bcornu_methode_context10 = new MethodContext(double.class, 131, 3935, 4133);
        try {
            CallChecker.varInit(this, "this", 131, 3935, 4133);
            CallChecker.varInit(this.menu, "menu", 131, 3935, 4133);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 131, 3935, 4133);
            CallChecker.varInit(this.choice, "choice", 131, 3935, 4133);
            CallChecker.varInit(this.state, "state", 131, 3935, 4133);
            CallChecker.varInit(this.cash, "cash", 131, 3935, 4133);
            if (CallChecker.beforeDeref(state, STATES.class, 132, 4010, 4014)) {
                state = CallChecker.beforeCalled(state, STATES.class, 132, 4010, 4014);
                if (!(CallChecker.isCalled(state, STATES.class, 132, 4010, 4014).equals(OFF)))
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
        MethodContext _bcornu_methode_context11 = new MethodContext(double.class, 138, 4140, 4296);
        try {
            CallChecker.varInit(this, "this", 138, 4140, 4296);
            CallChecker.varInit(this.menu, "menu", 138, 4140, 4296);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 138, 4140, 4296);
            CallChecker.varInit(this.choice, "choice", 138, 4140, 4296);
            CallChecker.varInit(this.state, "state", 138, 4140, 4296);
            CallChecker.varInit(this.cash, "cash", 138, 4140, 4296);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 139, 4173, 4192);
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
        MethodContext _bcornu_methode_context12 = new MethodContext(String.class, 147, 4303, 4920);
        try {
            CallChecker.varInit(this, "this", 147, 4303, 4920);
            CallChecker.varInit(this.menu, "menu", 147, 4303, 4920);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 147, 4303, 4920);
            CallChecker.varInit(this.choice, "choice", 147, 4303, 4920);
            CallChecker.varInit(this.state, "state", 147, 4303, 4920);
            CallChecker.varInit(this.cash, "cash", 147, 4303, 4920);
            state = COOK;
            CallChecker.varAssign(this.state, "this.state", 148, 4371, 4390);
            String item = CallChecker.varInit("", "item", 149, 4400, 4416);
            if (CallChecker.beforeDeref(menu, String.class, 150, 4459, 4462)) {
                for (Map.Entry<String, Double> arr : menu) {
                    if (CallChecker.beforeDeref(arr, Map.Entry.class, 151, 4483, 4485)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(arr, Map.Entry.class, 151, 4483, 4485).getKey(), String.class, 151, 4483, 4494)) {
                            if (CallChecker.isCalled(CallChecker.isCalled(arr, Map.Entry.class, 151, 4483, 4485).getKey(), String.class, 151, 4483, 4494).equals(choice)) {
                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 152, 4537, 4539)) {
                                    item = CallChecker.isCalled(arr, Map.Entry.class, 152, 4537, 4539).getKey();
                                    CallChecker.varAssign(item, "item", 152, 4530, 4549);
                                }
                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 153, 4577, 4579)) {
                                    if (check(CallChecker.isCalled(arr, Map.Entry.class, 153, 4577, 4579).getValue())) {
                                        if (CallChecker.beforeDeref(arr, Map.Entry.class, 154, 4624, 4626)) {
                                            if (CallChecker.beforeDeref(((Double) (CallChecker.isCalled(arr, Map.Entry.class, 154, 4624, 4626).getValue())), double.class, 154, 4624, 4637)) {
                                                if (CallChecker.beforeDeref(arr, Map.Entry.class, 154, 4624, 4626)) {
                                                    cash -= CallChecker.isCalled(((Double) (CallChecker.isCalled(arr, Map.Entry.class, 154, 4624, 4626).getValue())), double.class, 154, 4624, 4637);
                                                    CallChecker.varAssign(this.cash, "this.cash", 154, 4616, 4638);
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
            CallChecker.varAssign(this.cash, "this.cash", 160, 4778, 4816);
            if (CallChecker.beforeDeref(System.out, PrintStream.class, 161, 4826, 4835)) {
                CallChecker.isCalled(System.out, PrintStream.class, 161, 4826, 4835).println((("Remains: " + (getCash())) + "$"));
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
        MethodContext _bcornu_methode_context13 = new MethodContext(void.class, 166, 4927, 4983);
        try {
            CallChecker.varInit(this, "this", 166, 4927, 4983);
            CallChecker.varInit(this.menu, "menu", 166, 4927, 4983);
            CallChecker.varInit(Automata.jsonParseMap, "Automata.jsonParseMap", 166, 4927, 4983);
            CallChecker.varInit(this.choice, "choice", 166, 4927, 4983);
            CallChecker.varInit(this.state, "state", 166, 4927, 4983);
            CallChecker.varInit(this.cash, "cash", 166, 4927, 4983);
            state = WAIT;
            CallChecker.varAssign(this.state, "this.state", 167, 4958, 4977);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context13.methodEnd();
        }
    }
}

