package com.rgross.parser.common;

import org.json.simple.JSONObject;

/**
 * Created by Ryan on 1/8/2018.
 */
public interface JSONParser {

    JSONObject callExternalService(String url) throws Exception;

    Object processResponse(JSONObject jsonResponse) throws Exception;

}
