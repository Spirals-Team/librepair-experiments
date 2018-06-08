package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.persistence.DeleteAccountRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.persistence.PasswordRecoveryRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;

@Service
public class DeleteAccountRequestService extends AbstractService{

    private final DeleteAccountRequestRepository repository;

    public DeleteAccountRequestService(DeleteAccountRequestRepository repository) {
        this.repository = repository;
    }

    public DeleteAccountRequest create(User user) {
        DeleteAccountRequest request = new DeleteAccountRequest();
                             request.setDate(System.currentTimeMillis());
                             request.setUsername(user.getUsername());
                             request.setRequestid(UUIDGenerator.generateUniqueUUID(repository));

        return repository.save(request);
    }

    public DeleteAccountRequest findByUUID(String uuid) {
        Optional<DeleteAccountRequest> request = repository.findById(uuid);

        if(request.isPresent()){
            return request.get();
        }else {
            throw new RuntimeException("No delete request found with ID: " + uuid);
        }
    }

    public void deleteOldRequests() {
        repository.deleteByDateBefore(System.currentTimeMillis() - HOUR);
    }

    public void deleteRequest(DeleteAccountRequest request) {
        repository.delete(request);
    }

    public boolean hasDeletionRequest(User user) {
        return repository.existsByUsername(user.getUsername());
    }
}
