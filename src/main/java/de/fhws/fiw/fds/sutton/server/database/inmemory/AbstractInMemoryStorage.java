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

package de.fhws.fiw.fds.sutton.server.database.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang.ObjectUtils;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractInMemoryStorage<T extends AbstractModel> {
	protected Map<Long, T> storage;

	private AtomicLong nextId;

	protected AbstractInMemoryStorage() {
		this.storage = new HashMap<>();
		this.nextId = new AtomicLong(1l);
	}

	public NoContentResult create(final T model) {
		model.setId(nextId());
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	public SingleModelResult<T> readById(final long id) {
		if (this.storage.containsKey(id)) {
			return new SingleModelResult<>(clone(this.storage.get(id)));
		}
		else {
			return new SingleModelResult<>();
		}
	}

	public CollectionModelResult<T> readAll(SearchParameter searchParameter) {
		return this.readAllByPredicate(all(), searchParameter);
	}

	protected CollectionModelResult<T> readAllByPredicate(final Predicate<T> predicate,
														  final SearchParameter searchParameter) {
		final CollectionModelResult<T> filteredResult =
				new CollectionModelResult<>(filterBy(predicate));
		final CollectionModelResult<T> page = InMemoryPaging.page(filteredResult,
				searchParameter.getOffset(), searchParameter.getSize());
		final CollectionModelResult<T> returnValue =
				new CollectionModelResult<>(clone(page.getResult()));
		returnValue.setTotalNumberOfResult(filteredResult.getTotalNumberOfResult());

		return returnValue;
	}

	private Collection<T> filterBy(final Predicate<T> predicate) {
		return this.storage.values().stream().filter(predicate).collect(Collectors.toList());
	}

	public NoContentResult update(final T model) {
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	public NoContentResult delete(final long id) {
		this.storage.remove(id);
		return new NoContentResult();
	}

	public void deleteAll() {
		this.storage.clear();
		this.nextId = new AtomicLong(1);
	}

	private final long nextId() {
		return this.nextId.getAndIncrement();
	}

	public Collection<T> clone(final Collection<T> result) {
		return result.stream().map(e -> clone(e)).collect(Collectors.toList());
	}

	public T clone(final T result) {
		final T clone = (T) ObjectUtils.cloneIfPossible(result);
		return clone;
	}

	private Predicate<T> all() {
		return p -> true;
	}

}
