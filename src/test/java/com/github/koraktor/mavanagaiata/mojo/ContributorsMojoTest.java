/*
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2011-2017, Sebastian Staudt
 */

package com.github.koraktor.mavanagaiata.mojo;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.github.koraktor.mavanagaiata.git.GitCommit;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContributorsMojoTest extends GitOutputMojoAbstractTest<ContributorsMojo> {

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();

        this.mojo.contributorPrefix = " * ";
        this.mojo.header            = "Contributors\n============\n";
        this.mojo.showCounts        = true;
        this.mojo.showEmail         = false;
        this.mojo.sort              = "count";

        doAnswer(new Answer<ContributorsMojo.ContributorsWalkAction>() {
            long dateCounter = new Date().getTime();

            public ContributorsMojo.ContributorsWalkAction answer(InvocationOnMock invocation) throws Throwable {
                ContributorsMojo.ContributorsWalkAction walkAction = ((ContributorsMojo.ContributorsWalkAction) invocation.getArguments()[0]);
                walkAction.execute(this.mockCommit("Sebastian Staudt", "koraktor@gmail.com"));
                walkAction.execute(this.mockCommit("John Doe", "john.doe@example.com"));
                walkAction.execute(this.mockCommit("Joe Average", "joe.average@example.com"));
                walkAction.execute(this.mockCommit("Joe Average", "joe.average@example.com"));
                walkAction.execute(this.mockCommit("Sebastian Staudt", "koraktor@gmail.com"));
                walkAction.execute(this.mockCommit("Sebastian Staudt", "koraktor@gmail.com"));
                return walkAction;
            }

            private GitCommit mockCommit(String authorName, String authorEmail) {
                GitCommit commit = mock(GitCommit.class);
                when(commit.getAuthorEmailAddress()).thenReturn(authorEmail);
                when(commit.getAuthorName()).thenReturn(authorName);
                when(commit.getAuthorDate()).thenReturn(new Date(dateCounter ++));
                return commit;
            }
        }).when(this.repository).walkCommits(any(ContributorsMojo.ContributorsWalkAction.class));
    }

    @Test
    public void testError() {
        super.testError("Unable to read contributors from Git");
    }

    @Test
    public void testInitConfiguration() {
        this.mojo.sort = null;
        this.mojo.initConfiguration();
        assertThat(this.mojo.sort, is(equalTo("count")));

        this.mojo.sort = "count";
        this.mojo.initConfiguration();
        assertThat(this.mojo.sort, is(equalTo("count")));

        this.mojo.sort = "date";
        this.mojo.initConfiguration();
        assertThat(this.mojo.sort, is(equalTo("date")));

        this.mojo.sort = "name";
        this.mojo.initConfiguration();
        assertThat(this.mojo.sort, is(equalTo("name")));

        this.mojo.sort = "unknown";
        this.mojo.initConfiguration();
        assertThat(this.mojo.sort, is(equalTo("count")));
    }

    @Test
    public void testCustomization() throws Exception {
        this.mojo.contributorPrefix = "- ";
        this.mojo.header            = "Authors\\n-------\\n";
        this.mojo.showCounts        = false;
        this.mojo.showEmail         = true;
        this.mojo.sort              = "count";
        this.mojo.initConfiguration();
        mojo.generateOutput(repository, printStream);

        this.assertOutputLine("Authors");
        this.assertOutputLine("-------");
        this.assertOutputLine("");
        this.assertOutputLine("- Sebastian Staudt (koraktor@gmail.com)");
        this.assertOutputLine("- Joe Average (joe.average@example.com)");
        this.assertOutputLine("- John Doe (john.doe@example.com)");
        this.assertOutputLine("Footer");
        this.assertOutputLine(null);
    }

    @Test
    public void testSortCount() throws Exception {
        this.mojo.sort = "count";
        this.mojo.initConfiguration();
        mojo.generateOutput(repository, printStream);

        this.assertOutputLine("Contributors");
        this.assertOutputLine("============");
        this.assertOutputLine("");
        this.assertOutputLine(" * Sebastian Staudt (3)");
        this.assertOutputLine(" * Joe Average (2)");
        this.assertOutputLine(" * John Doe (1)");
        this.assertOutputLine("Footer");
        this.assertOutputLine(null);
    }

    @Test
    public void testSortDate() throws Exception {
        this.mojo.sort = "date";
        this.mojo.initConfiguration();
        mojo.generateOutput(repository, printStream);

        this.assertOutputLine("Contributors");
        this.assertOutputLine("============");
        this.assertOutputLine("");
        this.assertOutputLine(" * Sebastian Staudt (3)");
        this.assertOutputLine(" * John Doe (1)");
        this.assertOutputLine(" * Joe Average (2)");
        this.assertOutputLine("Footer");
        this.assertOutputLine(null);
    }

    @Test
    public void testSortName() throws Exception {
        this.mojo.sort = "name";
        this.mojo.initConfiguration();
        mojo.generateOutput(repository, printStream);

        this.assertOutputLine("Contributors");
        this.assertOutputLine("============");
        this.assertOutputLine("");
        this.assertOutputLine(" * Joe Average (2)");
        this.assertOutputLine(" * John Doe (1)");
        this.assertOutputLine(" * Sebastian Staudt (3)");
        this.assertOutputLine("Footer");
        this.assertOutputLine(null);
    }

}
