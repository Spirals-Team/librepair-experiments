package com.justinfrasier.storage;

import com.google.gson.JsonObject;
import com.justinfrasier.logger.Log;

import java.io.FileWriter;
import java.io.IOException;

public class MakeJsonFile {

    public boolean makeJsonFile(String user, String hashPassword, String salt,  int lastGame, String fileName){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserName",user);
        jsonObject.addProperty("PassHash", hashPassword);
        jsonObject.addProperty("Salt:", salt);
        jsonObject.addProperty("LastGameLookAt", lastGame);

        try{
           FileWriter file = new FileWriter(fileName);
           file.write(jsonObject.toString());
           file.flush();
           file.close();
           return true;
        } catch (IOException e) {
            Log log = new Log();
            log.writeln(e.getMessage());
            log.close();
            return false;
        }
    }
}
