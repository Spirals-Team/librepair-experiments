package ua.com.company.store.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Владислав on 06.12.2017.
 */
public final class CommandKeyGenerator {
    private CommandKeyGenerator() {
    }
    public static String generateCommandKeyByRequest(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        String[] normalPath = path.split("/ | ?",2);

        return method + ":" + normalPath[0] + normalPath[1];
    }
}
