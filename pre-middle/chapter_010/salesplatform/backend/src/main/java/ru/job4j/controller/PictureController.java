package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.service.PictureService;

/**
 * Get picture controller.
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService service;

    @GetMapping(value = "/pic", produces = "image/*;charset=UTF-8")
    public byte[] getPicture(@RequestParam("post") Long postId,
                             @RequestParam("pic") Long picId) {
        return service.findPicture(picId).getData();
    }
}
