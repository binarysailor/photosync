package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Photo;

/**
 * Created by binarysailor on 25/04/2016.
 */
public interface HashCalculator {
    String calculateHash(Photo photo) throws HashingException;
}
