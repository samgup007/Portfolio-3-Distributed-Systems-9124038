package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The {@link Link} class represents a hypermedia link. It encapsulates the href, rel,
 * and type attributes that define the characteristics of a hyperlink.
 */

public class Link {

    private String href;

    private String rel;

    private String type;

    /**
     * Constructs an empty {@link Link} object.
     */
    public Link() {
    }

    /**
     * Constructs a {@link Link} object with the specified href, rel, and type attributes.
     *
     * @param href The URL that the link points to.
     * @param rel  The relationship type of the link.
     * @param type The media type of the link target.
     */
    public Link(String href, String rel, String type) {
        this.href = href;
        this.rel = rel;
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
