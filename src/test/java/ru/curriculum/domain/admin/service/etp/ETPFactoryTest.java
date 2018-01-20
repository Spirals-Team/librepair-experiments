package ru.curriculum.domain.admin.service.etp;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.curriculum.domain.admin.domain.etp.ETPMock;
import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.service.etp.ETPFactory;
import ru.curriculum.service.etp.PlanFactory;
import ru.curriculum.service.etp.dto.ETP_DTO;

@RunWith(MockitoJUnitRunner.class)
public class ETPFactoryTest extends Assert {
    @Mock
    private PlanFactory planFactory;
    @InjectMocks
    private ETPFactory etpFactory;

    private ETPMock etpMock;

    @Before
    public void setUp() {
        etpMock = new ETPMock();
    }

    @Test
    public void createETP() {
        ETP_DTO etpDTO = etpMock.getETP_DTO();
        ETP etp = etpFactory.create(etpDTO);

        assertEquals(etpDTO.getId(), etp.id());
        assertEquals(etpDTO.getTitle(), etp.title());
        assertEquals(etpDTO.getTarget(), etp.target());
        assertEquals(etpDTO.getDistanceLearningBeginDate(), etp.distanceLearningBeginDate());
        assertEquals(etpDTO.getDistanceLearningEndDate(), etp.distanceLearningEndDate());
        assertEquals(etpDTO.getFullTimeLearningBeginDate(), etp.fullTimeLearningBeginDate());
        assertEquals(etpDTO.getFullTimeLearningEndDate(), etp.fullTimeLearningEndDate());
        assertEquals(etpDTO.getEaModules().size(), etp.eaModules().size());
        assertEquals(etpDTO.getEmaModules().size(), etp.emaModules().size());
        assertEquals(etpDTO.getOmaModules().size(), etp.omaModules().size());
    }
}
