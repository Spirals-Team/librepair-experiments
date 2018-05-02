package net.posesor.allocations;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.AllocationAggregate;
import net.posesor.accountcharges.UnsettledAccountFinancialEntry;
import net.posesor.accountcharges.UnsettledAccountFinancialRepository;
import net.posesor.SubjectEntry;
import net.posesor.SubjectRepository;
import net.posesor.subjectcharges.UnallocatedPeriod;
import net.posesor.subjectcharges.UnallocatedPeriodRepository;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;
import java.util.function.Supplier;

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
    private UnallocatedPeriodRepository subjectFinancialQueryRepository;

    @Autowired
    private UnsettledAccountFinancialRepository accountFinancialQueryRepository;

    @Autowired
    private SubjectRepository subjectQuery;

    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    @GetMapping("options")
    @ResponseBody
    public AvailableAllocationsDto getOptions() {
        val principalName = userName.get();

        val probe = new UnallocatedPeriod();
        probe.setPrincipalName(principalName);
        val example = Example.of(probe);

        val items = subjectFinancialQueryRepository.findAll(example);
        val result = items.stream()
                .map(it -> new AvailableAllocationsDto.SubjectDto(
                        subjectQuery.findOne(it.getSubjectId()).getName(),
                        it.getPaymentTitle(),
                        new AvailableAllocationsDto.PeriodDto(it.getYear(), it.getMonth()),
                        new AvailableAllocationsDto.PeriodDto(it.getYear(), it.getMonth())));
        val resultAsArray = result.toArray(AvailableAllocationsDto.SubjectDto[]::new);
        return new AvailableAllocationsDto(resultAsArray);
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
    public AllocationsDefinitionDto getDocuments(@RequestParam String subjectName,
                                                 @RequestParam String paymentTitle,
                                                 @DateTimeFormat(pattern = "yyyy-MM") @RequestParam(required=false) YearMonth from,
                                                 @DateTimeFormat(pattern = "yyyy-MM") @RequestParam(required=false) YearMonth to) {
        val example = new UnsettledAccountFinancialEntry();
        example.setPrincipalName(userName.get());
        val itemsList = accountFinancialQueryRepository.findAll(Example.of(example));
        val items = itemsList.stream()
                .map(it ->
                    AllocationsDefinitionDto.UnsettledPart.of(it.getChargesTotal(), it.getExpensesTotal(), AllocationsDefinitionDto.YearMonth.of(it.getYear(), it.getMonth()))
                ).toArray(AllocationsDefinitionDto.UnsettledPart[]::new);

        val result = new AllocationsDefinitionDto();
        result.setSubjectName(subjectName);
        result.setPaymentTitle(paymentTitle);

        val minValue = itemsList.stream()
                .map(it -> LocalDate.of(it.getYear(), it.getMonth(), 1))
                .min(LocalDate::compareTo)
                .get();

        val maxValue = itemsList.stream()
                .map(it -> LocalDate.of(it.getYear(), it.getMonth(), 1))
                .min(LocalDate::compareTo)
                .get();

        result.setPeriodFrom(AllocationsDefinitionDto.YearMonth.of(minValue.getYear(), minValue.getMonthValue()));
        result.setPeriodTo(AllocationsDefinitionDto.YearMonth.of(maxValue.getYear(), maxValue.getMonthValue()));
        result.setItems(items);

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
