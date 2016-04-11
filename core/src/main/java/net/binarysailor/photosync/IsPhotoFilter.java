package net.binarysailor.photosync;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class IsPhotoFilter implements FileFilter {

    private static final Set<String> EXTENSIONS = new HashSet<>();

    static {
        EXTENSIONS.add("jpeg");
        EXTENSIONS.add("JPEG");
    }

    public static boolean isPhoto(final File file) {
        if (file.exists()) {
            final String fileExtension = Files.getFileExtension(file.getName());
            return EXTENSIONS.contains(fileExtension);
        } else {
            return false;
        }
    }

    @Override
    public boolean accept(final File file) {
        return IsPhotoFilter.isPhoto(file);
    }
}
