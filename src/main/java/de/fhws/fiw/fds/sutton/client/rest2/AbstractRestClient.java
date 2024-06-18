package de.fhws.fiw.fds.sutton.client.rest2;

import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;
import okhttp3.Headers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractRestClient
{
	private static final String HEADER_TOTALNUMBEROFRESULTS = "X-totalnumberofresults";
	private static final String HEADER_NUMBEROFRESULTS = "X-numberofresults";

	private String locationHeaderURL = null;
	private Map<String, Link> possibleNextStates;
	private int numberOfResults;
	private int totalNumberOfResults;
	private int lastStatusCode;

	protected AbstractRestClient( )
	{
		this.possibleNextStates = new HashMap<>( );
	}

	public final int getLastStatusCode( )
	{
		return lastStatusCode;
	}

	public final int getNumberOfResults( )
	{
		return numberOfResults;
	}

	public final int getTotalNumberOfResults( )
	{
		return totalNumberOfResults;
	}

	protected final boolean isLinkAvailable( String relationType )
	{
		return this.possibleNextStates.containsKey( relationType );
	}

	protected final Link getLink( String relationType )
	{
		return this.possibleNextStates.get( relationType );
	}

	protected final String getUrl( String relationType )
	{
		return this.possibleNextStates.get( relationType ).getUrl( );
	}

	public Map<String, Link> getPossibleNextStates( )
	{
		return possibleNextStates;
	}

	public String getLocationHeaderURL( )
	{
		return locationHeaderURL;
	}

	public boolean isLocationHeaderAvailable( )
	{
		return locationHeaderURL != null;
	}

	protected final <T extends AbstractClientModel> void processResponse( WebApiResponse<T> response, SetData<T> setter )
	{
		readAllLinks( response );
		setter.setData( response );
		setNumberOfResults( response );
		setTotalNumberOfResults( response );
		setLastStatusCode( response );
	}

	private void readAllLinks( WebApiResponse<?> response )
	{
		this.locationHeaderURL = response.getLocationHeader().orElse(null);
		this.possibleNextStates = response.getResponseHeaders( )
										  .values( "Link" )
										  .stream( )
										  .map( l -> Link.parseFromHttpHeader( l ) )
										  .collect( Collectors.toMap( l -> l.getRelationType( ), l -> l ) );
	}

	private void setNumberOfResults( WebApiResponse<?> response )
	{
		this.numberOfResults = getHeaderAsInt( response.getResponseHeaders( ), HEADER_NUMBEROFRESULTS );
	}

	private void setTotalNumberOfResults( WebApiResponse<?> response )
	{
		this.totalNumberOfResults = getHeaderAsInt( response.getResponseHeaders( ), HEADER_TOTALNUMBEROFRESULTS );
	}

	private void setLastStatusCode( WebApiResponse<?> response )
	{
		this.lastStatusCode = response.getLastStatusCode( );
	}

	private int getHeaderAsInt( Headers headers, String headerName )
	{
		final String value = headers.get( headerName );
		return value != null ? Integer.parseInt( value ) : -1;
	}

	public interface SetData<T extends AbstractClientModel>
	{
		void setData( WebApiResponse<T> response );
	}
}
