/*
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2014-2017, Sebastian Staudt
 */

package com.github.koraktor.mavanagaiata.git;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Sebastian Staudt
 */
public class MailMapTest {

    private GitRepository repo;

    @Before
    public void setup() {
        repo = mock(GitRepository.class);
    }

    @Test
    public void testGetCanonicalMail() {
        MailMap mailMap = new MailMap(repo);
        mailMap.mailToMailMap = new HashMap<>(1);
        mailMap.mailToMailMap.put("oldmail@example.com", "newmail@example.com");
        mailMap.mailToNameAndMailMap = new HashMap<>(1);
        mailMap.mailToNameAndMailMap.put("oldmail2@example.com", new AbstractMap.SimpleEntry<>("Test", "newmail2@example.com"));
        mailMap.nameAndMailToNameAndMailMap = new HashMap<>(1);
        mailMap.nameAndMailToNameAndMailMap.put(new AbstractMap.SimpleEntry<>("Test", "oldmail3@example.com"), new AbstractMap.SimpleEntry<>("Test", "newmail3@example.com"));

        assertThat(mailMap.getCanonicalMail("Test", "oldmail@example.com"), is(equalTo("newmail@example.com")));
        assertThat(mailMap.getCanonicalMail("Test", "oldmail2@example.com"), is(equalTo("newmail2@example.com")));
        assertThat(mailMap.getCanonicalMail("Test", "oldmail3@example.com"), is(equalTo("newmail3@example.com")));
        assertThat(mailMap.getCanonicalMail("Test", "unknown@example.com"), is(equalTo("unknown@example.com")));

        GitCommit commit1 = mock(GitCommit.class);
        when(commit1.getAuthorName()).thenReturn("Test");
        when(commit1.getAuthorEmailAddress()).thenReturn("oldmail@example.com");
        GitCommit commit2 = mock(GitCommit.class);
        when(commit2.getCommitterName()).thenReturn("Unknown");
        when(commit2.getCommitterEmailAddress()).thenReturn("unknown@example.com");

        assertThat(mailMap.getCanonicalAuthorEmailAddress(commit1), is(equalTo("newmail@example.com")));
        assertThat(mailMap.getCanonicalAuthorName(commit1), is(equalTo("Test")));
        assertThat(mailMap.getCanonicalCommitterEmailAddress(commit2), is(equalTo("unknown@example.com")));
        assertThat(mailMap.getCanonicalCommitterName(commit2), is(equalTo("Unknown")));
    }

    @Test
    public void testGetCanonicalName() {
        MailMap mailMap = new MailMap(repo);
        mailMap.mailToNameMap = new HashMap<>(1);
        mailMap.mailToNameMap.put("mail1@example.com", "Test 1");
        mailMap.mailToNameAndMailMap = new HashMap<>(1);
        mailMap.mailToNameAndMailMap.put("mail2@example.com", new AbstractMap.SimpleEntry<>("Test 2", "mail@example.com"));
        mailMap.nameAndMailToNameAndMailMap = new HashMap<>(1);
        mailMap.nameAndMailToNameAndMailMap.put(new AbstractMap.SimpleEntry<>("Test", "mail3@example.com"), new AbstractMap.SimpleEntry<>("Test 3", "mail@example.com"));

        assertThat(mailMap.getCanonicalName("Test", "mail1@example.com"), is(equalTo("Test 1")));
        assertThat(mailMap.getCanonicalName("Test", "mail2@example.com"), is(equalTo("Test 2")));
        assertThat(mailMap.getCanonicalName("Test", "mail3@example.com"), is(equalTo("Test 3")));
        assertThat(mailMap.getCanonicalName("Unknown", "mail@example.com"), is(equalTo("Unknown")));

        GitCommit commit1 = mock(GitCommit.class);
        when(commit1.getAuthorName()).thenReturn("Test");
        when(commit1.getAuthorEmailAddress()).thenReturn("mail1@example.com");
        GitCommit commit2 = mock(GitCommit.class);
        when(commit2.getCommitterName()).thenReturn("Unknown");
        when(commit2.getCommitterEmailAddress()).thenReturn("mail@example.com");

        assertThat(mailMap.getCanonicalAuthorEmailAddress(commit1), is(equalTo("mail1@example.com")));
        assertThat(mailMap.getCanonicalAuthorName(commit1), is(equalTo("Test 1")));
        assertThat(mailMap.getCanonicalCommitterEmailAddress(commit2), is(equalTo("mail@example.com")));
        assertThat(mailMap.getCanonicalCommitterName(commit2), is(equalTo("Unknown")));
    }

    @Test
    public void testNewInstance() {
        MailMap mailMap = new MailMap(repo);

        assertThat(mailMap.exists, is(false));
        assertThat(mailMap.mailToMailMap, is(instanceOf(Map.class)));
        assertThat(mailMap.mailToNameMap, is(instanceOf(Map.class)));
        assertThat(mailMap.mailToNameAndMailMap, is(instanceOf(Map.class)));
        assertThat(mailMap.nameAndMailToNameAndMailMap, is(instanceOf(Map.class)));
        assertThat(mailMap.repository, is(repo));
    }

    @Test
    public void testExists() {
        MailMap mailMap = new MailMap(repo);

        assertThat(mailMap.exists(), is(false));

        mailMap.exists = true;

        assertThat(mailMap.exists(), is(true));
    }

    @Test
    public void testParseFromRepository() throws Exception {
        final MailMap mailMap = spy(new MailMap(repo));
        when(repo.getWorkTree()).thenReturn(new File("test"));

        doAnswer(invocation -> {
            mailMap.mailToMailMap = new HashMap<>(1);
            mailMap.mailToMailMap.put("test", "test");
            return null;
        }).when(mailMap).parseMailMap(eq(new File("test/.mailmap")));

        mailMap.parseMailMap();

        assertThat(mailMap.exists(), is(true));
    }

    @Test
    public void testParseFromRepositoryWithoutMailmap() throws Exception {
        MailMap mailMap = spy(new MailMap(repo));
        when(repo.getWorkTree()).thenReturn(new File("test"));

        doThrow(new FileNotFoundException()).when(mailMap).
                parseMailMap(eq(new File("test/.mailmap")));

        mailMap.parseMailMap();

        assertThat(mailMap.exists(), is(false));
    }

    @Test
    public void testParseFromFile() throws Exception {
        MailMap mailMap = new MailMap(repo);
        mailMap.mailToMailMap.put("old@example.com", "Old");
        File mailMapFile = new File(this.getClass().getResource("/.mailmap").getFile());

        mailMap.parseMailMap(mailMapFile);

        assertThat(mailMap.mailToMailMap.size(), is(1));
        assertThat(mailMap.mailToMailMap.get("oldmail@example.com"), is(equalTo("newmail@example.com")));
        assertThat(mailMap.mailToNameMap.size(), is(1));
        assertThat(mailMap.mailToNameMap.get("realmail@example.com"), is(equalTo("Real Name")));
        assertThat(mailMap.mailToNameAndMailMap.size(), is(1));
        assertThat(mailMap.mailToNameAndMailMap.get("oldmail@example.com"), is(equalTo(new AbstractMap.SimpleEntry<>("Real Name", "newmail@example.com"))));
        assertThat(mailMap.nameAndMailToNameAndMailMap.size(), is(1));
        assertThat(mailMap.nameAndMailToNameAndMailMap.get(new AbstractMap.SimpleEntry<>("Fake Name", "oldmail@example.com")), is(equalTo(new AbstractMap.SimpleEntry<>("Real Name", "newmail@example.com"))));
    }

}
