package net.binarysailor.photosync;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class DirectoryStoragePointer implements Serializable {
    static final char SEPARATOR = '/';

    private final @Nonnull String[] directoryNames;

    public static DirectoryStoragePointer createRootPointer() {
        return new DirectoryStoragePointer(new String[0]);
    }

    public DirectoryStoragePointer(@Nullable final String path) {
        final String cleanedUp;

        if (path != null) {
            final StringBuilder s = new StringBuilder(path);
            while (s.length() > 0 && s.charAt(0) == SEPARATOR) {
                s.deleteCharAt(0);
            }
            if (s.length() == 0) {
                directoryNames = new String[0];
            } else {
                directoryNames = Iterables.toArray(
                        Splitter.on(SEPARATOR).omitEmptyStrings().split(s.toString()),
                        String.class);
            }
        } else {
            directoryNames = new String[0];
        }
    }

    public DirectoryStoragePointer(@Nonnull final String[] directoryNames) {
        this.directoryNames = directoryNames;
    }

    public @Nonnull String[] getDirectoryNames() {
        return directoryNames;
    }

    public String toString() {
        return Joiner.on(SEPARATOR).join(directoryNames);
    }
}
