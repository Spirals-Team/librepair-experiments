package com.justinfrasier.email;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justinfrasier.https.HTTPS;

public class JsonEmail{

    public static String getUserName(){
        HTTPS https = new HTTPS();
        String s = https.sendGet("http://justinfrasier.com/test/codes.json");
        JsonObject json = new Gson().fromJson(s,JsonObject.class);
        return json.get("username").getAsString();
    }

    public static String getPassword(){
        HTTPS https = new HTTPS();
        String s = https.sendGet("http://justinfrasier.com/test/codes.json");
        JsonObject json = new Gson().fromJson(s,JsonObject.class);
        return json.get("password").getAsString();
    }

    public static String getsendto(){
        HTTPS https = new HTTPS();
        String s = https.sendGet("http://justinfrasier.com/test/codes.json");
        JsonObject json = new Gson().fromJson(s,JsonObject.class);
        return json.get("sendto").getAsString();
    }

}
