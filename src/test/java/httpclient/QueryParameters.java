package httpclient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;

class QueryParameters extends ValueType implements Iterable<QueryParameter> {

    private final Map<String, String> queryParameters;

    QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    static QueryParameters empty() {
        return new QueryParameters(new HashMap<>());
    }
    @Override
    public Iterator<QueryParameter> iterator() {
        return queryParameters.entrySet().stream()
                .map(entry -> new QueryParameter(entry.getKey(), entry.getValue()))
                .iterator();
    }

    // TODO How to get list of query params
//    public static QueryParameters fromParams(List<NameValuePair> queryParameters){
//        Multimap<String, String> accumulator = ArrayListMultimap.create();
//        for (NameValuePair queryParameter : queryParameters){
//            accumulator.put(queryParameter.getName(), queryParameter.getValue());
//        }
//        return new QueryParameters(accumulator);
//    }

    @Override
    public String toString() {
        if (queryParameters.isEmpty()) {
            return "";
        }
        Stream<QueryParameter> stream = StreamSupport.stream(spliterator(), false);
        return stream.map(QueryParameter::toString).collect(joining("&", "?", ""));
    }
}
