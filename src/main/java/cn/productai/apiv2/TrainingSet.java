package cn.productai.apiv2;

import cn.productai.api.core.IProfile;
import cn.productai.apiv2.exceptions.PAIException;

import java.io.File;

public interface TrainingSet {

    void setProfile(IProfile profile);

    String create(String name, String description) throws PAIException;

    String listAll() throws PAIException;

    String getById(String trainingSetId) throws PAIException;

    String update(String trainingSetId, String name, String description) throws PAIException;

    String delete(String trainingSetId) throws PAIException;

    String bulkAddTrainingData(String trainingSetId, File file) throws PAIException;

    String bulkDeleteTrainingData(String trainingSetId, File file) throws PAIException;

    String clearTrainingSet(String trainingSetId, String name) throws PAIException;
}
