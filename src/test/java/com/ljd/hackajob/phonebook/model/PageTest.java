package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.TestUtils.RAND;
import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PageTest extends ModelTest {
    private List<String> results;
    private long totalCount;
    private Page<String> page;
    
    @Before
    public void beforeEach() {
        results = Arrays.asList(generateRandomAlphanumericString(8), generateRandomAlphanumericString(8), generateRandomAlphanumericString(8));
        totalCount = 10L + RAND.nextInt(50);
        page = new Page<>(results, totalCount);
    }
    
    @Test
    public void testGetResults() {
        assertEquals(results, page.getResults());
    }
    
    @Test
    public void testGetTotalCount() {
        assertEquals(totalCount, page.getTotalCount());
    }
    
    @Test
    public void testSerialisation() throws Exception {
        String json = mapper.writeValueAsString(page);
        assertTrue(json.contains(Long.toString(totalCount)));
        for(String item : page.getResults()) {
            assertTrue(json.contains(item));
        }
    }
}
