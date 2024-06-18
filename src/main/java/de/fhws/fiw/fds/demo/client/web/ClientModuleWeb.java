package de.fhws.fiw.fds.demo.client.web;

import java.io.IOException;

import de.fhws.fiw.fds.demo.client.models.ClientModule;
import de.fhws.fiw.fds.sutton.client.web.GenericWebClient;
import de.fhws.fiw.fds.sutton.client.web.WebApiResponse;

public class ClientModuleWeb
{
    private GenericWebClient<ClientModule> client;

    public ClientModuleWeb()
    {
        this.client = new GenericWebClient<>();
    }

    public ResponseModuleWeb getDispatcher(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url));
    }

    public ResponseModuleWeb deleteModule(String url) throws IOException
    {
        return createResponse(this.client.sendDeleteRequest(url));
    }

    public ResponseModuleWeb getCollectionOfModules(String url) throws IOException
    {
        return createResponse(this.client.sendGetCollectionRequest(url, ClientModule.class));
    }

    public ResponseModuleWeb resetDatabaseOnServer(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url + "/resetdatabase"));
    }

    public ResponseModuleWeb getSingleModule(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url, ClientModule.class));
    }

    public ResponseModuleWeb postNewModule(String url, ClientModule module) throws IOException
    {
        return createResponse(this.client.sendPostRequest(url, module));
    }

    public ResponseModuleWeb putNewModule(String url, ClientModule module) throws IOException
    {
        return createResponse(this.client.sendPutRequest(url, module));
    }

    public ResponseModuleWeb fillDatabase(String url) throws IOException
    {
        return createResponse(this.client.sendGetSingleRequest(url + "/filldatabase"));
    }

    private ResponseModuleWeb createResponse(WebApiResponse<ClientModule> response)
    {
        return new ResponseModuleWeb(response.getResponseData(), response.getResponseHeaders(), response.getLastStatusCode());
    }
}
