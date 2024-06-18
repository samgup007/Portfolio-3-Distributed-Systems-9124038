package de.fhws.fiw.fds.demo.client.web;

import java.io.IOException;

import de.fhws.fiw.fds.demo.client.models.ClientUni;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

public class ClientUniWeb
{
    private GenericWebClient<ClientUni> client;

    public ClientUniWeb()
    {
        this.client = new GenericWebClient<>();
    }

    public ResponseUniWeb postNewUniversity(String url, ClientUni university) throws IOException
    {
        return createResponse(this.client.sendPostRequest(url, university));
    }

    public ResponseUniWeb updateUniversity(String url, ClientUni university) throws IOException
    {
        return createResponse(this.client.sendPutRequest(url, university));
    }

    public ResponseUniWeb deleteUniversity(String url) throws IOException
    {
        return createResponse(this.client.sendDeleteRequest(url));
    }

    public ResponseUniWeb getDispatcher(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url));
    }

    public ResponseUniWeb getSingleUniversity(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url, ClientUni.class));
    }

    public ResponseUniWeb getCollectionOfUniversities(String url) throws IOException
    {
        return createResponse(this.client.sendGetCollectionRequest(url, ClientUni.class));
    }

    public ResponseUniWeb resetDatabaseOnServer(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url + "/resetdatabase"));
    }

    public ResponseUniWeb fillDatabase(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url + "/filldatabase"));
    }

    private ResponseUniWeb createResponse(WebApiResponse<ClientUni> response)
    {
        return new ResponseUniWeb(response.getResponseData(), response.getResponseHeaders(), response.getLastStatusCode());
    }
}
