package de.fhws.fiw.fds.demo;

import java.util.HashSet;
import java.util.Set;

import de.fhws.fiw.fds.demo.server.api.services.JerseyDispatcher;
import de.fhws.fiw.fds.demo.server.api.services.JerseyUni;
import de.fhws.fiw.fds.sutton.server.api.AbstractJerseyApplication;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class DemoJersey extends AbstractJerseyApplication
{
    @Override
    protected Set<Class<?>> getServiceClasses() {
        final Set<Class<?>> returnValue = new HashSet<>();

        returnValue.add(JerseyUni.class);
        returnValue.add(JerseyDispatcher.class);

        return returnValue;
    }
}
