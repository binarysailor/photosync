package net.binarysailor.photosync;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class Photo {
    private final Storage storage;
    private final File ioFile;
    private final String relativePath;

    public Photo(final Storage storage, final File file) {
        this.storage = storage;
        this.ioFile = file;
        relativePath = buildRelativePath();
    }

    private String buildRelativePath() {
        final Path rootPath = Paths.get(storage.getRoot().getFileSystemPath());
        final Path photoPath = Paths.get(ioFile.getAbsolutePath());
        final Path relativePath = rootPath.relativize(photoPath);
        return relativePath.toString();
    }

    public String getAbsolutePath() {
        return ioFile.getPath();
    }

    public String getStorageRelativePath() {
        return relativePath;
    }
}
