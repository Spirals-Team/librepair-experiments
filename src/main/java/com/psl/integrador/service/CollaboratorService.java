package com.psl.integrador.service;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CollaboratorService {
    List<Collaborator> getCollaborators();

    Map<Collaborator, Role> getCollaboratorsByTopic(Topic topic);
}
