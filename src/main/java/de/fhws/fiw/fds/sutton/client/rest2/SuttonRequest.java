/*
 * Copyright (c) peter.braun@fhws.de
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

package de.fhws.fiw.fds.sutton.client.rest2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.ApiKey;
import de.fhws.fiw.fds.sutton.client.utils.Authentication;
import de.fhws.fiw.fds.sutton.client.utils.Header;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The SuttonRequest class describes the structure of HTTP requests to be executed by a sutton client.
 * When a SuttonRequest is executed by calling the {@link SuttonRequest#execute()} method it returns a
 * {@link SuttonResponse} containing all relevant information to the client.
 *
 * @see SuttonResponse
 */
public class SuttonRequest {
    /**
     * The endpoint {@link String} to send the request to
     */
    protected String uriTemplate;

    /**
     * The media type {@link String} of data to be sent in the request body
     */
    protected String mediaType;

    /**
     * The HTTP verb {@link HttpVerb} to be executed by the request
     */
    protected HttpVerb httpVerb;

    /**
     * The model {@link AbstractClientModel} to be sent in the request body
     */
    protected AbstractClientModel requestModel;

    /**
     * The image to be sent to the server in the request body
     */
    protected byte[] requestImage;

    /**
     * The authentication scheme {@link Authentication} to be used to authenticate the request
     */
    protected Authentication authentication;

    /**
     * The api key {@link ApiKey} to use it in the request
     */
    protected ApiKey apiKey;

    /**
     * The JSON Web Token {@link String} to be used in the request according to the (RFC 7519) standard for creating data with
     * optional signature and/or optional encryption whose payload holds JSON that asserts some number of claims.
     * The tokens are signed either using a private secret or a public/private key.
     */
    protected String jwtTokenName;

    /**
     * The headers {@link Header} in the request to be executed by the client
     */
    protected List<Header> headers = new LinkedList<>();

    /**
     * Constructs a SuttonRequest and sets its mediaType to "application/json"
     */
    public SuttonRequest() {
        this.mediaType = "application/json";
    }

    /**
     * Sets the {@link SuttonRequest#requestModel} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setRequestModel(final AbstractClientModel requestModel) {
        this.requestModel = requestModel;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#uriTemplate} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setUriTemplate(final String uriTemplate) {
        this.uriTemplate = uriTemplate;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#mediaType} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setMediaType(final String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#httpVerb} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setHttpVerb(final HttpVerb httpVerb) {
        this.httpVerb = httpVerb;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#requestImage} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setRequestImage(final byte[] requestImage) {
        this.requestImage = requestImage;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#authentication} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setAuthentication(final Authentication authentication) {
        this.authentication = authentication;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#apiKey} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setApiKey(final ApiKey apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Sets the {@link SuttonRequest#jwtTokenName} to the given value and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest setJwtTokenName(String jwtTokenName) {
        this.jwtTokenName = jwtTokenName;
        return this;
    }

    /**
     * Creates a new {@link Header} from the given header name and value and adds it to the headers list
     * {@link SuttonRequest#headers} of the SuttonRequest. The method returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest addHeader(String header, String value) {
        this.headers.add(new Header(header, value));
        return this;
    }

    /**
     * Adds the given header to the headers list
     * {@link SuttonRequest#headers} of the SuttonRequest and returns the current instance of
     * SuttonRequest to enable chaining other methods to it.
     */
    public final SuttonRequest addHeader(Header header) {
        this.headers.add(header);
        return this;
    }

    /**
     * Replaces the given template in the {@link SuttonRequest#uriTemplate} with the given value
     * after encoding it using the "UTF-8" encoding scheme
     *
     * @param value    {@link Long} the value to replace {@link SuttonRequest#uriTemplate} by after
     *                 encoding it
     * @param template {@link String} the template to be replaced within the {@link SuttonRequest#uriTemplate}
     * @return the current instance of SuttonRequest to enable chaining other methods to it.
     */
    public SuttonRequest replace(final String template, final long value) {
        replace(template, Long.toString(value));
        return this;
    }

    /**
     * Replaces the given template in the {@link SuttonRequest#uriTemplate} with the given value
     * after encoding it using the "UTF-8" encoding scheme
     *
     * @param value    {@link String} the value to replace {@link SuttonRequest#uriTemplate} by after
     *                 encoding it
     * @param template {@link String} the template to be replaced within the {@link SuttonRequest#uriTemplate}
     * @return the current instance of SuttonRequest to enable chaining other methods to it.
     */
    public SuttonRequest replace(final String template, String value) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.uriTemplate = this.uriTemplate.replace(template, value);
        return this;
    }

    /**
     * Executes the SuttonRequest
     *
     * @return a {@link SuttonResponse} or null if the request could not be executed due to cancellation,
     * a connectivity problem or timeout. Because networks can fail during an exchange,
     * it is possible that the remote server accepted the request before the failure.
     */
    public SuttonResponse execute() {
        this.setAllUnsetQueryParameterToEmptyString();
        final MediaType mediaType = MediaType.parse(this.mediaType);
        final OkHttpClient client = new OkHttpClient();
        final String serializedRequest = serializeRequestModel();
        final RequestBody body =
                serializedRequest != null ? RequestBody.create(mediaType, serializedRequest) : null;

        Request request =
                new Request.Builder().url(this.uriTemplate).method(httpVerb.name(), body).build();

        try {
            Response response = client.newCall(request).execute();
            return new SuttonResponse(response, this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final String serializeRequestModel() {
        try {
            if (this.requestModel != null) {
                if (this.mediaType.toLowerCase() == "application/json") {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(this.requestModel);
                }
                if (this.mediaType.toLowerCase() == "application/xml") {
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.writeValueAsString(this.requestModel);
                }

                throw new IllegalArgumentException("Unsupported mediaType: " + this.mediaType);
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final void setAllUnsetQueryParameterToEmptyString() {

        String[] splitedUri =
                this.uriTemplate == null ? new String[0] : this.uriTemplate.split("\\?");

        if (splitedUri.length == 2) {
            String query = this.uriTemplate.split("\\?")[1];
            List<AbstractMap.SimpleEntry<String, String>> list =
                    Pattern.compile("&").splitAsStream(query)
                            .map(s -> Arrays.copyOf(s.split("="), 2))
                            .map(o -> new AbstractMap.SimpleEntry<String, String>(o[0],
                                    replacePlaceholdersWithEmptyString(o[1])))
                            .collect(Collectors.toList());

            this.uriTemplate = rebuildUriTemplate(list);
        }
    }

    private String rebuildUriTemplate(List<AbstractMap.SimpleEntry<String, String>> list) {
        String reBuildUriTemplate = this.uriTemplate.split("\\?")[0];
        ListIterator<AbstractMap.SimpleEntry<String, String>> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (!iterator.hasPrevious()) {
                reBuildUriTemplate += "?";
            }

            AbstractMap.SimpleEntry<String, String> queryParameterEntry = iterator.next();

            reBuildUriTemplate +=
                    queryParameterEntry.getKey() + "=" + queryParameterEntry.getValue();

            if (iterator.hasNext()) {
                reBuildUriTemplate += "&";
            }
        }
        return reBuildUriTemplate;
    }

    private String replacePlaceholdersWithEmptyString(final String decodedValue) {
        if (decodedValue == null || (decodedValue.startsWith("{") && decodedValue.endsWith("}")
                && decodedValue.length() > 2)) {
            return "";
        } else {
            return decodedValue;
        }
    }

    /**
     * The HttpVerb enum represents the supported HTTP verbs by {@link SuttonRequest}
     */
    public enum HttpVerb {
        GET, POST, PUT, DELETE
    }
}
