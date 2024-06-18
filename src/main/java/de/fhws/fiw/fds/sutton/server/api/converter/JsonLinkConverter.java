package de.fhws.fiw.fds.sutton.server.api.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.ws.rs.core.Link;

import java.io.IOException;

public class JsonLinkConverter extends StdSerializer<Link> {

    public JsonLinkConverter() {
        this(null);
    }

    public JsonLinkConverter(Class<Link> t) {
        super(t);
    }

    @Override
    public void serialize(Link link, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(link.getTitle());
        gen.writeStringField("href", replaceCharacters(link.getUri().toASCIIString()));
        gen.writeStringField("rel", link.getRel());
        if (link.getType() != null && !link.getType().isEmpty()) {
            gen.writeStringField("type", link.getType());
        }
        gen.writeEndObject();
    }

    private String replaceCharacters(final String body) {
        return body.replace("%3F", "?").replaceAll("%7B", "{").replaceAll("%7D", "}");
    }
}

