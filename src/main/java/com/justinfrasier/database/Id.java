package com.justinfrasier.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class Id {

    public boolean save(String id, String userName, String hashPassword){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        jsonObject.addProperty("username", userName);
        jsonObject.addProperty("password", hashPassword);
        try {
            FileWriter file = new FileWriter("id.json");
            file.write(jsonObject.toString());
            file.flush();
            file.close();
            return true;
        }catch (IOException e){
            return false;
        }
    }

    private JsonObject getJson(){
        String input = "";
        try{
            File file = new File("id.json");
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[(int) file.length()];
            bin.read(buffer);
            input = new String(buffer);
        }catch (IOException e){ }
        return new Gson().fromJson(input,JsonObject.class);
    }

    public String getIdFromJson(){
       return getJson().get("id").getAsString();
    }
    public String getUsernameFromJson(){
        return getJson().get("username").getAsString();
    }
    public String getPasswordFromJson(){
        return getJson().get("password").getAsString();
    }
}
