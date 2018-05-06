package pl.hycom.ip2018.searchengine.wiki.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;
import pl.hycom.ip2018.searchengine.wiki.model.Result;
import pl.hycom.ip2018.searchengine.wiki.model.WikiSearchResponse;
import pl.hycom.ip2018.searchengine.wiki.wikimodel.WikiItem;
import pl.hycom.ip2018.searchengine.wiki.wikimodel.WikiResponse;
import pl.hycom.ip2018.searchengine.wiki.wikimodel.WikiResponseData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A converter converts a source object of type {@link WikiResponse } to a target of type {@link WikiSearchResponse}
 */
public class WikiResponseConverter implements Converter<WikiResponse, WikiSearchResponse> {

    @Autowired
    private Environment environment;

    /**
     * Convert the source object of type {@link WikiResponse} to target type {@link WikiSearchResponse}
     * @param wikiResponse the source object to convert
     * @return the converted object
     */
    @Override
    public WikiSearchResponse convert(WikiResponse wikiResponse) {
        WikiSearchResponse result = new WikiSearchResponse();
        List<SimpleResult> simpleItems = new ArrayList<>();

        Optional.ofNullable(wikiResponse)
            .map(WikiResponse::getQuery)
            .ifPresent(wikiResponseData -> {

                Optional.of(wikiResponseData)
                        .map(WikiResponseData::getSearch)
                        .ifPresent(wikiItems -> wikiItems.forEach(wikiItem -> {
                            simpleItems.add(getBuild(wikiItem));
                        }));

                if (Boolean.valueOf(environment.getProperty("rest.api.srInterWiki"))) {
                    Optional.of(wikiResponseData)
                            .map(WikiResponseData::getInterWikiSearch)
                            .ifPresent(wikiItems ->
                                    wikiItems.forEach((s, mapValue) ->
                                            mapValue.forEach(wikiItem ->
                                                    simpleItems.add(getBuild(wikiItem)))));
                }
            }
        );
        result.setResults(simpleItems);
        return result;
    }

    /**
     * Builder for item we convert to
     * @param wikiItem mapped object from actual whole (not null) response
     * @return SimpleResult provider contract object with recent data
     */
    private SimpleResult getBuild(WikiItem wikiItem) {
        return Result.builder()
            .provider(Result.PROVIDER)
            .header(wikiItem.getHeader())
            .snippet(wikiItem.getSnippet())
            .timestamp(wikiItem.getTimestamp())
            .url(environment.getProperty("rest.api.defaultUrl") + wikiItem.getHeader())
            .build();
    }
}