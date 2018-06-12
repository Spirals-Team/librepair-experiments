/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.examples;

import io.javalin.Javalin;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileUploadExample {

    public static void main(String[] args) {

        Javalin app = Javalin.start(7000);

        app.get("/", ctx ->
            ctx.html(
                ""
                    + "<form method='post' enctype='multipart/form-data'>"
                    + "    <input type='file' name='files' multiple>"
                    + "    <button>Upload</button>"
                    + "</form>"
            )
        );

        app.post("/", ctx -> {
            ctx.uploadedFiles("files").forEach(file -> {
                try {
                    FileUtils.copyInputStreamToFile(file.getContent(), new File("upload/" + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

    }

}
