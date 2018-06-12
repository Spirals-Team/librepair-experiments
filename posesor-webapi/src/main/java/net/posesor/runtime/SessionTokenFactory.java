package net.posesor.runtime;

import lombok.val;
import net.posesor.SessionToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SessionTokenFactory {

    @Scope("session")
    @Bean
    public SessionToken sessionToken() {
        val userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new SessionToken(userName);
    }
}
