package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;
import net.binarysailor.photosync.Storage;

/**
 * Created by binarysailor on 25/04/2016.
 */
class MemoryStorage implements Storage {

    private final MemoryDirectory root = new MemoryDirectory("");

    @Override
    public Directory getRoot() {
        return root;
    }

    @Override
    public Photo findPhoto(final String location) {
        Directory currentDirectory = root;

        final String[] pathParts = location.split("/");
        if (pathParts.length > 1) {
            for (int i = 0; i < pathParts.length - 1; i++) {
                currentDirectory = findSubdirectory(currentDirectory, pathParts[i]);
                if (currentDirectory == null) {
                    return null;
                }
            }
        }

        return findPhotoInDirectory(currentDirectory, pathParts[pathParts.length - 1]);
    }

    private Directory findSubdirectory(final Directory directory, final String subdirectoryName) {
        for (final Directory subdirectory : directory.getSubdirectories()) {
            if (subdirectory.getName().equals(subdirectoryName)) {
                return subdirectory;
            }
        }

        return null;
    }

    private Photo findPhotoInDirectory(final Directory directory, final String name) {
        for (final Photo photo : directory.getPhotos()) {
            if (photo.getLocation().endsWith(name)) {
                return photo;
            }
        }

        return null;
    }

}
