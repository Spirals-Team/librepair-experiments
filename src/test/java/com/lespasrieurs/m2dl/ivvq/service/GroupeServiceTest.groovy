package com.lespasrieurs.m2dl.ivvq.service

import com.lespasrieurs.m2dl.ivvq.domain.Groupe
import com.lespasrieurs.m2dl.ivvq.repositories.GroupeRepository
import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

/**
 * Created by Marti_000 on 06/04/2018.
 */
class GroupeServiceTest extends Specification {

    GroupeService groupeService
    GroupeRepository groupeRepository

    void setup() {
        groupeRepository = Mock()
        groupeService = new GroupeService()
        groupeService.groupeRepository = groupeRepository
    }

    def "test de vérification du type de GroupeRepository"() {
        expect: "GroupeRepository est un Spring repository"
        groupeRepository instanceof PagingAndSortingRepository
    }

    def "test de sauvegarde d'un groupe via le repository"() {
        given: "un groupe"
        def groupe = Mock(Groupe)

        when: "le groupe est sauvegardé"
        groupeService.saveGroupe(groupe);

        then: "la sauvegarde est délégué à GroupeRepository"
        1 * groupeRepository.save(groupe)
    }

    def "test de sauvegarde d'un groupe null qui doit déclencher une erreur"() {
        given: "un groupe null"
        def groupe = null

        when: "le groupe est sauvegardé"
        groupeService.saveGroupe(groupe);

        then: "une erreur est déclenché"
        thrown(IllegalArgumentException)
    }

    def "test de récupération d'un groupe via le repository"() {

        when: "je veux récupérer un groupe"
        groupeService.findOneGroupe(1)

        then: "la recherche est délégué au repository"
        1 * groupeRepository.findOne(1)
    }

    def "test de suppression d'un groupe via le repository"() {

        when: "je veux supprimer un groupe"
        groupeService.deleteGroupe(1)

        then: "la suppression est délégué au repository"
        1 * groupeRepository.delete(1)
    }
}
