package com.utn.tp5.service;

import com.utn.tp5.persistence.CabinPersistence;
import com.utn.tp5.model.Cabin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CabinService {

    @Autowired
    private CabinPersistence cabinPersistence;

    public List<Cabin> getAll() {
        return cabinPersistence.findAll();
    }

    public Cabin getById(Long id) {
        return cabinPersistence.getOne(id);
    }

    public boolean saveCabin(Cabin c) {
        Cabin saved = cabinPersistence.save(c);
        boolean rtn = false;
        if (saved.equals(c)) {
            rtn = true;
        }
        return rtn;
    }

    public boolean deleteCabin(String name) {
        return cabinPersistence.deleteByName(name);
    }
}
