package net.binarysailor.photosync;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by binarysailor on 28/04/2016.
 */
public class DirectoryStoragePointer {
    private final @Nullable String path;

    public DirectoryStoragePointer(final String path) {
        this.path = path;
    }

    public @Nonnull String[] getDirectoryNames() {
        if (path == null) {
            return new String[0];
        }

        return Iterables.toArray(Splitter.on(Locations.SEPARATOR).omitEmptyStrings().split(path), String.class);
    }
}
