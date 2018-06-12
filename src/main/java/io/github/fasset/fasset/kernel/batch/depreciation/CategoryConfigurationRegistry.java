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
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.model.CategoryConfiguration;
import io.github.fasset.fasset.model.nil.NilCategoryConfiguration;
import io.github.fasset.fasset.service.CategoryConfigurationService;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Using spring frameworks IOC container, this bean has a map into which the
 * categoryConfigurations are added as they are in the database. The bean also
 * exposes logic for programmatically adding more configurations or editing
 * existing ones
 *
 * @author edwin.njeru
 */
@Component("categoryConfigurationRegistry")
public class CategoryConfigurationRegistry {

    private static final Logger log = LoggerFactory.getLogger(CategoryConfigurationRegistry.class);
    private final CategoryConfigurationService categoryConfigurationService;
    private Map<String, CategoryConfiguration> categoryConfigurationMap = new ConcurrentHashMap<>();

    @Autowired
    public CategoryConfigurationRegistry(CategoryConfigurationService categoryConfigurationService) {
        this.categoryConfigurationService = categoryConfigurationService;
    }


    public CategoryConfiguration getCategoryConfiguration(String categoryName) {

        CategoryConfiguration categoryConfiguration;

        if (categoryConfigurationRegistryDoesNotContain(categoryName)) {
            updateConfigurationRegistry();
        }

        if (categoryConfigurationRegistryDoesNotContain(categoryName)) {
            log.error("The category Named : {} has not been configured in the category configuration repo. Please " + "check your category configurations listing...", categoryName);
            categoryConfiguration = new NilCategoryConfiguration().getCategoryConfiguration();
        }

        categoryConfiguration = categoryConfigurationMap.get(categoryName);

        return categoryConfiguration;
    }

    void addCategoryConfiguration(String categoryName, CategoryConfiguration categoryConfiguration) {
        categoryConfigurationMap.put(categoryName, categoryConfiguration);
    }

    boolean categoryConfigurationRegistryDoesNotContain(String categoryName) {
        return !categoryConfigurationMap.containsKey(categoryName);
    }

    boolean categoryConfigurationRegistryIsEmpty() {
        return categoryConfigurationMap.isEmpty();
    }

    @PostConstruct
    private void updateConfigurationRegistry() {

        if (categoryConfigurationMap.isEmpty()) {

            log.trace("Refreshing the category configuration mapping...");

            categoryConfigurationService.getAllCategoryConfigurations().stream().map(CategoryConfiguration::getDesignation).forEach(categoryName -> {
                log.trace("Registering category : {}", categoryName);
                categoryConfigurationMap.put(categoryName, categoryConfigurationService.getCategoryByName(categoryName));
            });
        }

    }
}
