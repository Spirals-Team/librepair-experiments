package dev.repositories.utilisateur;

import dev.repositories.BaseRepositoryMemoire;
import org.springframework.stereotype.Service;

import dev.entites.Utilisateur;

@Service
public class UtilisateurBaseRepositoryMemoire extends BaseRepositoryMemoire {

	public UtilisateurBaseRepositoryMemoire() {
		super("jdd/jdd-utilisateur.xml", Utilisateur.class);
	}

}
