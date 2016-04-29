package net.binarysailor.photosync.index;

import net.binarysailor.photosync.*;
import net.binarysailor.photosync.index.fsstored.IndexException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by binarysailor on 29/04/2016.
 */
public class MemoryIndex implements Index {

    private final Map<String, FileStoragePointer> data = new HashMap<>();

    @Override
    public void storeHash(final Photo photo, final String hash) throws IndexException {
        final FileStoragePointer fileStoragePointer = Locations.getFileStoragePointer(photo);
        data.put(hash, fileStoragePointer);
    }

    @Override
    public void removeHash(final String hash) throws IndexException {
        data.remove(hash);
    }

    @Override
    public FileStoragePointer findPhotoByHash(final String hash) throws IndexException {
        return data.get(hash);
    }

    public int getSize() {
        return data.size();
    }
}
