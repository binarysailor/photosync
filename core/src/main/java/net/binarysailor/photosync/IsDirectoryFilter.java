package net.binarysailor.photosync;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class IsDirectoryFilter implements FileFilter {

    public static boolean isExistingDirectory(final File file) {
        return file.exists() && file.isDirectory();
    }

    public static void assertIsExistingDirectory(final File file) throws DirectoryNotFoundException {
        if (!IsDirectoryFilter.isExistingDirectory(file)) {
            throw new DirectoryNotFoundException(file.getAbsolutePath());
        }
    }

    @Override
    public boolean accept(final File file) {
        return IsDirectoryFilter.isExistingDirectory(file);
    }
}
