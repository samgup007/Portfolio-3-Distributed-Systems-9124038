/*
 * Copyright (c) peter.braun@fhws.de
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

/**
 * The ApiKey class is a utility class. It is used to create immutable Api-keys to be used to authorize clients requests
 * */
public class ApiKey {
	private final String apiKeyHeader;

	private final String apiKeyValue;

	/**
	 * Constructs an Api-key and sets its header and its value according to the given values
	 * @param apiKeyHeader the header name {@link String} of the Api-key
	 * @param apiKeyValue the value {@link String} of the Api-key
	 * */
	public ApiKey(final String apiKeyHeader, final String apiKeyValue) {
		this.apiKeyHeader = apiKeyHeader;
		this.apiKeyValue = apiKeyValue;
	}

	/**
	 * @return the header name {@link String} of the Api-key
	 * */
	public String getApiKeyHeader() {
		return this.apiKeyHeader;
	}

	/**
	 * @return the value {@link String} of the Api-key
	 * */
	public String getApiKeyValue() {
		return this.apiKeyValue;
	}
}
