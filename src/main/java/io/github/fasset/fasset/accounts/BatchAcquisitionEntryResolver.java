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
package io.github.fasset.fasset.accounts;

import io.github.fasset.fasset.book.keeper.AccountingEntry;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.ghacupha.cash.HardCash;
import io.github.ghacupha.time.point.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements {@link BatchEntryResolver} interface whose job really is to generate entries based on a list of actual
 * {@code FixedAsset} items passed through the parameter in the main method.
 */
@Component("batchEntryResolver")
public class BatchAcquisitionEntryResolver implements BatchEntryResolver {

    private static final org.slf4j.Logger log = getLogger(BatchAcquisitionEntryResolver.class);

    private AccountResolver accountResolver;

    @Autowired
    public BatchAcquisitionEntryResolver(@Qualifier("acquisitionAccountResolver") AccountResolver accountResolver) {
        this.accountResolver = accountResolver;
    }

    /**
     * Generates {@code AccountingEntry} items based on {@code FixedAsset} items passed in the parameter args
     *
     * @param fixedAssets Collection of {@code FixedAsset} items from which we are to generate entries
     * @return List containing Entry bookings for the fixedAssets passed in the parameter
     */
    @Override
    public List<AccountingEntry> resolveEntries(List<FixedAsset> fixedAssets) {

        log.debug("Resolving entries for : {} fixed assets", fixedAssets.size());

        List<AccountingEntry> entries = new CopyOnWriteArrayList<>();

        fixedAssets.parallelStream().forEach(fixedAsset -> {
            entries.add(getAssetEntry(fixedAsset));
            entries.add(getSundryCreditorEntry(fixedAsset));
        });

        return entries;
    }

    /**
     * Generates the Entry which is to be made to the {@code sundryAccount} in respect of this fixedAsset
     *
     * @param fixedAsset for which we recognise acquisition settlement
     * @return AccountingEntry into the {@code sundryAccount}
     */
    private AccountingEntry getSundryCreditorEntry(FixedAsset fixedAsset) {

        log.debug("Resolving credit account for acquisition of asset: {}", fixedAsset.getAssetDescription());

        AccountingEntry sundryEntry = new AccountingEntry(SimpleDate.ofLocal(fixedAsset.getPurchaseDate()), accountResolver.resolveCreditAccount(fixedAsset), fixedAsset.getAssetDescription(), CREDIT,
            HardCash.fromMoneta(fixedAsset.getPurchaseCost()));

        sundryEntry.setEntryAttributes(getFixedAssetsAttributes(fixedAsset));

        return sundryEntry;
    }


    /**
     * Generates the Entry which is to be made in the {@code fixedAssetAccount} in respect to this fixedAsset
     *
     * @param fixedAsset for which we generate the appropriate {@code DEBIT} entry
     * @return AccountingEntry into the {@code fixedAssetAccount}
     */
    private AccountingEntry getAssetEntry(FixedAsset fixedAsset) {

        log.debug("Resolving debit account for acquisition of asset: {}", fixedAsset.getAssetDescription());

        AccountingEntry fixedAssetEntry =
            new AccountingEntry(SimpleDate.ofLocal(fixedAsset.getPurchaseDate()), accountResolver.resolveDebitAccount(fixedAsset), fixedAsset.getAssetDescription(), DEBIT,
                HardCash.fromMoneta(fixedAsset.getPurchaseCost()));

        fixedAssetEntry.setEntryAttributes(getFixedAssetsAttributes(fixedAsset));

        return fixedAssetEntry;
    }

    private Map<String, String> getFixedAssetsAttributes(FixedAsset fixedAsset) {

        Map<String, String> fixedAssetsAttributes = new ConcurrentSkipListMap<>();
        fixedAssetsAttributes.put("Barcode", fixedAsset.getBarcode());
        fixedAssetsAttributes.put("SolId", fixedAsset.getSolId());
        fixedAssetsAttributes.put("Category", fixedAsset.getCategory());
        fixedAssetsAttributes.put("NetBookValue", fixedAsset.getNetBookValue().toString());

        return fixedAssetsAttributes;
    }
}
