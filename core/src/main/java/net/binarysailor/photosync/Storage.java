package net.binarysailor.photosync;

import java.io.File;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class Storage {

    private final Directory root;

    public Storage(final String rootDirectoryPath) throws DirectoryNotFoundException {
        this.root = new Directory(this, rootDirectoryPath);
    }

    public Directory getRoot() {
        return root;
    }

    public Photo findPhoto(final String relativeFilePath) {
        final File file = new File(root.getFileSystemPath() + File.pathSeparator + relativeFilePath);
        if (IsPhotoFilter.isPhoto(file)) {
            return new Photo(this, file);
        } else {
            return null;
        }
    }
}
