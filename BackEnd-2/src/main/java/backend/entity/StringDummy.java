package backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yotam on 24/12/2017.
 */
@Document
public class StringDummy {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
