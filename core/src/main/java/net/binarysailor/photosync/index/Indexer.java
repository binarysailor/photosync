package net.binarysailor.photosync.index;

import com.google.common.base.MoreObjects;
import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;
import net.binarysailor.photosync.Storage;
import net.binarysailor.photosync.index.fsstored.IndexException;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by binarysailor on 11/04/2016.
 */
class Indexer implements Runnable {
    private final Storage storage;
    private final Index index;
    private final HashCalculator hashCalculator;
    private final IndexingCommand command;

    public Indexer(final Storage storage, final Index index, final HashCalculator hashCalculator, final IndexingCommand command) {
        this.storage = storage;
        this.index = index;
        this.hashCalculator = hashCalculator;
        this.command = command;
    }

    @Override
    public void run() {

        final @Nonnull Counter counter = createCounter();
        final @Nonnull Directory startingDirectory = createStartingDirectory();

        final Queue<Directory> dirQueue = new LinkedList<>();
        dirQueue.add(startingDirectory);

        // Breadth First Search through the directory tree

        while (counter.shouldContinue() && !dirQueue.isEmpty()) {
            final Directory currentDirectory = dirQueue.poll();

            enqueueSubdirectories(dirQueue, currentDirectory);

            hashPhotos(currentDirectory, counter);
        }
    }

    private Counter createCounter() {
        final int maxBatchSize = MoreObjects.firstNonNull(command.getMaxBatchSize(), 100 * 1000);
        return new Counter(maxBatchSize);
    }

    private Directory createStartingDirectory() {
        return MoreObjects.firstNonNull(command.getStartingPoint(), storage.getRoot());
    }

    private void enqueueSubdirectories(final Queue<Directory> dirQueue, final Directory currentDirectory) {
        for (final Directory child : currentDirectory.getSubdirectories()) {
            dirQueue.add(child);
        }
    }

    private void hashPhotos(final Directory currentDirectory, final Counter counter) {
        final Photo[] photos = currentDirectory.getPhotos();
        for (final Photo photo : photos) {
            try {
                final String hash = hashCalculator.calculateHash(photo);
                if (index.findPhotoByHash(hash) != null) {
                    continue;
                }
                index.storeHash(photo, hash);
            } catch (final IndexException e) {
                LogFactory.getLog(getClass()).warn(e, e);
            } catch (final HashingException e) {
                LogFactory.getLog(getClass()).warn(e, e);
            }

            counter.increment();

            if (!counter.shouldContinue()) {
                break;
            }
        }
    }
}
