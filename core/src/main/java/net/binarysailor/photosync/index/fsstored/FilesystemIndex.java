package net.binarysailor.photosync.index.fsstored;

import com.google.common.io.Files;
import net.binarysailor.photosync.*;
import net.binarysailor.photosync.index.Index;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class FilesystemIndex implements Index {

    private final Storage storage;
    private final File rootDirectory;

    public FilesystemIndex(final Storage storage, final String rootPath) throws DirectoryNotFoundException {
        this.storage = storage;
        this.rootDirectory = new File(rootPath);

        IsDirectoryFilter.assertIsExistingDirectory(rootDirectory);
    }

    @Override
    public void storeHash(final Photo photo, final String hash) throws IndexException {
        File hashFile = getHashFile(hash);
        try {
            Files.append(Locations.getFileStoragePointer(photo) + "\n", hashFile, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new IndexException(e);
        }
    }

    @Override
    public @Nullable
    FileStoragePointer findPhotoByHash(final String hash) throws IndexException {
        final FileStoragePointer pointer;

        final File hashFile = getHashFile(hash);
        if (hashFile.exists()) {
            try {
                String photoPath = Files.readFirstLine(hashFile, Charset.forName("UTF-8"));
                if (photoPath != null) {
                    pointer = null; // TODO Locations.createRelative(photoPath);
                } else {
                    pointer = null;
                }
            } catch (IOException e) {
                throw new IndexException(e);
            }
        } else {
            pointer = null;
        }

        return pointer;
    }

    @Override
    public void removeHash(final String hash) throws IndexException {
        final File hashFile = getHashFile(hash);
        if (hashFile.exists()) {
            hashFile.delete();
        }
    }

    private File getHashFile(final String hash) {
        return new File(rootDirectory, hash);
    }
}
