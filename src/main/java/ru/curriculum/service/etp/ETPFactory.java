package ru.curriculum.service.etp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.domain.etp.entity.educationActivity.EAModule;
import ru.curriculum.domain.etp.entity.educationActivity.EASection;
import ru.curriculum.domain.etp.entity.educationActivity.EATopic;
import ru.curriculum.domain.etp.entity.educationMethodicalActivity.EMAModule;
import ru.curriculum.domain.etp.entity.organizationMethodicalActivity.OMAModule;
import ru.curriculum.service.etp.dto.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class ETPFactory {
    @Autowired
    private PlanFactory planFactory;

    public ETP create(ETP_DTO etpDTO) {
        ETP etp = new ETP(
                etpDTO.getId(),
                etpDTO.getTitle(),
                etpDTO.getTarget(),
                etpDTO.getDistanceLearningBeginDate(),
                etpDTO.getDistanceLearningEndDate(),
                etpDTO.getFullTimeLearningBeginDate(),
                etpDTO.getFullTimeLearningEndDate(),
                createEAModules(etpDTO.getEaModules()),
                createEMAModules(etpDTO.getEmaModules()),
                createOMAModules(etpDTO.getOmaModules())
        );

        return etp;
    }

    private Set<EAModule> createEAModules(List<EAModuleDTO> dtos) {
        Set<EAModule> eaModules = new HashSet<>();
        dtos.forEach(dto -> {
            EAModule module = new EAModule(
                    dto.getId(), dto.getName(), createEASections(dto.getSections())
            );
            eaModules.add(module);
        });

        return eaModules;
    }

    private Set<EASection> createEASections(List<EASectionDTO> dtos) {
        Set<EASection> eaSections = new HashSet<>();
        dtos.forEach(dto -> {
            EASection section = new EASection(
                    dto.getId(), dto.getName(), createTopics(dto.getTopics())
            );
            eaSections.add(section);
        });

        return eaSections;
    }

    private Set<EATopic> createTopics(List<EATopicDTO> dtos) {
        Set<EATopic> topics = new HashSet<>();
        dtos.forEach(dto -> {
            EATopic topic = new EATopic(
                    dto.getId(), dto.getName(), planFactory.create(dto.getPlan())
            );
            topics.add(topic);
        });

        return topics;
    }

    private Set<OMAModule> createOMAModules(List<OMAModuleDTO> dtos) {
        Set<OMAModule> omaModules = new HashSet<>();
        dtos.forEach(dto -> {
            OMAModule module = new OMAModule(
                    dto.getId(), dto.getName(), planFactory.create(dto.getPlan())
            );
            omaModules.add(module);
        });

        return omaModules;
    }

    private Set<EMAModule> createEMAModules(List<EMAModuleDTO> dtos) {
        Set<EMAModule> emaSections = new HashSet<>();
        dtos.forEach(dto -> {
            EMAModule module = new EMAModule(
                    dto.getId(), dto.getName(), planFactory.create(dto.getPlan())
            );
            emaSections.add(module);
        });

        return emaSections;
    }
}
