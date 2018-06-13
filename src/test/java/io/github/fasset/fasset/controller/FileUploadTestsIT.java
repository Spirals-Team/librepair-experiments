/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.controller;

import io.github.fasset.fasset.kernel.storage.FileSystemStorageService;
import io.github.fasset.fasset.kernel.util.StorageFileNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTestsIT {

    private MockMvc mockMvc;
    @Qualifier("fileUploadsController")
    @Autowired
    private FileUploadsController fileUploadsController;
    @Qualifier("fileSystemStorageService")
    @Autowired
    private FileSystemStorageService fileSystemStorageService;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(fileUploadsController).build();
    }

    @Test
    public void shouldListAllFiles() throws Exception {
        /*given(fileSystemStorageService.loadAll())
                .willReturn(Stream.of(Paths.get("Data1.xlsx"),Paths.get("Data2.xlsx")));*/

        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "Data1.xlsx", "text/plain", "Spring Framework".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "Data2.xlsx", "text/plain", "Spring Framework".getBytes());
        this.fileSystemStorageService.store(multipartFile1);
        this.fileSystemStorageService.store(multipartFile2);

        Stream files = fileSystemStorageService.loadAll();

        this.mockMvc.perform(get("/files")).andExpect(status().isOk())
            .andExpect(model().attribute("files", Matchers.contains("http://localhost/files/Data1.xlsx", "http://localhost/files/Data2.xlsx")));

        Assert.assertEquals(2, files.count());
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "Data1.xlsx", "text/plain", "Spring Framework".getBytes());
        this.mockMvc.perform(multipart("/files").file(multipartFile)).andExpect(status().isFound()).andExpect(header().string("Location", "/files"));

        this.fileSystemStorageService.store(multipartFile);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = StorageFileNotFoundException.class)
    public void should404WhenMissingFile() throws Exception {
        given(this.fileSystemStorageService.loadAsResource("/Data1.xlsx")).willThrow(StorageFileNotFoundException.class);

        this.mockMvc.perform(get("/files/Data1.xlsx")).andExpect(status().isNotFound());
    }
}
