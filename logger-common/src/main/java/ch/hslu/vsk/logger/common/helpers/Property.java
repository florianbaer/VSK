package ch.hslu.vsk.logger.common.helpers;

import java.io.File;
import java.io.IOException;

public abstract class Property {

    /**
     * Creates a file if it does not exist.
     *
     * @param file The file to be created if it does not exist.
     * @throws IOException The unhandled io exception.
     */
    protected void createFileIfNotExisting(final File file) throws IOException {
        if (!file.isFile()) {
            var parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
        }

        if (!file.isFile()) {
            file.createNewFile();
        }
    }
}
