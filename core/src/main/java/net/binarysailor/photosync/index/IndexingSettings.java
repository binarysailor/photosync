package net.binarysailor.photosync.index;

/**
 * Created by binarysailor on 12/04/2016.
 */
public class IndexingSettings {
    private int intervalSeconds = 300;

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }
}
