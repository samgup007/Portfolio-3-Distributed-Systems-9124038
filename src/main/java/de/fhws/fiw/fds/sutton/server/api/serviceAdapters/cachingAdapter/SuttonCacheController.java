package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter;

/**
 * An abstraction for the value of a HTTP Cache-Control response header.
 * */
public class SuttonCacheController {

    private boolean privateFlag;

    private boolean noCacheFlag;

    private boolean noStoreFlag;

    private boolean noTransformFlag;

    private boolean mustRevalidateFlag;

    private boolean proxyRevalidate;

    private int maxAge = -1;

    /**
     * Create a new instance of CacheControl. The new instance will have the
     * following default settings:
     *
     * <ul>
     * <li>private = false</li>
     * <li>noCache = false</li>
     * <li>noStore = false</li>
     * <li>noTransform = true</li>
     * <li>mustRevalidate = false</li>
     * <li>proxyRevalidate = false</li>
     * </ul>
     */
    public SuttonCacheController() {
        privateFlag = false;
        noCacheFlag = false;
        noStoreFlag = false;
        noTransformFlag = true;
        mustRevalidateFlag = false;
        proxyRevalidate = false;
    }

    public boolean isPrivateFlag() {
        return privateFlag;
    }

    public boolean isNoCacheFlag() {
        return noCacheFlag;
    }

    public boolean isNoStoreFlag() {
        return noStoreFlag;
    }

    public boolean isNoTransformFlag() {
        return noTransformFlag;
    }

    public boolean isMustRevalidateFlag() {
        return mustRevalidateFlag;
    }

    public boolean isProxyRevalidate() {
        return proxyRevalidate;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setPrivate(final boolean flag) {
        privateFlag = flag;
    }

    public void setNoCache(final boolean flag) {
        noCacheFlag = flag;
    }

    public void setNoStore(final boolean flag) {
        noStoreFlag = flag;
    }

    public void setNoTransform(final boolean flag) {
        noTransformFlag = flag;
    }

    public void setMustRevalidate(final boolean flag) {
        mustRevalidateFlag = flag;
    }

    public void setProxyRevalidate(final boolean flag) {
        proxyRevalidate = flag;
    }

    public void setMaxAge(final int maxAge) {
        this.maxAge = maxAge;
    }
}
