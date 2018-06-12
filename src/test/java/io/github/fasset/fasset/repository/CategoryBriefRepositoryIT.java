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
package io.github.fasset.fasset.repository;

import io.github.fasset.fasset.config.MoneyProperties;
import io.github.fasset.fasset.model.brief.CategoryBrief;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryBriefRepositoryIT {

    CategoryBrief categoryBrief;
    @Qualifier("categoryBriefRepository")
    @Autowired
    private CategoryBriefRepository categoryBriefRepository;
    @Autowired
    private MoneyProperties moneyProperties;


    @Before
    public void setUp() throws Exception {

        categoryBrief = new CategoryBrief();
        categoryBrief.setAccruedDepreciation(Money.of(560, moneyProperties.getDefaultCurrency()));
        categoryBrief.setDesignation("COMPUTERS");
        categoryBrief.setPoll(5);
        categoryBrief.setPurchaseCost(Money.of(1000, moneyProperties.getDefaultCurrency()));
        categoryBrief.setNetBookValue(Money.of(440, moneyProperties.getDefaultCurrency()));
    }

    @Test
    public void categoryRepositoryWorks() throws Exception {

        assertNotNull(categoryBriefRepository.save(categoryBrief));

        /*CategoryBrief savedCategoryBrief = categoryBriefRepository.findDistinctByDesignation("COMPUTERS");

        //assertEquals(1000,categoryBrief.getPurchaseCost(),0.0);
        assertNotNull(savedCategoryBrief.getId());*/
    }
}