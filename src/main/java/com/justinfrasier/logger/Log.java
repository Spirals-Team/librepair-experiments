package com.justinfrasier.logger;


import com.justinfrasier.email.Email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private String fileName;
    private String log="";

    public Log(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        Date date = new Date();
        fileName = dateFormat.format(date);
        log+="Log File \n"+fileName+'\n';

    }


    public void writeln(String string){
        log+=string+'\n';
    }

    public void close(){
        Email email = new Email();
        email.sendEmail(log);
    }
}
