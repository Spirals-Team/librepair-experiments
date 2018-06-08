package cn.productai.api.core;

import cn.productai.api.core.enums.LanguageType;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public interface IProfile {

    void setAccessKeyId(String accessKeyId);

    String getAccessKeyId();

    void setSecretKey(String secretKey);

    String getSecretKey();

    void setVersion(String version);

    String getVersion();

    void setGlobalLanguage(LanguageType language);

    LanguageType getGlobalLanguage();
}
