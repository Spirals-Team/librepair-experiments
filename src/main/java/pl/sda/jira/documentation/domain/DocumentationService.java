package pl.sda.jira.documentation.domain;

import org.springframework.stereotype.Service;
import pl.sda.jira.documentation.domain.exception.DocumentDoestExist;
import pl.sda.jira.documentation.domain.exception.ThisSameDocumentExist;
import pl.sda.jira.documentation.dto.DocumentationDto;
@Service
public class DocumentationService {

    private final DocumentationRepository documentationRepository;

    public DocumentationService(DocumentationRepository documentationRepository) {
        this.documentationRepository = documentationRepository;
    }


    public Documentation get(Long documentationId) {

        if (exists(documentationId)) {
            return documentationRepository.get(documentationId);
        }
        throw new DocumentDoestExist(documentationId);
    }

    public boolean exists(Long documentationId) {
        return documentationRepository.exists(documentationId);
    }

    public void add(DocumentationDto documentationDto) {

        if (exists(documentationDto.getId())) {
            throw new ThisSameDocumentExist(documentationDto.getId());
        }
        Documentation documentation = convert(documentationDto);
        documentationRepository.add(documentation);
    }

    private Documentation convert(DocumentationDto documentationDto) {
        Long id = documentationDto.getId();
        String title = documentationDto.getTitle();
        return new Documentation(id, title);

    }

    public void delete(Long documentationId) {
        if (exists(documentationId)) {
            documentationRepository.delete(documentationId);
        } else {
            throw new DocumentDoestExist(documentationId);
        }
    }

    public void update(Long documentationId, String newTitle) {
        if (exists(documentationId)) {
            Documentation documentation = get(documentationId);
            documentation.setName(newTitle);
            documentationRepository.update(documentation);
        } else {
            throw new DocumentDoestExist(documentationId);
        }
    }



}

