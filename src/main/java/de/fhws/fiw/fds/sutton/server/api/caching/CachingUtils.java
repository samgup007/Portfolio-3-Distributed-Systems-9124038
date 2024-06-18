/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.caching;


import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter.SuttonCacheController;

/**
 * The CachingUtils class is a utility class that provides useful methods to simplify the creation of cache control
 * in the response
 */
public class CachingUtils {

    /**
     * Creates a cache control object, where the returned data are allowed to be saved in public caches and are valid
     * for 2 seconds since they have been sent from the origin server
     *
     * @return {@link SuttonCacheController}
     */
    public static SuttonCacheController create2SecondsPublicCaching() {
        final SuttonCacheController cacheControl = new SuttonCacheController();
        cacheControl.setPrivate(false);
        cacheControl.setMaxAge(2);

        return cacheControl;
    }

    /**
     * Creates a cache control object, where the returned data are allowed to be saved in public caches and are valid
     * for 30 seconds since they have been sent from the origin server
     *
     * @return {@link SuttonCacheController}
     */
    public static SuttonCacheController create30SecondsPublicCaching() {
        final SuttonCacheController cacheControl = new SuttonCacheController();
        cacheControl.setPrivate(false);
        cacheControl.setMaxAge(30);

        return cacheControl;
    }

    /**
     * Creates a cache control object, where the returned data are allowed to be saved in public caches and are valid
     * for 60 seconds since they have been sent from the origin server
     *
     * @return {@link SuttonCacheController}
     */
    public static SuttonCacheController create60SecondsPublicCaching() {
        final SuttonCacheController cacheControl = new SuttonCacheController();
        cacheControl.setPrivate(false);
        cacheControl.setMaxAge(60);

        return cacheControl;
    }

    /**
     * Creates a cache control object, <strong>where the returned data are only allowed to be saved in private
     * caches</strong> and are valid for 30 seconds since they have been sent from the origin server
     *
     * @return {@link SuttonCacheController}
     */
    public static SuttonCacheController create30SecondsPrivateCaching() {
        final SuttonCacheController cacheControl = new SuttonCacheController();
        cacheControl.setPrivate(true);
        cacheControl.setMaxAge(30);

        return cacheControl;
    }

    /**
     * Creates a cache control object, <strong>where the returned data are not allowed to be cached
     * </strong>
     *
     * @return {@link SuttonCacheController}
     */
    public static SuttonCacheController createNoCacheNoStoreCaching() {
        final SuttonCacheController cacheControl = new SuttonCacheController();
        cacheControl.setNoCache(true);
        cacheControl.setNoStore(true);

        return cacheControl;
    }

}
