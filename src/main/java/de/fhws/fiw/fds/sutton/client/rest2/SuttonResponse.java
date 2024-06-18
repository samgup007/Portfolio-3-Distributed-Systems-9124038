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
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Header;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * The SuttonResponse class describes the structure of HTTP responses received after executing {@link SuttonRequest}.
 * It contains all necessary information for Sutton clients to take further action, such as reading
 * the relation-type links in the response and creating additional requests based on them.
 * By utilizing the SuttonResponse, clients can easily implement the HATEOAS principle of REST architecture.
 */
public class SuttonResponse {
    public static final int OK = 200;

    public static final int CREATED = 201;

    public static final int NO_CONTENT = 204;

    public static final int NOT_MODIFIED = 304;

    public static final int BAD_REQUEST = 400;

    public static final int UNAUTHORIZED = 401;

    public static final int FORBIDDEN = 403;

    public static final int NOT_FOUND = 404;

    public static final int METHOD_NOT_ALLOWED = 405;

    public static final int NOT_ACCEPTABLE = 406;

    public static final int CONFLICT = 409;

    public static final int PRECONDITION_FAILED = 412;

    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final int INTERNAL_SERVER_ERROR = 500;

    public static final int SERVICE_UNAVAILABLE = 503;

    private static final String HEADER_TOTALNUMBEROFRESULTS = "X-totalnumberofresults";

    private static final String HEADER_NUMBEROFRESULTS = "X-numberofresults";

    private SuttonRequest requestForThisResponse;

    private int statusCode;

    private Map<String, Link> mapRelationTypeToLink;

    private Response response;

    private byte[] responseData;

    private Map<String, List<String>> responseHeaders;

    private final ObjectMapper objectMapper;

    /**
     * Constructs a SuttonResponse using the provided response and the SuttonRequest and processes it accordingly
     *
     * @param response    {@link Response} the response received after executing a SuttonRequest
     * @param lastRequest {@link SuttonRequest} an executed request by a Sutton client
     */
    protected SuttonResponse(final Response response, final SuttonRequest lastRequest) {
        this.mapRelationTypeToLink = new HashMap<>();
        this.requestForThisResponse = lastRequest;
        this.response = response;
        this.objectMapper = initializeObjectMapper();
        processResponse();
    }

    /**
     * @return the HTTP status code {@link Integer} after executing a {@link SuttonRequest}
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * @return the <strong>Location</strong> header in the response if exists, otherwise, it returns null
     */
    public final String getLocationUri() {
        return getHeaderString("Location");
    }

    /**
     * @param relationType {@link String} the relation-type link name to search for in the relation-type headers' list
     * @return true if relation-type headers' list contains the provided relation-type link
     */
    public final boolean containsLink(final String relationType) {
        return this.mapRelationTypeToLink.containsKey(relationType);
    }

    public final byte[] getResponseBytes() {
        return this.responseData;
    }

    /**
     * Reads the response body that contains a single model and returns it
     *
     * @param clazz {@link Class} the class of the model in the response body to use it for the deserialization
     *              process
     * @return a single {@link T}
     */
    public final <T extends AbstractClientModel> T readEntity(final Class<T> clazz) {
        try {
            return this.objectMapper.readValue(this.responseData, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads the response body and extracts the collection of models from it
     *
     * @param toType {@link Class} the generic type of the collection of the models in the response body
     * @return a collection of {@link T}
     */
    public final <T extends AbstractClientModel> List<T> readEntities(final Class<T> toType) {
        try {
            return this.objectMapper.readerForListOf(toType).readValue(this.responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return the response body as a {@link String}
     */
    public final String getResponseString() {
        return new String(this.responseData);
    }

    /**
     * @return the total number {@link Integer} of results found after executing an HTTP GET collection request
     */
    public final int getTotalNumberOfResults() {
        return getHeaderInt(HEADER_TOTALNUMBEROFRESULTS);
    }

    /**
     * @return the number {@link Integer} of received results in the response after executing an HTTP GET collection request
     */
    public final int getNumberOfResults() {
        return getHeaderInt(HEADER_NUMBEROFRESULTS);
    }

    /**
     * Returns a list of headers {@link Header}, which have the given name
     *
     * @param header {@link String} name of the header to search for in the list of headers in the response
     * @return a list of headers {@link Header} if they exist or an empty list instead
     */
    public final List<Header> getHeaders(String header) {
        List<String> responseHeaders = this.responseHeaders.get(header);

        List<Header> headers = new LinkedList<>();

        if (responseHeaders != null) {
            for (String value : responseHeaders) {
                headers.add(new Header(header, value));
            }
        }

        return headers;
    }

    /**
     * Returns all relation-type links {@link Link} from the response
     *
     * @return a list of all relation-type links {@link Link} from the response if they exist or an empty list instead
     */
    public List<Link> getLinks() {
        return new LinkedList<>(this.mapRelationTypeToLink.values());
    }

    /**
     * @param relationType {@link String} the relation-type name to search for relation-type link by
     * @return a relation-type link {@link Link}. whose relation-type matches the provided
     * relation-type
     */
    public final Link getLink(final String relationType) {
        return this.mapRelationTypeToLink.get(relationType);
    }

    /**
     * Creates a SuttonRequest using the provided relation-type to choose the proper relation-type header from
     * the response to create this request. This method enables the client to utilize the HATEOAS concept of
     * the REST-architecture by using the relation-type headers in the response.
     *
     * @param headerRelationType {@link String} the relation-type to search for a relation-type link in the response
     *                           headers by it
     * @return a {@link SuttonRequest}  ready for execution
     */
    public final SuttonRequest createRequestFromHeaderLink(final String headerRelationType) {
        return createRequest(getLink(headerRelationType));
    }

    /**
     * Creates a SuttonRequest using the provided relation-type to extract the relation-type link from a model in the
     * response body. This method enables the client to utilize the HATEOAS concept of
     * the REST-architecture by using the relation-type headers in the response.
     *
     * @param relationType {@link String} the relation-type to search for relation-type link within the response body
     * @return a {@link SuttonRequest} with its {@link SuttonRequest#uriTemplate} and {@link SuttonRequest#mediaType}
     * configured according to the relation-type link <strong>if found</strong>, otherwise the
     * {@link SuttonRequest#uriTemplate} and {@link SuttonRequest#mediaType} will not be set.
     */
    public final SuttonRequest createRequestFromBodyLink(final String relationType) {
        return createRequest(getLinkFromBody(relationType));
    }

    private Link getLinkFromBody(String relationType) {
        try {
            JsonNode jsonNode = objectMapper.readTree(this.responseData);
            if (jsonNode.has(relationType)) {
                JsonNode linkString = jsonNode.get(relationType);
                return new Link(linkString.get("href").textValue(),
                        linkString.get("type").textValue(),
                        linkString.get("rel").textValue());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a SuttonRequest using the provided relation-type Link {@link Link} to instantiate this request
     *
     * @param link {@link Link} the relation-type link to use for creating the SuttonRequest
     * @return a {@link SuttonRequest} with its {@link SuttonRequest#uriTemplate} and {@link SuttonRequest#mediaType}
     * configured according to the relation-type link <strong>if the Link is not null</strong>, otherwise the
     * {@link SuttonRequest#uriTemplate} and {@link SuttonRequest#mediaType} will not be set.
     */
    public final SuttonRequest createRequest(final Link link) {
        SuttonRequest nextRequest = requestForThisResponse;

        if (link != null) {
            nextRequest.setUriTemplate(link.getUrl());
            nextRequest.setMediaType(link.getMediaType());
        }

        return nextRequest;
    }

    /**
     * Processes the SuttonResponse by setting its status code, its headers, its relation-type links, and its
     * body according to the information of the response used to instantiate this SuttonResponse
     */
    protected final void processResponse() {
        try {
            readStatusCode();
            readAllHeaders();
            readAllLinks();
            readResponseBody();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the HTTP status code of SuttonResponse to the status code of the response, which was used to instantiate it
     */
    protected void readStatusCode() {
        this.statusCode = this.response.code();
    }

    /**
     * Extracts all headers from the response, used to instantiate the SuttonResponse, to a multimap
     */
    protected void readAllHeaders() {
        final Headers headers = this.response.headers();
        this.responseHeaders = headers.toMultimap();
    }

    /**
     * Extracts all relation-type headers from the response and formats them to a map of links {@link Link}
     */
    protected final void readAllLinks() {
        final List<String> linkHeaders = this.responseHeaders.get("Link");

        if (linkHeaders != null) {
            for (final String linkHeader : linkHeaders) {
                final Link link = Link.parseFromHttpHeader(linkHeader);
                this.mapRelationTypeToLink.put(link.getRelationType(), link);
            }
        }
    }

    /**
     * Reads the response body into a byte array, which will be set as the response body data for SuttonResponse
     */
    protected void readResponseBody() throws IOException {
        this.responseData = this.response.body().bytes();
    }

    private String getHeaderString(String headerName) {
        List<String> allValues = this.responseHeaders.get(headerName);
        String value = allValues.stream().findFirst().orElseGet(() -> null);
        return value;
    }

    private int getHeaderInt(String headerName) {
        String value = getHeaderString(headerName);
        return value != null ? Integer.parseInt(value) : -1;
    }

    private ObjectMapper initializeObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
