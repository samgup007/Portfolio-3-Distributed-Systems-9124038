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
 * The AbstractResult class defines the required properties, that all kind of results should possess in order to
 * give descriptive information about possible errors
 */
public abstract class AbstractResult {

    /**
     * Defines whether the result contains an error
     */
    protected boolean hasError;

    /**
     * Defines the error code to be returned with the result in case on an error
     */
    protected int errorCode;

    /**
     * Defines the error message to be returned with the result in case on an error
     */
    protected String errorMessage;

    /**
     * Defines the time needed {@link Long} to execute a CRUD operation in the database <strong>in milliseconds</strong>
     */
    protected long databaseExecutionTimeInMs;

    /**
     * The default constructor, which sets the {@link AbstractResult#hasError} to false by default
     */
    protected AbstractResult() {
        this.hasError = false;
    }

    /**
     * @return true if the result is empty and contains no information
     */
    public abstract boolean isEmpty();

    /**
     * Calculates the {@link AbstractResult#databaseExecutionTimeInMs} by subtracting the time when the command was
     * executed from the time when the command was started
     */
    public final void setTimes(final long startTime, final long stopTime) {
        this.databaseExecutionTimeInMs = stopTime - startTime;
    }

    /**
     * @return the {@link AbstractResult#databaseExecutionTimeInMs}
     */
    public final long getDuration() {
        return this.databaseExecutionTimeInMs;
    }

    /**
     * @return true if the result contains an error
     */
    public final boolean hasError() {
        return this.hasError;
    }

    /**
     * Sets {@link AbstractResult#hasError} to true
     */
    public final void setError() {
        this.hasError = true;
    }

    /**
     * Sets {@link AbstractResult#hasError} to true and sets the {@link AbstractResult#errorCode}
     * as well as {@link AbstractResult#errorMessage} to the given values, referring that the result contains an
     * error
     *
     * @param errorCode    {@link Integer} the error code to be set
     * @param errorMessage {@link String} the error message, that should be returned with the result
     */
    public final void setError(final int errorCode, final String errorMessage) {
        this.hasError = true;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
