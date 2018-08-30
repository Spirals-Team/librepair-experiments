package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.model.Metadata;
import ru.job4j.repository.car.parts.*;

@Service("metadataServiceImpl")
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    private CarModelRepository carModelDao;
    @Autowired
    private ManufactureRepository manufactureDao;
    @Autowired
    private CategoryRepository categoryDao;
    @Autowired
    private BodyRepository bodyDao;
    @Autowired
    private FuelRepository fuelDao;
    @Autowired
    private TransmissionRepository transmissionDao;

    @Override
    public Metadata getMetadata() {
        return new Metadata()
            .setCarmodels(carModelDao.findAll())
            .setManufactures(manufactureDao.findAll())
            .setCategories(categoryDao.findAll())
            .setBodies(bodyDao.findAll())
            .setFuels(fuelDao.findAll())
            .setTransmissions(transmissionDao.findAll());
    }

}
