package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.impl.AvatarService;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class AvatarController implements LoggingWeb{

    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/avatar/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(service.getAvatar(id).getAvatarData(), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/avatar")
    public String setAvatar(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        log.info("Received file '" + file.getOriginalFilename() + "' with content type '" + file.getContentType() + "'");
        User user = (User) session.getAttribute("client");
        log.info("Received user's id: " + user.getUserId());
        Long id = service.uploadAvatar(user.getUserId(), file.getBytes(), file.getContentType());
        log.info("Saved avatar with id: " + id);
        return "redirect:/profile";
    }
}
