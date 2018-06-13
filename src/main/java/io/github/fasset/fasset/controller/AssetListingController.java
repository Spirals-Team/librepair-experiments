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
package io.github.fasset.fasset.controller;

import io.github.fasset.fasset.dto.FixedAssetResponseDto;
import io.github.fasset.fasset.kernel.util.DataRetrievalFromControllerException;
import io.github.fasset.fasset.kernel.util.ImmutableListCollector;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * This controller generates the template for the asset list and provides Response to the
 * dataTable implementation running in the client, providing with it a collection of
 * all {@link FixedAsset} items in the data sink
 */
@Controller
public class AssetListingController {

    private static final Logger log = LoggerFactory.getLogger(AssetListingController.class);

    private final FixedAssetService fixedAssetService;

    @Autowired
    public AssetListingController(@Qualifier("fixedAssetService") FixedAssetService fixedAssetService) {
        this.fixedAssetService = fixedAssetService;
    }

    @GetMapping("/assetList")
    public String showAssetListTemplate(Model model) {

        return "reports/allAssets";
    }

    @GetMapping("/listing/assets/data")
    @ResponseBody
    public List<FixedAssetResponseDto> fetchAllAssets() {

        List<FixedAsset> fixedAssets;

        try {
            fixedAssets = fixedAssetService.fetchAllExistingAssets();
        } catch (Throwable e) {
            String message = "Exception encountered: Could not extract assets from repo" + "for the path \"/assetList\"";
            throw new DataRetrievalFromControllerException(message, e);
        }

        log.info("Returning a list of : {} assets", fixedAssets.size());

        return fixedAssets.parallelStream().map(FixedAssetResponseDto::new).collect(ImmutableListCollector.toImmutableList());
    }

    @GetMapping("/listing/assets/data/{nomenclature}")
    @ResponseBody
    public FixedAssetResponseDto getMonthGivenId(@PathVariable("nomenclature") int id) {

        FixedAsset asset = null;

        try {
            asset = fixedAssetService.fetchAssetGivenId(id);
        } catch (Throwable e) {
            String message = String.format("Exception encountered when extracting asset with nomenclature# : %s", id);

            throw new DataRetrievalFromControllerException(message, e);
        }

        log.debug("Returning : {}", asset);

        return new FixedAssetResponseDto(asset);
    }
}
