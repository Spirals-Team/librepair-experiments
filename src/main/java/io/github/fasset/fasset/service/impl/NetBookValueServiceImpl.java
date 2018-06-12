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

import io.github.fasset.fasset.model.NetBookValue;
import io.github.fasset.fasset.repository.NetBookValueRepository;
import io.github.fasset.fasset.service.NetBookValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implements the {@link NetBookValueService} interface saving and retrieving {@link NetBookValue}
 * records into the {@link io.github.fasset.fasset.repository.NetBookValueRepository}
 *
 * @author edwin.njeru
 */
@Service("netBookValueService")
@Transactional
public class NetBookValueServiceImpl implements NetBookValueService {


    private final NetBookValueRepository netBookValueRepository;

    @Autowired
    public NetBookValueServiceImpl(@Qualifier("netBookValueRepository") NetBookValueRepository netBookValueRepository) {
        this.netBookValueRepository = netBookValueRepository;
    }

    /**
     * Saves the {@link NetBookValue} object in the param
     *
     * @param netBookValue entity to be saved into the repository
     */
    @Override
    public void saveNetBookValue(NetBookValue netBookValue) {

        netBookValueRepository.save(netBookValue);
    }

    /**
     * Saves a {@link List} collection of all items passed in the parameter to the
     * {@link NetBookValueRepository}
     *
     * @param netBookValues {@link List} of {@link NetBookValue} entities to be saved in the repository
     */
    @Override
    public void saveAllNetBookValueItems(List<? extends NetBookValue> netBookValues) {

        netBookValueRepository.saveAll(netBookValues);

    }

}
