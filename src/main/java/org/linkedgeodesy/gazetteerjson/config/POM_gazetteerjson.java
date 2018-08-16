package org.linkedgeodesy.gazetteerjson.config;

import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Class for POM details
 */
public class POM_gazetteerjson {

    /**
     * get POM info as JSON
     *
     * @return pom JSON
     * @throws IOException
     */
    public static JSONObject getInfo() throws IOException {
        JSONObject outObj = new JSONObject();
        JSONObject maven = new JSONObject();
        maven.put("modelVersion", GazetteerJSONProperties.getPropertyParam("modelVersion"));
        maven.put("mavenCompilerSource", GazetteerJSONProperties.getPropertyParam("source"));
        maven.put("mavenCompilerTarget", GazetteerJSONProperties.getPropertyParam("target"));
        outObj.put("maven", maven);
        JSONObject project = new JSONObject();
        project.put("buildNumber", GazetteerJSONProperties.getPropertyParam("buildNumber"));
        project.put("buildNumberShort", GazetteerJSONProperties.getPropertyParam("buildNumber").substring(0, 7));
        project.put("buildRepository", GazetteerJSONProperties.getPropertyParam("url").replace(".git", "/tree/" + GazetteerJSONProperties.getPropertyParam("buildNumber")));
        project.put("artifactId", GazetteerJSONProperties.getPropertyParam("artifactId"));
        project.put("groupId", GazetteerJSONProperties.getPropertyParam("groupId"));
        project.put("version", GazetteerJSONProperties.getPropertyParam("version"));
        project.put("packaging", GazetteerJSONProperties.getPropertyParam("packaging"));
        project.put("name", GazetteerJSONProperties.getPropertyParam("name"));
        project.put("description", GazetteerJSONProperties.getPropertyParam("description"));
        project.put("url", GazetteerJSONProperties.getPropertyParam("url"));
        project.put("encoding", GazetteerJSONProperties.getPropertyParam("sourceEncoding"));
        outObj.put("project", project);
        return outObj;
    }

}
