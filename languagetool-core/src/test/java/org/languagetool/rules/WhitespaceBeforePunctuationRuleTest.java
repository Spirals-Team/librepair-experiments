/* LanguageTool, a natural language style checker
 * Copyright (C) 2016 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WhitespaceBeforePunctuationRuleTest {
  
  @Test
  public void testOkay() {
    WhitespaceBeforePunctuationRule rule = new WhitespaceBeforePunctuationRule(TestTools.getEnglishMessages());
    AnalyzedSentence sentence = new AnalyzedSentence(new AnalyzedTokenReadings[] {
            new AnalyzedTokenReadings(new AnalyzedToken(" ", null, null), 0),
            new AnalyzedTokenReadings(new AnalyzedToken("%", "SYM", null), 1)
    });
    assertThat(rule.match(sentence).length, is(0));
  }

  @Test
  public void testError() {
    WhitespaceBeforePunctuationRule rule = new WhitespaceBeforePunctuationRule(TestTools.getEnglishMessages());
    AnalyzedSentence sentence = new AnalyzedSentence(new AnalyzedTokenReadings[] {
            new AnalyzedTokenReadings(new AnalyzedToken("2", null, null), 0),
            new AnalyzedTokenReadings(new AnalyzedToken(" ", null, null), 1),
            new AnalyzedTokenReadings(new AnalyzedToken("%", "SYM", null), 2)
    });
    assertThat(rule.match(sentence).length, is(1));
  }

}