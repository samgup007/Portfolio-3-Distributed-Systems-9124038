package de.fhws.fiw.fds.sutton.client.auth;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * The BasicAuthInterceptor class is responsible for creating an auth credential for the Basic scheme
 * to authorize outgoing requests from the client
 * */
public class BasicAuthInterceptor implements Interceptor {
	private final String credentials;

	public BasicAuthInterceptor(final String userName, final String password) {
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
			this.credentials = Credentials.basic(userName, password);
		} else {
			this.credentials = "";
		}
	}

	@Override
	public Response intercept(final Chain chain) throws IOException {
		if (StringUtils.isNotEmpty(this.credentials)) {
			final Request request = chain.request();
			final Request authenticatedRequest = request.newBuilder()
					.header("Authorization", credentials)
					.build();
			return chain.proceed(authenticatedRequest);
		} else {
			return chain.proceed(chain.request());
		}
	}
}
