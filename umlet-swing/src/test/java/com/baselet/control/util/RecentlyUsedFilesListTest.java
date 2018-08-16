package com.baselet.control.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecentlyUsedFilesListTest {

    @InjectMocks
    private RecentlyUsedFilesList recentlyUsedFilesListMock;

    @Mock
    private List<String> recentFiles;

    @Before
    public void setUp() throws Exception {

        recentlyUsedFilesListMock = mock(RecentlyUsedFilesList.class);
        recentFiles = new ArrayList();
    }

    @Test
    public void testAdd() throws Exception {

        String name = "testName";
        recentFiles.add(name);

        when(recentlyUsedFilesListMock.getList()).thenReturn(recentFiles);
        assertEquals(recentlyUsedFilesListMock.getList().get(0), name);
    }
}


