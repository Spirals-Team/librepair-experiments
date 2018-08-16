/*
 * SonarQube :: Plugins :: SCM :: Git
 * Copyright (C) 2014-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.scm.git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.scm.BlameCommand;
import org.sonar.api.batch.scm.ScmProvider;
import org.sonar.api.utils.MessageException;

public class GitScmProvider extends ScmProvider {

  private static final Logger LOG = LoggerFactory.getLogger(GitScmProvider.class);

  private final JGitBlameCommand jgitBlameCommand;

  public GitScmProvider(JGitBlameCommand jgitBlameCommand) {
    this.jgitBlameCommand = jgitBlameCommand;
  }

  @Override
  public String key() {
    return "git";
  }

  @Override
  public boolean supports(File baseDir) {
    RepositoryBuilder builder = new RepositoryBuilder().findGitDir(baseDir);
    return builder.getGitDir() != null;
  }

  @Override
  public BlameCommand blameCommand() {
    return this.jgitBlameCommand;
  }

  @CheckForNull
  @Override
  public Set<Path> branchChangedFiles(String targetBranchName, Path rootBaseDir) {
    try (Repository repo = buildRepo(rootBaseDir)) {
      Ref targetRef = resolveTargetRef(targetBranchName, repo);
      if (targetRef == null) {
        return null;
      }

      try (Git git = newGit(repo)) {
        return git.diff().setShowNameAndStatusOnly(true)
          .setOldTree(prepareTreeParser(repo, targetRef))
          .setNewTree(prepareNewTree(repo))
          .call().stream()
          .filter(diffEntry -> diffEntry.getChangeType() == DiffEntry.ChangeType.ADD || diffEntry.getChangeType() == DiffEntry.ChangeType.MODIFY)
          .map(diffEntry -> repo.getWorkTree().toPath().resolve(diffEntry.getNewPath()))
          .collect(Collectors.toSet());
      }
    } catch (IOException | GitAPIException e) {
      LOG.warn(e.getMessage(), e);
    }
    return null;
  }

  @CheckForNull
  public Map<Path, Set<Integer>> branchChangedLines(String targetBranchName, Path rootBaseDir, Set<Path> changedFiles) {
    try (Repository repo = buildRepo(rootBaseDir)) {
      Ref targetRef = resolveTargetRef(targetBranchName, repo);
      if (targetRef == null) {
        return null;
      }

      Map<Path, Set<Integer>> changedLines = new HashMap<>();

      try (Git git = newGit(repo)) {
        for (Path path : changedFiles) {
          ChangedLinesComputer computer = new ChangedLinesComputer();
          List<DiffEntry> diffEntries = git.diff()
            .setOutputStream(computer.receiver())
            .setOldTree(prepareTreeParser(repo, targetRef))
            .setPathFilter(PathFilter.create(rootBaseDir.relativize(path).toString()))
            .call();

          diffEntries
            .stream()
            .filter(diffEntry -> diffEntry.getChangeType() == DiffEntry.ChangeType.ADD
              || diffEntry.getChangeType() == DiffEntry.ChangeType.MODIFY)
            .forEach(diffEntry -> changedLines.put(path, computer.changedLines()));
        }
      }
      return changedLines;
    } catch (Exception e) {
      LOG.warn("Failed to get changed lines from git", e);
    }
    return null;
  }

  @CheckForNull
  private static Ref resolveTargetRef(String targetBranchName, Repository repo) throws IOException {
    Ref targetRef = repo.exactRef("refs/heads/" + targetBranchName);
    if (targetRef == null) {
      targetRef = repo.exactRef("refs/remotes/origin/" + targetBranchName);
    }
    if (targetRef == null) {
      LOG.warn("Could not find ref: {} in refs/heads or refs/remotes/origin", targetBranchName);
      return null;
    }
    return targetRef;
  }

  @Override
  public Path relativePathFromScmRoot(Path path) {
    RepositoryBuilder builder = getVerifiedRepositoryBuilder(path);
    return builder.getGitDir().toPath().getParent().relativize(path);
  }

  @Override
  public String revisionId(Path path) {
    RepositoryBuilder builder = getVerifiedRepositoryBuilder(path);
    try {
      return getHead(builder.build()).getObjectId().getName();
    } catch (IOException e) {
      throw new IllegalStateException("I/O error while getting revision ID for path: " + path, e);
    }
  }

  private static AbstractTreeIterator prepareNewTree(Repository repo) throws IOException {
    CanonicalTreeParser treeParser = new CanonicalTreeParser();
    try (ObjectReader objectReader = repo.newObjectReader()) {
      treeParser.reset(objectReader, repo.parseCommit(getHead(repo).getObjectId()).getTree());
    }
    return treeParser;
  }

  private static Ref getHead(Repository repo) throws IOException {
    return repo.exactRef("HEAD");
  }

  private AbstractTreeIterator prepareTreeParser(Repository repo, Ref targetRef) throws IOException {
    try (RevWalk walk = newRevWalk(repo)) {
      walk.markStart(walk.parseCommit(targetRef.getObjectId()));
      walk.markStart(walk.parseCommit(getHead(repo).getObjectId()));
      walk.setRevFilter(RevFilter.MERGE_BASE);
      RevCommit base = walk.parseCommit(walk.next());
      LOG.debug("Merge base sha1: {}", base.getName());
      CanonicalTreeParser treeParser = new CanonicalTreeParser();
      try (ObjectReader objectReader = repo.newObjectReader()) {
        treeParser.reset(objectReader, base.getTree());
      }

      walk.dispose();

      return treeParser;
    }
  }

  Git newGit(Repository repo) {
    return new Git(repo);
  }

  RevWalk newRevWalk(Repository repo) {
    return new RevWalk(repo);
  }

  Repository buildRepo(Path basedir) throws IOException {
    return getVerifiedRepositoryBuilder(basedir).build();
  }

  static RepositoryBuilder getVerifiedRepositoryBuilder(Path basedir) {
    RepositoryBuilder builder = new RepositoryBuilder()
      .findGitDir(basedir.toFile())
      .setMustExist(true);

    if (builder.getGitDir() == null) {
      throw MessageException.of("Not inside a Git work tree: " + basedir);
    }
    return builder;
  }
}
