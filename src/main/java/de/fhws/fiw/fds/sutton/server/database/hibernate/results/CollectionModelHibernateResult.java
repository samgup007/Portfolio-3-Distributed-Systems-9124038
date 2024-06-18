package de.fhws.fiw.fds.sutton.server.database.hibernate.results;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionModelHibernateResult<T extends AbstractDBModel> extends AbstractResult {

    protected Collection<T> result;

    protected int totalNumberOfResult;

    public CollectionModelHibernateResult() {
        this.result = new LinkedList<>();
        this.totalNumberOfResult = 0;
    }

    public CollectionModelHibernateResult(final Collection<T> result) {
        this.result = result != null ? result : new LinkedList<>();
        this.totalNumberOfResult = result.size();
    }

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
