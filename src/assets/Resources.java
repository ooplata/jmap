package assets;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * Dummy class to be able to load resources from this package.
 */
public class Resources {
    public static URL getResource(String name) {
        return Objects.requireNonNull(Resources.class.getResource(name));
    }

    public static InputStream getResourceAsStream(String name) {
        return Objects.requireNonNull(Resources.class.getResourceAsStream(name));
    }
}
