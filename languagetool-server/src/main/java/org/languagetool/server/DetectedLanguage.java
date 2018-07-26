/* LanguageTool, a natural language style checker
 * Copyright (C) 2018 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.server;

import org.languagetool.Language;

import java.util.Objects;

/**
 * @since 4.2
 */
public class DetectedLanguage {

  private final Language givenLanguage;
  private final Language detectedLanguage;

  public DetectedLanguage(Language givenLanguage, Language detectedLanguage) {
    this.givenLanguage = Objects.requireNonNull(givenLanguage);
    this.detectedLanguage = detectedLanguage;
  }

  public Language getGivenLanguage() {
    return givenLanguage;
  }

  public Language getDetectedLanguage() {
    return detectedLanguage;
  }

  @Override
  public String toString() {
    return detectedLanguage.getShortCodeWithCountryAndVariant();
  }
}
