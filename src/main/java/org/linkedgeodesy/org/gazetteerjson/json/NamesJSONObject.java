package org.linkedgeodesy.org.gazetteerjson.json;

import java.util.HashSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONObject to store names information
 *
 * @author Florian Thiery
 */
public class NamesJSONObject extends JSONObject {

    public NamesJSONObject() {
        super();
    }

    /**
     * add a pref name
     *
     * @param language
     * @param name
     */
    public void addPrefName(String language, String name) {
        if (language == null) {
            language = "unknown";
        }
        if (language.equals("")) {
            language = "unknown";
        }
        if (language.equals("link")) {
            return;
        }
        JSONObject prefName = new JSONObject();
        prefName.put("name", name);
        prefName.put("lang", language);
        super.put("prefName", prefName);
        JSONArray namesArray = new JSONArray();
        namesArray.add(name);
        super.remove(language);
        super.put(language, namesArray);
    }

    /**
     * add a alternative name
     *
     * @param language
     * @param name
     */
    public void addSingleName(String language, String name) {
        if (language == null) {
            language = "unknown";
        }
        if (language.equals("")) {
            language = "unknown";
        }
        if (language.equals("link")) {
            return;
        }
        JSONArray namesArray = (JSONArray) super.get(language);
        if (namesArray == null) {
            JSONArray tmp = new JSONArray();
            tmp.add(name);
            namesArray = tmp;
        }
        if (!namesArray.contains(name)) {
            namesArray.add(name);
        }
        super.remove(language);
        super.put(language, namesArray);
    }

    /**
     * get array of names by language
     *
     * @param language
     * @return
     */
    public JSONArray getNamesByLanguage(String language) {
        return (JSONArray) super.get(language);
    }

}
