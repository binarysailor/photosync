package net.binarysailor.photosync;

import java.io.IOException;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class DirectoryNotFoundException extends IOException {
    private final String requestedPath;

    DirectoryNotFoundException(final String requestedPath) {
        this.requestedPath = requestedPath;
    }

    public String getRequestedPath() {
        return requestedPath;
    }
}
