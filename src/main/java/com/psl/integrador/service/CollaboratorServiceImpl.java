package com.psl.integrador.service;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Detail;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Role;
import com.psl.integrador.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    @Autowired
    public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;

    }

    @Override
    public List<Collaborator> getCollaborators() {
        return collaboratorRepository.findAll();
    }

    @Override
    public Map<Collaborator, Role> getCollaboratorsByTopic(Topic topic) {

        Map<Collaborator, Role> collaboratorIntegerMap = new HashMap<>();

        for (Collaborator collaborator : getCollaborators()) {
            for (Detail detail : collaborator.getTopicsToLearn()) {
                if (detail.getTopic().getId().equals(topic.getId())) {
                    collaboratorIntegerMap.put(collaborator, Role.STUDENT);
                }
            }
            for (Detail detail : collaborator.getTopicsToTeach()) {
                if (detail.getTopic().getId().equals(topic.getId())) {
                    collaboratorIntegerMap.put(collaborator, Role.TEACHER);
                }
            }
        }

        return collaboratorIntegerMap;
    }
}
