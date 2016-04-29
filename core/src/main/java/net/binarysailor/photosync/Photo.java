package net.binarysailor.photosync;

import java.io.InputStream;

/**
 * Created by binarysailor on 11/04/2016.
 */
public interface Photo {

    String getName();

    Directory getDirectory();

    long getSize();

    InputStream getContent();
}
