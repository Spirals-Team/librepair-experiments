/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
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
package org.sonar.server.computation.task.projectanalysis.scm;

import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static java.util.Objects.requireNonNull;

@Immutable
public final class Changeset {

  private final String revision;
  private final long date;
  @CheckForNull
  private final String author;

  private Changeset(Builder builder) {
    this.revision = builder.revision.intern();
    this.author = builder.author == null ? null : builder.author.intern();
    this.date = builder.date;
  }

  public static Builder newChangesetBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String revision;
    private Long date;
    @CheckForNull
    private String author;

    private Builder() {
      // prevents direct instantiation
    }

    public Builder setRevision(String revision) {
      this.revision = checkRevision(revision);
      return this;
    }

    public Builder setDate(Long date) {
      this.date = checkDate(date);
      return this;
    }

    public Builder setAuthor(@Nullable String author) {
      this.author = author;
      return this;
    }

    public Changeset build() {
      checkRevision(revision);
      checkDate(date);
      return new Changeset(this);
    }

    private static String checkRevision(String revision) {
      return requireNonNull(revision, "Revision cannot be null");
    }

    private static long checkDate(Long date) {
      return requireNonNull(date, "Date cannot be null");
    }

  }

  public String getRevision() {
    return revision;
  }

  public long getDate() {
    return date;
  }

  @CheckForNull
  public String getAuthor() {
    return author;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Changeset changeset = (Changeset) o;

    return revision.equals(changeset.revision);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revision);
  }

  @Override
  public String toString() {
    return "Changeset{" +
      "revision='" + revision + '\'' +
      ", author='" + author + '\'' +
      ", date=" + date +
      '}';
  }
}
