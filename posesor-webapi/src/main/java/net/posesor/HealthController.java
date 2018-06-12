package net.posesor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint which allows to check if application is ready to work.
 */
@RestController
@RequestMapping("/health")
public final class HealthController {

    @GetMapping("check")
    public ResponseEntity<Boolean> check(){
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}
