package de.fhws.fiw.fds.sutton.client.auth;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class ApiKeyInterceptor implements Interceptor {

    private final String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        if (StringUtils.isEmpty(apiKey)) {
            this.apiKey = "API_KEY_01";
        } else {
            this.apiKey = apiKey;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("Api-Key", this.apiKey).build();
        return chain.proceed(request);
    }
}
