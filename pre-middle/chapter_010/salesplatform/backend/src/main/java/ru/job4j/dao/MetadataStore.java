package ru.job4j.dao;

import ru.job4j.dao.car.parts.*;
import ru.job4j.model.Metadata;

public class MetadataStore {

    public Metadata getMetadata() {
        return new Metadata()
            .setCarmodels(new CarModelDaoImpl().findAll())
            .setManufactures(new ManufactureDaoImpl().findAll())
            .setCategories(new CategoryDaoImpl().findAll())
            .setBodies(new BodyDaoImpl().findAll())
            .setFuels(new FuelDaoImpl().findAll())
            .setTransmissions(new TransmissionDaoImpl().findAll());
    }
}
