package com.aidanwhiteley.books.repository;

import com.aidanwhiteley.books.domain.Book;
import com.aidanwhiteley.books.util.IntegrationTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;

@ActiveProfiles({"test", "fongo"})
@AutoConfigureWireMock(port=0)
public class GoogleBookDaoAsyncTest extends IntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleBookDaoAsyncTest.class);
    private static final String SPRING_FRAMEWORK_GOOGLE_BOOK_ID = "oMVIzzKjJCcC";

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GoogleBooksDaoAsync async;

    @Test
    public void testBookUpdatedWithGoogleBookDetails() {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Wiremock Mappings: " + WireMock.listAllStubMappings().getMappings());
        }

        Book book = BookRepositoryTest.createTestBook();
        Book savedBook = bookRepository.insert(book);
        assertNull(savedBook.getGoogleBookDetails());

        // This will result in a call to the Google Books API being mocked by WireMock
        async.updateBookWithGoogleBookDetails(savedBook, SPRING_FRAMEWORK_GOOGLE_BOOK_ID);

        Book updatedBook = bookRepository.
                findById(savedBook.getId()).orElseThrow(() -> new IllegalStateException("Expected book not found"));
        assertNotNull(updatedBook.getGoogleBookDetails(),
                "Google book details in Item object should not be null");

    }

}
