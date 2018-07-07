package pl.sda.jira.documentation.domain;

import org.springframework.stereotype.Repository;
import pl.sda.jira.documentation.domain.exception.DocumentDoestExist;
import pl.sda.jira.documentation.domain.exception.ThisSameDocumentExist;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDocumentationRepository implements DocumentationRepository {

    private List<Documentation> documentations = new ArrayList<>();

    @Override
    public void add(Documentation documentation) {

        if (!exists(documentation.getId())) {
            documentations.add(documentation);
        } else {
            throw new ThisSameDocumentExist(documentation.getId());
        }
    }

    public boolean exists(Long documentationId) {
        for (Documentation documentation : documentations) {
            if (documentation.getId().equals(documentationId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Documentation get(Long documentationId) {
        if (exists(documentationId)) {
            return findDocumentation(documentationId);
        }
        throw new DocumentDoestExist(documentationId);
    }

    @Override
    public void delete(Long documentationId) {
        if (exists(documentationId)) {
            Documentation documentation = findDocumentation(documentationId);
            documentations.remove(documentation);
        } else {
            throw new DocumentDoestExist(documentationId);
        }
    }

    @Override
    public void update(Documentation documentation) {
        delete(documentation.getId());
        add(documentation);
    }

    private Documentation findDocumentation(Long documentationId) {
        Documentation documentation = null;
        for (int i = 0; i < documentations.size(); i++) {
            if (documentations.get(i).getId().equals(documentationId)) {
                documentation = documentations.get(i);
            }

        }
        return documentation;
    }
}

