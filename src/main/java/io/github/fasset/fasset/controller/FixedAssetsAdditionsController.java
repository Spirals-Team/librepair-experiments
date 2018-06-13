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

import io.github.fasset.fasset.dto.FixedAssetFormDto;
import io.github.fasset.fasset.kernel.util.convert.DoubleToMoneyConverter;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for FixedAssets additions work flows
 */
@Controller
public class FixedAssetsAdditionsController {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetsAdditionsController.class);

    @Autowired
    @Qualifier("fixedAssetService")
    private FixedAssetService fixedAssetService;

    @Autowired
    @Qualifier("doubleToMoneyConverter")
    private DoubleToMoneyConverter doubleToMoneyConverter;

    @GetMapping("/add/asset")
    public String assetAdditionForm(Model model) {

        model.addAttribute("fixedAsset", new FixedAssetFormDto(doubleToMoneyConverter));

        return "forms/addAsset";
    }

    @PostMapping("/add/asset")
    public String saveFixedAssetFromForm(@ModelAttribute FixedAssetFormDto fixedAssetDto, RedirectAttributes redirectAttributes) {

        FixedAsset persistedAsset = null;
        // TODO replace with FixedAssetResponseDto
        if (fixedAssetDto != null) {
            persistedAsset = fixedAssetService.saveFixedAsset(fixedAssetDto.getFixedAsset());
        } else {
            log.error("The framework has passed a null object to the controller, it could not be save");
        }

        String message = String.format("You have successfully added asset # %s", persistedAsset);

        redirectAttributes.addFlashAttribute("successAdd", message);

        return "redirect:/add/asset";
    }
}
