/*
 * Copyright 2013 The Error Prone Authors.
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

package com.google.errorprone.bugpatterns.inject;

import com.google.errorprone.CompilationTestHelper;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** @author sgoldfeder@google.com (Steven Goldfeder) */
@RunWith(JUnit4.class)
public class ScopeOrQualifierAnnotationRetentionTest {
  private CompilationTestHelper compilationHelper;

  @Before
  public void setUp() {
    compilationHelper =
        CompilationTestHelper.newInstance(ScopeOrQualifierAnnotationRetention.class, getClass());
  }

  @Test
  public void testPositiveCase() throws Exception {
    compilationHelper
        .addSourceFile("ScopeOrQualifierAnnotationRetentionPositiveCases.java")
        .doTest();
  }

  @Test
  public void testNegativeCase() throws Exception {
    compilationHelper
        .addSourceFile("ScopeOrQualifierAnnotationRetentionNegativeCases.java")
        .doTest();
  }

  @Test
  public void nestedQualifierInDaggerModule() {
    compilationHelper
        .addSourceLines(
            "DaggerModule.java", //
            "@dagger.Module class DaggerModule {",
            "@javax.inject.Scope",
            "public @interface TestAnnotation {}",
            "}")
        .doTest();
  }

  @Test
  public void testIgnoredOnAndroid() {
    compilationHelper
        .setArgs(Collections.singletonList("-XDandroidCompatible=true"))
        .addSourceLines(
            "TestAnnotation.java", //
            "@javax.inject.Scope",
            "public @interface TestAnnotation {}")
        .doTest();
  }

  @Test
  public void testSourceRetentionStillFiringOnAndroid() {
    compilationHelper
        .setArgs(Collections.singletonList("-XDandroidCompatible=true"))
        .addSourceLines(
            "TestAnnotation.java",
            "import java.lang.annotation.Retention;",
            "import java.lang.annotation.RetentionPolicy;",
            "@javax.inject.Scope",
            "// BUG: Diagnostic contains: @Retention(RUNTIME)",
            "@Retention(RetentionPolicy.SOURCE)",
            "public @interface TestAnnotation {}")
        .doTest();
  }
}
