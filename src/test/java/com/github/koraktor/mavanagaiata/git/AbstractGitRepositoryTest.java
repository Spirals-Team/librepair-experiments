/*
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012-2016, Sebastian Staudt
 */

package com.github.koraktor.mavanagaiata.git;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Sebastian Staudt
 */
public class AbstractGitRepositoryTest {

    private static GitCommit headCommit = mock(GitCommit.class);

    class GenericGitRepository extends AbstractGitRepository {

        public void check() {}

        public void close() {}

        public GitTagDescription describe() {
            return null;
        }

        public String getAbbreviatedCommitId(GitCommit commit) {
            return commit == headCommit ? "deadbeef" : null;
        }

        public String getBranch() {
            return null;
        }

        public GitCommit getHeadCommit() {
            return headCommit;
        }

        public String getHeadRef() {
            return null;
        }

        public File getWorkTree() {
            return new File("test");
        }

        public Map<String, GitTag> getTags() {
            return null;
        }

        @Override
        public boolean isChecked() {
            return false;
        }

        public boolean isDirty(boolean dirtyCheckLoose) {
            return false;
        }

        @Override
        public boolean isOnUnbornBranch() {
            return false;
        }

        public <T extends CommitWalkAction> T walkCommits(T action) {
            return action;
        }

    }

    @Test
    public void testGetAbbreviatedHead() throws Exception {
        GitRepository repo = new GenericGitRepository();

        assertThat(repo.getAbbreviatedCommitId(), is(equalTo("deadbeef")));
    }

    @Test
    public void testGetMailMap() throws Exception {
        GitRepository repo = new GenericGitRepository();
        MailMap mailMap = repo.getMailMap();

        assertThat(mailMap.repository, is(equalTo(repo)));
        assertThat(mailMap.mailToMailMap, is(notNullValue()));
        assertThat(mailMap.mailToNameAndMailMap, is(notNullValue()));
        assertThat(mailMap.mailToNameMap, is(notNullValue()));
        assertThat(mailMap.nameAndMailToNameAndMailMap, is(notNullValue()));
    }

    @Test
    public void testGetMailMapAlreadyParsed() {
        AbstractGitRepository repo = new GenericGitRepository();
        repo.mailMap = mock(MailMap.class);

        verifyNoMoreInteractions(repo.mailMap);
    }

    @Test
    public void testSetHeadRef() {
        AbstractGitRepository repo = new GenericGitRepository();
        repo.setHeadRef("HEAD");

        assertThat(repo.headRef, is(equalTo("HEAD")));
    }
}
