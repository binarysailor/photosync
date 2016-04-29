package net.binarysailor.photosync;

import java.io.File;

/**
 * Created by binarysailor on 29/04/2016.
 */
public class FileSystemLocations {

    public static String getFileSystemPath(final FileSystemStorage storage, final FileSystemPhoto photo) {
        return photo.getDirectory().getFileSystemPath() + File.pathSeparator + photo.getName();
    }
}
