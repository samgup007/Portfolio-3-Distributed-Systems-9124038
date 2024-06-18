/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SuttonResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;

import java.net.URI;

/**
 * The Hyperlinks class provides the required functionality to create hyperlinks according to the specifications of
 * the REST architecture
 */
public class Hyperlinks {

    /**
     * Creates hyperlinks as specified in the specifications of the REST architecture and adds them to the response as
     * headers
     *
     * @param uriInfo         the {@link SuttonUriInfo} to derive the required path information from
     * @param suttonResponse the {@link SuttonResponse} to add the created hyperlink to as a header
     * @param mediaType       {@link String} the media type in which the data will be sent when this link is requested
     * @param relationType    {@link String} describes what the hyperlink stands for
     * @param path            {@link String} the relative path to use it to build the href for the hyperlink
     * @param params          an ellipsis of {@link Object} to be built to the href part of the hyperlink
     */
    public static <R, T> void addLink(final SuttonUriInfo uriInfo,
                                      final SuttonResponse<R, T> suttonResponse,
                                      final String path,
                                      final String relationType,
                                      final String mediaType,
                                      final Object... params) {
        String uriTemplate = uriInfo.getUriTemplate(path);

        for (final Object p : params) {
            uriTemplate = replaceFirstTemplate(uriTemplate, p);
        }

        suttonResponse.header("Link", linkHeader(uriTemplate, relationType, mediaType));
    }

    public static String replaceFirstTemplate(final String uri, final Object value) {
        return uri.replaceFirst("\\{id\\}", value.toString());
    }

    public static String linkHeader(final String uri, final String rel, final String mediaType) {
        final StringBuilder sb = new StringBuilder();
        sb.append('<').append(uri).append(">;");
        sb.append("rel").append("=\"").append(rel).append("\"");
        if (mediaType != null && !mediaType.isEmpty()) {
            sb.append(";");
            sb.append("type").append("=\"").append(mediaType).append("\"");
        }

        return sb.toString();
    }

    /**
     * Creates hyperlinks as specified in the specifications of the REST architecture and adds them to the response as
     * headers
     *
     * @param uri             the {@link URI} to derive the required path information from
     * @param responseBuilder the {@link SuttonResponse} to add the created hyperlink to as a header
     * @param mediaType       {@link String} the media type in which the data will be sent when this link is requested
     * @param relType         {@link String} describes what the hyperlink stands for
     */
    public static <R, T> void addLink(final SuttonResponse<R, T> responseBuilder,
                                      final URI uri,
                                      final String relType,
                                      final String mediaType) {
        responseBuilder.header("Link", linkHeader(uri.toASCIIString(), relType, mediaType));
    }

}
