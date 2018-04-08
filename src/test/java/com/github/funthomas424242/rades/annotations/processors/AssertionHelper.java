package com.github.funthomas424242.rades.annotations.processors;

import com.google.common.truth.StringSubject;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;

import javax.annotation.Nullable;

public class AssertionHelper {


    public static CompilationSubject assertThat(final Compilation compilation) {
        return CompilationSubject.assertThat(compilation);
    }

    public static StringSubject assertThat(@Nullable String actual) {
        return com.google.common.truth.Truth.assertThat(actual);
    }

}
