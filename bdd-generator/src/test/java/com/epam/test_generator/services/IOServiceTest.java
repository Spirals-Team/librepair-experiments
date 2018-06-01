package com.epam.test_generator.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.featurefile.request.FeatureFileDTO;
import com.epam.test_generator.controllers.project.ProjectTransformer;
import com.epam.test_generator.controllers.project.response.ProjectFullDTO;
import com.epam.test_generator.controllers.suit.SuitTransformer;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import com.epam.test_generator.dao.interfaces.SuitDAO;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.file_generator.FileGenerator;
import com.epam.test_generator.services.exceptions.NotFoundException;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IOServiceTest {

    @InjectMocks
    private IOService ioService;

    @Mock
    private FileGenerator fileGenerator;

    @Mock
    private ProjectService projectService;

    @Mock
    private SuitTransformer suitTransformer;

    @Mock
    private SuitDAO suitDAO;

    @Mock
    private ProjectTransformer projectTransformer;

    @Mock
    private ProjectFullDTO projectFullDTO;

    //@Mock
    //private SuitDTO suitDTO;

    @Mock
    private CaseDTO caseDTO;

    @Mock
    private Suit suit;

    private FeatureFileDTO featureFileDTO;

    private final static long SIMPLE_PROJECT_ID = 1L;
    private final static long SIMPLE_SUIT_ID = 1L;
    private final static long SIMPLE_CASE_ID = 1L;

    private SuitDTO suitDTO;

    @Before
    public void setUp() throws Exception {
        suitDTO.setId(SIMPLE_SUIT_ID);

        when(fileGenerator.generate(any(), anyList())).thenReturn("feature");
        when(projectTransformer.toFullDto(any())).thenReturn(projectFullDTO);
        when(projectFullDTO.getSuits()).thenReturn(Lists.newArrayList(suitDTO));
        when(suitDTO.getId()).thenReturn(SIMPLE_SUIT_ID);
        when(suitDTO.getCases()).thenReturn(Lists.newArrayList(caseDTO));
        when(suitDTO.getName()).thenReturn("");
        when(caseDTO.getId()).thenReturn(SIMPLE_CASE_ID);
        when(projectService.getProjectByProjectId(anyLong())).thenReturn(null);
        when(suitDAO.findById(anyLong())).thenReturn(Optional.of(suit));
        when(suitTransformer.toDto(suit)).thenReturn(suitDTO);
        featureFileDTO = new FeatureFileDTO();
        featureFileDTO.setSuitId(SIMPLE_SUIT_ID);
        featureFileDTO.setCaseIds(Lists.newArrayList(SIMPLE_CASE_ID));
    }

    @Test
    public void generateZipFile_SimpleInput_Ok() throws IOException {
        byte[] bytes = ioService
            .generateZipFile(SIMPLE_PROJECT_ID, Lists.newArrayList(featureFileDTO));
        assertTrue(bytes.length > 0);
    }

    @Test(expected = NotFoundException.class)
    public void generateZipFile_InvalidSuitId_NotFoundException() throws IOException {
        featureFileDTO.setSuitId(2L);
        ioService.generateZipFile(SIMPLE_PROJECT_ID, Lists.newArrayList(featureFileDTO));
    }

    @Test(expected = NotFoundException.class)
    public void generateZipFile_InvalidCaseId_NotFoundException() throws IOException {
        featureFileDTO.setCaseIds(Lists.newArrayList(2L));
        ioService.generateZipFile(SIMPLE_PROJECT_ID, Lists.newArrayList(featureFileDTO));
    }
}