package de.fhws.fiw.fds.sutton.client.converters;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fhws.fiw.fds.sutton.client.utils.Link;

import java.io.IOException;

public class ClientLinkJsonConverter extends StdDeserializer<Link> {

    public ClientLinkJsonConverter() {
        this(null);
    }

    public ClientLinkJsonConverter(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Link deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        Link link = new Link();
        link.setMediaType(jsonNode.get("type").asText());
        link.setUrl(jsonNode.get("href").asText());
        link.setRelationType(jsonNode.get("rel").asText());
        return link;
    }
}
