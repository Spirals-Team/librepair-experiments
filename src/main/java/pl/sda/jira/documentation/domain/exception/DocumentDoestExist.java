package pl.sda.jira.documentation.domain.exception;

public class DocumentDoestExist extends RuntimeException {

    public DocumentDoestExist(Long documentationId ) {
        super("Document doest exist . About this id : " + documentationId);
    }
}

