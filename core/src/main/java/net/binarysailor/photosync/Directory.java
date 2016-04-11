package net.binarysailor.photosync;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class Directory {

    private static final FileFilter IS_DIR_FILTER = new IsDirectoryFilter();
    private static final FileFilter IS_PHOTO_FILTER = new IsPhotoFilter();

    private final @Nonnull Storage storage;
    private final @Nullable Directory parent;
    private final @Nonnull File ioDirectory;

    Directory(final @Nonnull Storage storage, final @Nonnull String path) throws DirectoryNotFoundException {
        this.storage = storage;
        this.parent = null;
        this.ioDirectory = new File(path);

        assertDirectoryExists();
    }

    Directory(final @Nonnull Storage storage, final @Nonnull Directory parent, final @Nonnull String shortName) throws DirectoryNotFoundException {
        this.storage = storage;
        this.parent = parent;

        final String path = parent.getFileSystemPath() + File.pathSeparator + shortName;
        ioDirectory = new File(path);

        assertDirectoryExists();
    }

    private void assertDirectoryExists() throws DirectoryNotFoundException {
        IsDirectoryFilter.assertIsExistingDirectory(ioDirectory);
    }

    public Directory[] getSubdirectories() {
        final File[] files = ioDirectory.listFiles(IS_DIR_FILTER);
        return toDirectories(files);
    }

    public Photo[] getPhotos() {
        final File[] files = ioDirectory.listFiles(IS_PHOTO_FILTER);
        return toPhotos(files);
    }

    private Directory[] toDirectories(final @Nonnull File[] files) {
        final Collection<Directory> resultCollection = new ArrayList<>(files.length);

        for (final File file : files) {
            try {
                resultCollection.add(new Directory(storage, this, file.getName()));
            } catch (final DirectoryNotFoundException ex) {
                Logger.getLogger(getClass().getName()).warning("Directory construction error: " + file.getAbsolutePath());
            }
        }

        return resultCollection.toArray(new Directory[0]);
    }

    private Photo[] toPhotos(final @Nonnull File[] files) {
        final Collection<Photo> resultCollection = new ArrayList<>(files.length);

        for (final File file : files) {
            resultCollection.add(new Photo(storage, file));
        }

        return resultCollection.toArray(new Photo[0]);
    }

    String getFileSystemPath() {
        return ioDirectory.getAbsolutePath();
    }
}
