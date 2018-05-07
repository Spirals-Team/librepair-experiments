package pw.spn.crawler.livelib.http;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

public class LivelibHttpService {
    private static final String BASE_URL = "https://www.livelib.ru/apiapp";

    private static final Logger logger = LoggerFactory.getLogger(LivelibHttpService.class);

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public LivelibHttpService() {
        this(new ObjectMapper());
    }

    public LivelibHttpService(ObjectMapper objectMapper) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new LiveLibRequestInterceptor());
        okHttpClientBuilder.connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2,
                TimeUnit.MINUTES);
        httpClient = okHttpClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public <T> Optional<T> doGet(String url, TypeReference<T> entityType) {
        try {
            String jsonResponse = httpClient.newCall(new Builder().url(BASE_URL + url).build()).execute().body()
                    .string();
            return Optional.ofNullable(mapToEntity(jsonResponse, entityType));
        } catch (IOException e) {
            logger.error("Error when making request to {}", BASE_URL + url);
        }
        return Optional.empty();
    }

    private <T> T mapToEntity(String jsonResponse, TypeReference<T> entityType) throws IOException {
        logger.debug("Response from LiveLib:\n {}", jsonResponse);
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ResponseWrapper.class,
                objectMapper.getTypeFactory().constructType(entityType));
        ResponseWrapper<T> wrapper = objectMapper.readValue(jsonResponse, type);
        return wrapper.getData();
    }
}
