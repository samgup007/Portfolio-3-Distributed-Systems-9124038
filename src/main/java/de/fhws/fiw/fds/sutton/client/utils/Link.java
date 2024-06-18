/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.sutton.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Link class represents the relation-type links that are sent in HTTP-responses to apply the HATEOAS concept
 * as defined in the REST architecture
 *
 * */
public class Link {
	private String url;

	private String mediaType;

	private String relationType;

	/**
	 * Constructs a default relation-type Link
	 * */
	public Link() {}

	/**
	 * Constructs a relation-type Link assigning its URL, its media-type, and its relation-type according to
	 * the given parameters.
	 * @param url the url {@link String} of the relation-type Link
	 * @param relationType the relation type {@link String} that the Link represents
	 * @param mediaType the media type {@link String} of the relation-type Link
	 * */
	public Link(final String url, final String mediaType, final String relationType) {
		this.url = url;
		this.mediaType = mediaType;
		this.relationType = relationType;
	}

	/**
	 * Parses a header in the HTTP-response to a relation-type Link
	 * @param header the header {@link String} in the HTTP-response to be parsed
	 * @return a relation-type {@link Link} parsed from the given header
	 * */
	public static Link parseFromHttpHeader(final String header) {
		final String[] elements = header.split(";");
		final String href = elements[0];
		final String rel = elements.length > 1 ? elements[1] : "";
		final String type = elements.length > 2 ? elements[2] : "type=\"*/*\"";
		return new Link(parseHref(href), parseType(type), parseRel(rel));
	}

	private static String parseHref(final String headerElement) {
		return parse(headerElement, "<([^>]*)>");
	}

	private static String parse(final String headerElement, final String patternExpression) {
		final Pattern pattern = Pattern.compile(patternExpression);
		final Matcher matcher = pattern.matcher(headerElement.trim());
		return matcher.find() ? matcher.group(1) : null;
	}

	private static String parseRel(final String headerElement) {
		return parse(headerElement, "^rel=\"(.+)\"$");
	}

	private static String parseType(final String headerElement) {
		return parse(headerElement, "^type=\"(.+)\"$");
	}

	/**
	 * @return {@link String} the href of the relation-type Link
	 * */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the href of the relation-type Link to the given href
	 * @param url the href {@link String} to be set
	 * */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return {@link String} the media-type of the relation-type Link
	 * */
	public String getMediaType() {
		return this.mediaType;
	}

	/**
	 * Sets the media-type of the relation-type Link to the given media-type
	 * @param mediaType the media-type {@link String} to be set
	 * */
	public void setMediaType(final String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return {@link String} the relation-type, the relation-type Link stands for
	 * */
	public String getRelationType() {
		return this.relationType;
	}

	/**
	 * Sets the relation-type of the Link to the given relation-type
	 * @param relationType the relation-type {@link String} to be set
	 * */
	public void setRelationType(final String relationType) {
		this.relationType = relationType;
	}

	@Override
	public String toString() {
		return "NorburyLink{" + "url='" + this.url + '\'' + ", mediaType='" + this.mediaType + '\''
				+ ", relationType='" + this.relationType + '\'' + '}';
	}

}
