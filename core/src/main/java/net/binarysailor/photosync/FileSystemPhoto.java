package net.binarysailor.photosync;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by binarysailor on 11/04/2016.
 */
class FileSystemPhoto implements Photo {
    private final FileSystemStorage storage;
    private final FileSystemDirectory directory;
    private final File ioFile;

    public FileSystemPhoto(final FileSystemStorage storage, final FileSystemDirectory directory, final File file) {
        this.storage = storage;
        this.directory = directory;
        this.ioFile = file;
    }

    public String getLocation() {
        final Path rootPath = Paths.get(storage.getRootPath());
        final Path photoPath = Paths.get(ioFile.getAbsolutePath());
        final Path relativePath = rootPath.relativize(photoPath);
        return relativePath.toString();
    }

    @Override
    public String getName() {
        return ioFile.getName();
    }

    @Override
    public long getSize() {
        return ioFile.length();
    }

    @Override
    public FileSystemDirectory getDirectory() {
        return directory;
    }

    @Override
    public InputStream getContent() {
        try {
            return new FileInputStream(ioFile);
        } catch (FileNotFoundException e) {
            return new ByteArrayInputStream(new byte[0]);
        }
    }
}
