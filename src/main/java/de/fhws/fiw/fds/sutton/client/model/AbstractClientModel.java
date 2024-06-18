/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.sutton.client.model;

/**
 * This class specifies the basic requirements, that a Model should fulfill to be
 * used as a resource or a sub-resource within the client side.
 * */
public abstract class AbstractClientModel {

	/**
	 * A unique identifier for the model object
	 * */
	protected long id;

	protected AbstractClientModel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}
}
