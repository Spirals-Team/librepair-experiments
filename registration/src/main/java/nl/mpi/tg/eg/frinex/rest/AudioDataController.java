/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.util.List;
import nl.mpi.tg.eg.frinex.model.AudioData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Aug 13, 2018 5:16:42 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class AudioDataController {

    @Autowired
    private AudioDataRepository audioDataRepository;

    @RequestMapping(value = "audio/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public HttpEntity<byte[]> participantListing(@PathVariable("id") long id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("audio", "ogg"));
        final byte[] dataBlob = this.audioDataRepository.findOne(id).getDataBlob();
        header.setContentLength(dataBlob.length);
        return new HttpEntity<>(dataBlob, header);
    }

    @RequestMapping("audiolisting")
    public String participantListing(Model model,
            @RequestParam(value = "sort", required = false, defaultValue = "submitDate") String sortColumn,
            @RequestParam(value = "dir", required = false, defaultValue = "a") String sortDirection,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "2000", required = false) Integer size) {
        model.addAttribute("count", this.audioDataRepository.count());
        final Page<AudioData> pageData = this.audioDataRepository.findAll(new PageRequest(page, size, ("a".equals(sortDirection)) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn));
        final List<AudioData> content = pageData.getContent();
        model.addAttribute("allAudioData", content);
        model.addAttribute("pageData", pageData);
        return "audiolisting";
    }
}
