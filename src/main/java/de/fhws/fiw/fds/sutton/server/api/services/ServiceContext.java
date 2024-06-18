package de.fhws.fiw.fds.sutton.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter.SuttonServletRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter.SuttonRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;

public class ServiceContext {
    private SuttonUriInfo uriInfo;
    private SuttonRequest request;
    private SuttonServletRequest servletRequest;

    public ServiceContext(SuttonUriInfo uriInfo, SuttonRequest request, SuttonServletRequest servletRequest) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.servletRequest = servletRequest;
    }

    public SuttonUriInfo getUriInfo() {
        return uriInfo;
    }

    public void setUriInfo(SuttonUriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public SuttonRequest getRequest() {
        return request;
    }

    public void setRequest(SuttonRequest request) {
        this.request = request;
    }

    public SuttonServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(SuttonServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}
