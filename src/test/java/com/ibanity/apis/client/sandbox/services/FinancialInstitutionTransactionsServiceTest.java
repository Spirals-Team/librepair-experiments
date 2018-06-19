package com.ibanity.apis.client.sandbox.services;

import com.ibanity.apis.client.AbstractServiceTest;
import com.ibanity.apis.client.exceptions.ResourceNotFoundException;
import com.ibanity.apis.client.models.FinancialInstitution;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionAccount;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionTransaction;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionUser;
import com.ibanity.apis.client.sandbox.services.impl.FinancialInstitutionTransactionsServiceImpl;
import org.apache.commons.math3.util.Precision;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FinancialInstitutionTransactionsServiceImpl Tester.
 *
 * @author Daniel De Luca
 * @version 1.0
 * @since <pre>Jun 13, 2018</pre>
 */
public class FinancialInstitutionTransactionsServiceTest extends AbstractServiceTest {

    private static final FinancialInstitutionTransactionsService financialInstitutionTransactionsService = new FinancialInstitutionTransactionsServiceImpl();

    @BeforeEach
    public void before() {
    }

    @AfterEach
    public void after() {
    }

    /**
     * Method: getFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, UUID financialInstitutionTransactionId)
     */
    @Test
    public void testGetFinancialInstitutionTransaction() throws Exception {
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());
        FinancialInstitutionTransaction financialInstitutionTransaction = createFinancialInstitutionTransaction(financialInstitutionUser.getId(), financialInstitutionAccount);
        FinancialInstitutionTransaction financialInstitutionTransactionGet = financialInstitutionTransactionsService.getFinancialInstitutionTransaction(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId(), financialInstitutionTransaction.getId());
        assertTrue(financialInstitutionTransactionGet.getCounterpartName().equals(financialInstitutionTransaction.getCounterpartName()));
        assertTrue(financialInstitutionTransactionGet.getCounterpartReference().equals(financialInstitutionTransaction.getCounterpartReference()));
        assertTrue(financialInstitutionTransactionGet.getCurrency().equals(financialInstitutionTransaction.getCurrency()));
        assertTrue(financialInstitutionTransactionGet.getDescription().equals(financialInstitutionTransaction.getDescription()));
        assertTrue(financialInstitutionTransactionGet.getRemittanceInformation().equals(financialInstitutionTransaction.getRemittanceInformation()));
        assertTrue(financialInstitutionTransactionGet.getRemittanceInformationType().equals(financialInstitutionTransaction.getRemittanceInformationType()));
        assertTrue(financialInstitutionTransactionGet.getAmount().equals(financialInstitutionTransaction.getAmount()));
        assertTrue(financialInstitutionTransactionGet.getExecutionDate().equals(financialInstitutionTransaction.getExecutionDate()));
        assertTrue(financialInstitutionTransactionGet.getValueDate().equals(financialInstitutionTransaction.getValueDate()));

        deleteFinancialInstitutionTransaction(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId(),financialInstitutionTransaction.getId());
        deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }

    /**
     * Method: getFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, UUID financialInstitutionTransactionId)
     */
    @Test
    public void testGetFinancialInstitutionTransactionWithWrongIDs() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionTransactionsService.getFinancialInstitutionTransaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    }

    /**
     * Method: getFinancialInstitutionAccountTransactions(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId)
     */
    @Test
    public void testGetFinancialInstitutionAccountTransactions() throws Exception {
        List<FinancialInstitutionTransaction> financialInstitutionTransactions = new ArrayList<>();
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());
        financialInstitutionTransactions.add(createFinancialInstitutionTransaction(financialInstitutionUser.getId(), financialInstitutionAccount));
        financialInstitutionTransactions.add(createFinancialInstitutionTransaction(financialInstitutionUser.getId(), financialInstitutionAccount));
        financialInstitutionTransactions.add(createFinancialInstitutionTransaction(financialInstitutionUser.getId(), financialInstitutionAccount));

        List<FinancialInstitutionTransaction> financialInstitutionTransactionsList = financialInstitutionTransactionsService.getFinancialInstitutionAccountTransactions(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        assertTrue (financialInstitutionTransactionsList.containsAll(financialInstitutionTransactions));

        for (FinancialInstitutionTransaction financialInstitutionTransaction: financialInstitutionTransactions){
            deleteFinancialInstitutionTransaction(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId(),financialInstitutionTransaction.getId());
        }
        deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }


    /**
     * Method: getFinancialInstitutionAccountTransactions(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId)
     */
    @Test
    public void testGetFinancialInstitutionAccountTransactionsWithWrongIds() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionTransactionsService.getFinancialInstitutionAccountTransactions(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    }

    /**
     * Method: createFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, FinancialInstitutionTransaction financialInstitutionTransaction)
     */
    @Test
    public void testCreateFinancialInstitutionTransaction() throws Exception {
        testGetFinancialInstitutionTransaction();
    }

    /**
     * Method: createFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, FinancialInstitutionTransaction financialInstitutionTransaction)
     */
    @Test
    public void testCreateFinancialInstitutionTransactionWithWrongIds() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionTransactionsService.createFinancialInstitutionTransaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new FinancialInstitutionTransaction()));
    }

    /**
     * Method: deleteFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, UUID financialInstitutionTransactionId)
     */
    @Test
    public void testDeleteFinancialInstitutionTransaction() throws Exception {
        testGetFinancialInstitutionTransaction();
    }

    /**
     * Method: deleteFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, UUID financialInstitutionTransactionId)
     */
    @Test
    public void testDeleteFinancialInstitutionTransactionWithWrongIds() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> deleteFinancialInstitutionTransaction(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    }

    public static void deleteFinancialInstitutionTransaction(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId, UUID financialInstitutionTransactionId) throws ResourceNotFoundException {
        financialInstitutionTransactionsService.deleteFinancialInstitutionTransaction(financialInstitutionId, financialInstitutionUserId, financialInstitutionAccountId, financialInstitutionTransactionId);
    }

    public static FinancialInstitutionTransaction createFinancialInstitutionTransaction(UUID financialInstitutionUserId, FinancialInstitutionAccount financialInstitutionAccount) throws ResourceNotFoundException {
        Instant now = Instant.now();
        Random random = new Random();

        Instant executionDate = now.plus(3, ChronoUnit.DAYS);
        Instant valueDate = now.minus(1, ChronoUnit.DAYS);
        FinancialInstitutionTransaction financialInstitutionTransaction = new FinancialInstitutionTransaction();
        financialInstitutionTransaction.setFinancialInstitutionAccount(financialInstitutionAccount);
        financialInstitutionTransaction.setAmount(Precision.round(random.doubles(10,1000).findFirst().getAsDouble() * (random .nextBoolean() ? -1 : 1), 2));
        financialInstitutionTransaction.setCounterpartName("Stroman, Hettinger and Swift");
        financialInstitutionTransaction.setCounterpartReference(Iban.random(CountryCode.BE).getAccountNumber());
        financialInstitutionTransaction.setCurrency("EUR");
        financialInstitutionTransaction.setDescription("Car rental");
        financialInstitutionTransaction.setExecutionDate(executionDate);
        financialInstitutionTransaction.setRemittanceInformation("Aspernatur et quibusdam.");
        financialInstitutionTransaction.setRemittanceInformationType("unstructured");
        financialInstitutionTransaction.setValueDate(valueDate);
        return financialInstitutionTransactionsService.createFinancialInstitutionTransaction(
                financialInstitutionAccount.getFinancialInstitution().getId()
                , financialInstitutionUserId
                , financialInstitutionAccount.getId()
                , financialInstitutionTransaction
        );
    }

} 
