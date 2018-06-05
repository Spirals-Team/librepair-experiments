package pl.hycom.ip2018.searchengine.googledrivesearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

@Data
public class Result extends SimpleResult {
    public static final String SNIPPET_KEY = "snippet";
    public static final String MIME_TYPE_KEY = "mimeType";
    public static final String DESCRIPTION_KEY = "description";
    public static final String WEB_CONTENT_LINK_KEY = "webContentLink";
    public static final String ICON_LINK_KEY = "iconLink";
    public static final String CREATED_TIME_KEY = "createdTime";
    public static final String MODIFIED_TIME_KEY = "modifiedTime";
    public static final String SIZE_KEY = "size";

    public static final String PROVIDER = "googledrive";
}
