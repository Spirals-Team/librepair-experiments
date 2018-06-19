package com.ibanity.apis.client.sandbox.services;

import com.ibanity.apis.client.AbstractServiceTest;
import com.ibanity.apis.client.exceptions.ResourceNotFoundException;
import com.ibanity.apis.client.models.FinancialInstitution;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionAccount;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FinancialInstitutionAccountsServiceImpl Tester.
 *
 * @author Daniel De Luca
 * @version 1.0
 * @since <pre>Jun 13, 2018</pre>
 */
public class FinancialInstitutionAccountsServiceTest extends AbstractServiceTest {

    @BeforeEach
    public void before() {
    }

    @AfterEach
    public void after() {
    }

    /**
     * Method: getFinancialInstitutionAccount(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId)
     */
    @Test
    public void testGetFinancialInstitutionAccount() throws Exception {
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();

        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());

        FinancialInstitutionAccount financialInstitutionAccountGet = financialInstitutionAccountsService.getFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());

        assertTrue(financialInstitutionAccountGet.equals(financialInstitutionAccount));

        financialInstitutionAccountsService.deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }

    /**
     * Method: getFinancialInstitutionUserAccounts(UUID financialInstitutionId, UUID financialInstitutionUserId)
     */
    @Test
    public void testGetFinancialInstitutionUserAccounts() throws Exception {
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();

        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());

        List<FinancialInstitutionAccount> financialInstitutionAccounts = financialInstitutionAccountsService.getFinancialInstitutionUserAccounts(financialInstitution.getId(), financialInstitutionUser.getId());
        
        assertTrue(financialInstitutionAccounts.size() > 0);

        financialInstitutionAccountsService.deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }

    /**
     * Method: createFinancialInstitutionAccount(UUID financialInstitutionId, UUID financialInstitutionUserId, FinancialInstitutionAccount financialInstitutionAccount)
     */
    @Test
    public void testCreateFinancialInstitutionAccount() throws Exception {
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();

        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());
        FinancialInstitutionAccount financialInstitutionGet = financialInstitutionAccountsService.getFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());

        assertTrue(financialInstitutionAccount.getCurrency().equals(financialInstitutionGet.getCurrency()));
        assertTrue(financialInstitutionAccount.getReference().equals(financialInstitutionGet.getReference()));
        assertTrue(financialInstitutionAccount.getDescription().equals(financialInstitutionGet.getDescription()));
        assertTrue(financialInstitutionAccount.getReferenceType().equals(financialInstitutionGet.getReferenceType()));
        assertTrue(financialInstitutionAccount.getSubType().equals(financialInstitutionGet.getSubType()));
        assertTrue(financialInstitutionAccount.getFinancialInstitution().getId().equals(financialInstitution.getId()));

        financialInstitutionAccountsService.deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }

    /**
     * Method: createFinancialInstitutionAccount(UUID financialInstitutionId, UUID financialInstitutionUserId, FinancialInstitutionAccount financialInstitutionAccount)
     */
    @Test
    public void testCreateFinancialInstitutionAccountWithWrongIDs() throws Exception {
        FinancialInstitution financialInstitution = new FinancialInstitution();
        financialInstitution.setId(UUID.randomUUID());
        assertThrows(ResourceNotFoundException.class, () -> createFinancialInstitutionAccount(financialInstitution, UUID.randomUUID()));
    }

    /**
     * Method: deleteFinancialInstitutionAccount(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId)
     */
    @Test
    public void testDeleteFinancialInstitutionAccount() throws Exception {
        FinancialInstitution financialInstitution = createFinancialInstitution();
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();

        FinancialInstitutionAccount financialInstitutionAccount = createFinancialInstitutionAccount(financialInstitution, financialInstitutionUser.getId());

        financialInstitutionAccountsService.deleteFinancialInstitutionAccount(financialInstitution.getId(), financialInstitutionUser.getId(), financialInstitutionAccount.getId());
        deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        deleteFinancialInstitution(financialInstitution.getId());
    }

    /**
     * Method: deleteFinancialInstitutionAccount(UUID financialInstitutionId, UUID financialInstitutionUserId, UUID financialInstitutionAccountId)
     */
    @Test
    public void testDeleteFinancialInstitutionAccountWithWrongIDs() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionAccountsService.deleteFinancialInstitutionAccount(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    }
}
