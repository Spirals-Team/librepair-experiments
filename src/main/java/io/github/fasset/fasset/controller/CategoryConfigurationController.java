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

import io.github.fasset.fasset.model.CategoryConfiguration;
import io.github.fasset.fasset.service.CategoryConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * This Controller provides a template in which a dataTable implementatation will require
 * a response containing a collection of {@link CategoryConfiguration} items from a persistent
 * sink
 */
@Controller
public class CategoryConfigurationController {

    private static final Logger log = LoggerFactory.getLogger(CategoryConfigurationController.class);

    @Autowired
    @Qualifier("categoryConfigurationService")
    private CategoryConfigurationService categoryConfigurationService;

    @GetMapping("/categories")
    public String categories(Model model) {

        model.addAttribute("categories", categoryConfigurationService.getAllCategoryConfigurations());

        model.addAttribute("category", new CategoryConfiguration());

        return "forms/category";
    }

    @PostMapping(value = "/categories")
    public String newCategory(@Valid @ModelAttribute CategoryConfiguration category, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            log.error("{} error(s) when saving the user-generated data {}", bindingResult.getErrorCount(), bindingResult.getAllErrors());

            return "forms/category";
        } else {
            categoryConfigurationService.saveCategoryConfiguration(category);
        }

        return "redirect:/categories";
    }

    @GetMapping(value = "/categories/{nomenclature}/edit")
    public String handleEditGet(@PathVariable int id, Model model) {

        log.info("Editing categaory nomenclature : {}", id);

        model.addAttribute("categories", categoryConfigurationService.getAllCategoryConfigurations());

        model.addAttribute("category", categoryConfigurationService.getCategoryConfigurationById(id));

        return "forms/category";
    }

    /*@RequestMapping(value="/category/{nomenclature}/edit", method = RequestMethod.POST)
    public String handleEdit(@Valid @ModelAttribute CategoryConfiguration category, BindingResult bindingResult){

        categoryConfigurationService.updateCategoryConfiguration(category);

        return "redirect:/categories";
    }*/
}
