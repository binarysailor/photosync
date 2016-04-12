package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;

import javax.annotation.Nullable;

/**
 * Created by binarysailor on 11/04/2016.
 */
public class IndexingCommand {
    private @Nullable Directory startingPoint;
    private @Nullable Integer maxBatchSize;

    @Nullable
    public Directory getStartingPoint() {
        return startingPoint;
    }

    @Nullable
    public Integer getMaxBatchSize() {
        return maxBatchSize;
    }
}
