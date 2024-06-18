package de.fhws.fiw.fds.sutton.server.api.queries;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The PagingBehaviorUsingPage class is an instance of {@link PagingBehavior} and describes a paging behavior
 * in which the collection of the full results is divided into pages. The page has a fixed size and the client can
 * request a certain page from the server.
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 * @see PagingBehaviorUsingOffsetSize
 * @see OnePageWithAllResults
 */
public class PagingBehaviorUsingPage<R, T extends AbstractModel> extends PagingBehavior<R, T> {

    /**
     * Default name {@link String} of the page query parameter
     */
    public static final String QUERY_PARAM_PAGE = "page";

    /**
     * Default size {@link Integer} of the page to be sent in the result
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * the used name {@link String} for the page query parameter
     */
    protected String pageQueryParamName = QUERY_PARAM_PAGE;

    /**
     * Number {@link Integer} of the page to be sent in the response to the client
     */
    protected int pageNumber;

    /**
     * This constructor instantiate a PagingBehaviorUsingPage and sets the page query parameter to the given value.
     * It also checks if the given pageNumber value is valid and sets the {@link PagingBehaviorUsingPage#pageNumber}
     * accordingly
     *
     * @param pageQueryParamName {@link String} page query parameter to be used in the request to request a
     *                           certain page
     * @param pageNumber         {@link Integer} number of the page to be returned to the client
     */
    public PagingBehaviorUsingPage(final String pageQueryParamName, final int pageNumber) {
        this.pageQueryParamName = pageQueryParamName;
        setPageNumber(pageNumber);
    }

    /**
     * This constructor instantiate a PagingBehaviorUsingPage and checks if the given pageNumber value is valid
     * and sets the {@link PagingBehaviorUsingPage#pageNumber} accordingly
     *
     * @param pageNumber {@link Integer} number of the page to be returned to the client
     */
    public PagingBehaviorUsingPage(final int pageNumber) {
        setPageNumber(pageNumber);
    }

    @Override
    public int getOffset() {
        return (this.pageNumber - 1) * DEFAULT_PAGE_SIZE;
    }

    @Override
    public int getSize() {
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    protected boolean hasNextLink(final CollectionModelResult<T> result) {
        return this.pageNumber * DEFAULT_PAGE_SIZE < result.getTotalNumberOfResult();
    }

    @Override
    protected boolean hasPrevLink() {
        return this.pageNumber > 1;
    }

    @Override
    protected URI getSelfUri(final SuttonUriInfo uriInfo) {
        Map<String, Integer> queryParamsMap = getQueryParamAsMap(this.pageNumber);
        final String uriTemplate = uriInfo.createURIWithQueryParamTemplates(getPageParamName());
        return uriInfo.getURI(uriTemplate, queryParamsMap);
    }

    @Override
    protected URI getPrevUri(final SuttonUriInfo uriInfo) {
        Map<String, Integer> queryParamsMap = getQueryParamAsMap(this.pageNumber - 1);
        final String uriTemplate = uriInfo.createURIWithQueryParamTemplates(getPageParamName());
        return uriInfo.getURI(uriTemplate, queryParamsMap);
    }

    @Override
    protected URI getNextUri(final SuttonUriInfo uriInfo, final CollectionModelResult<T> result) {
        Map<String, Integer> queryParamsMap = getQueryParamAsMap(this.pageNumber + 1);
        final String uriTemplate = uriInfo.createURIWithQueryParamTemplates(getPageParamName());
        return uriInfo.getURI(uriTemplate, queryParamsMap);
    }

    private Map<String, Integer> getQueryParamAsMap(final int pageNumber) {
        Map<String, Integer> result = new HashMap<>();
        result.put(getPageParamName(), pageNumber);
        return result;
    }

    private void setPageNumber(final int pageNumber) {
        this.pageNumber = Math.max(1, pageNumber);
    }

    private String getPageParamName() {
        return this.pageQueryParamName;
    }

}
