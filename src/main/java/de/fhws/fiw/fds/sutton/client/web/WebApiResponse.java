package de.fhws.fiw.fds.sutton.client.web;

import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.HttpHeaders;
import okhttp3.Headers;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * The WebApiResponse class describes the main structure of the response received after executing an HTTP request
 * to the Sutton backend. It provides information about the headers, the HTTP status code, and the body
 * of the received response
 * */
public class WebApiResponse<T extends AbstractClientModel> {
	private final static String HEADER_LOCATION = HttpHeaders.LOCATION;

	private final Collection<T> responseData;

	private final Headers responseHeaders;

	private final int lastStatusCode;

	/**
	 * Constructs a WebApiResponse with no headers, with an empty collection of resources, and it sets the HTTP status
	 * code according to the given status code
	 * @param lastStatusCode {@link Integer} the HTTP status code of the response
	 * */
	public WebApiResponse(final int lastStatusCode) {
		this(Collections.EMPTY_LIST, Headers.of(), lastStatusCode);
	}

	/**
	 * Constructs a WebApiResponse with an empty collection of resources to return it to the client. It also sets
	 * the HTTP status code and the headers in the response according to the given parameters
	 * @param lastStatusCode {@link Integer} the HTTP status code of the response
	 * @param headers {@link Headers}  the headers from the response
	 * */
	public WebApiResponse(final int lastStatusCode, final Headers headers) {
		this(Collections.EMPTY_LIST, headers, lastStatusCode);
	}

	/**
	 * Constructs a WebApiResponse and sets the response body, the headers as well as the HTTP status code to
	 * the given parameters. The response body in this case will be a collection with only a single resource
	 * @param lastStatusCode {@link Integer} the HTTP status code of the response
	 * @param headers {@link Headers}  the headers from the response
	 * @param responseData {@link T}   the single resource in the response body
	 * */
	public WebApiResponse(final T responseData, final Headers headers, final int lastStatusCode) {
		this(Optional.of(responseData), headers, lastStatusCode);
	}

	/**
	 * Constructs a WebApiResponse and sets the response body, the headers as well as the HTTP status code to
	 * the given parameters. The response body in this case will be a collection with only a single resource
	 * @param lastStatusCode {@link Integer} the HTTP status code of the response
	 * @param headers {@link Headers}  the headers from the response
	 * @param responseData {@link Optional}
	 * */
	public WebApiResponse(final Optional<T> responseData, final Headers headers,
			final int lastStatusCode) {
		this(convertToList(responseData), headers, lastStatusCode);
	}

	/**
	 * Constructs a WebApiResponse and sets the response body, the headers as well as the HTTP status code to
	 * the given parameters.
	 * @param lastStatusCode {@link Integer} the HTTP status code of the response
	 * @param headers {@link Headers}  the headers from the response
	 * @param responseData {@link Collection} the collection of the resources in the response body
	 * */
	public WebApiResponse(final Collection<T> responseData, final Headers headers,
			final int lastStatusCode) {
		this.responseData = responseData;
		this.responseHeaders = headers;
		this.lastStatusCode = lastStatusCode;
	}

	public Collection<T> getResponseData() {
		return responseData;
	}

	public Headers getResponseHeaders() {
		return responseHeaders;
	}

	public Optional<T> getFirstResponse() {
		return this.responseData.stream().findFirst();
	}

	public int getLastStatusCode() {
		return lastStatusCode;
	}

	public Optional<String> getLocationHeader() {
		return getResponseHeaders().values(HEADER_LOCATION).stream().findFirst();
	}

	private static <T> Collection<T> convertToList(final Optional<T> object) {
		return object.isPresent() ? Collections.singletonList(object.get())
				: Collections.emptyList();
	}
}
