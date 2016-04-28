package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Storage;

/**
 * Created by binarysailor on 25/04/2016.
 */
public class IndexerTest {
    public void shouldIndex() {

        final Storage storage = MemoryStorageBuilder.parse(
                            "/a/photo_a1,photo_a2,photo_a3\n" +
                            "/b/photo_b1,photo_b2\n" +
                            "/a/c/photo_c1,photo_c2,photo_c3");

        /*
        final Index index = new

        final Indexer indexer = new Indexer(storage, index, hashCalculator, command);
        */
    }
}
