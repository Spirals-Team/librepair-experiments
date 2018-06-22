/**
 * Anserini: An information retrieval toolkit built on Lucene
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

package io.anserini.document;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.bzip2.CBZip2OutputStream;
import org.junit.Before;
import org.junit.Test;

import io.anserini.collection.Collection;
import io.anserini.collection.WikipediaCollection;


public class WikipediaArticleTest extends DocumentTest {
  protected static Path tmpPath;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    dType = new TrecwebDocument();

    String doc = "<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.10/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xm\n" +
        "l/export-0.10.xsd\" version=\"0.10\" xml:lang=\"en\">\n" +
        "  <siteinfo>\n" +
        "    <sitename>Wiktionary</sitename>\n" +
        "    <dbname>enwiktionary</dbname>\n" +
        "    <base>https://en.wiktionary.org/wiki/Wiktionary:Main_Page</base>\n" +
        "    <generator>MediaWiki 1.31.0-wmf.26</generator>\n" +
        "    <case>case-sensitive</case>\n" +
        "    <namespaces>\n" +
        "      <namespace key=\"-2\" case=\"case-sensitive\">Media</namespace>\n" +
        "      <namespace key=\"-1\" case=\"first-letter\">Special</namespace>\n" +
        "      <namespace key=\"0\" case=\"case-sensitive\" />\n" +
        "      <namespace key=\"1\" case=\"case-sensitive\">Talk</namespace>\n" +
        " .... " +
        "    </namespaces>\n" +
        "  </siteinfo>\n" +
        "  <page>\n" +
        "    <title>Wiktionary:Welcome, newcomers</title>\n" +
        "    <ns>0</ns>\n" +
        "    <id>7</id>\n" +
        "<revision>\n" +
        "      <id>28863815</id>\n" +
        "      <parentid>18348012</parentid>\n" +
        "      <timestamp>2014-08-24T22:39:11Z</timestamp>\n" +
        "      <contributor>\n" +
        "        <username>Ready Steady Yeti</username>\n" +
        "        <id>1705268</id>\n" +
        "      </contributor>\n" +
        "      <comment>This link doesn't actually exist. If you don't believe me, check it yourself.</comment>\n" +
        "      <model>wikitext</model>\n" +
        "      <format>text/x-wiki</format>\n" +
        "      <text xml:space=\"preserve\">this is the \n" +
        " real content \n" +
        "      </text>\n" +
        "  </page>\n" +
        "</mediawiki>";

    tmpPath = createTempFile();
    OutputStream fout = Files.newOutputStream(tmpPath);
    BufferedOutputStream out = new BufferedOutputStream(fout);
    BZip2CompressorOutputStream tmpOut = new BZip2CompressorOutputStream(out);
    StringInputStream in = new StringInputStream(doc);
    final byte[] buffer = new byte[2048];
    int n = 0;
    while (-1 != (n = in.read(buffer))) {
      tmpOut.write(buffer, 0, n);
    }
    tmpOut.close();
  }

  @Test
  public void test() throws IOException {
    WikipediaCollection wc = new WikipediaCollection();
    Collection.FileSegment iter = wc.createFileSegment(tmpPath);
    SourceDocument parsed = iter.next();
    assertEquals(parsed.id(), "Wiktionary:Welcome, newcomers");
    assertEquals(parsed.content(), "Wiktionary:Welcome, newcomers.\nthis is the   real content");
  }
}
