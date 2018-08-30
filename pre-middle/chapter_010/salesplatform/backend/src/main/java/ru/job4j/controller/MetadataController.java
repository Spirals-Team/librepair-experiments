package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Metadata;
import ru.job4j.service.MetadataService;

/**
 * Metadata controller.
 * Return metadata for add post page. (e.g. dropdown values etc.)
 */
@RestController
public class MetadataController {

    @Autowired
    private MetadataService service;

    @GetMapping(value = "/metadata", produces = "application/json;charset=UTF-8")
    public Metadata getMetadata() {
        return service.getMetadata();
    }
}
