/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.service.impl;

import io.github.fasset.fasset.kernel.util.DataRetrievalFromControllerException;
import io.github.fasset.fasset.model.brief.ServiceOutletBrief;
import io.github.fasset.fasset.repository.ServiceOutletBriefRepository;
import io.github.fasset.fasset.service.ServiceOutletBriefService;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * {@link ServiceOutletBriefService} implementation
 */
@Service("serviceOutletBriefService")
public class ServiceOutletBriefServiceImpl implements ServiceOutletBriefService {

    private static final Logger log = LoggerFactory.getLogger(ServiceOutletBriefServiceImpl.class);


    private final ServiceOutletBriefRepository serviceOutletBriefRepository;

    @Autowired
    public ServiceOutletBriefServiceImpl(@Qualifier("serviceOutletBriefRepository") ServiceOutletBriefRepository serviceOutletBriefRepository) {
        this.serviceOutletBriefRepository = serviceOutletBriefRepository;
    }

    /**
     * @return {@link List} of {@link ServiceOutletBrief} entities from repository
     */
    @Override
    public List<ServiceOutletBrief> fetchAllServiceOutletBriefs() {

        log.info("Fetching a list of ServiceOutletBriefs... ");

        List<ServiceOutletBrief> serviceOutletBriefs;

        try {
            serviceOutletBriefs = serviceOutletBriefRepository.findAll();
        } catch (Exception e) {
            String message = "Exception encountered while retrieving serviceOutletBriefs from repository";
            throw new DataRetrievalFromControllerException(message, e);
        }

        log.info("Returning : {} serviceOutletBriefs", serviceOutletBriefs.size());

        return serviceOutletBriefs;
    }

    /**
     * @param id of the serviceOutletBrief
     * @return {@link ServiceOutletBrief} of the nomenclature given as parameter
     */
    @Override
    @Cacheable("serviceOutletBriefsByIds")
    public ServiceOutletBrief fetchServiceOutletBriefGivenId(int id) {

        ServiceOutletBrief serviceOutletBrief;

        try {
            serviceOutletBrief = Objects.requireNonNull(serviceOutletBriefRepository.findById(id)).get();
        } catch (Throwable e) {
            String message = "Exception encountered while extracting data from the serviceOutletBriefRepository";
            throw new DataRetrievalFromControllerException(message, e);
        }

        log.debug("Returning serviceOutletBrief : {}", serviceOutletBrief);

        return serviceOutletBrief;
    }

    /**
     * Save all ServiceOutletBrief items in the collection. All existing items are updated while new ones
     * are newly added, to the ends that the designation remains unique
     *
     * @param serviceOutletBriefs to be persisted to the serviceOutletBriefsRepository
     */
    @Override
    public void saveAllServiceOutletBriefItems(Iterable<ServiceOutletBrief> serviceOutletBriefs) {

        List<ServiceOutletBrief> unsavedItems = FastList.newList();

        for (ServiceOutletBrief brief : serviceOutletBriefs) {

            ServiceOutletBrief persistedBrief = serviceOutletBriefRepository.findDistinctByDesignation(brief.getDesignation());

            if (persistedBrief != null) {

                persistedBrief.setDesignation(brief.getDesignation());
                persistedBrief.setPurchaseCost(brief.getPurchaseCost());
                persistedBrief.setNetBookValue(brief.getNetBookValue());
                persistedBrief.setAccruedDepreciation(brief.getAccruedDepreciation());
                persistedBrief.setPoll(brief.getPoll());
            } else {

                unsavedItems.add(brief);
            }
        }

        serviceOutletBriefRepository.saveAll(unsavedItems);
    }
}
