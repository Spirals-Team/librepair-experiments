package org.leagueoftests.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import org.leagueoftests.repository.ChampionsRepository;
import org.leagueoftests.repository.ChampionsRepositoryFactory;

import org.junit.Before;
@RunWith(JUnit4.class)
public class ChampionTest {
    ChampionsRepository championsRepository;

    @Test
    public void getById() {
        int idToFind = 1;
        assertNotNull(championsRepository.getById(idToFind));
    }

    @Test
    public void getAll() {
        assertNotNull(championsRepository.getAll());
    }

    @Test
    public void add() {
        Champions champion = new Champions();
        champion.setId(1);
        champion.setChampionName("Ashe");
        champion.setChampionPriceBE(450);
        champion.setChampionPriceRP(295);
        championsRepository.add(champion);
        assertNotNull(championsRepository.getById(champion.getId()));

    }

    @Test
    public void delete() {
        Champions champion = championsRepository.getById(1);
        championsRepository.delete(champion);
        assertNull(championsRepository.getById(champion.getId()).getChampionName());
        assertNotNull(championsRepository.getById(2));
    }

    @Test
    public void update() {
        Champions otherChampion = championsRepository.getById(4);
        Champions champion = new Champions();
        champion.setId(1);
        champion.setChampionName("Ashe");
        champion.setChampionPriceBE(450);
        champion.setChampionPriceRP(145);
        Champions oldChampion=championsRepository.getById(1);
        championsRepository.update(oldChampion, champion);
        assertEquals(championsRepository.getById(oldChampion.getId()).getChampionName(), championsRepository.getById(champion.getId()).getChampionName());
        assertNotEquals(otherChampion,champion);
        }

    @Before
    public void init() {
        championsRepository = ChampionsRepositoryFactory.getInstance();
        Champions champion1 = new Champions();
        Champions champion2 = new Champions();
        Champions champion3 = new Champions();
        Champions champion4 = new Champions();
        Champions champion5 = new Champions();

        champion1.setId(1);
        champion1.setChampionName("Ahri");
        champion1.setChampionPriceBE(4800);
        champion1.setChampionPriceRP(750);


        champion2.setId(2);
        champion2.setChampionName("Rengar");
        champion2.setChampionPriceBE(6300);
        champion2.setChampionPriceRP(975);

        champion3.setId(3);
        champion3.setChampionName("Annie");
        champion3.setChampionPriceBE(450);
        champion3.setChampionPriceRP(275);

        champion4.setId(4);
        champion4.setChampionName("Heimerdinger");
        champion4.setChampionPriceBE(3150);
        champion4.setChampionPriceRP(450);

        champion5.setId(5);
        champion5.setChampionName("Vladimir");
        champion5.setChampionPriceBE(4800);
        champion5.setChampionPriceRP(750);

        championsRepository.add(champion1);
        championsRepository.add(champion2);
        championsRepository.add(champion3);
        championsRepository.add(champion4);
        championsRepository.add(champion5);
    }
}