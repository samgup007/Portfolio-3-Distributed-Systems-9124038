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

/**
 * The SearchParameter class provides the required attributes to apply the paging behavior using offset and size.
 * It could also be used to define sorting criteria to sort the results that were read from the database
 */
public class SearchParameter {

    public static final SearchParameter DEFAULT = new SearchParameter();

    /**
     * The offset {@link Integer} from the collection of the full results where the page should start
     */
    private int offset = 0;

    /**
     * The size {@link Integer} of the page
     */
    private int size = Integer.MAX_VALUE;

    /**
     * The sorting criterion {@link String} to be used to sort the results from the database
     */
    private String orderByAttribute = "";

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderByAttribute() {
        return this.orderByAttribute;
    }

    public void setOrderByAttribute(String orderByAttribute) {
        this.orderByAttribute = orderByAttribute;
    }

}
