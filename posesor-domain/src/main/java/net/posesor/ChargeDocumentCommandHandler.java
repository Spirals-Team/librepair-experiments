package net.posesor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.query.chargedocuments.ChargeDocumentRepository;
import net.posesor.query.settlementaccounts.SettlementAccountRepository;
import net.posesor.query.subjects.SubjectRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Slf4j
public class ChargeDocumentCommandHandler {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SettlementAccountRepository settlementAccountRepository;

    @Autowired
    private ChargeDocumentRepository chargeDocumentRepository;

    private final Repository<UnallocatedChargeDocument> repository;

    public ChargeDocumentCommandHandler(Repository<UnallocatedChargeDocument> repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @CommandHandler
    public void on(UnallocatedChargeDocument.CreateCommand cmd) {

        val documentId = cmd.getDocumentId();
        val principalName = cmd.getPrincipalName();

        // intentionally blocking operation. in async continuation we don't have UnitOfWork (yes, we could create a new one)
        // but for sake of maintenance I would like o prefer sync code in axonframework
        // maybe will be refactored later, if needed

        // for similar case refer to https://groups.google.com/forum/#!topic/axonframework/oq0HxI_zEDksee
        val subjectId = SubjectRepositoryExtensions.awaitSubjectEntity(subjectRepository, commandBus, cmd.getSubjectName(), principalName).get();
        val settlementAccountId  = awaitSettlementAccountEntity(cmd.getCustomerName(), principalName, subjectId).get();

        val content = new UnallocatedChargeDocument.Snapshot(
                subjectId, cmd.getSubjectName(),
                settlementAccountId, cmd.getCustomerName(),
                cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount());
        repository.newInstance(() -> new UnallocatedChargeDocument(documentId, principalName, content));

    }

    @CommandHandler
    @SneakyThrows
    public void on(UnallocatedChargeDocument.UpdateCommand cmd) {
        val documentId = cmd.getDocumentId();
        val document = chargeDocumentRepository.findOne(documentId);
        if (document == null) return;

        val principalName = document.getPrincipalName();

        val subjectId = SubjectRepositoryExtensions.awaitSubjectEntity(subjectRepository, commandBus, cmd.getSubjectName(), principalName).get();
        val settlementAccountId  = awaitSettlementAccountEntity(cmd.getCustomerName(), principalName, subjectId).get();

        repository.load(documentId).execute(agg ->
                agg.update(subjectId, cmd.getSubjectName(), settlementAccountId, cmd.getCustomerName(), cmd.getPaymentDate(), cmd.getPaymentTitle(), cmd.getAmount()));

    }

    private CompletableFuture<String> awaitSettlementAccountEntity(String requestedName, String principalName, String subjectId) {
        val entity = settlementAccountRepository.findByNameStartsWithIgnoreCaseAndPrincipalName(requestedName, principalName);
        val entityId = entity == null || entity.isEmpty()
                ? UUID.randomUUID().toString()
                : entity.stream().findFirst().get().getSettlementAccountId();
        val commandCallback = new FutureCallback<Object, Object>();
        if (entity == null || entity.isEmpty()) {
            commandBus.dispatch(asCommandMessage(new AccountReceivable.CreateCommand(entityId, requestedName, principalName, subjectId)), commandCallback);
        } else {
            // command return value is not defined so can be null.
            commandCallback.complete(null);
        }
        return commandCallback.thenApply(it -> entityId);
    }
}
