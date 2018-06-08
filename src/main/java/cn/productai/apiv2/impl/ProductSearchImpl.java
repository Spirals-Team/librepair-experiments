package cn.productai.apiv2.impl;

import cn.productai.api.core.IProfile;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.apiv2.ProductSearch;
import cn.productai.apiv2.exceptions.PAIException;
import cn.productai.apiv2.lib.Http;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ProductSearchImpl extends AbstractService implements ProductSearch {

    private static final Logger logger = LogManager.getLogger(ProductSearchImpl.class);
    private static final String URL = BASE_URL + "/product_sets/_0000178/services";

    @Override
    public String query(String serviceId, String imageUrl, String loc, Integer count, String tags,
                        String keywords, String minPrice, String maxPrice, Map<String, String> params) {
        if (loc == null) {
            loc = "0-0-1-1";
        }
        if (count == null) {
            count = 20;
        }
        try {
            String json = "{";
            if (tags != null) {
                json += "\"tags\":\"" + tags + "\",";
            }
            if (minPrice != null) {
                json += "\"min_price\":\"" + minPrice + "\",";
            }
            if (maxPrice != null) {
                json += "\"max_price\":\"" + maxPrice + "\",";
            }
            if (keywords != null) {
                json += "\"keywords\":\"" + keywords + "\",";
            }

            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    json += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
                }
            }
            json += "\"loc\":\"" + loc + "\","
                    + "\"count\":\"" + count + "\","
                    + "\"url\":\"" + imageUrl + "\""
                    + "}";

            String url = BASE_URL + "/product_search/" + serviceId;
            return Http.request(HttpMethod.POST, url, getHeaders(), json);
        } catch (PAIException paie) {
            logger.error("ProductSearch createService request error", paie);
            return null;
        }
    }

    @Override
    public String createService(String name, String scenario, String productSetId) {
        try {
            String json = "{"
                    + "\"name\":\"" + name + "\","
                    + "\"scenario\":\"" + scenario + "\","
                    + "\"product_set_id\":\"" + productSetId + "\""
                    + "}";

            return Http.request(HttpMethod.POST, URL, getHeaders(), json);
        } catch (PAIException paie) {
            logger.error("ProductSearch createService request error", paie);
            return null;
        }
    }

    @Override
    public String listAllService() {
        try {
            return Http.request(HttpMethod.GET, URL, getHeaders());
        } catch (PAIException paie) {
            logger.error("ProductSearch listAllService request error", paie);
            return null;
        }
    }

    @Override
    public String getServiceById(String productSetId) {
        try {
            String url = URL + "/" + productSetId;
            return Http.request(HttpMethod.GET, url, getHeaders());
        } catch (PAIException paie) {
            logger.error("ProductSearch getServiceById request error", paie);
            return null;
        }
    }

    @Override
    public String updateServiceName(String productSetId, String name) {
        try {
            String url = URL + "/" + productSetId;
            String json = "{\"name\":\"" + name + "\"}";
            return Http.request(HttpMethod.PATCH, url, getHeaders(), json);
        } catch (PAIException paie) {
            logger.error("ProductSearch updateServiceName request error", paie);
            return null;
        }
    }

    @Override
    public String deleteServiceById(String productSetId) {
        try {
            String url = URL + "/" + productSetId;
            return Http.request(HttpMethod.DELETE, url, getHeaders());
        } catch (PAIException paie) {
            logger.error("ProductSearch deleteServiceById request error", paie);
            return null;
        }
    }

    @Override
    public void setProfile(IProfile profile) {
        this.profile = profile;
    }
}
