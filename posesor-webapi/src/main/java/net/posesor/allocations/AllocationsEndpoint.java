package net.posesor.allocations;

import io.reactivex.Observable;
import lombok.SneakyThrows;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.AllocationAggregate;
import net.posesor.query.*;
import net.posesor.query.subjects.SubjectEntry;
import net.posesor.query.subjects.SubjectRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryMessage;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * Exposes with HTTP protocol group of methods related to subjectcharges - related operations.
 */
@RestController
@RequestMapping(value = "api/allocations")
@Slf4j
public final class AllocationsEndpoint {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private QueryBus queryBus;

    @Autowired
    private SubjectRepository subjectQuery;

    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    @GetMapping("options")
    @ResponseBody
    @SneakyThrows
    public CompletableFuture<AvailableAllocationsDto> getOptions() {
        return AvailableAllocationsObtainer.of(queryBus, userName.get());
    }

    /**
     * Retrieves all documents which covers (even partially) given context provided by input parameters
     * and they are still not have been cleared by Clearing option.
     *
     * In other words, this is a list of candidates for clearing operation.
     * @param subjectName Subject name
     * @param paymentTitle Payment title
     * @param from
     * @param to
     */
    @GetMapping("documents")
    @ResponseBody
    @SneakyThrows
    public AllocationDocumentsDto getDocuments(@RequestParam String subjectName,
                                               @RequestParam String paymentTitle,
                                               @DateTimeFormat(pattern = "yyyy-MM") @RequestParam(required=false) YearMonth from,
                                               @DateTimeFormat(pattern = "yyyy-MM") @RequestParam(required=false) YearMonth to) {
        val queryMessage = new GenericQueryMessage<UnallocatedDocumentsQuery, List<UnallocatedDocumentsView>>(
                new UnallocatedDocumentsQuery(userName.get()),
                ResponseTypes.multipleInstancesOf(UnallocatedDocumentsView.class));

        // TODO make async with Flux
        val items = queryBus.query(queryMessage).get().getPayload();

        val dtoItems = items.stream()
                .map(it ->
                        new AllocationDocumentsDto.DocumentData(it.getChargesTotal(),
                                it.getExpensesTotal(),
                                AllocationDocumentsDto.YearMonth.of(it.getPeriod().getYear(), it.getPeriod().getMonthValue()))
                ).toArray(AllocationDocumentsDto.DocumentData[]::new);

        val result = new AllocationDocumentsDto();
        result.setSubjectName(subjectName);
        result.setPaymentTitle(paymentTitle);

        result.setItems(dtoItems);

        return result;
    }

    @PostMapping("calculateCostsAllocation")
    @SneakyThrows
    public ResponseEntity settle(@RequestBody AllocateCostsDto dto) {

        val periodFrom = YearMonth.of(dto.getYearFrom(), dto.getMonthFrom());
        val periodTo = YearMonth.of(dto.getYearTo(), dto.getMonthTo());
        val paymentTitle = dto.getPaymentTitle();
        val operationDate = LocalDate.of(dto.getOperationYear(), dto.getOperationMonth(), dto.getOperationDay());
        val subjectName = dto.getSubjectName();
        val principalName = userName.get();

        val probe = new SubjectEntry();
        probe.setName(subjectName);
        probe.setPrincipalName(principalName);
        val example = Example.of(probe);
        val subjectId = subjectQuery.findOne(example).getSubjectId();
        if (subjectId == null) return ResponseEntity.notFound().build();

        val cmd = new AllocationAggregate.CreateCommand(principalName, UUID.randomUUID().toString(), subjectId, paymentTitle,
                periodFrom, periodTo, operationDate);
        commandBus.dispatch(asCommandMessage(cmd));

        return ResponseEntity.ok().build();
    }

}
