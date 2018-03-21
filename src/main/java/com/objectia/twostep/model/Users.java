package com.objectia.twostep.model;

import java.util.HashMap;
import java.util.Map;

import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.net.RestClient;

public class Users {

    public static User createUser(String email, String phone, int countryCode) throws TwostepException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("email", email);
        params.put("phone", phone);
        params.put("country_code", countryCode);

        Resource<User> resp = RestClient.post("/users", params, User.class);
        return resp.getData();
    }

    public static User getUser(String userId) throws TwostepException {
        Resource<User> resp = RestClient.get("/users/" + userId, User.class);
        return resp.getData();
    }

    public static User deleteUser(String userId) throws TwostepException {
        Resource<User> resp = RestClient.delete("/users/" + userId, User.class);
        //FIXME: does not return user....!
        return resp.getData();
    }


    public static Sms requestSms(String userId, Map<String, Object> params) throws TwostepException {
        Resource<Sms> resp = RestClient.post("/users/" + userId + "/sms", params, Sms.class);
        return resp.getData();
    }

}
