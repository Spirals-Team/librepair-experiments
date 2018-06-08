package cn.productai.apiv2;

import cn.productai.api.core.IProfile;

import java.util.Map;

public interface ProductSearch {

    String query(String serviceId, String imageUrl, String loc, Integer count, String tags,
                 String keywords, String minPrice, String maxPrice, Map<String, String> params);

    String createService(String name, String scenario, String productSetId);

    String listAllService();

    String getServiceById(String productSetId);

    String updateServiceName(String productSetId, String name);

    String deleteServiceById(String productSetId);

    void setProfile(IProfile profile);
}
