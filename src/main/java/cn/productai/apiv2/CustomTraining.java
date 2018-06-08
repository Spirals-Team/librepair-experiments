package cn.productai.apiv2;

import cn.productai.api.core.IProfile;
import cn.productai.apiv2.exceptions.PAIException;

public interface CustomTraining {

    void setProfile(IProfile profile);

    String listAllService() throws PAIException;

    String getServiceById(String serviceId) throws PAIException;

    String updateServiceName(String serviceId, String name) throws PAIException;

    String deleteServiceById(String serviceId) throws PAIException;

    String predict(String serviceId, String imageUrl, String image) throws PAIException;

    String createService(String trainingSetId, String name, String description, String scenario) throws PAIException;
}
