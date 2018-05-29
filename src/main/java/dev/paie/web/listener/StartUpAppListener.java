package dev.paie.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.service.CalculerRemunerationServiceSimple;
import dev.paie.service.InitialiserDonneesService;
import dev.paie.util.PaieUtils;

@Component
public class StartUpAppListener {

    @Autowired
    private InitialiserDonneesService initService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        // capture du démarrage de l'application
        // à un moment où le contexte Spring est complètement créé
        initService.initialiser();
    }
}