package org.linkedgeodesy.gazetteerjson.log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Logging {

    /**
     * get error message as JSON
     *
     * @param exception
     * @param endClass
     * @return
     */
    public static JSONObject getMessageJSON(Exception exception, String endClass) {
        JSONObject jsonobj_error = new JSONObject();
        JSONObject jsonobj_error_data = new JSONObject();
        JSONArray jsonarray_element = new JSONArray();
        for (StackTraceElement element : exception.getStackTrace()) {
            JSONObject errMessage = new JSONObject();
            errMessage.put("class", element.getClassName());
            errMessage.put("method", element.getMethodName());
            errMessage.put("line", element.getLineNumber());
            jsonarray_element.add(errMessage);
            if (element.getClassName().equals(endClass)) {
                break;
            }
        }
        // get error code
        String code = "unknown";
        String userMessage = "n/a";
        // output
        jsonobj_error.put("errors", jsonobj_error_data);
        jsonobj_error_data.put("internalMessage", exception.toString());
        jsonobj_error_data.put("userMessage", userMessage);
        jsonobj_error_data.put("code", code);
        jsonobj_error_data.put("developerInfo", jsonarray_element);
        return jsonobj_error;
    }

}
