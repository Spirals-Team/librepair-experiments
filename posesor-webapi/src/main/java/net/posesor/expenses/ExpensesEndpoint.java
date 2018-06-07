package net.posesor.expenses;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.ExpenseDocument;
import net.posesor.query.expensedocuments.ExpenseDocumentEntry;
import net.posesor.query.expensedocuments.ExpenseDocumentRepository;
import net.posesor.runtime.LocationBuilder;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * Exposes with HTTP protocol group of methods related to expense - related operations.
 */
@Controller
@RequestMapping(value = "/api/expenses")
@Slf4j
@Scope("session")
public final class ExpensesEndpoint {

    private final CommandBus commandBus;
    private final ExpenseDocumentRepository documentQuery;
    private final Function<String, URI> locationSupplier;
    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    @Autowired
    public ExpensesEndpoint(CommandBus commandBus, ExpenseDocumentRepository documentQuery) {
        this(commandBus, documentQuery, new LocationBuilder<>(ExpensesEndpoint.class));
    }

    public ExpensesEndpoint(CommandBus commandBus, ExpenseDocumentRepository documentQuery, Function<String, URI> locationSupplier) {
        this.commandBus = commandBus;
        this.documentQuery = documentQuery;
        this.locationSupplier = locationSupplier;
    }

    @GetMapping("template")
    public ResponseEntity template() {
        val result = new ExpenseDocumentDto();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ExpenseDocumentDto dto) {

        val documentId = UUID.randomUUID().toString();

        val cmd = new ExpenseDocument.CreateCommand(
                documentId,
                userName.get(),
                dto.getSubjectName(),
                dto.getCustomerName(),
                LocalDate.parse(dto.getPaymentDate(), DateTimeFormatter.ISO_LOCAL_DATE),
                dto.getPaymentTitle(),
                dto.getAmount(),
                dto.getDescription());
        commandBus.dispatch(new GenericCommandMessage<Object>(cmd));

        val location = locationSupplier.apply(documentId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity get(@PathVariable("documentId") String documentId) {

        val probe = new ExpenseDocumentEntry();
        probe.setDocumentId(documentId);
        probe.setPrincipalName(userName.get());

        val example = Example.of(probe);

        val result = documentQuery.findOne(example);
        if (result == null) return ResponseEntity.notFound().build();

        val model = new ExpenseDocumentDto();
        model.setSubjectName(result.getSubjectName());
        model.setCustomerName(result.getCustomerName());
        model.setPaymentDate(result.getPaymentDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        model.setPaymentTitle(result.getPaymentTitle());
        model.setAmount(result.getAmount());
        model.setDescription(result.getDescription());

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity put(@PathVariable("documentId") String documentId, @RequestBody ExpenseDocumentDto dto) {
        val cmd = new ExpenseDocument.UpdateCommand(documentId,
                userName.get(),
                dto.getSubjectName(),
                dto.getCustomerName(),
                LocalDate.parse(dto.getPaymentDate(), DateTimeFormatter.ISO_LOCAL_DATE),
                dto.getPaymentTitle(),
                dto.getAmount(),
                dto.getDescription()
                );

        commandBus.dispatch(asCommandMessage(cmd));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{documentId}")
    public ResponseEntity delete(@PathVariable("documentId") String documentId) {

        val cmd = new ExpenseDocument.ExpenseDocumentDeleteCommand(documentId, userName.get());

        commandBus.dispatch(new GenericCommandMessage<Object>(cmd));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("query")
    public ResponseEntity query() {
        val probe = new ExpenseDocumentEntry();
        probe.setPrincipalName(userName.get());
        val example = Example.of(probe);

        val items = documentQuery.findAll(example).stream()
                .map(it -> {
                    val item = new ExpenseDocumentDto();
                    item.setSubjectName(it.getSubjectName());
                    item.setCustomerName(it.getCustomerName());
                    item.setPaymentDate(it.getPaymentDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    item.setPaymentTitle(it.getPaymentTitle());
                    item.setAmount(it.getAmount());
                    item.setDescription(it.getDescription());
                    val location = locationSupplier.apply(it.getDocumentId());
                    return new QueryItemDto(location, item);
                }).toArray(QueryItemDto[]::new);

        return ResponseEntity.ok(items);
    }

}
