package com.gianhaack.desafiocontaazul.controller;

import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.repository.BankSlipRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BankSlipControllerTest {

    @InjectMocks
    private BankSlipController controller;

    @Mock
    private BankSlipRepository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    public void shouldBankSlipCreate() {

    }

}
