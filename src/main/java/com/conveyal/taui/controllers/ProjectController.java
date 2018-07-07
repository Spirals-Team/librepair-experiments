package com.conveyal.taui.controllers;

import com.conveyal.taui.models.AddTripPattern;
import com.conveyal.taui.models.ConvertToFrequency;
import com.conveyal.taui.models.Modification;
import com.conveyal.taui.models.Project;
import com.conveyal.taui.persistence.Persistence;
import com.conveyal.taui.util.JsonUtil;
import com.mongodb.QueryBuilder;
import org.bson.types.ObjectId;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Controller for projects.
 */
public class ProjectController {
    public static Project findById(Request req, Response res) {
        return Persistence.projects.findByIdFromRequestIfPermitted(req);
    }

    public static Collection<Project> getAllProjects (Request req, Response res) {
        return Persistence.projects.findPermitted(
                QueryBuilder.start("regionId").is(req.params("region")).get(),
                req.attribute("accessGroup")
        );
    }

    public static Project create(Request req, Response res) throws IOException {
        return Persistence.projects.createFromJSONRequest(req, Project.class);
    }

    public static Project update(Request req, Response res) throws IOException {
        return Persistence.projects.updateFromJSONRequest(req, Project.class);
    }

    public static Collection<Modification> modifications (Request req, Response res) {
        return Persistence.modifications.findPermitted(
                QueryBuilder.start("projectId").is(req.params("_id")).get(),
                req.attribute("accessGroup")
        );
    }

    public static Collection<Modification> importModifications (Request req, Response res) {
        String importId = req.params("_importId");
        String newId = req.params("_id");
        Collection<Modification> modifications = Persistence.modifications.findPermitted(
                QueryBuilder.start("projectId").is(importId).get(),
                req.attribute("accessGroup")
        );

        // This would be a lot easier if we just used the actual `_id`s and dealt with it elsewhere when searching. They
        // should be unique anyways. Hmmmmmmmmmmmm. Tradeoffs.
        // Need to make two passes to create all the pairs and rematch for phasing
        Map<String, String> modificationIdPairs = new HashMap<>();
        Map<String, String> timetableIdPairs = new HashMap<>();

        return modifications
                .stream()
                .map(modification -> {
                    String oldModificationId = modification._id;
                    Modification clone = Persistence.modifications.create(modification);
                    modificationIdPairs.put(oldModificationId, clone._id);

                    // Change the projectId, most important part!
                    clone.projectId = newId;

                    // Set `name` to include "(import)"
                    clone.name = clone.name + " (import)";

                    // Set `updatedBy` by manually, `createdBy` stays with the original author
                    clone.updatedBy = req.attribute("email");

                    // Matched up the phased entries and timetables
                    if (modification.getType().equals(AddTripPattern.type)) {
                        ((AddTripPattern) clone).timetables.forEach(tt -> {
                            String oldTTId = tt._id;
                            tt._id = new ObjectId().toString();
                            timetableIdPairs.put(oldTTId, tt._id);
                        });
                    } else if (modification.getType().equals(ConvertToFrequency.type)) {
                        ((ConvertToFrequency) clone).entries.forEach(tt -> {
                            String oldTTId = tt._id;
                            tt._id = new ObjectId().toString();
                            timetableIdPairs.put(oldTTId, tt._id);
                        });
                    }

                    return clone;
                })
                .collect(Collectors.toList())
                .stream()
                .map(modification -> {
                    if (modification.getType().equals(AddTripPattern.type)) {
                        ((AddTripPattern) modification).timetables.forEach(tt -> {
                            String pft = tt.phaseFromTimetable;
                            if (pft != null && pft.length() > 0) {
                                String[] pfts = pft.split(":");
                                tt.phaseFromTimetable = modificationIdPairs.get(pfts[0]) + ":" + timetableIdPairs.get(pfts[1]);
                            }
                        });
                    } else if (modification.getType().equals(ConvertToFrequency.type)) {
                        ((ConvertToFrequency) modification).entries.forEach(tt -> {
                            String pft = tt.phaseFromTimetable;
                            if (pft != null && pft.length() > 0) {
                                String[] pfts = pft.split(":");
                                tt.phaseFromTimetable = modificationIdPairs.get(pfts[0]) + ":" + timetableIdPairs.get(pfts[1]);
                            }
                        });
                    }

                    return Persistence.modifications.put(modification);
                })
                .collect(Collectors.toList());
    }

    public static Project deleteProject (Request req, Response res) {
        return Persistence.projects.removeIfPermitted(req.params("_id"), req.attribute("accessGroup"));
    }

    public static void register () {
        get("/api/project/:_id", ProjectController::findById, JsonUtil.objectMapper::writeValueAsString);
        get("/api/project/:_id/modifications", ProjectController::modifications, JsonUtil.objectMapper::writeValueAsString);
        post("/api/project/:_id/import/:_importId", ProjectController::importModifications, JsonUtil.objectMapper::writeValueAsString);
        post("/api/project", ProjectController::create, JsonUtil.objectMapper::writeValueAsString);
        options("/api/project", (q, s) -> "");
        put("/api/project/:_id", ProjectController::update, JsonUtil.objectMapper::writeValueAsString);
        delete("/api/project/:_id", ProjectController::deleteProject, JsonUtil.objectMapper::writeValueAsString);
        options("/api/project/:_id", (q, s) -> "");
    }
}
