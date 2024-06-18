package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The OnePageWithAllResults is a paging behavior and represents sending the whole amount of the request data in one single response
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 * @see PagingBehaviorUsingOffsetSize
 * @see PagingBehaviorUsingPage
 */
public class OnePageWithAllResults<R, T extends AbstractModel> extends PagingBehaviorUsingOffsetSize<R, T> {

    public OnePageWithAllResults() {
        super(0, Integer.MAX_VALUE);
    }

    @Override
    protected int getDefaultMaxPageSize() {
        return Integer.MAX_VALUE;
    }
}
