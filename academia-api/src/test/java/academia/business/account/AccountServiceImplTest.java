package academia.business.account;

import academia.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ValidationException;

/**
 * Created by rafael on 4/8/18.
 */
//@ContextConfiguration
@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    private AccountService accountService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AccountRepository accountRepository;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl();
        accountRepository = Mockito.mock(AccountRepository.class);

        // injeta dependências
        ReflectionTestUtils.setField(accountService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(accountService, "accountRepository", accountRepository);
    }

    @Test
    public void deve_falhar_se_senha_nao_foi_preenchida() {
        // arrange
        Account account = new Account();

        try {
            // act
            Account created = accountService.create(account);
            Assert.fail("Deve falhar se senha não foi preenchida");
        } catch (ValidationException e) {
            // assert
            Assert.assertEquals(e.getMessage(), "Senha deve ser preenchida");
        }
    }

    @Test
    public void deve_salvar_se_dados_estao_ok() {
        // arrange
        Account account = new Account();
        account.setPassword("abc123");

        // act
        Account created = accountService.create(account);

        // assert
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
    }
}
