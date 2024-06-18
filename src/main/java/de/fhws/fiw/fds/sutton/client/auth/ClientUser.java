package de.fhws.fiw.fds.sutton.client.auth;

/**
 * The ClientUser enum is used to define users to be used in the context of the Basic authorization scheme
 * to authorize the requests made by a client
 * */
public enum ClientUser {
	ANONYMOUS("", "");

	private final String userName;

	private final String password;

	ClientUser(final String userName, final String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}