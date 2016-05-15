package net.binarysailor.photosync;

import java.util.LinkedList;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class Locations {

    public static DirectoryStoragePointer getDirectoryStoragePointer(final Storage storage, final Directory directory) {
        final LinkedList<String> directoryNames = new LinkedList<>();

        Directory currentDirectory = directory;
        while (!currentDirectory.equals(storage.getRoot())) {
            directoryNames.addFirst(currentDirectory.getName());
            currentDirectory = currentDirectory.getParent();
        }

        return new DirectoryStoragePointer(directoryNames.toArray(new String[0]));
    }

    public static FileStoragePointer getFileStoragePointer(final Storage storage, final Photo photo) {
        return new FileStoragePointer(
                Locations.getDirectoryStoragePointer(storage, photo.getDirectory()),
                photo.getName());
    }
}
