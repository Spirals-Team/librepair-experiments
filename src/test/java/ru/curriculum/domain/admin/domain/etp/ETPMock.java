package ru.curriculum.domain.admin.domain.etp;

import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.domain.etp.entity.educationActivity.EAModule;
import ru.curriculum.domain.etp.entity.educationActivity.EASection;
import ru.curriculum.domain.etp.entity.Plan;
import ru.curriculum.domain.etp.entity.educationActivity.EATopic;
import ru.curriculum.domain.etp.entity.educationMethodicalActivity.EMAModule;
import ru.curriculum.domain.etp.entity.organizationMethodicalActivity.OMAModule;
import ru.curriculum.service.etp.dto.ETP_DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ETPMock {

    public ETP getETP() {
        ETP etp = new ETP(
                "Учебный план",
                "Научить",
                new Date(1),
                new Date(2),
                new Date(3),
                new Date(4),
                getEAModules(),
                getEMAModules(),
                getOMAModules()
        );

        return etp;
    }

    public Plan getPlan() {
        return new Plan(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 6.0, 7.0, null);

    }

    public Set<EAModule> getEAModules() {
        Set<EAModule> modules = new HashSet<>();
        EAModule eaModule = new EAModule("Модуль учебной деятельности", getEASections());
        modules.add(eaModule);

        return modules;
    }

    public Set<EMAModule> getEMAModules() {
        Set<EMAModule> modules = new HashSet<>();
        EMAModule emaModule = new EMAModule("Модуль учебно-методической деятельности деятельности", getPlan());
        modules.add(emaModule);

        return modules;
    }

    public Set<OMAModule> getOMAModules() {
        Set<OMAModule> modules = new HashSet<>();
        OMAModule omaModule = new OMAModule("Модуль орагнизационно-методической деятельности", getPlan());
        modules.add(omaModule);

        return modules;
    }

    public Set<EASection> getEASections() {
        HashSet<EASection> sections = new HashSet<>();
        EASection section = new EASection("Раздел учебной деятельности", getEATopics());
        sections.add(section);

        return sections;
    }

    public Set<EATopic> getEATopics() {
        Set<EATopic> topics = new HashSet<>();
        EATopic eaTopic = new EATopic("Тема раздела учебной деятельности", getPlan());
        topics.add(eaTopic);

        return topics;
    }

    public ETP_DTO getETP_DTO() {
        return new ETP_DTO(getETP());
    }
}
