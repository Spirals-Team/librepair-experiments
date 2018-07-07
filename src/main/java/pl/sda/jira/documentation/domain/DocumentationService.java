package pl.sda.jira.documentation.domain;

import org.springframework.stereotype.Service;
import pl.sda.jira.documentation.domain.exception.DocumentDoestExist;
import pl.sda.jira.documentation.dto.DocumentationDto;

import java.util.UUID;

@Service
public class DocumentationService {
    private final DocumentationRepository documentationRepository;

    public DocumentationService(DocumentationRepository documentationRepository) {
        this.documentationRepository = documentationRepository;
    }


    public DocumentationDto get(Long documentationId) {
        if (documentationRepository.exists(documentationId)) {
            return documentationRepository.get(documentationId).asDto();
        }
        throw new DocumentDoestExist(documentationId);
    }

    public Long add(DocumentationDto documentationDto) {
        Long documentationId = UUID.randomUUID().getMostSignificantBits();
        Documentation documentation = new Documentation(documentationId, documentationDto.getTitle());
        documentationRepository.add(documentation);
        return documentationId;
    }

    public Long delete(Long documentationId) {
        documentationRepository.delete(documentationId);
        return documentationId;

    }

    public void update(DocumentationDto documentationDto, Long id) {
        Documentation documentation = documentationRepository.get(id);
        documentation.setNewName(documentationDto.getTitle());
        documentationRepository.update(documentation);

    }

    public boolean exists(Long documentationId) {
        return documentationRepository.exists(documentationId);
    }


}

