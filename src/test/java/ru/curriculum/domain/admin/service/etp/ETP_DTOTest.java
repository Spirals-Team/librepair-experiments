package ru.curriculum.domain.admin.service.etp;

import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import ru.curriculum.domain.admin.domain.etp.ETPMock;
import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.domain.etp.entity.educationActivity.EAModule;
import ru.curriculum.domain.etp.entity.educationActivity.EASection;
import ru.curriculum.domain.etp.entity.educationActivity.EATopic;
import ru.curriculum.domain.etp.entity.educationMethodicalActivity.EMAModule;
import ru.curriculum.domain.etp.entity.organizationMethodicalActivity.OMAModule;
import ru.curriculum.service.etp.dto.EMAModuleDTO;
import ru.curriculum.service.etp.dto.OMAModuleDTO;
import ru.curriculum.service.etp.dto.EAModuleDTO;
import ru.curriculum.service.etp.dto.EASectionDTO;
import ru.curriculum.service.etp.dto.EATopicDTO;
import ru.curriculum.service.etp.dto.ETP_DTO;


public class ETP_DTOTest extends Assert {
    private ETPMock etpMock;

    @Before
    public void setUp() {
        etpMock = new ETPMock();
    }

    @Test
    public void createETP_DTOFromETP_mustBeCreateCorrectly() {
        ETP etp = etpMock.getETP();

        ETP_DTO etpDTO = new ETP_DTO(etp);

        assertEquals(etp, etpDTO);
    }

    @Test
    public void createEAModuleDTOFromETP_mustBeCreateCorrectly() {
        EAModule eaModule = new EAModule("Модуль учебной деятельности", etpMock.getEASections());

        EAModuleDTO eaModuleDTO = new EAModuleDTO(eaModule);

        assertEquals(eaModule.name(), eaModuleDTO.getName());
        assertEquals(eaModule.id(), eaModuleDTO.getId());
        assertEquals(eaModule.sections().size(), eaModuleDTO.getSections().size());
    }

    @Test
    public void createEASectionDTOFromEASection_mustBeCreateCorrectly() {
        EASection eaSection = new EASection("Раздел учебной деятельности", etpMock.getEATopics());

        EASectionDTO eaSectionDTO = new EASectionDTO(eaSection);

        assertEquals(eaSection.id(), eaSectionDTO.getId());
        assertEquals(eaSection.name(), eaSectionDTO.getName());
        assertEquals(eaSection.topics().size(), eaSectionDTO.getTopics().size());
    }

    @Test
    public void createEATopicDTOFromEATopic_mustBeCreateCorrectly() {
        EATopic eaTopic = new EATopic("Тема раздела модуля учебной деятельности", etpMock.getPlan());

        EATopicDTO eaTopicDTO = new EATopicDTO(eaTopic);

        assertEquals(eaTopic.id(), eaTopicDTO.getId());
        assertEquals(eaTopic.name(), eaTopicDTO.getName());
    }

    @Test
    public void createEMAModuleDTOFromEMAModule_mustBeCreateCorrectly() {
        EMAModule emaModule = new EMAModule("Модуль учебно-методической деятельности", etpMock.getPlan());

        EMAModuleDTO emaModuleDTO = new EMAModuleDTO(emaModule);

        assertEquals(emaModule.id(), emaModuleDTO.getId());
        assertEquals(emaModule.name(), emaModuleDTO.getName());
    }

    @Test
    public void createOMAModuleDTOFromOMAModule_mustBeCreateCorrectly() {
        OMAModule omaModule = new OMAModule("Модуль-организационно-методической деятельности", etpMock.getPlan());

        OMAModuleDTO omaModuleDTO = new OMAModuleDTO(omaModule);

        assertEquals(omaModule.id(), omaModuleDTO.getId());
        assertEquals(omaModule.name(), omaModuleDTO.getName());
    }

    public void assertEquals(ETP etp, ETP_DTO etpDTO) {
        assertEquals(etp.id(), etpDTO.getId());
        assertEquals(etp.title(), etpDTO.getTitle());
        assertEquals(etp.target(), etpDTO.getTarget());
        assertEquals(etp.distanceLearningBeginDate(), etpDTO.getDistanceLearningBeginDate());
        assertEquals(etp.distanceLearningEndDate(), etpDTO.getDistanceLearningEndDate());
        assertEquals(etp.fullTimeLearningBeginDate(), etpDTO.getFullTimeLearningBeginDate());
        assertEquals(etp.fullTimeLearningEndDate(), etpDTO.getFullTimeLearningEndDate());
        assertEquals(etp.eaModules().size(), etpDTO.getEaModules().size());
        assertEquals(etp.emaModules().size(), etpDTO.getEmaModules().size());
        assertEquals(etp.omaModules().size(), etpDTO.getOmaModules().size());
    }
}
