/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.rest;

import java.util.ArrayList;
import java.util.List;
import nl.mpi.tg.eg.frinex.model.TagPairData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Jul 23, 2015 3:27:13 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class TagPairController {

    @Autowired
    private TagPairRepository tagPairRepository;

    @RequestMapping("tagpairviewer")
    public String tagPairViewer(Model model, @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "sort", required = false, defaultValue = "tagDate") String sortColumn,
            @RequestParam(value = "size", defaultValue = "2000", required = false) Integer size,
            @RequestParam(value = "dir", required = false, defaultValue = "a") String sortDirection) {//, Pageable pageable
        final long count = this.tagPairRepository.count();
        model.addAttribute("count", count);
        final Page<TagPairData> pageData = this.tagPairRepository.findAll(new PageRequest(page, size, ("a".equals(sortDirection)) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn));
        final List<TagPairData> content = pageData.getContent();
        final List<TagPairData> contentDistinct = new ArrayList<>();
        for (TagPairData tagData : content) {
            if (!contentDistinct.contains(tagData)) {
                contentDistinct.add(tagData);
            }
        }
        model.addAttribute("allTagPairData", contentDistinct);
        model.addAttribute("pageData", pageData);
        return "tagpairviewer";
    }
}
