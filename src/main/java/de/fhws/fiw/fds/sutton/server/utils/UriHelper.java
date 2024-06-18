package de.fhws.fiw.fds.sutton.server.utils;

import jakarta.ws.rs.core.Link;

import java.net.URI;

public class UriHelper {

    public static int getLastPathElementAsId(final Link link) {
        if (link != null) {
            return getLastPathElementAsId(link.getUri());
        } else {
            return 0;
        }
    }

    public static int getLastPathElementAsId(final URI uri) {
        final String path = uri.getPath();
        final int lastSlash = path.lastIndexOf("/");
        return Integer.parseInt(path.substring(lastSlash + 1));
    }

}
