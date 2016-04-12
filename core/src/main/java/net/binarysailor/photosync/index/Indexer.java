package net.binarysailor.photosync.index;

import com.google.common.base.MoreObjects;
import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;
import net.binarysailor.photosync.Storage;
import net.binarysailor.photosync.index.fsstored.IndexException;

import javax.annotation.Nonnull;

/**
 * Created by binarysailor on 11/04/2016.
 */
class Indexer implements Runnable {
    private final Storage storage;
    private final Index index;
    private final IndexingCommand command;

    public Indexer(final Storage storage, final Index index, final IndexingCommand command) {
        this.storage = storage;
        this.index = index;
        this.command = command;
    }

    @Override
    public void run() {

        @Nonnull Directory directory = MoreObjects.firstNonNull(command.getStartingPoint(), storage.getRoot());
        final int maxBatchSize = MoreObjects.firstNonNull(command.getMaxBatchSize(), 100 * 1000);

        int count = 0;
        while (count < maxBatchSize && directory != null) {
            final Photo[] photos = directory.getPhotos();
            for (final Photo photo : photos) {
                String hash = hash(photo);
                try {
                    index.storeHash(photo, hash);
                } catch (final IndexException e) {
                    e.printStackTrace(); // TODO
                }

                count++;
                if (count >= maxBatchSize) {
                    break;
                }
            }

            directory = null; // TODO
        }
    }

    private String hash(final Photo photo) {
        return String.valueOf(photo.getDiskSize());
    }
}
