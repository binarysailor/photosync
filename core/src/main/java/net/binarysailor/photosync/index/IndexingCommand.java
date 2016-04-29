package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class IndexingCommand {
    private @Nullable Directory startingPoint;
    private @Nullable Integer maxBatchSize;

    public IndexingCommand() {
        this.startingPoint = null;
    }

    public IndexingCommand(final @Nonnull Directory startingPoint) {
        this.startingPoint = startingPoint;
    }

    @Nullable
    public Directory getStartingPoint() {
        return startingPoint;
    }

    @Nullable
    public Integer getMaxBatchSize() {
        return maxBatchSize;
    }

    public void setMaxBatchSize(@Nullable final Integer maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
    }
}
