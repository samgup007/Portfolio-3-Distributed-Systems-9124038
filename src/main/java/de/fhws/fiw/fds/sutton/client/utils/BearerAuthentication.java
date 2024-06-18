package de.fhws.fiw.fds.sutton.client.utils;

/**
 * The BearerAuthentication class is a utility class. It is used to create immutable Bearer Token to be used
 * to authorize clients requests
 * */
public class BearerAuthentication implements Authentication {
	private final String token;

	/**
	 * Constructs a Bearer authorization assigning its token to the given token
	 * @param token the token {@link String} to be used to authorize client's requests in the context of Bearer
	 *              authorization
	 * */
	public BearerAuthentication(String token) {
		this.token = token;
	}

	/**
	 * @return a Bearer authorization header to be used to authorize this request
	 * */
	@Override
	public String authenticationHeader() {
		return "Bearer " + token;
	}
}
