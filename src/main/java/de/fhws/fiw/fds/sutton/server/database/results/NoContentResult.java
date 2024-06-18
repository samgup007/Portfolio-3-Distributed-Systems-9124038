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

/**
 * This class extends {@link AbstractResult} and represent an empty result with no information to return
 * to the client in the response
 *
 * @see CollectionModelResult
 * @see SingleModelResult
 */
public class NoContentResult extends AbstractResult {

    /**
     * Default constructor to instantiate an empty result with no content
     */
    public NoContentResult() {
        super();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

}
