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

package de.fhws.fiw.fds.sutton.server.database.results;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * This class extends the {@link AbstractResult} and represents a result which contains only one
 * instance of {@link AbstractModel}
 *
 * @see CollectionModelResult
 * @see NoContentResult
 */
public class SingleModelResult<T extends AbstractModel> extends AbstractResult {

    /**
     * an instance of {@link AbstractModel} to be returned within the response
     */
    protected T result;

    /**
     * Defines whether the searched instance of {@link AbstractModel} was found in the database
     */
    protected boolean found;

    /**
     * Empty constructor and represents that the searched resource was not found
     */
    public SingleModelResult() {
        this.found = false;
    }

    /**
     * instantiates a SingleModelResult and sets its resource to be returned to the found one in the database
     * or to null if no resource was found in the database. It also sets the {@link SingleModelResult#found} property accordingly
     */
    public SingleModelResult(final T result) {
        this.result = result;
        this.found = result != null;
    }

    /**
     * @return {@link SingleModelResult#result}
     */
    public T getResult() {
        return this.result;
    }

    @Override
    public boolean isEmpty() {
        return !this.found;
    }

}
