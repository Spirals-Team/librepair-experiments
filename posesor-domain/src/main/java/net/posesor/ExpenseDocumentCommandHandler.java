package net.posesor;

import lombok.SneakyThrows;
import lombok.val;
import net.posesor.query.expensedocuments.ExpenseDocumentRepository;
import net.posesor.query.subjects.SubjectRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpenseDocumentCommandHandler {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExpenseDocumentRepository expenseDocumentRepository;

    private final Repository<ExpenseDocument> repository;

    public ExpenseDocumentCommandHandler(Repository<ExpenseDocument> repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @CommandHandler
    public void on(ExpenseDocument.CreateCommand cmd) {

        val documentId = cmd.getDocumentId();
        val principalName = cmd.getPrincipalName();

        // intentionally blocking operation. in async continuation we don't have UnitOfWork (yes, we could create a new one)
        // but for sake of maintenance I would like o prefer sync code in axonframework
        // maybe will be refactored later, if needed

        // for similar case refer to https://groups.google.com/forum/#!topic/axonframework/oq0HxI_zEDksee
        val subjectId = SubjectRepositoryExtensions.awaitSubjectEntity(subjectRepository, commandBus, cmd.getSubjectName(), principalName).get();

        val payload = new ExpenseDocument.ExpensePayload(documentId, principalName,
                subjectId, cmd.getSubjectName(), cmd.getCustomerName(),
                cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount(), cmd.getDescription());
        repository.newInstance(() -> new ExpenseDocument(payload));

    }

    @CommandHandler
    @SneakyThrows
    public void on(ExpenseDocument.UpdateCommand cmd) {
        val documentId = cmd.getDocumentId();
        val document = expenseDocumentRepository.findOne(documentId);
        if (document == null) return;

        val principalName = document.getPrincipalName();

        val subjectId = SubjectRepositoryExtensions.awaitSubjectEntity(subjectRepository, commandBus, cmd.getSubjectName(), principalName).get();

        repository.load(documentId).execute(agg ->
                agg.update(subjectId, cmd.getSubjectName(), cmd.getCustomerName(), cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount(), cmd.getDescription()));

    }
}
