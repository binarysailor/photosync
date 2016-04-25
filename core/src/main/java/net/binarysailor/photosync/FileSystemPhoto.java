package net.binarysailor.photosync;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by binarysailor on 11/04/2016.
 */
class FileSystemPhoto implements Photo {
    final FileSystemStorage storage;
    private final File ioFile;

    public FileSystemPhoto(final FileSystemStorage storage, final File file) {
        this.storage = storage;
        this.ioFile = file;
    }

    @Override
    public String getLocation() {
        final Path rootPath = Paths.get(storage.getRootPath());
        final Path photoPath = Paths.get(ioFile.getAbsolutePath());
        final Path relativePath = rootPath.relativize(photoPath);
        return relativePath.toString();
    }

    @Override
    public long getSize() {
        return ioFile.length();
    }
}
