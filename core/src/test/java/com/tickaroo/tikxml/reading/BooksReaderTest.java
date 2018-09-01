/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.reading;

import com.tickaroo.tikxml.TestUtils;
import com.tickaroo.tikxml.TikXml;
import java.io.IOException;
import okio.BufferedSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hannes Dorfmann
 */
public class BooksReaderTest {


  @Test
  public void read() throws IOException {
    BufferedSource input = TestUtils.sourceForFile("books.xml");

    TikXml tikXml = new TikXml.Builder()
        .addTypeAdapter(Book.class, new BookTypeAdapter())
        .addTypeAdapter(Catalogue.class, new CatalogueTypeAdapter())
        .build();


    Catalogue catalogue = tikXml.read(input, Catalogue.class);


    Assert.assertEquals(12, catalogue.books.size());

  }
}
