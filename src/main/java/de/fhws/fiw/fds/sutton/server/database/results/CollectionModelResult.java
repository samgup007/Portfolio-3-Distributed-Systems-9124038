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

import java.util.Collection;
import java.util.LinkedList;

/**
 * This class extends {@link AbstractResult} and represents a result, which contains a collection of models of
 * type {@link AbstractModel}
 *
 * @see SingleModelResult
 * @see NoContentResult
 */
public class CollectionModelResult<T extends AbstractModel> extends AbstractResult {

    /**
     * a collection of the found resources, that descend from {@link AbstractModel} in the database
     */
    protected Collection<T> result;

    /**
     * The total number {@link Integer} of all found results in the database. This property is handy to distinguish
     * between the total number of the found results and the number of results sent in a single response
     * while applying pagination
     */
    protected int totalNumberOfResult;

    /**
     * Empty Constructor, which indicates that no results were found in the database
     */
    public CollectionModelResult() {
        this.result = new LinkedList<>();
        this.totalNumberOfResult = 0;
    }

    /**
     * Constructor to instantiate a {@link CollectionModelResult} ,to set the result to the given collection
     * and to set the number of the found results accordingly
     */
    public CollectionModelResult(final Collection<T> result) {
        this.result = result != null ? result : new LinkedList<>();
        this.totalNumberOfResult = this.result.size();
    }

    @Override
    public boolean isEmpty() {
        return this.result.isEmpty();
    }

    public Collection<T> getResult() {
        return this.result;
    }

    public int getTotalNumberOfResult() {
        return this.totalNumberOfResult;
    }

    public void setTotalNumberOfResult(final int totalNumberOfResult) {
        this.totalNumberOfResult = totalNumberOfResult;
    }

}
