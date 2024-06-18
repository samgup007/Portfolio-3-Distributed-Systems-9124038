package de.fhws.fiw.fds.sutton.server.database.hibernate.results;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;

public class SingleModelHibernateResult<T extends AbstractDBModel> extends AbstractResult {

    protected T result;

    protected boolean found;

    public SingleModelHibernateResult() {
        this.found = false;
    }

    public SingleModelHibernateResult(final T result) {
        this.result = result;
        this.found = result != null;
    }

    public T getResult() {
        return this.result;
    }

    public boolean isEmpty() {
        return !this.found;
    }
}
