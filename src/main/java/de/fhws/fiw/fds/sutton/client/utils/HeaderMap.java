package de.fhws.fiw.fds.sutton.client.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The HeaderMap class is a utility class. It utilizes the usage of headers both in HTTP-requests and responses.
 * */
public class HeaderMap {
	private final Map<String, String> headerMap;

	/**
	 * Constructs a default HeaderMap
	 * */
	public HeaderMap() {
		this.headerMap = new HashMap<>();
	}

	/**
	 * Adds a header to the collection of headers that the HeaderMap contains
	 * @param headerName name {@link String} of the header to be added
	 * @param headerValue value {@link String} of the header to be added
	 * */
	public void addHeader(final String headerName, final String headerValue) {
		this.headerMap.put(headerName, headerValue);
	}

	/**
	 * retrieve a header from the collection of the headers that has the given name
	 * @param headerName name of the header to be retrieved
	 * @return the name of the header {@link String} that has the given name <strong>if it exists</strong> or an empty
	 * string otherwise
	 * */
	public String getHeader(final String headerName) {
		return this.headerMap.getOrDefault(headerName, "");
	}

	/**
	 * @return a map containing all the headers with the names of the headers as keys and the values are the headers'
	 * value
	 * */
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
}
