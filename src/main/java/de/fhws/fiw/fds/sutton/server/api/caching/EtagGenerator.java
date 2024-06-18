package de.fhws.fiw.fds.sutton.server.api.caching;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;

/**
 * The EtagGenerator class creates Etags for serializable objects to send them to the client as a header in the
 * response to apply the caching mechanism according to the REST specifications
 */
public class EtagGenerator {

    /**
     * Creates an Etag for the given serializable object
     *
     * @param object {@link Serializable} the object to create an Etag for
     * @return an Etag {@link String} for the given object if it is not null
     * @throws NullPointerException
     */
    public static String createEtag(final Serializable object) {
        if (object == null) {
            throw new NullPointerException("Cannot create Etag for null object");
        }

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            final MessageDigest md = MessageDigest.getInstance("MD5");
            return printHexBinary(md.digest(baos.toByteArray()));
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (final IOException e) {
                // not handled.
            }
        }
    }

    private static String printHexBinary(byte[] data) {
        final char[] hexCode = "0123456789ABCDEF".toCharArray();
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
