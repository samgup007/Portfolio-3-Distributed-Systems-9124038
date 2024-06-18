// Copyright 2022 Peter Braun
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.fhws.fiw.fds.sutton.server.database.inmemory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public class InMemoryPaging {
    private InMemoryPaging() {
    }

    public static <T extends AbstractModel> CollectionModelResult<T> page(
            final CollectionModelResult<T> fullResult,
            final int offset,
            final int size) {
        final CollectionModelResult<T> returnValue = new CollectionModelResult<>();
        returnValue.getResult().addAll(page(fullResult.getResult(), offset, size));
        returnValue.setTotalNumberOfResult(fullResult.getTotalNumberOfResult());
        return returnValue;
    }

    private static <T extends AbstractModel> List<T> page(final Collection<T> result, int offset, int size) {
        if (offset >= result.size()) {
            offset = Math.max(0, result.size() - size);
        }

        return result.stream().skip(offset).limit(size).collect(Collectors.toList());
    }
}