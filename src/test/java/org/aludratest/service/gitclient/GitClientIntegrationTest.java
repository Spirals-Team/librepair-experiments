/*
 * Copyright (C) 2010-2014 Hamburg Sud and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.service.gitclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import org.aludratest.service.AbstractAludraServiceTest;
import org.aludratest.service.cmdline.CommandLineService;
import org.aludratest.service.gitclient.data.AddData;
import org.aludratest.service.gitclient.data.BranchCreationData;
import org.aludratest.service.gitclient.data.BranchDeletionData;
import org.aludratest.service.gitclient.data.BranchListData;
import org.aludratest.service.gitclient.data.CheckoutData;
import org.aludratest.service.gitclient.data.CloneRepositoryData;
import org.aludratest.service.gitclient.data.CommitData;
import org.aludratest.service.gitclient.data.ConfigData;
import org.aludratest.service.gitclient.data.FetchData;
import org.aludratest.service.gitclient.data.InvocationData;
import org.aludratest.service.gitclient.data.LogData;
import org.aludratest.service.gitclient.data.LogItemData;
import org.aludratest.service.gitclient.data.MergeData;
import org.aludratest.service.gitclient.data.MvData;
import org.aludratest.service.gitclient.data.PushData;
import org.aludratest.service.gitclient.data.RebaseData;
import org.aludratest.service.gitclient.data.RenamedStatusData;
import org.aludratest.service.gitclient.data.ResetData;
import org.aludratest.service.gitclient.data.RmData;
import org.aludratest.service.gitclient.data.StatusData;
import org.aludratest.service.gitclient.data.VersionData;
import org.aludratest.testcase.After;
import org.aludratest.util.data.StringData;
import org.databene.commons.FileUtil;
import org.databene.commons.IOUtil;
import org.databene.commons.StringUtil;
import org.databene.commons.SystemInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;

/** Tests the {@link GitClient} against an installation of git.
 * @author Volker Bergmann */
@SuppressWarnings("javadoc")
public class GitClientIntegrationTest extends AbstractAludraServiceTest {

    private static final String ADDED_FILE = "added.txt";
    private static final String UNTRACKED_FILE = "untracked.txt";
    private CommandLineService service;

    @Before
    public void prepareService() {
        this.service = getLoggingService(CommandLineService.class, "gittest");
    }

    @After
    public void closeService() {
        // service instance is closed by parent classes,
        // only need to remove the reference within this class
        this.service = null;
    }

    @Test
    public void testVersion() throws Exception {
        VersionData data = new VersionData();
        createGitClient().version(data);
        System.out.println(data);
        assertFalse("git version number is empty", StringUtil.isEmpty(data.getVersionNumber()));
    }

    @Test
    public void testStatus() throws Exception {
        StatusData data = new StatusData();
        createGitClient().status(data);
        System.out.println("Status: On branch " + data.getCurrentBranch());
        assertFalse("Branch name is empty", StringUtil.isEmpty(data.getCurrentBranch()));
    }

    @Test
    public void testLog() throws Exception {
        LogData data = LogData.createWithMaxCount(5);
        createGitClient().log(data);
        for (LogItemData entry : data.getItems()) {
            System.out.println(entry);
            System.out.println();
        }
    }

    @Test
    public void testAdd() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                createOrOverwriteFile("some_file.txt", "some content", true, gitClient);
                StatusData status = getStatus(gitClient);
                List<StringData> addedFiles = status.getAddedFiles();
                assertEquals(1, addedFiles.size());
                assertEquals("some_file.txt", addedFiles.get(0).getValue());
            }
        });
    }

    @Test
    public void testGetCurrentBranch() throws Exception {
        StringData branchData = new StringData();
        createGitClient().getCurrentBranch(branchData);
        assertNotNull(branchData.getValue());
    }

    @Test
    public void testListBranches() throws Exception {
        BranchListData data = new BranchListData();
        createGitClient().listBranches(data);
        System.out.println("Branches:");
        for (StringData branch : data.getBranches()) {
            if (branch.getValue().equals(data.getCurrentBranch())) {
                System.out.print("* ");
            }
            else {
                System.out.print("  ");
            }
            System.out.println(branch);
        }
    }

    @Test
    public void testCreateAndDeleteBranch() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                final String branchName = "branch2";
                // there must be a committed file before a branch can be created
                createOrOverwriteFile("file1.txt", "content1", true, gitClient);
                gitClient.commit(new CommitData("commit1"));
                // create the branch
                gitClient.createBranch(new BranchCreationData(branchName));
                // verify its existence
                BranchListData list = new BranchListData();
                gitClient.listBranches(list);
                assertTrue(branchExists(branchName, gitClient));
                // delete the branch
                gitClient.deleteBranch(new BranchDeletionData(branchName));
                // verify its absence
                assertFalse(branchExists(branchName, gitClient));
            }
        });
    }

    @Test
    public void testCommit() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                String fileName = "file1.txt";
                // first create and commit a file
                createOrOverwriteFile(fileName, "content", true, gitClient);
                // verify its presence
                StatusData status = getStatus(gitClient);
                assertFileContained(fileName, status.getAddedFiles());
                // commit the file
                gitClient.commit(new CommitData("commit1"));
                // verify its change
                status = getStatus(gitClient);
                assertFileMissing(fileName, status.getAddedFiles());
            }
        });
    }

    @Test
    public void testMv() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                final String fileName1 = "file1.txt";
                final String fileName2 = "file2.txt";
                // first create and commit a file
                createOrOverwriteFile(fileName1, "content1", true, gitClient);
                // verify its presence
                StatusData status = getStatus(gitClient);
                assertFileContained(fileName1, status.getAddedFiles());
                // commit the file
                gitClient.commit(new CommitData("commit1"));
                // move the file
                gitClient.mv(new MvData(fileName1, fileName2));
                // verify its change
                status = getStatus(gitClient);
                assertTrue(status.getRenamedFiles().contains(new RenamedStatusData(fileName1, fileName2)));
            }
        });
    }

    @Test
    public void testRm() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                // first create and commit a file
                final String fileName = "file1.txt";
                createOrOverwriteFile(fileName, "content1", true, gitClient);
                // verify its presence
                StatusData status = getStatus(gitClient);
                assertFileContained(fileName, status.getAddedFiles());
                // commit the file
                gitClient.commit(new CommitData("commit1"));
                // delete the file
                gitClient.rm(new RmData(fileName));
                // verify its deletion
                status = getStatus(gitClient);
                assertFileContained(fileName, status.getDeletedFiles());
            }
        });
    }

    @Test
    public void testResetSoft() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                createTestFilesForReset(gitClient);
                // reset
                gitClient.resetSoft(new ResetData());
                // verify their absence
                StatusData status = getStatus(gitClient);
                assertFileContained(UNTRACKED_FILE, status.getUntrackedFiles());
                assertFileContained(ADDED_FILE, status.getAddedFiles());
            }
        });
    }

    @Test
    public void testResetMixed() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                createTestFilesForReset(gitClient);
                // reset
                gitClient.resetMixed(new ResetData());
                // verify their absence
                StatusData status = getStatus(gitClient);
                assertFileContained(UNTRACKED_FILE, status.getUntrackedFiles());
                assertFileMissing(ADDED_FILE, status.getAddedFiles());
                assertFileContained(ADDED_FILE, status.getUntrackedFiles());
            }
        });
    }

    @Test
    public void testResetHard() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                createTestFilesForReset(gitClient);
                // reset
                gitClient.resetHard(new ResetData());
                // verify their absence
                StatusData status = getStatus(gitClient);
                assertFileContained(UNTRACKED_FILE, status.getUntrackedFiles());
                assertFileMissing(ADDED_FILE, status.getAddedFiles());
            }
        });
    }

    @Test
    public void testStashSaveAndPop() throws Exception {
        // Skip the test on Mac OSX Yosemite since there is a known issue
        if (runningOnYosemite()) {
            // for the description of the issue see
            // https://stackoverflow.com/questions/24022582/osx-10-10-yosemite-beta-on-git-pull-git-sh-setup-no-such-file-or-directory
            throw new AssumptionViolatedException("Skipping testStashSaveAndPop() due to known git issue on Mac OSX Yosemite.");
        }
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                // create a first commit
                createOrOverwriteFile("test.txt", "content", true, gitClient);
                gitClient.commit(new CommitData("initial commit"));
                // create test files
                createTestFilesForReset(gitClient);
                // stash save
                gitClient.stashSave();
                // verify the files' absence
                StatusData status = getStatus(gitClient);
                assertFileContained(UNTRACKED_FILE, status.getUntrackedFiles());
                assertFileMissing(ADDED_FILE, status.getUntrackedFiles());
                assertFileMissing(ADDED_FILE, status.getAddedFiles());
                // stash pop
                gitClient.stashPop();
                // verify the files' absence
                status = getStatus(gitClient);
                assertFileContained(UNTRACKED_FILE, status.getUntrackedFiles());
                assertFileContained(ADDED_FILE, status.getAddedFiles());
            }
        });
    }

    @Test
    public void testCheckout() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient gitClient) throws Exception {
                // create a first commit on master
                createOrOverwriteFile("file.txt", "master_content", true, gitClient);
                gitClient.commit(new CommitData("initial commit"));
                // create a branch 'branch2', switch to it, overwrite the file and commit
                gitClient.createBranch(new BranchCreationData("branch2"));
                gitClient.checkout(new CheckoutData("branch2"));
                createOrOverwriteFile("file.txt", "branch_content", true, gitClient);
                gitClient.commit(new CommitData("created branch #2"));
                assertFileContent("branch_content", "file.txt", gitClient);
                // check out master and verify its content
                gitClient.checkout(new CheckoutData("master"));
                assertFileContent("master_content", "file.txt", gitClient);
                // check out branch2 and verify its content
                gitClient.checkout(new CheckoutData("branch2"));
                assertFileContent("branch_content", "file.txt", gitClient);
            }
        });
    }

    @Test
    public void testRebase() throws Exception {
        // Skip the test on Mac OSX Yosemite since there is a known issue
        if (runningOnYosemite()) {
            // for the description of the issue see
            // https://stackoverflow.com/questions/24022582/osx-10-10-yosemite-beta-on-git-pull-git-sh-setup-no-such-file-or-directory
            throw new AssumptionViolatedException("Skipping testRebase() due to known git issue on Mac OSX Yosemite.");
        }
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient git) throws Exception {
                // create a file "m.txt" and commit master
                createOrOverwriteFile("m.txt", "m_content", true, git);
                git.commit(new CommitData("initial commit"));
                // create a branch 'experiment', switch to it, create a file 'e.txt' and commit
                git.createBranch(new BranchCreationData("experiment"));
                git.checkout(new CheckoutData("experiment"));
                createOrOverwriteFile("e.txt", "e_content", true, git);
                git.commit(new CommitData("created experiment"));
                // check out master, modify the file and commit
                git.checkout(new CheckoutData("master"));
                assertFileContent("m_content", "m.txt", git);
                createOrOverwriteFile("m2.txt", "m2_content", true, git);
                git.commit(new CommitData("modified master"));
                // check out experiment and rebase it on master
                git.checkout(new CheckoutData("experiment"));
                assertFileNotFound("m2.txt", git);
                assertFileContent("e_content", "e.txt", git);
                git.rebase(new RebaseData(null, null, "master"));
                assertFileContent("m2_content", "m2.txt", git);
            }
        });
    }

    @Test
    public void testMerge() throws Exception {
        runInNewRepo(new GitTest() {
            @Override
            public void run(GitClient git) throws Exception {
                // create a file "m.txt" and commit master
                createOrOverwriteFile("m.txt", "m_content", true, git);
                git.commit(new CommitData("initial commit"));
                // create a branch 'experiment', switch to it, create a file 'e.txt' and commit
                git.createBranch(new BranchCreationData("experiment"));
                git.checkout(new CheckoutData("experiment"));
                createOrOverwriteFile("e.txt", "e_content", true, git);
                git.commit(new CommitData("created experiment"));
                // check out master, modify the file and commit
                git.checkout(new CheckoutData("master"));
                assertFileContent("m_content", "m.txt", git);
                createOrOverwriteFile("m2.txt", "m2_content", true, git);
                git.commit(new CommitData("modified master"));
                // check out experiment and merge it with master
                git.checkout(new CheckoutData("experiment"));
                assertFileNotFound("m2.txt", git);
                assertFileContent("e_content", "e.txt", git);
                git.merge(new MergeData("merging experiment and master", "master"));
                assertFileContent("m2_content", "m2.txt", git);
            }
        });
    }

    @Test
    public void testCloneRepository() throws Exception {
        GitClient origin = null;
        GitClient copy = null;
        try {
            origin = createGitRepository();
            copy = createAndCloneRepository(origin, "testClone");
            // verify the clone
            assertFileContent("m_content", "m.txt", copy);
        }
        finally {
            deleteGitRepository(copy);
            deleteGitRepository(origin);
        }
    }

    @Test
    public void testFetchAndMerge() throws Exception {
        GitClient origin = null;
        GitClient local = null;
        try {
            origin = createGitRepository();
            local = createAndCloneRepository(origin, "testFetchAndMerge");
            assertFileContent("m_content", "m.txt", local);
            createOrOverwriteFile("newfile.txt", "new content", true, origin);
            origin.commit(new CommitData("added newfile.txt"));
            assertFileNotFound("newfile.txt", local);
            local.fetch(new FetchData("origin"));
            assertFileNotFound("newfile.txt", local);
            local.merge(new MergeData("merged", "origin/master"));
            assertFileContent("new content", "newfile.txt", local);
        }
        finally {
            deleteGitRepository(local);
            deleteGitRepository(origin);
        }
    }

    @Test
    public void testPush() throws Exception {
        GitClient origin = null;
        GitClient local = null;
        try {
            origin = createGitRepository();
            local = createAndCloneRepository(origin, "testPush");
            configureUserAndMail(local);
            assertFileContent("m_content", "m.txt", local);
            // create a local branch 'experiment' and add a file
            local.createBranch(new BranchCreationData("experiment"));
            local.checkout(new CheckoutData("experiment"));
            createOrOverwriteFile("newfile.txt", "new content", true, local);
            local.commit(new CommitData("added newfile.txt"));
            assertFileNotFound("newfile.txt", origin);
            // push the local changes
            local.push(new PushData("origin", "experiment"));
            // verify origin
            origin.checkout(new CheckoutData("experiment"));
            assertFileContent("new content", "newfile.txt", origin);
        }
        finally {
            deleteGitRepository(local);
            deleteGitRepository(origin);
        }
    }

    @Test
    public void testInvokeGenerically() throws Exception {
        InvocationData data = new InvocationData("git", "--version");
        createGitClient().invokeGenerically(data);
        String stdOut = data.getStdOut();
        System.out.println(stdOut);
        assertTrue("Version info does not start with 'git version '", stdOut.startsWith("git version "));
    }

    // private methods ---------------------------------------------------------

    private GitClient createGitClient() {
        return new GitClient(service, 10000, 1500);
    }

    private void runInNewRepo(GitTest test) throws Exception {
        GitClient git = null;
        try {
            git = createGitRepository();
            test.run(git);
        }
        finally {
            deleteGitRepository(git);
        }
    }

    private GitClient createGitRepository() {
        GitClient git = createGitClient();
        File tempDir = createTempDirectory(git);
        git.setRelativeWorkingDirectory(new StringData(tempDir.getName()));
        git.init();
        return configureUserAndMail(git);
    }

    private GitClient configureUserAndMail(GitClient git) {
        // git config user.name "you"
        git.config(new ConfigData("user.name", "you"));
        // git config user.email "you@example.com"
        git.config(new ConfigData("user.email", "you@example.com"));
        return git;
    }

    private GitClient createAndCloneRepository(GitClient origin, String directory) throws IOException {
        // create a file "m.txt" and commit master on origin
        createOrOverwriteFile("m.txt", "m_content", true, origin);
        origin.commit(new CommitData("initial commit"));

        // clone origin on local git
        GitClient copy = createGitClient();
        copy.setRelativeWorkingDirectory(new StringData(directory));
        String originPath = workingDirectory(origin).getCanonicalPath();
        copy.cloneRepository(new CloneRepositoryData(originPath, "."));
        return copy;
    }

    private File createTempDirectory(GitClient git) {
        String tempRoot = git.getBaseDirectory();
        File tempDir = new File(tempRoot, UUID.randomUUID().toString());
        tempDir.mkdirs();
        return tempDir;
    }

    private void deleteGitRepository(GitClient git) throws IOException {
        if (git != null) {
            FileUtil.deleteDirectory(workingDirectory(git));
        }
    }

    private File workingDirectory(GitClient git) throws IOException {
        return file(".", git);
    }

    private static File createOrOverwriteFile(String name, String content, boolean add, GitClient gitClient) throws IOException {
        File file = file(name, gitClient);
        IOUtil.writeTextFile(file.getAbsolutePath(), content);
        if (add) {
            gitClient.add(new AddData(name));
        }
        return file;
    }

    private static File file(String name, GitClient git) throws IOException {
        String tempDir = git.getRelativeWorkingDirectory().getValue();
        return new File(new File(git.getBaseDirectory(), tempDir), name).getCanonicalFile();
    }

    private void assertFileNotFound(String localFilePath, GitClient git) throws IOException {
        File file = file(localFilePath, git);
        assertFalse("Unexpected file: " + localFilePath, file.exists());
    }

    private void assertFileContent(String expectedContent, String localFilePath, GitClient git) throws IOException {
        String actualContent = getFileContent(localFilePath, git);
        assertEquals(expectedContent, actualContent);
    }

    private String getFileContent(String localFilePath, GitClient git) throws IOException {
        File file = file(localFilePath, git);
        String actualContent = getContentOfURI(file.getAbsolutePath(), SystemInfo.getFileEncoding());
        return actualContent;
    }

    public static String getContentOfURI(String uri, String encoding) throws IOException {
        Reader reader = IOUtil.getReaderForURI(uri, encoding);
        StringWriter writer = new StringWriter();
        IOUtil.transfer(reader, writer);
        IOUtil.close(reader);
        IOUtil.close(writer);
        return writer.toString();
    }

    private static StatusData getStatus(GitClient gitClient) {
        StatusData status = new StatusData();
        gitClient.status(status);
        System.out.println(status);
        return status;
    }

    private static boolean branchExists(String branchName, GitClient gitClient) {
        BranchListData list = new BranchListData();
        gitClient.listBranches(list);
        for (StringData branch : list.getBranches()) {
            if (branch.getValue().equals(branchName)) {
                return true;
            }
        }
        return false;
    }

    private void createTestFilesForReset(GitClient gitClient) throws IOException {
        // give git-reset a target commit
        InvocationData data = new InvocationData("git", "commit", "--allow-empty", "-m", "Initial commit.");
        gitClient.invokeGenerically(data);
        // create the files
        createOrOverwriteFile(UNTRACKED_FILE, "untracked", false, gitClient);
        createOrOverwriteFile(ADDED_FILE, "added", true, gitClient);
        // verify their presence
        StatusData status = getStatus(gitClient);
        assertTrue(containsFile(UNTRACKED_FILE, status.getUntrackedFiles()));
        assertTrue(containsFile(ADDED_FILE, status.getAddedFiles()));
    }

    public void assertFileContained(String expectedFile, List<StringData> actualFiles) {
        assertTrue("File missing: " + expectedFile, containsFile(expectedFile, actualFiles));
    }

    public void assertFileMissing(String unexpectedFile, List<StringData> actualFiles) {
        assertFalse("File expected to be missing, but was found: " + unexpectedFile, containsFile(unexpectedFile, actualFiles));
    }

    private static boolean containsFile(String fileName, List<StringData> list) {
        for (StringData file : list) {
            if (file.getValue().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private boolean runningOnYosemite() {
        return "Mac OS X".equals(SystemInfo.getOsName()) && SystemInfo.getOsVersion().startsWith("10.10.");
    }

    public static abstract class GitTest {
        public abstract void run(GitClient gitClient) throws Exception;
    }

}
