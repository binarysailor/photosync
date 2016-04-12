package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Storage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by binarysailor on 12/04/2016.
 */
public class IndexingService {
    private final ScheduledExecutorService executorService;
    private final Storage storage;
    private final Index index;

    public IndexingService(final Storage storage, final Index index) {
        this.storage = storage;
        this.index = index;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(final IndexingSettings settings) {
        final IndexingCommand command = createScheduledIndexingCommand(settings);
        executorService.scheduleAtFixedRate(new Indexer(storage, index, command), 1, settings.getIntervalSeconds(), TimeUnit.SECONDS);
    }

    private IndexingCommand createScheduledIndexingCommand(final IndexingSettings settings) {
        return new IndexingCommand();
    }

    public void stop() {
        executorService.shutdown();
    }
}
