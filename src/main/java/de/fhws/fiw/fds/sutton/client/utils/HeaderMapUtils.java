package de.fhws.fiw.fds.sutton.client.utils;

/**
* The HeaderMapUtils class is a utility class and provides a group of predefined headers to be used
 * in HTTP-requests made by a client
* */
public class HeaderMapUtils {
	/**
	 * @return an empty {@link HeaderMap}
	 * */
	public static HeaderMap empty() {
		return new HeaderMap();
	}

	/**
	 * @return a {@link HeaderMap} defining that the client accepts content in the JSON format
	 * */
	public static HeaderMap withAcceptJson() {
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Accept", "application/json");

		return headers;
	}

	/**
	 * @return a {@link HeaderMap} defining that the client accepts content in the XML format
	 * */
	public static HeaderMap withAcceptXml() {
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Accept", "application/xml");

		return headers;
	}

	/**
	 * @return a {@link HeaderMap} defining that the content of the data in the request is of type JSON
	 * */
	public static HeaderMap withContentTypeJson() {
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Content-Type", "application/json");

		return headers;
	}

	/**
	 * @return a {@link HeaderMap} defining that the content of the data in the request is of type XML
	 * */
	public static HeaderMap withContentTypeXml() {
		final HeaderMap headers = new HeaderMap();
		headers.addHeader("Content-Type", "application/xml");

		return headers;
	}
}
