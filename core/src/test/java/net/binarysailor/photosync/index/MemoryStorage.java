package net.binarysailor.photosync.index;

import com.google.common.base.Splitter;
import net.binarysailor.photosync.*;

/**
 * Created by binarysailor on 25/04/2016.
 */
class MemoryStorage implements Storage {

    private final MemoryDirectory root = new MemoryDirectory(null, "");

    @Override
    public Directory getRoot() {
        return root;
    }

    @Override
    public Photo findPhoto(final FileStoragePointer location) {
        Directory currentDirectory = root;

        for (final String directoryName : location.getDirectoryPointer().getDirectoryNames()) {
            currentDirectory = findSubdirectory(currentDirectory, directoryName);
            if (currentDirectory == null) {
                return null;
            }
        }

        return findPhotoInDirectory(currentDirectory, location.getFileName());
    }

    public MemoryDirectory getOrCreateDirectory(final String location) {
        final Iterable<String> directoryNames = Splitter.on('/').omitEmptyStrings().split(location);

        MemoryDirectory currentDirectory = root;
        for (final String directoryName : directoryNames) {
            Directory subdirectory = findSubdirectory(currentDirectory, directoryName);
            if (subdirectory == null) {
                subdirectory = currentDirectory.addSubdirectory(directoryName);
            }
            currentDirectory = (MemoryDirectory) subdirectory;
        }

        return currentDirectory;
    }

    public void addPhoto(final String location, final MemoryPhoto photo) {
        final MemoryDirectory directory = getOrCreateDirectory(location);
        directory.addPhoto(photo);
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
            if (photo.getName().equals(name)) {
                return photo;
            }
        }

        return null;
    }

}
