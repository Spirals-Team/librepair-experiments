package org.sjc.transparencia;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    public static void main() {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "all good");
    }

    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String herokuPort = processBuilder.environment().get("PORT");
        Integer port = herokuPort != null ? Integer.parseInt(herokuPort) : 4567;
        return port;
    }
}
