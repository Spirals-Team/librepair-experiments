package net.posesor;

import lombok.NonNull;
import lombok.val;
import net.posesor.query.subjects.SubjectEntry;
import net.posesor.query.subjects.SubjectRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class SubjectRepositoryExtensions {

    /**
     * Blocks creating instance of this utils class.
     */
    private SubjectRepositoryExtensions() { }

    /**
     * Find or create SubjectEntry with requested name.
     * @param repository
     * @param requestedName
     * @param principalName
     * @return
     */
    public static CompletableFuture<String> awaitSubjectEntity(@NonNull SubjectRepository repository, @NotNull CommandBus commandBus, String requestedName, String principalName) {
        val probe = new SubjectEntry();
        probe.setName(requestedName);
        probe.setPrincipalName(principalName);
        val example = Example.of(probe);
        val entity = repository.findOne(example);
        val entityId = entity == null
                ? UUID.randomUUID().toString()
                : entity.getSubjectId();
        val commandCallback = new FutureCallback<Object, Object>();
        if (entity == null) {
            commandBus.dispatch(asCommandMessage(new SubjectCreateCommand(principalName, entityId, requestedName)), commandCallback);
        } else {
            // command return value is not defined so can be null.
            commandCallback.complete(null);
        }
        return commandCallback.thenApply(it -> entityId);
    }

}
