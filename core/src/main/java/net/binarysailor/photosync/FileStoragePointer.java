package net.binarysailor.photosync;

import java.io.Serializable;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class FileStoragePointer implements Serializable {

    private final DirectoryStoragePointer directoryPointer;
    private final String fileName;

    public FileStoragePointer(final DirectoryStoragePointer directoryPointer, final String fileName) {
        this.directoryPointer = directoryPointer;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return String.valueOf(directoryPointer) + DirectoryStoragePointer.SEPARATOR + String.valueOf(fileName);
    }

    public DirectoryStoragePointer getDirectoryPointer() {
        return directoryPointer;
    }

    public String getFileName() {
        return fileName;
    }
}
