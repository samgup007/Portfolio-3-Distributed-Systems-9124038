package de.fhws.fiw.fds.sutton.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang.ObjectUtils;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryRelationStorage<T extends AbstractModel> {
	protected MultiValuedMap<Long, Long> storage;

	protected AbstractInMemoryRelationStorage() {
		this.storage = new HashSetValuedHashMap<>();
	}

	public NoContentResult create(final long primaryId, final T secondary) {
		getSecondaryStorage().create(secondary);
		this.storage.put(primaryId, secondary.getId());
		return new NoContentResult();
	}

	public NoContentResult update(final long primaryId, final T secondary) {
		getSecondaryStorage().update(secondary);

		if (this.storage.containsMapping(primaryId, secondary.getId()) == false) {
			this.storage.put(primaryId, secondary.getId());
		}

		return new NoContentResult();
	}

	public NoContentResult deleteRelation(final long primaryId, final long secondaryId) {
		if (this.storage.containsMapping(primaryId, secondaryId)) {
			this.storage.removeMapping(primaryId, secondaryId);
			return new NoContentResult();
		} else {
			return noMappingError();
		}
	}

	public NoContentResult deleteRelationsFromPrimary(final long primaryId) {
		this.storage.get(primaryId).stream().forEach(s -> deleteRelation(primaryId, s));
		return new NoContentResult();
	}

	public NoContentResult deleteRelationsToSecondary(final long secondaryId) {
		this.storage.keySet()
				.stream()
				.collect(Collectors.toSet())
				.forEach(primaryId -> deleteRelation(primaryId, secondaryId));

		return noMappingError();
	}

	public SingleModelResult<T> readById(final long primaryId, final long secondaryId) {
		if (this.storage.containsMapping(primaryId, secondaryId)) {
			return clone(primaryId, getSecondaryStorage().readById(secondaryId));
		} else {
			return new SingleModelResult<>();
		}
	}

	/**
	 * Reads all sub-resources linked to a primary resource defined by the provided primaryId
	 *
	 * @param primaryId
	 * @param primaryId
	 * @param predicate
	 * @return
	 */
	protected CollectionModelResult<T> readAllLinkedByPredicate(final long primaryId, final Predicate<T> predicate) {
		return new CollectionModelResult<>(
				clone(primaryId, this.storage.get(primaryId)
						.stream()
						.map(secondaryId -> loadSecondary(secondaryId))
						.filter(result -> result.isEmpty() == false)
						.map(result -> result.getResult())
						.filter(predicate)
						.collect(Collectors.toList())));
	}

	/**
	 * Reads all sub-resources and returns a set of entities where the primary Id is set to the given
	 * primary Id even if the entity is not linked to the primary resource
	 *
	 * @param primaryId
	 * @param predicate
	 * @return
	 */
	protected CollectionModelResult<T> readAllByPredicate(final long primaryId, final Predicate<T> predicate) {
		return new CollectionModelResult<>(
				clone(primaryId, this.getSecondaryStorage().readAll().getResult().stream()
						.filter(predicate)
						.collect(Collectors.toList())));
	}

	protected abstract IDatabaseAccessObject<T> getSecondaryStorage();

	private SingleModelResult<T> loadSecondary(final long id) {
		return getSecondaryStorage().readById(id);
	}

	private NoContentResult noMappingError() {
		final NoContentResult errorResult = new NoContentResult();
		errorResult.setError(1, "No mapping between resources");
		return errorResult;
	}

	private SingleModelResult<T> clone(final long primaryId, final SingleModelResult<T> result) {
		return new SingleModelResult<>(clone(primaryId, result.getResult()));
	}

	private CollectionModelResult<T> clone(final long primaryId, final CollectionModelResult<T> result) {
		return new CollectionModelResult<>(clone(primaryId, result.getResult()));
	}

	private Collection<T> clone(final long primaryId, final Collection<T> result) {
		return result.stream().map(e -> clone(primaryId, e)).collect(Collectors.toList());
	}

	private T clone(final long primaryId, final T result) {
		final T clone = (T) ObjectUtils.cloneIfPossible(result);
		clone.setPrimaryId(primaryId);
		return clone;
	}
}
