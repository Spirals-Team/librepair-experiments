/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.repository.integration;

import com.microsoft.azure.spring.data.cosmosdb.common.TestConstants;
import com.microsoft.azure.spring.data.cosmosdb.common.TestUtils;
import com.microsoft.azure.spring.data.cosmosdb.domain.Importance;
import com.microsoft.azure.spring.data.cosmosdb.domain.Memo;
import com.microsoft.azure.spring.data.cosmosdb.repository.TestRepositoryConfig;
import com.microsoft.azure.spring.data.cosmosdb.repository.repository.MemoRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class MemoRepositoryIT {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(TestConstants.DATE_FORMAT);

    private static Date memoDate;
    private static Date memoDateBefore;
    private static Date memoDateAfter;
    private static Memo testMemo1;
    private static Memo testMemo2;
    private static Memo testMemo3;

    @Autowired
    MemoRepository repository;

    @BeforeClass
    public static void init() throws ParseException {
        memoDate = DATE_FORMAT.parse(TestConstants.DATE_STRING);
        memoDateBefore = DATE_FORMAT.parse(TestConstants.DATE_BEFORE_STRING);
        memoDateAfter = DATE_FORMAT.parse(TestConstants.DATE_AFTER_STRING);
        testMemo1 = new Memo(TestConstants.ID_1, TestConstants.MESSAGE, memoDateBefore, Importance.HIGH);
        testMemo2 = new Memo(TestConstants.ID_2, TestConstants.NEW_MESSAGE, memoDate, Importance.LOW);
        testMemo3 = new Memo(TestConstants.ID_3, TestConstants.NEW_MESSAGE, memoDateAfter, Importance.LOW);
    }

    @Before
    public void setup() {
        repository.saveAll(Arrays.asList(testMemo1, testMemo2, testMemo3));
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testFindAll() {
        final List<Memo> result = TestUtils.toList(repository.findAll());

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void testFindByDate() {
        final List<Memo> result = repository.findMemoByDate(memoDate);

        assertThat(result.size()).isEqualTo(1);
        assertMemoEquals(result.get(0), testMemo1);
    }

    @Test
    public void testFindByEnum() {
        final List<Memo> result = repository.findMemoByImportance(testMemo1.getImportance());

        assertThat(result.size()).isEqualTo(1);
        assertMemoEquals(result.get(0), testMemo1);
    }

    private void assertMemoEquals(Memo actual, Memo expected) {
        assertThat(actual.getId().equals(expected.getId()));
        assertThat(actual.getMessage().equals(expected.getMessage()));
        assertThat(actual.getDate().equals(expected.getDate()));
        assertThat(actual.getImportance().equals(expected.getImportance()));
    }

    @Test
    public void testFindByBefore() {
        List<Memo> memos = this.repository.findByDateBefore(memoDateBefore);

        Assert.assertTrue(memos.isEmpty());

        memos = this.repository.findByDateBefore(memoDate);

        Assert.assertEquals(memos.size(), 1);
        Assert.assertEquals(memos.get(0), testMemo1);

        memos = this.repository.findByDateBefore(memoDateAfter);
        final List<Memo> reference = Arrays.asList(testMemo1, testMemo2);

        memos.sort(Comparator.comparing(Memo::getId));
        reference.sort(Comparator.comparing(Memo::getId));

        Assert.assertEquals(memos.size(), reference.size());
        Assert.assertEquals(memos, reference);
    }

    @Test
    public void testFindByBeforeWithAndOr() {
        List<Memo> memos = this.repository.findByDateBeforeAndMessage(memoDate, TestConstants.NEW_MESSAGE);

        Assert.assertTrue(memos.isEmpty());

        memos = this.repository.findByDateBeforeAndMessage(memoDate, TestConstants.MESSAGE);

        Assert.assertEquals(memos.size(), 1);
        Assert.assertEquals(memos.get(0), testMemo1);

        memos = this.repository.findByDateBeforeOrMessage(memoDateAfter, TestConstants.MESSAGE);
        final List<Memo> reference = Arrays.asList(testMemo1, testMemo2);

        memos.sort(Comparator.comparing(Memo::getId));
        reference.sort(Comparator.comparing(Memo::getId));

        Assert.assertEquals(memos.size(), reference.size());
        Assert.assertEquals(memos, reference);
    }

    @Test
    public void testFindByAfter() {
        List<Memo> memos = this.repository.findByDateAfter(memoDateBefore);

        Assert.assertTrue(memos.isEmpty());

        memos = this.repository.findByDateBefore(memoDate);

        Assert.assertEquals(memos.size(), 1);
        Assert.assertEquals(memos.get(0), testMemo2);

        memos = this.repository.findByDateBefore(memoDateAfter);
        final List<Memo> reference = Arrays.asList(testMemo1, testMemo2);

        memos.sort(Comparator.comparing(Memo::getId));
        reference.sort(Comparator.comparing(Memo::getId));

        Assert.assertEquals(memos.size(), reference.size());
        Assert.assertEquals(memos, reference);
    }

    @Test
    public void testFindByAfterWithAndOr() {
        List<Memo> memos = this.repository.findByDateAfterAndMessage(memoDate, TestConstants.MESSAGE);

        Assert.assertTrue(memos.isEmpty());

        memos = this.repository.findByDateAfterAndMessage(memoDate, TestConstants.NEW_MESSAGE);

        Assert.assertEquals(memos.size(), 1);
        Assert.assertEquals(memos.get(0), testMemo3);

        memos = this.repository.findByDateAfterOrMessage(memoDateBefore, TestConstants.MESSAGE);
        final List<Memo> reference = Arrays.asList(testMemo1, testMemo2, testMemo3);

        memos.sort(Comparator.comparing(Memo::getId));
        reference.sort(Comparator.comparing(Memo::getId));

        Assert.assertEquals(memos.size(), reference.size());
        Assert.assertEquals(memos, reference);
    }
}
