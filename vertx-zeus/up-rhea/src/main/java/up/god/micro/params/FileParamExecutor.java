package up.god.micro.params;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.StreamParam;
import java.io.File;

@EndPoint
@Path("/api")
public class FileParamExecutor {

    @Path("param/file")
    @POST
    public String sayFile(
            @StreamParam final File file) {
        return "Hello, File = " + file.getAbsolutePath();
    }

    @Path("param/fileupload")
    @POST
    public JsonObject sayFileUpload(
            @StreamParam final FileUpload fileUpload) {
        return new JsonObject()
                .put("filename", fileUpload.fileName())
                .put("charset", fileUpload.charSet())
                .put("content-type", fileUpload.contentType())
                .put("size", fileUpload.size())
                .put("name", fileUpload.name());
    }
}
