/*
 * Copyright 2017 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.bugpatterns;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.BugCheckerRefactoringTestHelper;
import com.google.errorprone.BugCheckerRefactoringTestHelper.TestMode;
import com.google.errorprone.CompilationTestHelper;
import com.google.errorprone.ErrorProneFlags;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** @author hanuszczak@google.com (Łukasz Hanuszczak) */
@RunWith(JUnit4.class)
public final class UngroupedOverloadsTest {

  private final CompilationTestHelper compilationHelper =
      CompilationTestHelper.newInstance(UngroupedOverloads.class, getClass());

  private final BugCheckerRefactoringTestHelper refactoringHelper =
      BugCheckerRefactoringTestHelper.newInstance(
          new UngroupedOverloads(ErrorProneFlags.empty()), getClass());

  @Test
  public void ungroupedOverloadsPositiveCasesSingle() throws Exception {
    compilationHelper.addSourceFile("UngroupedOverloadsPositiveCasesSingle.java").doTest();
  }

  @Test
  public void ungroupedOverloadsPositiveCasesMultiple() throws Exception {
    compilationHelper.addSourceFile("UngroupedOverloadsPositiveCasesMultiple.java").doTest();
  }

  @Test
  public void ungroupedOverloadsPositiveCasesInterleaved() throws Exception {
    compilationHelper.addSourceFile("UngroupedOverloadsPositiveCasesInterleaved.java").doTest();
  }

  @Test
  public void ungroupedOverloadsPositiveCasesCovering() throws Exception {
    compilationHelper.addSourceFile("UngroupedOverloadsPositiveCasesCovering.java").doTest();
  }

  @Test
  public void ungroupedOverloadsPositiveCasesCoveringOnlyFirstOverload() throws Exception {
    compilationHelper
        .addSourceFile("UngroupedOverloadsPositiveCasesCoveringOnlyOnFirst.java")
        .setArgs(ImmutableList.of("-XepOpt:UngroupedOverloads:FindingsOnFirstOverload"))
        .doTest();
  }

  @Test
  public void ungroupedOverloadsNegativeCases() throws Exception {
    compilationHelper.addSourceFile("UngroupedOverloadsNegativeCases.java").doTest();
  }

  @Test
  public void ungroupedOverloadsRefactoringComments() throws Exception {
    refactoringHelper
        .addInput("UngroupedOverloadsRefactoringComments.java")
        .addOutput("UngroupedOverloadsRefactoringComments_expected.java")
        .doTest();
  }

  @Test
  public void ungroupedOverloadsRefactoringMultiple() throws Exception {
    refactoringHelper
        .addInput("UngroupedOverloadsRefactoringMultiple.java")
        .addOutput("UngroupedOverloadsRefactoringMultiple_expected.java")
        .doTest();
  }

  @Test
  public void ungroupedOverloadsRefactoringInterleaved() throws Exception {
    refactoringHelper
        .addInput("UngroupedOverloadsRefactoringInterleaved.java")
        .addOutput("UngroupedOverloadsRefactoringInterleaved_expected.java")
        .doTest();
  }

  @Test
  public void ungroupedOverloadsRefactoringBelowCutoffLimit() throws Exception {
    // Here we have 4 methods so refactoring should be applied.
    refactoringHelper
        .addInputLines(
            "in/BelowLimit.java",
            "class BelowLimit {",
            "  BelowLimit() {}",
            "  void foo() {}",
            "  void bar() {}",
            "  void foo(int x) {}",
            "}")
        .addOutputLines(
            "out/BelowLimit.java",
            "class BelowLimit {",
            "  BelowLimit() {}",
            "  void foo() {}",
            "  void foo(int x) {}",
            "  void bar() {}",
            "}")
        .doTest();
  }

  @Test
  public void ungroupedOverloadsRefactoring_fiveMethods() throws Exception {
    refactoringHelper
        .addInputLines(
            "in/AboveLimit.java",
            "class AboveLimit {",
            "  AboveLimit() {}",
            "  void foo() {}",
            "  void bar() {}",
            "  void foo(int x) {}",
            "  void baz() {}",
            "}")
        .addOutputLines(
            "out/AboveLimit.java",
            "class AboveLimit {",
            "  AboveLimit() {}",
            "  void foo() {}",
            "  void foo(int x) {}",
            "  void bar() {}",
            "  void baz() {}",
            "}")
        .doTest();
  }

  @Ignore // TODO(b/71818169): fix and re-enable
  @Test
  public void staticAndNonStatic() throws Exception {
    refactoringHelper
        .addInputLines(
            "Test.java",
            "class Test {",
            "  void foo() {}",
            "  void bar() {}",
            "  static void foo(int x) {}",
            "}")
        .expectUnchanged()
        .doTest();
  }

  @Test
  public void staticAndNonStaticInterspersed() throws Exception {
    compilationHelper
        .addSourceLines(
            "Test.java",
            "class Test {",
            "  private void foo(int x) {}",
            "  private static void foo(int x, int y, int z) {}",
            "  private void foo(int x, int y) {}",
            "}")
        .doTest();
  }

  @Test
  public void suppressOnAnyMethod() throws Exception {
    compilationHelper
        .addSourceLines(
            "Test.java",
            "class Test {",
            "  void foo() {}",
            "  void bar() {}",
            "  @SuppressWarnings(\"UngroupedOverloads\") void foo(int x) {}",
            "}")
        .doTest();
  }

  @Test
  public void javadoc() throws Exception {
    refactoringHelper
        .addInputLines(
            "in/Test.java",
            "class Test {",
            "  void foo() {}",
            "  void bar() {}",
            "  /** doc */",
            "  void foo(int x) {}",
            "}")
        .addOutputLines(
            "out/Test.java",
            "class Test {",
            "  void foo() {}",
            "",
            "  /** doc */",
            "  void foo(int x) {}",
            "  void bar() {}",
            "}")
        .doTest(TestMode.TEXT_MATCH);
  }
}
