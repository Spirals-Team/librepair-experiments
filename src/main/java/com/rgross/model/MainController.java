package com.rgross.model;

import com.rgross.contract.GeneralContract;
import com.rgross.parser.GeneralContractParser;
import com.rgross.repository.GeneralContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Ryan on 10/1/2017.
 */
@RestController
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private GeneralContractParser generalContractParser;

    // Trying this out.
    @Autowired
    private GeneralContractRepository generalContractRepository;

    @GetMapping(path = "/2017")
    public List<GeneralContract> example() {
        return generalContractRepository.findAllByFiscalYear(2017);
    }

    @GetMapping(path="/{year}")
    public List<GeneralContract> getAllContractsByYear(@PathVariable("year") int year) {

        return generalContractRepository.findAllByFiscalYear(year);

    }

    @GetMapping(path="/{year}/county")
    public HashMap<String, List<GeneralContract>> getContractsForFiscalYearPerCounty(@PathVariable("year") int year) {

        HashMap<String,List<GeneralContract>> contractsPerCounty = new HashMap<>();

        for (GeneralContract generalContract : generalContractRepository.findAllByFiscalYear(year)) {
            String currentCounty = generalContract.getPlaceOfPerformanceLocation().getCounty().getCountyName();

            if (contractsPerCounty.containsKey(currentCounty)) {
                contractsPerCounty.get(currentCounty).add(generalContract);
            } else {
                List<GeneralContract> generalContractForIndividualCounty = new ArrayList<>();
                generalContractForIndividualCounty.add(generalContract);

                contractsPerCounty.put(currentCounty, generalContractForIndividualCounty);
            }
        }
        return contractsPerCounty;
    }

    @GetMapping(path="/all")
    public List<GeneralContract> getAllContracts() {
        return generalContractRepository.findAllContracts();
    }

}


