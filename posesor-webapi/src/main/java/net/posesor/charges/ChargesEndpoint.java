package net.posesor.charges;

import com.google.common.collect.ImmutableMap;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.UnallocatedChargeDocument;
import net.posesor.UnallocatedChargeDocumentDeleteCommand;
import net.posesor.query.chargedocuments.ChargeDocumentEntry;
import net.posesor.query.chargedocuments.ChargeDocumentRepository;
import net.posesor.runtime.LocationBuilder;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * Exposes with HTTP protocol group of methods about charges - related operations.
 */
@RestController
@RequestMapping(value = "/api/charges")
@Slf4j
@Scope("session")
public final class ChargesEndpoint {

    private final CommandBus commandBus;
    private final Function<String, URI> locationSupplier;
    private ChargeDocumentRepository chargeDocumentQuery;

    private Supplier<String> userName = () -> {
            val authentication = SecurityContextHolder.getContext().getAuthentication();
            return (String) authentication.getPrincipal();
    };

    @Autowired
    public ChargesEndpoint(CommandBus commandBus, ChargeDocumentRepository chargeDocumentQuery) {
        this(commandBus, chargeDocumentQuery, new LocationBuilder<>(ChargesEndpoint.class));
    }

    public ChargesEndpoint(CommandBus commandBus, ChargeDocumentRepository repository, Function<String, URI> locationSupplier) {
        this.commandBus = commandBus;
        this.chargeDocumentQuery = repository;
        this.locationSupplier = locationSupplier;
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid ChargeDocumentDto createDto) {
        val documentId = UUID.randomUUID().toString();
        val location = locationSupplier.apply(documentId);
        val principalName = userName.get();
        val cmd = new UnallocatedChargeDocument.CreateCommand(principalName, documentId,
                Optional.ofNullable(createDto.getCustomerName()).orElse("Nazwa klienta"),
                Optional.ofNullable(createDto.getSubjectName()).orElse("Nazwa wspólnoty"),
                LocalDate.parse(Optional.ofNullable(createDto.getPaymentDate()).orElse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))),
                Optional.ofNullable(createDto.getPaymentTitle()).orElse("Tytuł płatności"),
                Optional.ofNullable(createDto.getAmount()).orElse(BigDecimal.ZERO));

        val metadata = ImmutableMap.of("principalName", userName.get(), "location", location.toString());
        commandBus.dispatch(new GenericCommandMessage<>(cmd, metadata));

        return ResponseEntity.created(location).build();
    }

    /***
     * put method is designed for update only. creation is not supported (maybe in the future with a bigger group of devs :)
     * @param documentId
     * @param updateDto
     * @return
     */
    @PutMapping("/{chargeId}")
    @ResponseBody
    public ResponseEntity put(@PathVariable("chargeId") String documentId, @RequestBody ChargeDocumentDto updateDto) {
        val location = locationSupplier.apply(documentId);
        val cmd = new UnallocatedChargeDocument.UpdateCommand(documentId,
                Optional.ofNullable(updateDto.getCustomerName()).orElse("Nazwa klienta"),
                Optional.ofNullable(updateDto.getSubjectName()).orElse("Nazwa wspólnoty"),
                LocalDate.parse(Optional.ofNullable(updateDto.getPaymentDate()).orElse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))),
                Optional.ofNullable(updateDto.getPaymentTitle()).orElse("Tytuł płatności"),
                Optional.ofNullable(updateDto.getAmount()).orElse(BigDecimal.ZERO));

        val metadata = ImmutableMap.of("principalName", userName.get(), "location", location.toString());
        commandBus.dispatch(new GenericCommandMessage<>(cmd, metadata));

        return ResponseEntity.ok(location);
    }

    @GetMapping("/template")
    public ResponseEntity getTemplate() {
        return ResponseEntity.ok(new ChargeDocumentDto());
    }

    @GetMapping("/{chargeId}")
    public ResponseEntity get(@PathVariable("chargeId") String chargeId) {
        val entry = chargeDocumentQuery.findOne(chargeId);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }

        val dto = new ChargeDocumentDto();
        dto.setChargeId(entry.getDocumentId());
        dto.setCustomerName(entry.getCustomerName());
        dto.setSubjectName(entry.getSubjectName());
        dto.setPaymentDate(entry.getPaymentDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        dto.setPaymentTitle(entry.getPaymentTitle());
        dto.setAmount(entry.getAmount());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deletePaymentBySubjectName(@PathVariable("documentId") String documentId) {
        val principalName = userName.get();
        commandBus.dispatch(asCommandMessage(new UnallocatedChargeDocumentDeleteCommand(documentId, principalName)));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/search/query")
    @ResponseBody
    public int searchQuery(@RequestBody QueryDto query) {
        return 0;
    }


    @GetMapping(value = "/search/execute/{queryId}")
    @ResponseBody
    public QueryItem[] searchExecute(@PathVariable("queryId") int queryId) {

        val probe = new ChargeDocumentEntry();
        probe.setPrincipalName(userName.get());

        val example = Example.of(probe);

        val items = this.chargeDocumentQuery.findAll(example);

        return items.stream()
                .map(it -> {
                    val dto = new ChargeDocumentDto();
                    dto.setChargeId(it.getDocumentId());
                    dto.setCustomerName(it.getCustomerName());
                    dto.setSubjectName(it.getSubjectName());
                    dto.setPaymentDate(it.getPaymentDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    dto.setPaymentTitle(it.getPaymentTitle());
                    dto.setAmount(it.getAmount());

                    return new ChargesEndpoint.QueryItem(
                            locationSupplier.apply(it.getDocumentId()), dto);
                })
                .toArray(QueryItem[]::new);
    }


    @Value
    public static class QueryDto {
        // This QueryDto represents query filter used to query 'Return all items'
        // so intentionally doesn't contains any filtering-related fields


        // dummy field to avoid serialization exception
        // com.fasterxml.jackson.databind.JsonMappingException: No serializer found for class ChargesEndpoint$QueryDto
        // and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
        private String ignore;
    }

    @Value
    public static class QueryItem {
        private URI location;
        private ChargeDocumentDto item;
    }
}
