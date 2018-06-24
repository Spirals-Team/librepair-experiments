package pl.sda.jira.documentation.domain;

import org.junit.Test;
import pl.sda.jira.documentation.domain.exception.DocumentDoestExist;
import pl.sda.jira.documentation.domain.exception.ThisSameDocumentExist;
import pl.sda.jira.documentation.dto.DocumentationDto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


public class DocumentationServiceTest {
    private DocumentationRepository documentationRepository = new InMemoryDocumentationRepository();
    private DocumentationService documentationService = new DocumentationService(documentationRepository);
    private final long DOCUMENTATION_ID = 2L;
    private final DocumentationDto SOME_DOCUMENTATION_DTO = new DocumentationDto(DOCUMENTATION_ID , "JIRA") ;
    private final String DOCUMENT_NAME_UPDATE = "JIRA2";


    @Test(expected = DocumentDoestExist.class)
    public void shouldThrowDocumentDoestExistWhenDocumentationNotExist() {

        documentationService.get(DOCUMENTATION_ID);
    }

    @Test
    public void shouldReturnDocumentationWhenExist() {
        documentationService.add(SOME_DOCUMENTATION_DTO);
        documentationService.get(DOCUMENTATION_ID);
    }

    @Test
    public void shouldDeleteDocumentWhenExist() {
        documentationService.add(SOME_DOCUMENTATION_DTO);
        documentationService.delete(DOCUMENTATION_ID);
        assertFalse(documentationService.exists(DOCUMENTATION_ID));
    }

    @Test(expected = DocumentDoestExist.class)
    public void shouldThrowsExceptionWhenDocumentNotExist() {
        documentationService.delete(DOCUMENTATION_ID);
    }

    @Test(expected = DocumentDoestExist.class)
    public void shouldNotUpdateWhenDocumentNotExist() {
        documentationService.update(DOCUMENTATION_ID , DOCUMENT_NAME_UPDATE);
    }

    @Test
    public void shouldUpdateDocument() {


        documentationService.add(SOME_DOCUMENTATION_DTO);
        documentationService.update(DOCUMENTATION_ID, DOCUMENT_NAME_UPDATE);
        Documentation documentation = documentationRepository.get(DOCUMENTATION_ID);
        assertEquals(documentation.getName() , DOCUMENT_NAME_UPDATE);
    }

    @Test(expected = ThisSameDocumentExist.class)
    public void shouldNotAddWhenThisSameDocumentExist() {
        documentationService.add(SOME_DOCUMENTATION_DTO);
        documentationService.add(SOME_DOCUMENTATION_DTO);
    }
}