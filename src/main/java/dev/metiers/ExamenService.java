package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.entites.Examen;
import dev.entites.Note;
import dev.repositories.examen.ExamenRepository;
import dev.repositories.note.NoteDataJpaRepo;

@Service
public class ExamenService {

	private ExamenRepository examenRepository;
	private NoteDataJpaRepo noteRepository;

	public ExamenService(ExamenRepository examenRepository, NoteDataJpaRepo noteRepository) {
		super();
		this.examenRepository = examenRepository;
		this.noteRepository = noteRepository;
	}

	public List<Examen> lister() {
		return examenRepository.findAll();
	}

	public void ajouter(Examen examToAdd) {
		examenRepository.save(examToAdd);
	}

	public Examen getById(Long id) {

		return examenRepository.findById(id).orElse(null);
	}

	public void updateById(Examen entiteAvecId) {
		examenRepository.update(entiteAvecId);
	}

	public void supprimerExam(Examen entite) {
		examenRepository.delete(entite);
	}

	@Transactional
	public void addNote(Note note) {
		noteRepository.save(note);
		// examenRepository.findById(id).get().addNote(note);
	}

	public boolean exist(Long examenId) {
		return examenRepository.findById(examenId).isPresent();
	}

	public void deleteById(Long examenId) {
		examenRepository.delete(getById(examenId));
		
	}

}
