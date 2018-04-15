/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
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
package guru.nidi.codeassert.junit.kotlin;

import guru.nidi.codeassert.detekt.DetektMatcher;
import guru.nidi.codeassert.detekt.DetektResult;
import guru.nidi.codeassert.ktlint.KtlintMatcher;
import guru.nidi.codeassert.ktlint.KtlintResult;
import org.hamcrest.Matcher;

public final class KotlinCodeAssertMatchers {
    private KotlinCodeAssertMatchers() {
    }

    public static Matcher<KtlintResult> hasNoKtlintIssues() {
        return new KtlintMatcher();
    }

    public static Matcher<DetektResult> hasNoDetektIssues() {
        return new DetektMatcher();
    }
}
