package ru.curriculum.service.etp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.domain.etp.repository.ETPRepository;
import ru.curriculum.service.etp.dto.ETP_DTO;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

@Component
public class ETP_CRUDService {
    @Autowired
    private ETPRepository etpRepository;
    @Autowired
    private ETPFactory etpFactory;

    public List<ETP_DTO> getAll() {
        List<ETP_DTO> dtos = new ArrayList<>();
        etpRepository.findAll().forEach(etp -> dtos.add(new ETP_DTO(etp)));

        return dtos;
    }

    public ETP_DTO get(Integer etpId) {
        ETP etp = etpRepository.findOne(etpId);
        if(null == etp) {
            throw new EntityNotFoundException(format("УТП в иненитфикатором %s не найде в системе", etpId));
        }

        return new ETP_DTO(etp);
    }

    public void create(ETP_DTO etpDTO) {
        ETP etp = etpFactory.create(etpDTO);
        etpRepository.save(etp);
    }

    public void update(ETP_DTO etpDTO) {
        ETP etpNeedToUpdate = etpRepository.findOne(etpDTO.getId());
        if(null == etpNeedToUpdate) {
            throw new EntityNotFoundException(format("УТП в иненитфикатором %s не найде в системе", etpDTO.getId()));
        }
        create(etpDTO);
    }

    public void delete(Integer etpId) {
        etpRepository.delete(etpId);
    }
}

