package org.pdxfinder.web.controllers;

import org.pdxfinder.dao.Specimen;
import org.pdxfinder.services.GraphService;
import org.pdxfinder.services.SearchService;
import org.pdxfinder.services.dto.DetailsDTO;
import org.pdxfinder.services.dto.VariationDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by csaba on 12/05/2017.
 */
@Controller
public class DetailsPageController {

    private SearchService searchService;
    private GraphService graphService;


    @Autowired
    public DetailsPageController(SearchService searchService, GraphService graphService) {
        this.searchService = searchService;
        this.graphService = graphService;
    }

    @RequestMapping(value = "/pdx/{dataSrc}/{modelId}")
    public String details(@PathVariable String dataSrc,
                          @PathVariable String modelId,
                          @RequestParam(value="page", required = false) Integer page,
                          @RequestParam(value="size", required = false) Integer size,Model model){

        int viewPage = (page == null || page < 1) ? 0 : page-1;
        int viewSize = (size == null || size < 1) ? 10 : size;

        Map<String, String> patientTech = searchService.findPatientPlatforms(dataSrc,modelId);
        Map<String, Set<String>> modelTechAndPassages = searchService.findModelPlatformAndPassages(dataSrc,modelId,"");

        DetailsDTO dto = searchService.searchForModel(dataSrc,modelId,viewPage,viewSize,"","","");

        List<String> relatedModels = searchService.getModelsOriginatedFromSamePatient(dataSrc, modelId);

        List<VariationDataDTO> variationDataDTOList = new ArrayList<>();
        for (String tech : modelTechAndPassages.keySet()) {
            VariationDataDTO variationDataDTO = searchService.variationDataByPlatform(dataSrc,modelId,tech,"",viewPage,viewSize,"",1,"","");
            variationDataDTOList.add(variationDataDTO);
        }

        // dto.setTotalPages((int) Math.ceil(totalRecords/dSize) );

        //auto suggestions for the search field
        Set<String> autoSuggestList = graphService.getMappedNCITTerms();
        model.addAttribute("mappedTerm", autoSuggestList);


        model.addAttribute("nonjsVariationdata", variationDataDTOList);

        model.addAttribute("fullData",dto);

        model.addAttribute("modelId",modelId);
        model.addAttribute("dataSrc",dataSrc);

        model.addAttribute("externalId", dto.getExternalId());
        model.addAttribute("dataSource", dto.getDataSource());
        model.addAttribute("patientId", dto.getPatientId());
        model.addAttribute("gender", dto.getGender());
        model.addAttribute("age", dto.getAge());
        model.addAttribute("race", dto.getRace());
        model.addAttribute("ethnicity", dto.getEthnicity());
        model.addAttribute("diagnosis", dto.getDiagnosis());
        model.addAttribute("tumorType", dto.getTumorType());
        model.addAttribute("classification", dto.getClassification());
        model.addAttribute("originTissue", dto.getOriginTissue());
        model.addAttribute("sampleSite", dto.getSampleSite());

        model.addAttribute("sampleType", dto.getSampleType());
        model.addAttribute("strain", dto.getStrain());
        model.addAttribute("mouseSex", dto.getMouseSex());
        model.addAttribute("engraftmentSite", dto.getEngraftmentSite());
        model.addAttribute("markers", dto.getCancerGenomics());
        model.addAttribute("url", dto.getExternalUrl());
        model.addAttribute("urlText", dto.getExternalUrlText());
        model.addAttribute("mappedOntology", dto.getMappedOntology());

        //model.addAttribute("specimenId", dto.getSpecimenId());
        for (Specimen specimen : dto.getSpecimens()) {
            model.addAttribute("specimenId",specimen.getExternalId() );
        }

        model.addAttribute("totalPages", dto.getTotalPages());
        model.addAttribute("presentPage", viewPage+1);
        model.addAttribute("totalRecords", dto.getVariationDataCount());

        model.addAttribute("variationData", dto.getMarkerAssociations());

        model.addAttribute("modelInfo", modelTechAndPassages);
        model.addAttribute("patientInfo", patientTech);

        model.addAttribute("relatedModels", relatedModels);
        /*
        if(relatedModels.size()>0){
            String rm = "";
            for (String mod:relatedModels){
                rm+="<a href=\"/data/pdx/"+dto.getDataSource()+"/"+mod+"\">"+mod+"</a>";
            }
            model.addAttribute("relatedModels", rm);
        }
        else{
            model.addAttribute("relatedModels", "-");
        }
        */

        return "details";
    }
}