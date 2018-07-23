import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

// Create enumeration statements of Automata
enum STATES {
    OFF, WAIT, ACCEPT, CHECK, COOK
}

class Automata {

    // Main field of Automata
    private double cash;
    private STATES state;

    private String choice;

    private static HashMap<String, Double> jsonParseMap;
    private Set<Entry<String, Double>> menu;

    static {
        jsonParseMap = new HashMap<>();
    }

    public Automata() {
        state = STATES.OFF;
    }

    private void parseMenu() {
        final String PATH = "Java-lab-2\\src\\main\\resources\\main_menu.json";
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(PATH));
            JSONObject jsonObject;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                jsonObject = iterator.next();
                jsonParseMap.put(jsonObject.get("caption").toString(),
                        Double.parseDouble(jsonObject.get("price").toString()));
            }
            menu = jsonParseMap.entrySet();
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    // turn on
    public void on() {
        state = STATES.WAIT;
        System.out.println(printMenu());
    }

    // turn off
    public void off() {
        state = STATES.OFF;
    }

    // add coins
    public void coin(double cash) throws UnsupportedOperationException {
        if(!state.equals(STATES.OFF) && cash != 0) {
            state = STATES.ACCEPT;
            this.cash += Math.rint(cash * 100) / 100;
            System.out.println("Your cash: " + getCash() + "$");
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String printMenu() {
        if(state.equals(STATES.WAIT)) {
            parseMenu();
            StringBuilder sb = new StringBuilder();
            sb.append("----MENU----");
            sb.append('\n');
            for (Entry<String, Double> arr : menu) {
                sb.append(arr.getKey() + ": ");
                sb.append(arr.getValue() + "$\n");
            }

            return sb.toString();
        }
        throw new UnsupportedOperationException();
    }

    private void updateJsonMenu(String s) {
        final String PATH = "Java-lab-2\\src\\main\\resources\\main_menu.json";
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(PATH));
            JSONObject jsonObject;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                jsonObject = iterator.next();
                if(jsonObject.get("caption").equals(s)) {
                    System.out.println(jsonObject);
                }
            }
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    public STATES printState() {
        return state;
    }

    public String choice(String choice) throws UnsupportedOperationException {
        state = STATES.CHECK;
        for (Entry<String, Double> arr : menu) {
            if (arr.getKey().equals(choice) && check(arr.getValue())) {
                this.choice = arr.getKey();
            } else if(arr.getKey().equals(choice) && !(check(arr.getValue()))) {
                this.choice = null;
                throw new UnsupportedOperationException("Not enough money for this product.");
            }
        }
        return this.choice;
    }

    private boolean check(double price) {
        this.state = STATES.CHECK;
        if(cash < price) return false;
        else return true;
    }

    public double getCash() throws UnsupportedOperationException {
        if(!state.equals(STATES.OFF))
            return cash;
        else
            throw new UnsupportedOperationException();
    }

    public double cancel() {
        state = STATES.WAIT;
        if(cash != 0) {
            return cash;
        } else {
            return 0;
        }
    }

    public String cook() throws UnsupportedOperationException {
        state = STATES.COOK;
        String item = "";
        for (Entry<String, Double> arr : menu) {
            if (arr.getKey().equals(choice)) {
                item = arr.getKey();
                if (check(arr.getValue())) {
                    cash -= arr.getValue();
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        }
        cash = Math.rint(100.0 * cash) / 100.0;
        System.out.println("Remains: " + getCash() + "$");
        finish();
        return item;
    }

    public void finish() {
        state = STATES.WAIT;
    }

}
