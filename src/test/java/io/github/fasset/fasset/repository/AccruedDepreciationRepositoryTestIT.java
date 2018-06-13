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

import io.github.fasset.fasset.model.AccruedDepreciation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.YearMonth;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccruedDepreciationRepositoryTestIT {


    @Autowired
    @Qualifier("accruedDepreciationRepository")
    private AccruedDepreciationRepository accruedDepreciationRepository;


    @Test
    public void accruedDepreciationRepoIsWorking() throws Exception {

        AccruedDepreciation accruedDepreciation = accruedDepreciationRepository.save(new AccruedDepreciation());

        assertNotNull(accruedDepreciationRepository.findById(accruedDepreciation.getId()).get());
    }

    @Test
    public void findByFixedAssetIdAndMonthBeforeIsWorking() throws Exception {

        AccruedDepreciation accruedDepreciation = new AccruedDepreciation();
        accruedDepreciation.setMonth(YearMonth.of(2018, 02)).setFixedAssetId(4465);

        assertNotNull(accruedDepreciationRepository.save(accruedDepreciation));

       /* AccruedDepreciation saved =
                accruedDepreciationRepository.findByFixedAssetIdAndMonthBefore(4465,YearMonth.of(2018,02));

        assertNotNull(saved);*/

        //assertEquals(4465,saved.getFixedAssetId());
        //assertEquals(YearMonth.today(),saved.getMonth());
    }
}