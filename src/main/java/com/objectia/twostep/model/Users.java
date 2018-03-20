package com.objectia.twostep.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        Response<User> resp = RestClient.post2("/users", params, User.class);
        return resp.getData();
    }

    public static User getUser(String email, String phone, int countryCode) throws TwostepException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("email", email);
        params.put("phone", phone);
        params.put("country_code", countryCode);

        User user = new User();
        user = RestClient.post("/users", params, user.getClass());
        return user;
    }

    public static User deleteUser(String email, String phone, int countryCode) throws TwostepException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("email", email);
        params.put("phone", phone);
        params.put("country_code", countryCode);

        User user = new User();
        user = RestClient.post("/users", params, user.getClass());
        return user;
    }


    public static SmsResponse requestSms(String userId, Map<String, Object> params) throws TwostepException {
        SmsResponse res = new SmsResponse();
        try {
            res = RestClient.post("/users/" + URLEncoder.encode(userId, "UTF-8") + "/sms", params, res.getClass());
        } catch (UnsupportedEncodingException ex) {
            //throw new InvalidRequestException("Unable to encode partameters"); 
        }
        return res;
    }

}
