package net.binarysailor.photosync;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by binarysailor on 11/04/2016.
 */
class FileSystemDirectory implements Directory {

    private static final FileFilter IS_DIR_FILTER = new IsDirectoryFilter();
    private static final FileFilter IS_PHOTO_FILTER = new IsPhotoFilter();

    private final FileSystemStorage storage;
    private final FileSystemDirectory parent;
    private final @Nonnull File ioDirectory;

    public static FileSystemDirectory createRoot(final FileSystemStorage storage, final String absoluteDiskPath) throws DirectoryNotFoundException {
        return new FileSystemDirectory(storage, absoluteDiskPath);
    }

    private FileSystemDirectory(final FileSystemStorage storage, final @Nonnull String path) throws DirectoryNotFoundException {
        this.storage = storage;
        this.parent = null;
        this.ioDirectory = new File(path);

        assertDirectoryExists();
    }

    FileSystemDirectory(final FileSystemStorage storage, final @Nonnull FileSystemDirectory parent, final @Nonnull String shortName) throws DirectoryNotFoundException {
        this.storage = storage;
        this.parent = parent;

        final String path = parent.getFileSystemPath() + File.separator + shortName;
        ioDirectory = new File(path);

        assertDirectoryExists();
    }

    private void assertDirectoryExists() throws DirectoryNotFoundException {
        IsDirectoryFilter.assertIsExistingDirectory(ioDirectory);
    }

    @Override
    public FileSystemDirectory[] getSubdirectories() {
        final File[] files = ioDirectory.listFiles(IS_DIR_FILTER);
        return toDirectories(files);
    }

    @Override
    public Photo[] getPhotos() {
        final File[] files = ioDirectory.listFiles(IS_PHOTO_FILTER);
        return toPhotos(files);
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    private FileSystemDirectory[] toDirectories(final @Nonnull File[] files) {
        final Collection<FileSystemDirectory> resultCollection = new ArrayList<>(files.length);

        for (final File file : files) {
            try {
                resultCollection.add(new FileSystemDirectory(storage, this, file.getName()));
            } catch (final DirectoryNotFoundException ex) {
                Logger.getLogger(getClass().getName()).warning("Directory construction error: " + file.getAbsolutePath());
            }
        }

        return resultCollection.toArray(new FileSystemDirectory[0]);
    }

    private Photo[] toPhotos(final @Nonnull File[] files) {
        final Collection<Photo> resultCollection = new ArrayList<>(files.length);

        for (final File file : files) {
            resultCollection.add(new FileSystemPhoto(storage, this, file));
        }

        return resultCollection.toArray(new Photo[0]);
    }

    @Override
    public String getName() {
        return ioDirectory.getName();
    }

    String getFileSystemPath() {
        return ioDirectory.getAbsolutePath();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof FileSystemDirectory)) {
            return false;
        }

        return this.ioDirectory.equals(((FileSystemDirectory)obj).ioDirectory);
    }

    @Override
    public int hashCode() {
        return ioDirectory.hashCode();
    }
}
