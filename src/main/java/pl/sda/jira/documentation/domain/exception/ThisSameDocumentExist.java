package pl.sda.jira.documentation.domain.exception;

public class ThisSameDocumentExist extends RuntimeException {


    public ThisSameDocumentExist( Long documentationId) {
        super("This same document exist . About this ID : " + documentationId);
    }
}
