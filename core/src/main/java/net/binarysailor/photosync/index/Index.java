package net.binarysailor.photosync.index;

import net.binarysailor.photosync.FileStoragePointer;
import net.binarysailor.photosync.Photo;
import net.binarysailor.photosync.index.fsstored.IndexException;

/**
 * Created by binarysailor on 11/04/2016.
 */
public interface Index {
    void storeHash(Photo photo, String hash) throws IndexException;
    void removeHash(String hash) throws IndexException;
    FileStoragePointer findPhotoByHash(String hash) throws IndexException;
}
