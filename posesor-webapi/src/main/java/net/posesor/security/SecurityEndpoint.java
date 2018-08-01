package net.posesor.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/security")
@Slf4j
public class SecurityEndpoint {

    @GetMapping
    public ResponseEntity validate() {
        return ResponseEntity.ok().build();
    }
}
