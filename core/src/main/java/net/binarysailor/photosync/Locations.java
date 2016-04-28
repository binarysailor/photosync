package net.binarysailor.photosync;

import com.google.common.base.Joiner;

import java.util.LinkedList;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class Locations {
    public static final char SEPARATOR = '/';

    public static String getFullDirectoryLocation(final Directory directory) {
        final LinkedList<String> directoryNames = new LinkedList<>();
        Directory currentDirectory = directory;
        do {
            directoryNames.addFirst(currentDirectory.getName());
            currentDirectory = currentDirectory.getParent();
        } while (currentDirectory != null);

        return Joiner.on(SEPARATOR).join(directoryNames);
    }

    public static String getFullPhotoLocation(final Directory directory, final Photo photo) {
        return getFullDirectoryLocation(directory) + SEPARATOR + photo.getName();
    }

    public static DirectoryStoragePointer createDirectoryStoragePointer(final String path) {
        final String cleanedUp;

        if (path != null) {
            final StringBuilder s = new StringBuilder(path);
            while (s.length() > 0 && s.charAt(0) == SEPARATOR) {
                s.deleteCharAt(0);
            }
            if (s.length() == 0) {
                cleanedUp = null;
            } else {
                cleanedUp = s.toString();
            }
        } else {
            cleanedUp = null;
        }

        return new DirectoryStoragePointer(cleanedUp);
    }
}
