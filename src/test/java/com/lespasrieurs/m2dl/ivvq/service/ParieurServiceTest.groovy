package com.lespasrieurs.m2dl.ivvq.service

import com.lespasrieurs.m2dl.ivvq.domain.Parieur
import com.lespasrieurs.m2dl.ivvq.repositories.ParieurRepository
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

/**
 * Created by SoF on 13/04/2018.
 */
class ParieurServiceTest extends Specification {

    ParieurService parieurService
    ParieurRepository parieurRepository

    void setup() {
        parieurRepository = Mock()
        parieurService = new ParieurService()
        parieurService.parieurRepository = parieurRepository
    }

    def "check type of parieurRepository"() {
        expect: "parieurRepository is a Spring repository"
        parieurRepository instanceof PagingAndSortingRepository
    }

    def "test delegation of save of an Parieur to the repository"() {
        given: "an parieur"
        def parieur = Mock(Parieur)

        when: "the parieur is saved"
        parieurService.saveParieur(parieur);

        then: "the save is delegated to the parieurRepository"
        1 * parieurRepository.save(parieur)
    }
}
