package com.psg.glms.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.psg.glms.model.BookCategory;
import com.psg.glms.repository.BookCategoryRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class BookCategoryControllerTest {
    // @InjectMocks
    // private PortfolioController portfolioController;
    @InjectMocks
    private BookCategoryController BookCategoryController;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

   
    public static List<BookCategory> expectedBookCategory;
    private BookCategory bookCategory1 = new BookCategoryBuilder().id(1L).name("name").build();
    private BookCategory bookCategory2 = new BookCategoryBuilder().id(1L).name("name").build();

    @Test
    public void GetAllPortfolioTestService() {

        expectedBookCategory = Arrays.asList(bookCategory1, bookCategory2);
        when(bookCategoryRepository.findAll()).thenReturn(expectedBookCategory);
        ResponseEntity<List<BookCategory>> actualBookCategory = BookCategoryController.all();
        // assertNotNull(actualBookCategory);
        assertEquals(expectedBookCategory, actualBookCategory);
    }

  

}