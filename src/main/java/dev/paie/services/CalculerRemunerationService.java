package dev.paie.services;

import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;

@Service
public interface CalculerRemunerationService {
    ResultatCalculRemuneration calculer(BulletinSalaire bulletin);
}