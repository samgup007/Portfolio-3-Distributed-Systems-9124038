package de.fhws.fiw.fds.sutton.server.api.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.jakarta.rs.xml.JacksonXmlBindXMLProvider;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.time.format.DateTimeFormatter;

/**
 * The {@code JacksonConfig} class provides configuration for Jackson JSON and XML modules
 * to be used with Jersey. It includes setup for Java time module serialization
 * and deserialization, as well as enabling default typing and indentation for better readability.
 */
public class JacksonConfig {

    /**
     * Creates and configures a {@link JavaTimeModule} for Java time serialization.
     *
     * @return A configured {@link JavaTimeModule} instance.
     */
    private JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        return javaTimeModule;
    }

    /**
     * Creates and configures an {@link ObjectMapper} with modules for JSON processing.
     *
     * @return A configured {@link ObjectMapper} instance.
     */
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(javaTimeModule())
                .registerModule(new JakartaXmlBindAnnotationModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .findAndRegisterModules();
    }

    /**
     * Creates a {@link JacksonJaxbJsonProvider} for JSON processing using the configured {@link ObjectMapper}.
     *
     * @return A new {@link JacksonJaxbJsonProvider} instance.
     */
    public JacksonJaxbJsonProvider jsonProvider() {
        return new JacksonJaxbJsonProvider(objectMapper(), JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }

    /**
     * Creates a {@link JacksonXmlBindXMLProvider} for XML processing using a configured {@link XmlMapper}.
     *
     * @return A new {@link JacksonXmlBindXMLProvider} instance.
     */
    public JacksonXmlBindXMLProvider xmlProvider() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(javaTimeModule());
        xmlMapper.registerModule(new JakartaXmlBindAnnotationModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.findAndRegisterModules();
        return new JacksonXmlBindXMLProvider(xmlMapper, JacksonXmlBindXMLProvider.DEFAULT_ANNOTATIONS);
    }
}
