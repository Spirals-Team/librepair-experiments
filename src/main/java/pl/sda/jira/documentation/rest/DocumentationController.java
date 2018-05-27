package pl.sda.jira.documentation.rest;

import pl.sda.jira.documentation.domain.DocumentationRepository;

public class DocumentationController {

    private DocumentationRepository documentationRepository;

    public DocumentationController(DocumentationRepository documentationRepository){
        this.documentationRepository = documentationRepository;
    }

    public boolean existForProject(String projectName) {
        return documentationRepository.existForProject();
    }
}
