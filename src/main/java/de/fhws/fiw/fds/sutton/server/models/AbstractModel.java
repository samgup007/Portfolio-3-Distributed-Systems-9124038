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

package de.fhws.fiw.fds.sutton.server.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * This class specifies the basic requirements, that a Model should fulfill to be
 * used as a resource or a sub-resource.
 */
public abstract class AbstractModel implements Serializable, Cloneable {

    /**
     * A unique identifier for the model object
     */
    protected long id;

    /**
     * The id of the primary resource the model is related to, when the model is being used as a sub-resource
     */
    private long primaryId;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    /**
     * @return the {@link AbstractModel#primaryId} {@link Long}
     */
    @JsonIgnore
    public long getPrimaryId() {
        return primaryId;
    }

    /**
     * @param primaryId {@link Long} - the id of the primary resource, this model is related to
     *                  Set the {@link AbstractModel#primaryId} to the provided value
     */
    @JsonIgnore
    public void setPrimaryId(final long primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * Creates and returns a copy of the model
     *
     * @throws CloneNotSupportedException - if the model couldn't be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
