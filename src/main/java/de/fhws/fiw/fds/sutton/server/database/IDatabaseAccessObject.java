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

package de.fhws.fiw.fds.sutton.server.database;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * This interface defines the CRUD operations methods, that each model's database has to implement.
 */
public interface IDatabaseAccessObject<T extends AbstractModel> {

    /**
     * the create method is intended to create the provided resource and save it to the database
     *
     * @param model the resource (an instance of {@link AbstractModel}) to be created
     * @return a {@link NoContentResult}
     */
    NoContentResult create(final T model);

    /**
     * Searches the database for a resource using the provided id
     *
     * @param id the id {@link Long} of the resource to be searched for in the database
     * @return a {@link SingleModelResult} wrapping the resource if founded, otherwise an
     * empty {@link SingleModelResult}
     */
    SingleModelResult<T> readById(final long id);

    /**
     * @return a {@link CollectionModelResult} of all the results in the database
     */
    default CollectionModelResult<T> readAll() {
        return readAll(SearchParameter.DEFAULT);
    }

    /**
     * Searches the database for all resources using the provided searchParameter to configure the paging behavior
     * and the sorting criteria
     *
     * @param searchParameter {@link SearchParameter} to set the paging behavior and the sorting criteria
     * @return a {@link CollectionModelResult} of all resources in the database
     */
    CollectionModelResult<T> readAll(SearchParameter searchParameter);

    /**
     * Updates a resource (an instance of {@link AbstractModel}) in the database <strong>if exists</strong>
     * by the given resources
     *
     * @param model the new updated resource (an instance of {@link AbstractModel})
     * @return a {@link NoContentResult}
     */
    NoContentResult update(final T model);

    /**
     * Deletes a resource (an instance of {@link AbstractModel}) from the database defined by the provided id
     *
     * @param id id {@link Long} of the resource to be deleted
     * @return a {@link NoContentResult}
     */
    NoContentResult delete(final long id);

}
