package net.binarysailor.photosync;

import java.util.LinkedList;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class Locations {

    public static DirectoryStoragePointer getDirectoryStoragePointer(final Directory directory) {
        final LinkedList<String> directoryNames = new LinkedList<>();
        Directory currentDirectory = directory;
        do {
            directoryNames.addFirst(currentDirectory.getName());
            currentDirectory = currentDirectory.getParent();
        } while (currentDirectory != null);

        return new DirectoryStoragePointer(directoryNames.toArray(new String[0]));
    }

    public static FileStoragePointer getFileStoragePointer(final Photo photo) {
        return new FileStoragePointer(getDirectoryStoragePointer(photo.getDirectory()), photo.getName());
    }
}
