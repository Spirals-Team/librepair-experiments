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
package io.github.fasset.fasset.kernel.batch.depreciation.batch;

import io.github.fasset.fasset.kernel.batch.depreciation.model.DepreciationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Object generates months in  an increasing order in which depreciation is supposed to be
 * pre-calculated
 */
@Component("depreciationRelay")
public class DepreciationRelay {

    private static final Logger log = LoggerFactory.getLogger(DepreciationRelay.class);

    private final MonthlyIncrementer monthlyIncrementer;

    private final DepreciationProperties depreciationProperties;

    private List<YearMonth> monthlySequence = new LinkedList<>();

    @Autowired
    public DepreciationRelay(@Qualifier("MonthlyIncrementer") MonthlyIncrementer monthlyIncrementer, DepreciationProperties depreciationProperties) {
        this.monthlyIncrementer = monthlyIncrementer;
        this.depreciationProperties = depreciationProperties;
    }

    public List<YearMonth> getMonthlyDepreciationSequence() {

        return monthlySequence;
    }

    @PostConstruct
    private List<YearMonth> generateMonthlyDepreciationSequence() {

        YearMonth from = depreciationProperties.getStartMonth();
        YearMonth to = depreciationProperties.getStopMonth();

        log.debug("Producing depreciation relay...between : {} and : {}", from, to);

        monthlySequence.add(from);

        long noOfMonths = from.until(to, ChronoUnit.MONTHS);

        log.debug("Creating a monthly depreciation sequence for : {} months", noOfMonths + 1);

        LongStream.range(0, noOfMonths).mapToObj(i -> monthlyIncrementer.getNext(from.plusMonths(i))).forEachOrdered(monthSeq -> {
            log.trace("Adding the month : {} to the sequence", monthSeq);
            monthlySequence.add(monthSeq);
        });

        return monthlySequence;
    }
}
