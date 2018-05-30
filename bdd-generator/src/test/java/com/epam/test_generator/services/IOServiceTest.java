package com.epam.test_generator.services;

import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.controllers.featurefile.request.FeatureFileDTO;
import com.epam.test_generator.controllers.project.ProjectTransformer;
import com.epam.test_generator.controllers.project.response.ProjectFullDTO;
import com.epam.test_generator.controllers.suit.SuitTransformer;
import com.epam.test_generator.controllers.suit.response.SuitDTO;
import com.epam.test_generator.dao.interfaces.SuitDAO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.file_generator.FileGenerator;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
    @Mock
    private SuitDTO suitDTO;
    @Mock
    private CaseDTO caseDTO;
    @Mock
    private Suit suit;

    private FeatureFileDTO featureFileDTO;

    @Before
    public void setUp() throws Exception {
        featureFileDTO = new FeatureFileDTO();
        featureFileDTO.setSuitId(1L);
        featureFileDTO.setCaseIds(Lists.newArrayList(1L));

        when(fileGenerator.generate(any(SuitDTO.class), anyList())).thenReturn("feature");
        when(projectTransformer.toFullDto(any(Project.class))).thenReturn(projectFullDTO);
        when(projectFullDTO.getSuits()).thenReturn(Lists.newArrayList(suitDTO));
        when(suitDTO.getId()).thenReturn(1L);
        when(suitDTO.getCases()).thenReturn(Lists.newArrayList(caseDTO));
        when(suitDTO.getName()).thenReturn("");
        when(caseDTO.getId()).thenReturn(1L);

        when(suitDAO.findById(anyLong())).thenReturn(Optional.of(suit));
        when(suitTransformer.toDto(suit)).thenReturn(suitDTO);
    }

    @Test
    public void generateZipFile_SimpleSuit_Ok() throws IOException {
        byte[] bytes = ioService.generateZipFile(1L, Lists.newArrayList(featureFileDTO));
        assertTrue(bytes.length > 0);
    }
}