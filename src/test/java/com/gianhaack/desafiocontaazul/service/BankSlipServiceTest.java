package com.gianhaack.desafiocontaazul.service;

import com.gianhaack.desafiocontaazul.dto.BankSlipDTO;
import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.repository.BankSlipRepository;
import com.gianhaack.desafiocontaazul.service.exceptions.BankslipNotProvidedException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankSlipServiceTest {

    @InjectMocks
    private BankSlipService service;

    @Mock
    private BankSlipRepository repository;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveBankslip() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setDueDate(new Date());
        bankSlip.setTotalInCents(999L);
        bankSlip.setCustomer("Test Customer");

        Mockito.when(service.save(bankSlip)).thenReturn(bankSlip);
        BankSlip bankSlipSaved = service.save(bankSlip);
        Assert.assertEquals(bankSlip.getId(), bankSlipSaved.getId());
    }

    @Test(expected = BankslipNotProvidedException.class)
    public void shouldNotSaveBankslip() {
        BankSlip bankSlip = new BankSlip();
        Mockito.when(service.save(bankSlip)).thenThrow(BankslipNotProvidedException.class);
        service.save(bankSlip);
    }

    @Test
    public void shouldFindByUUID() {
        Optional<BankSlip> bankslipOptional = Optional.of(new BankSlip());
        UUID uuid = UUID.randomUUID();

        Mockito.when(repository.findById(uuid)).thenReturn(bankslipOptional);
        BankSlip returnedBankslip = service.findByUuid(uuid);
        Assert.assertEquals(bankslipOptional.get(), returnedBankslip);
    }

    @Test(expected = BankslipNotProvidedException.class)
    public void shouldNotFindByUUID() {
        Mockito.when(repository.findById(UUID.randomUUID())).thenThrow(BankslipNotProvidedException.class);
        service.findByUuid(UUID.randomUUID());
    }

}
