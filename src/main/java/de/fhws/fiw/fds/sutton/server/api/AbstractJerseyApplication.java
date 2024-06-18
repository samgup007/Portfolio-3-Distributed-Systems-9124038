/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.sutton.server.api;

import java.util.HashSet;
import java.util.Set;

import de.fhws.fiw.fds.sutton.server.api.converter.JacksonConfig;
import org.apache.catalina.filters.CorsFilter;
import org.apache.catalina.loader.ParallelWebappClassLoader;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public abstract class AbstractJerseyApplication extends ResourceConfig {

    protected AbstractJerseyApplication() {
        super();
        registerClasses(getDefaultAndSpecificServiceClasses());
        packages("org.glassfish.jersey.examples.linking");
        register(new JacksonConfig().jsonProvider());
        register(new JacksonConfig().xmlProvider());
        register(DeclarativeLinkingFeature.class);
        register(MultiPartFeature.class);
        register(CorsFilter.class);
    }

    /**
     * This extends the given project specific ServiceClasses with the general ServiceClasses of Sutton.
     * @return the full {@link Set} of ServiceClasses
     */
    private Set<Class<?>> getDefaultAndSpecificServiceClasses() {
        /*
         * The following two lines solve the problem that the embedded version of Tomcat cannot be started
         * by using class Start. The problem was that JPA is initialized using the system class loader
         * whereas the Web app is loaded by the classloader ParallelWebappClassLoader. The latter one is
         * configured by default not to use delegation, i.e. the ParallelWebappClassloader does know
         * the system class loader as parent but does not use it. Delegation is activated by the following
         * two lines.
         */

        ParallelWebappClassLoader classloader = (ParallelWebappClassLoader) this.getClass().getClassLoader();
        classloader.setDelegate(true);

        Set<Class<?>> allServiceClasses = new HashSet<>(getServiceClasses());

        return allServiceClasses;
    }

    /**
     * this method should be used to register the services to be used in the webapp
     */
    protected abstract Set<Class<?>> getServiceClasses();

}
