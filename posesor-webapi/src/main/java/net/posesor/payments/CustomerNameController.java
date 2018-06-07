package net.posesor.payments;

import com.mongodb.annotations.ThreadSafe;
import lombok.val;
import net.posesor.query.settlementaccounts.SettlementAccountEntry;
import net.posesor.query.settlementaccounts.SettlementAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customerName")
@Scope("request")
@ThreadSafe
@SuppressWarnings("unused")
public final class CustomerNameController {

    private final SettlementAccountRepository queryRepository;

    private Supplier<String> userName = () -> {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    };

    @Autowired
    public CustomerNameController(SettlementAccountRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @GetMapping(path = "/search/{hint}")
    public ResponseEntity<List<String>> searchAccountName(@PathVariable String hint) {

        val filtered = queryRepository.findByNameStartsWithIgnoreCaseAndPrincipalName(hint, userName.get());
        val result = filtered.stream()
                .map(SettlementAccountEntry::getName)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
