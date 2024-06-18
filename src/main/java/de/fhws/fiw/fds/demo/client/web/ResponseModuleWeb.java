package de.fhws.fiw.fds.demo.client.web;

import java.util.Collection;
import java.util.Optional;

import de.fhws.fiw.fds.demo.client.models.ClientModule;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.HttpHeaders;
import okhttp3.Headers;

public class ResponseModuleWeb extends WebApiResponse<ClientModule> {

    private final static String HEADER_LOCATION = HttpHeaders.LOCATION;

    public ResponseModuleWeb(final Collection<ClientModule> responseData,
    final Headers headers, final int lastStatusCode) {
        super(responseData, headers, lastStatusCode);
    }

    public Optional<String> getLocationHeader() {
        return getResponseHeaders().values(HEADER_LOCATION).stream().findFirst();
    }

}
