package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Photo;
import net.binarysailor.photosync.Storage;
import net.binarysailor.photosync.index.fsstored.IndexException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by binarysailor on 25/04/2016.
 */
public class IndexerTest {

    private Storage hierarchicalStorage;
    private Storage singleLevelStorage;
    private Storage emptyStorage;

    private HashCalculator hashCalculator;

    @BeforeSuite
    public void setupTestSuite() {
        hierarchicalStorage = MemoryStorageBuilder.parse(
                        "/a/photo_a1,photo_a2,photo_a3\n" +
                        "/b/photo_b1,photo_b2\n" +
                        "/a/c/photo_c1,photo_c2,photo_c3");

        singleLevelStorage = MemoryStorageBuilder.parse("/photo_r1,photo_r2,photo_r3");

        emptyStorage = new MemoryStorage();

        hashCalculator = new HashCalculator() {
            @Override
            public String calculateHash(final Photo photo) {
                return "#" + photo.getName();
            }
        };
    }

    @Test
    public void shouldIndexThreeItems() throws IndexException {

        final IndexingCommand command = new IndexingCommand();
        command.setMaxBatchSize(3);

        final MemoryIndex index = new MemoryIndex(hierarchicalStorage);
        final Indexer indexer = new Indexer(hierarchicalStorage, index, hashCalculator, command);

        indexer.run();

        Assert.assertEquals(index.getSize(), 3, "Index should contain 3 entries");
        Assert.assertNotNull(index.findPhotoByHash("#photo_a1"));
        Assert.assertNull(index.findPhotoByHash("#photo_b1"));
    }

    @Test
    public void shouldIndexUpToHundredItems() throws IndexException {

        final IndexingCommand command = new IndexingCommand();
        command.setMaxBatchSize(100);

        final MemoryIndex index = new MemoryIndex(hierarchicalStorage);
        final Indexer indexer = new Indexer(hierarchicalStorage, index, hashCalculator, command);

        indexer.run();

        Assert.assertEquals(index.getSize(), 8, "Index should contain 8 entries");
        Assert.assertNotNull(index.findPhotoByHash("#photo_a1"));
        Assert.assertNotNull(index.findPhotoByHash("#photo_c3"));
    }

    @Test
    public void shouldIndexStartingAtSubdirectory() throws IndexException {

        final IndexingCommand command = new IndexingCommand(hierarchicalStorage.getRoot().getSubdirectories()[0]);
        command.setMaxBatchSize(100);

        final MemoryIndex index = new MemoryIndex(hierarchicalStorage);
        final Indexer indexer = new Indexer(hierarchicalStorage, index, hashCalculator, command);

        indexer.run();

        Assert.assertEquals(index.getSize(), 6, "Index should contain 3 entries");
        Assert.assertNotNull(index.findPhotoByHash("#photo_a1"));
        Assert.assertNull(index.findPhotoByHash("#photo_b1"));
    }

    @Test
    public void shouldIndexAtRootLevel() throws IndexException {

        final IndexingCommand command = new IndexingCommand();
        command.setMaxBatchSize(100);

        final MemoryIndex index = new MemoryIndex(singleLevelStorage);
        final Indexer indexer = new Indexer(singleLevelStorage, index, hashCalculator, command);

        indexer.run();

        Assert.assertEquals(index.getSize(), 3, "Index should contain 3 entries");
        Assert.assertNotNull(index.findPhotoByHash("#photo_r1"));
        Assert.assertNotNull(index.findPhotoByHash("#photo_r2"));
        Assert.assertNotNull(index.findPhotoByHash("#photo_r3"));
    }

    @Test
    public void shouldNotFailOnEmptyStorage() {
        final IndexingCommand command = new IndexingCommand();

        final MemoryIndex index = new MemoryIndex(emptyStorage);
        final Indexer indexer = new Indexer(emptyStorage, index, hashCalculator, command);

        indexer.run();

        Assert.assertEquals(index.getSize(), 0, "Index should contain no entries");
    }
}
