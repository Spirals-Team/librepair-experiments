package academia.controller;

import academia.business.account.AccountRepository;
import academia.business.account.AccountService;
import academia.business.account.PasswordUpdateDto;
import academia.domain.AccountType;
import academia.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/private/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    // TODO validar roles dos usuários nesses requests, apenas role admin pode buscar todos etc...

    @RequestMapping(method = RequestMethod.GET, path = "/settings")
    public Map<String, List> settings() {
        Map<String, List> map = new HashMap<>();
        map.put("accountType", Arrays.asList(AccountType.values()));
        return map;
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Account> all() {
        return accountRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/find/trainers")
    public List<Account> findTrainers() {
        return this.accountService.getTrainers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/find/customers")
    public List<Account> findCustomers() {
        return this.accountService.getCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Account findById(@PathVariable Long id) {
        return accountRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/password/{id}")
    public Account updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDto passwordUpdateDto) {
        return accountService.updatePassword(id, passwordUpdateDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public Account update(@PathVariable Long id, @RequestBody Account account) {
        return accountService.update(account);
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }
}
