package com.epam.brest.course.rest;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller class for version.
 */
@CrossOrigin
@RestController
public class VersionController {
    /**
     * logger.
     */
    private static final String VERSION = "1.0";

    /**
     *
     * @return version.
     */
    @GetMapping(value = "/version")
    public final String getVersion() {
        return VERSION;
    }
}
