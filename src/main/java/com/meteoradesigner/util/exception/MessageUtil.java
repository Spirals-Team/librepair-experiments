package com.meteoradesigner.util.exception;

import org.springframework.context.i18n.LocaleContextHolder;

//TODO documentation
/**
 * This @code{MessageUtil} class represents util for getting the error message.
 */
public class MessageUtil {

    private static String getMessage(String msgCode,
                                     String msg) {
        return new StringBuilder()
                .append(msgCode)
                .append(LocaleContextHolder.getLocale())
                .append(msg)
                .toString();
    }

    public static String getMessage(ApplicationException appEx) {
        return getMessage(appEx.getMsgCode(), appEx.getMsg());
    }
}