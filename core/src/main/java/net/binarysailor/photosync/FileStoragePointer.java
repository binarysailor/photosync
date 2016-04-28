package net.binarysailor.photosync;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class FileStoragePointer {

    private final DirectoryStoragePointer directoryPointer;
    private final String fileName;

    public FileStoragePointer(final DirectoryStoragePointer directoryPointer, final String fileName) {
        this.directoryPointer = directoryPointer;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return String.valueOf(directoryPointer) + Locations.SEPARATOR + String.valueOf(fileName);
    }

    public DirectoryStoragePointer getDirectoryPointer() {
        return directoryPointer;
    }

    public String getFileName() {
        return fileName;
    }
}
