package org.sjc.transparencia.data;

import static spark.Spark.get;

public class DataController {

    public void dataController(){
        get("/", (req, res) -> "all good");
    }
}
