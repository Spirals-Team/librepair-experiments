package com.utn.tp5.controllers;

import com.utn.tp5.service.CountryService;
import com.utn.tp5.DTO.CountryDTO;
import com.utn.tp5.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryControllerTest {

    private CountryController countryController;
    private CountryService countryService;
    private Country country;

    @Before
    public void contextLoads() {
        this.countryService = mock(CountryService.class);
        this.countryController = new CountryController(this.countryService);
        this.country = new Country("Example", "Exa");
        this.country.setId((long) 1);
        when(this.countryService.getById((long) 1)).thenReturn(this.country);
        when(this.countryService.saveCountry(this.country)).thenReturn(true);
    }

    @Test
    public void whenCountryListIsAsked() {
        List<Country> countries = new ArrayList<>();
        countries.add(this.country);
        when(this.countryService.getAll()).thenReturn(countries);
        List<CountryDTO> DTOList = countryController.listCountries();
        for (Country country : countries) {
            DTOList.add(new CountryDTO(country));
        }
        assertEquals(countries.get(0).getName(), DTOList.get(0).getName());
    }

    @Test
    public void whenACountryIsAskedById() {
        CountryDTO a = countryController.getCountryById(this.country.getId());
        CountryDTO b = new CountryDTO(this.country);
        assertEquals(a.getName(), b.getName());
    }

    @Test
    public void whenACountryIsAdded() {
        /*this.countryService.saveCountry(this.country);
        verify(this.countryService).saveCountry(this.country);*/
    }

    @Test
    public void whenACountryIsDeleted() {
        this.countryController.deleteCountry(this.country.getName());
        verify(this.countryService).deleteCountry(this.country.getName());
    }
}
