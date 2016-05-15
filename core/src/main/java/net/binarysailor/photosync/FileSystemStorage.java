package net.binarysailor.photosync;

import com.google.common.base.Joiner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by binarysailor on 11/04/2016.
 */
class FileSystemStorage implements Storage {

    private final FileSystemDirectory root;

    public FileSystemStorage(final String rootDirectoryPath) throws DirectoryNotFoundException {
        this.root = FileSystemDirectory.createRoot(this, rootDirectoryPath);
    }

    @Override
    public Directory getRoot() {
        return root;
    }

    public String getRootPath() {
        return root.getFileSystemPath();
    }

    @Override
    public Photo findPhoto(final FileStoragePointer pointer) {
        final File file = new File(
                getRootPath()
                        + File.separator
                        + toFileSystemDirectoryPath(pointer.getDirectoryPointer())
                        + pointer.getFileName());
        if (IsPhotoFilter.isPhoto(file)) {
            try {
                final FileSystemDirectory directory = buildFileSystemDirectory(pointer.getDirectoryPointer());
                return new FileSystemPhoto(this, directory, file);
            } catch (final DirectoryNotFoundException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    private String toFileSystemDirectoryPath(final DirectoryStoragePointer directoryPointer) {
        return Joiner.on(File.separatorChar).join(directoryPointer.getDirectoryNames());
    }

    private FileSystemDirectory buildFileSystemDirectory(final DirectoryStoragePointer pointer) throws DirectoryNotFoundException {
        FileSystemDirectory directory = root;
        for (final String directoryName : pointer.getDirectoryNames()) {
            directory = new FileSystemDirectory(this, directory, directoryName);
        }

        return directory;
    }
}
