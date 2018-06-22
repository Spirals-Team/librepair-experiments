package com.ibanity.apis.client.services;

import com.ibanity.apis.client.exceptions.ResourceNotFoundException;
import com.ibanity.apis.client.models.FinancialInstitution;
import com.ibanity.apis.client.paging.IbanityPagingSpec;

import java.util.List;
import java.util.UUID;

/**
 * Service for Financial Institutions related APIs
 */
public interface FinancialInstitutionsService {
    /**
     * Get all Financial Institutions supported by Ibanity
     * @return List of Financial Institutions
     */
    List<FinancialInstitution> getFinancialInstitutions();

    /**
     * Get all Financial Institutions supported by Ibanity using the provided PagingSpec
     * @param pagingSpec The paging specification to be used for gathering the financial institutions list
     * @return List of Financial Institutions
     */
    List<FinancialInstitution> getFinancialInstitutions(IbanityPagingSpec pagingSpec);

    /**
     * Get the details of a specific Financial Institution
     * @param financialInstitutionId the id of the Financial Institution
     * @return The Financial Institution Details
     * @throws ResourceNotFoundException when the provided ID is not known
     */
    FinancialInstitution getFinancialInstitution(UUID financialInstitutionId) throws ResourceNotFoundException;
}
