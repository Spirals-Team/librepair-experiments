package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.persistence.JournalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.unbescape.html.HtmlEscape.escapeHtml5;

@Service
public class JournalService extends AbstractService{

    private final JournalRepository repository;
    private final ImageService imageService;

    public JournalService(JournalRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    public List<Journal> findAll(String username) {
        List<Journal> journals = repository.findAllByUsernameOrderByDateDesc(username);

        for (Journal j : journals) {
            j.setContent(j.getContent().replaceAll("\n", "<br/>"));
            j.setJournalName(j.getJournalName());
        }

        return journals;
    }

    public Journal save(Journal journal) {
        journal.setJournalid(UUIDGenerator.generateUniqueUUID(repository));
        journal.setContent(escapeHtml5(journal.getContent()));
        journal.setJournalName(escapeHtml5(journal.getJournalName()));
        return repository.save(journal);
    }

    public Journal update(Journal journal) {
        return repository.save(journal);
    }

    public Journal findById(String journalId) {
        Optional<Journal> journalOptional = repository.findById(journalId);

        if(journalOptional.isPresent()){
            return journalOptional.get();
        }else {
            throw new RuntimeException("No journal found with Id: " + journalId);
        }
    }

    public void deleteById(String journalId) {
        imageService.deleteAllByJournalId(journalId);
        repository.deleteById(journalId);
    }

    public int countByUsername(String username) {
        return repository.countByUsername(username);
    }

    public void deleteAllFromUser(String username) {
        List<Journal> journals = repository.findAllByUsernameOrderByDateDesc(username);
        for(Journal j : journals){
            deleteById(j.getJournalid());
        }
    }
}
