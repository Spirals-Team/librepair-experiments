package com.ibanity.apis.client.sandbox.services;

import com.ibanity.apis.client.AbstractServiceTest;
import com.ibanity.apis.client.exceptions.ResourceNotFoundException;
import com.ibanity.apis.client.paging.IbanityPagingSpec;
import com.ibanity.apis.client.sandbox.models.FinancialInstitutionUser;
import com.ibanity.apis.client.sandbox.services.impl.FinancialInstitutionUsersServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FinancialInstitutionUsersServiceImpl Tester.
 *
 * @author Daniel De Luca
 * @version 1.0
 * @since <pre>Jun 13, 2018</pre>
 */
public class FinancialInstitutionUsersServiceTest extends AbstractServiceTest {

    private static final FinancialInstitutionUsersService financialInstitutionUsersService = new FinancialInstitutionUsersServiceImpl();

    @BeforeEach
    public void before() {
    }

    @AfterEach
    public void after() {
    }

    /**
     * Method: getFinancialInstitutionUsers()
     */
    @Test
    public void testGetFinancialInstitutionUsers() throws ResourceNotFoundException {
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        List<FinancialInstitutionUser> financialInstitutionUsers = financialInstitutionUsersService.getFinancialInstitutionUsers();
        assertTrue(financialInstitutionUsers.size() > 0);
        financialInstitutionUsersService.deleteFinancialInstitutionUser(financialInstitutionUser.getId());
    }

    /**
     * Method: getFinancialInstitutionUsers(IbanityPagingSpec pagingSpec)
     */
    @Test
    public void testGetFinancialInstitutionUsersPagingSpec() throws ResourceNotFoundException {
        List<FinancialInstitutionUser> financialInstitutionUsers =  new ArrayList<>();
        for( int index = 0; index < 3; index ++){
            financialInstitutionUsers.add(createFinancialInstitutionUser());
        }

        IbanityPagingSpec pagingSpec = new IbanityPagingSpec();
        pagingSpec.setLimit(3L);
        List<FinancialInstitutionUser> financialInstitutionUsersList = financialInstitutionUsersService.getFinancialInstitutionUsers(pagingSpec);
        assertTrue(financialInstitutionUsersList.size() == 3);
        for (FinancialInstitutionUser financialInstitutionUser :  financialInstitutionUsers){
            financialInstitutionUsersService.deleteFinancialInstitutionUser(financialInstitutionUser.getId());
        }
    }

    /**
     * Method: getFinancialInstitutionUser(UUID financialInstitutionUserId)
     */
    @Test
    public void testGetFinancialInstitutionUser() throws ResourceNotFoundException {
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        FinancialInstitutionUser financialInstitutionUserGet = financialInstitutionUsersService.getFinancialInstitutionUser(financialInstitutionUser.getId());
        financialInstitutionUsersService.deleteFinancialInstitutionUser(financialInstitutionUserGet.getId());
        assertTrue(financialInstitutionUserGet.equals(financialInstitutionUser));
    }

    /**
     * Method: getFinancialInstitutionUser(UUID financialInstitutionUserId)
     */
    @Test
    public void testGetFinancialInstitutionUserUnknown() throws ResourceNotFoundException {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionUsersService.getFinancialInstitutionUser(UUID.randomUUID()));
    }

    /**
     * Method: createFinancialInstitutionUser(FinancialInstitutionUser financialInstitutionUser)
     */
    @Test
    public void testCreateFinancialInstitutionUser() throws Exception {
        testGetFinancialInstitutionUser();
    }

    /**
     * Method: updateFinancialInstitutionUser(FinancialInstitutionUser financialInstitutionUser)
     */
    @Test
    public void testUpdateFinancialInstitutionUser() throws ResourceNotFoundException {
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        financialInstitutionUser.setPassword("Password");
        FinancialInstitutionUser updatedFinancialInstitutionUser = financialInstitutionUsersService.updateFinancialInstitutionUser(financialInstitutionUser);
        assertTrue(updatedFinancialInstitutionUser.getPassword().equals(financialInstitutionUser.getPassword()));
        assertTrue(updatedFinancialInstitutionUser.getFirstName().equals(financialInstitutionUser.getFirstName()));
        assertTrue(updatedFinancialInstitutionUser.getLastName().equals(financialInstitutionUser.getLastName()));
        assertTrue(updatedFinancialInstitutionUser.getLogin().equals(financialInstitutionUser.getLogin()));
        assertTrue(updatedFinancialInstitutionUser.getCreatedAt().equals(financialInstitutionUser.getCreatedAt()));
        assertFalse(updatedFinancialInstitutionUser.getUpdatedAt().equals(financialInstitutionUser.getUpdatedAt()));
        assertTrue(updatedFinancialInstitutionUser.getUpdatedAt().isAfter(financialInstitutionUser.getUpdatedAt()));
        assertNull(updatedFinancialInstitutionUser.getDeletedAt());
        financialInstitutionUsersService.deleteFinancialInstitutionUser(financialInstitutionUser.getId());
    }

    /**
     * Method: deleteFinancialInstitutionUser(UUID financialInstitutionUserId)
     */
    @Test
    public void testDeleteFinancialInstitutionUser() throws Exception {
        FinancialInstitutionUser financialInstitutionUser = createFinancialInstitutionUser();
        financialInstitutionUsersService.deleteFinancialInstitutionUser(financialInstitutionUser.getId());
    }

    /**
     * Method: deleteFinancialInstitutionUser(UUID financialInstitutionUserId)
     */
    @Test
    public void testDeleteUnkownFinancialInstitutionUser() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> financialInstitutionUsersService.deleteFinancialInstitutionUser(UUID.randomUUID()));
    }
}
