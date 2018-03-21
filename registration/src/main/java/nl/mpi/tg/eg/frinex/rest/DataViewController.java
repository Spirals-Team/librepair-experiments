package nl.mpi.tg.eg.frinex.rest;

import java.util.List;
import nl.mpi.tg.eg.frinex.model.ScreenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
/**
 * @since Jul 13, 2015 11:28:43 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class DataViewController {

    @Autowired
    private ScreenDataRepository screenDataRepository;

    @RequestMapping("dataviewer")
    public String dataViewer(Model model) {
        final List<ScreenData> distinctRecords = this.screenDataRepository.findAllDistinctRecords();
        model.addAttribute("count", distinctRecords.size());
        model.addAttribute("allScreenData", distinctRecords);
        return "dataviewer";
    }

//    @ModelAttribute("allScreenData")
//    public List<ScreenData> findAll() {
//        return this.screenDataRepository.findAll();
//    }
}
