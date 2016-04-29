package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Photo;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

/**
 * Created by binarysailor on 29/04/2016.
 */
public class SHA1HashCalculator implements HashCalculator {
    @Override
    public String calculateHash(final Photo photo) throws HashingException {
        try {
            return DigestUtils.sha1Hex(photo.getContent());
        } catch (final IOException e) {
            throw new HashingException(e);
        }
    }
}
