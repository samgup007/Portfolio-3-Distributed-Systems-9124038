/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.sutton.client.web;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import de.fhws.fiw.fds.sutton.client.auth.ApiKeyInterceptor;
import de.fhws.fiw.fds.sutton.client.auth.BasicAuthInterceptor;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.HttpHeaders;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This class defines a very simple generic Web client that always uses content type JSON and does
 * not allow for any additional HTTP headers in requests or responses other than Accept,
 * Content-Type and Authorization, which are all set automatically and cannot be changed by the
 * user)
 *
 * @param <T>
 */
public class GenericWebClient<T extends AbstractClientModel> {

    private static final String APPLICATION_JSON = "application/json";
    private static final String HEADER_ACCEPT = HttpHeaders.ACCEPT;

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    /**
     * Constructs a {@link GenericWebClient} with no credentials to be used for the Basic auth scheme for requests
     * made by this client
     */
    public GenericWebClient() {
        this("", "");
    }

    /**
     * Constructs a {@link GenericWebClient} with the given username and password to use them in the context of
     * the Basic auth scheme to authorize requests made by this client
     *
     * @param userName {@link String} the username to be used in the context of the basic auth scheme
     * @param password {@link String} the password to be used in the context of the basic auth scheme
     */
    public GenericWebClient(final String userName, final String password) {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(userName, password))
                .addInterceptor(new ApiKeyInterceptor("API_KEY_01"))
                .build();

        this.objectMapper = initializeObjectMapper();
    }

    private ObjectMapper initializeObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    /**
     * Executes a GET request to the given endpoint to request a single resource
     *
     * @param url {@link String} the endpoint to call with a GET request
     * @return a {@link WebApiResponse} with its responseData set to an empty {@link Optional}.
     * The header and the code of the returned WebApiResponse are set respectively to the response headers and
     * HTTP status code. In case the HTTP status code of the response is not 200, the method will return a
     * WebApiResponse with status code of the response
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendGetSingleRequest(final String url) throws IOException {
        final Response response = executeGetRequest(url);

        final int statusCodeOfLastRequest = response.code();

        if (statusCodeOfLastRequest == 200) {
            return new WebApiResponse<>(Optional.empty(), response.headers(), response.code());
        } else {
            return new WebApiResponse<>(statusCodeOfLastRequest);
        }
    }

    private Response executeGetRequest(final String url) throws IOException {
        final Request request = new Request.Builder().url(url)
                .header(HEADER_ACCEPT, APPLICATION_JSON).get().build();

        return this.client.newCall(request).execute();
    }

    /**
     * Executes a GET request to the given endpoint to request a single resource
     *
     * @param url   {@link String} the endpoint to call with a GET request
     * @param clazz {@link Class} to be used to deserialize the object contained in the response body
     * @return a {@link WebApiResponse} with its responseData set to the requested resource.
     * The header and the code of the returned WebApiResponse are set respectively to the response headers and
     * HTTP status code. In case the HTTP status code of the response is not 200, the method will return a
     * WebApiResponse with status code of the response
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendGetSingleRequest(final String url, final Class<T> clazz)
            throws IOException {
        final Response response = executeGetRequest(url);

        final int statusCodeOfLastRequest = response.code();

        if (statusCodeOfLastRequest == 200) {
            return new WebApiResponse<>(deserialize(response, clazz), response.headers(),
                    response.code());
        } else {
            return new WebApiResponse<>(statusCodeOfLastRequest);
        }
    }

    /**
     * Executes a GET request to the given endpoint to request multiple resources.
     *
     * @param url   {@link String} the endpoint to call with a GET request
     * @param clazz {@link Class<T>} to be used to deserialize a collection of objects sent in the response data
     * @return a {@link WebApiResponse} with its responseData set to the requested resources.
     * The header and the code of the returned WebApiResponse are set respectively to the response headers and
     * HTTP status code. In case the HTTP status code of the response is not 200, the method will return a
     * WebApiResponse with status code of the response
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendGetCollectionRequest(final String url,
                                                      final Class<T> clazz) throws IOException {
        final Request request = new Request.Builder().url(url).get().build();

        final Response response = this.client.newCall(request).execute();

        final int statusCodeOfLastRequest = response.code();

        if (statusCodeOfLastRequest == 200) {
            return new WebApiResponse<>(deserializeToCollection(response, clazz),
                    response.headers(), response.code());
        } else {
            return new WebApiResponse<>(statusCodeOfLastRequest);
        }
    }

    /**
     * Executes a POST request to the given endpoint with the given object in the request body to be created
     *
     * @param url    {@link String} the endpoint to call with a POST request
     * @param object {@link AbstractClientModel} the object to create
     * @return a {@link WebApiResponse} with the HTTP status code and the headers from the response received from the server
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendPostRequest(final String url, final T object) throws IOException {
        final RequestBody body =
                RequestBody.create(MediaType.parse(APPLICATION_JSON), serialize(object));

        final Request request = new Request.Builder().url(url).post(body).build();

        final Response response = this.client.newCall(request).execute();

        final int statusCodeOfLastRequest = response.code();

        return new WebApiResponse<>(statusCodeOfLastRequest, response.headers());
    }

    /**
     * Executes a PUT request to the given endpoint with the given object in the request body to be updated
     *
     * @param url    {@link String} the endpoint to call with a PUT request
     * @param object {@link AbstractClientModel} the object to update an existing resource with it
     * @return a {@link WebApiResponse} with the HTTP status code and the headers from the response received from the server
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendPutRequest(final String url, final T object) throws IOException {
        final RequestBody body =
                RequestBody.create(MediaType.parse(APPLICATION_JSON), serialize(object));

        final Request request = new Request.Builder().url(url).put(body).build();

        final Response response = this.client.newCall(request).execute();

        return new WebApiResponse<>(response.code(), response.headers());
    }

    /**
     * Executes a DELETE request to the given endpoint to delete a resource
     *
     * @param url {@link String} the endpoint to call with a DELETE request
     * @return a {@link WebApiResponse} with the HTTP status code and the headers from the response received from the server
     * @throws IOException if the request could not be executed due to cancellation, a connectivity problem or timeout.
     *                     Because networks can fail during an exchange, it is possible that the remote server accepted the request before the failure.
     */
    public WebApiResponse<T> sendDeleteRequest(final String url) throws IOException {
        final Request request = new Request.Builder().url(url).delete().build();

        final Response response = this.client.newCall(request).execute();

        return new WebApiResponse<>(response.code(), response.headers());
    }

    private String serialize(final T object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
        }
    }

    private List<T> deserializeToCollection(final Response response,
                                            final Class<T> clazz) throws IOException {
        final String data = response.body().string();

        return objectMapper.readerForListOf(clazz).readValue(data);
    }

    private Optional<T> deserialize(final Response response, final Class<T> clazz)
            throws IOException {
        final String data = response.body().string();

        return Optional.ofNullable(objectMapper.readValue(data, clazz));
    }
}
