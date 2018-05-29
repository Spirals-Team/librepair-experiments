package dev.paie.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.ProfilRemuneration;
import dev.paie.repository.ProfilRepository;

@Service
public class ProfilService {

	@Autowired
	private ProfilRepository profilRepository;

	public List<ProfilRemuneration> getLesProfil() {
		return profilRepository.findAll();
	}
}
