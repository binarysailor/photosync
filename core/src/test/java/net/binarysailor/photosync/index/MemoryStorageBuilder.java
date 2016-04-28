package net.binarysailor.photosync.index;

import com.google.common.base.Splitter;

/**
 * Created by tszymanski on 25/04/2016.
 */
class MemoryStorageBuilder {
    public static MemoryStorage parse(final String tree) {

        final MemoryStorage storage = new MemoryStorage();
        Iterable<String> lines = Splitter.on('\n').omitEmptyStrings().trimResults().split(tree);
        for (final String line : lines) {

            final String targetDirectoryLocation;
            final MemoryPhoto photos;

            final int lastSlashIndex = line.lastIndexOf('/');
            if (lastSlashIndex < 0) {
                // add photos to root
                targetDirectoryLocation = null;
            } else {
                targetDirectoryLocation = line.substring(0, lastSlashIndex);
            }

            final Iterable<String> photoSpecs = Splitter.on(',').split(line.substring(lastSlashIndex + 1));
            for (final String photoSpec : photoSpecs) {
                storage.addPhoto(targetDirectoryLocation, createPhoto(photoSpec));
            }
        }

        return storage;
    }

    private static MemoryPhoto createPhoto(final String photoSpec) {
        final MemoryPhoto photo = new MemoryPhoto();
        photo.setName(photoSpec);

        return photo;
    }
}
