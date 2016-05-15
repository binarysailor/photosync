package net.binarysailor.photosync;

import com.google.common.base.Splitter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

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

    public FileStoragePointer(final Storage storage, final @Nonnull String relativePath) {
        final List<String> parts =
                Splitter.on(DirectoryStoragePointer.SEPARATOR)
                .omitEmptyStrings()
                .splitToList(relativePath.substring(1));

        if (parts.size() > 1) {
            directoryPointer = new DirectoryStoragePointer(parts.subList(0, parts.size() - 1).toArray(new String[0]));
        } else {
            directoryPointer = DirectoryStoragePointer.createRootPointer();
        }

        this.fileName = parts.get(parts.size() - 1);
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
