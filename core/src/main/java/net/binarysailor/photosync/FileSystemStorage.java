package net.binarysailor.photosync;

import java.io.File;

/**
 * Created by binarysailor on 11/04/2016.
 */
class FileSystemStorage implements Storage {

    private final FileSystemDirectory root;

    public FileSystemStorage(final String rootDirectoryPath) throws DirectoryNotFoundException {
        this.root = new FileSystemDirectory(this, rootDirectoryPath);
    }

    @Override
    public Directory getRoot() {
        return root;
    }

    public String getRootPath() {
        return root.getFileSystemPath();
    }

    @Override
    public Photo findPhoto(final String location) {
        final File file = new File(getRootPath() + File.pathSeparator + location);
        if (IsPhotoFilter.isPhoto(file)) {
            return new FileSystemPhoto(this, file);
        } else {
            return null;
        }
    }
}
