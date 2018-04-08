package netcracker.study.monopoly.api.controllers.filters;

import lombok.extern.log4j.Log4j2;
import netcracker.study.monopoly.models.entities.Player;
import netcracker.study.monopoly.models.repositories.PlayerRepository;
import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This filter insert into database new players and update profile if session is new
 * Also keep track of all active users (that have requests last 30 seconds)
 */
@Component
@Log4j2
public class RegistrationFilter extends GenericFilterBean {
    private final PlayerRepository playerRepository;
    private final Set<String> sessionsId = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public RegistrationFilter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        StandardSessionFacade session = (StandardSessionFacade) req.getSession(false);
        // "Infinity" session
        OAuth2Authentication authentication = (OAuth2Authentication)
                SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        if (!sessionsId.contains(session.getId())) {


            Map details = (Map) authentication.getUserAuthentication().getDetails();

            Player player = playerRepository.findByNickname(name)
                    .orElseGet(() -> new Player(name));
            player.setAvatarUrl((String) details.get("avatar_url"));
            playerRepository.save(player);
            log.info(String.format("Player %s logged in", player.getNickname()));
            session.setMaxInactiveInterval(0);
            session.setAttribute("id", player.getId());
        }

        sessionsId.add(session.getId());
        chain.doFilter(request, response);
    }

}
