package de.fhws.fiw.fds.sutton.client.utils;

/**
 * The Header class is a utility class. It utilizes the usage of headers both in HTTP-requests and responses.
 * @author (c) Tobias Fertig, FHWS 2016
 * */
public class Header {
	private String key;

	private String value;

	/**
	 * Constructs a default request header
	 * */
	public Header() {}

	/**
	 * Constructs a header setting its key and value according to the given values
	 * @param value the value {@link String} of the header
	 * @param key the key (name) {@link String} of the header
	 * */
	public Header(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key (name) of the header {@link String}
	 * */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key (name) of the header {@link Header#key} to the given key
	 * */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value of the header {@link String}
	 * */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the header {@link Header#value} to the given value
	 * */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return key + ": " + value;
	}
}
