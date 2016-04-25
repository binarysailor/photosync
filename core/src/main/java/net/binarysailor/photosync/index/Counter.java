package net.binarysailor.photosync.index;

/**
 * Created by binarysailor on 25/04/2016.
 */
class Counter {
    private int count;
    private final int maximum;

    Counter(final int maximum) {
        this.maximum = maximum;
    }

    void increment() {
        count++;
    }

    boolean shouldContinue() {
        return count < maximum;
    }
}
