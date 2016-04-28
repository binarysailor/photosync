package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;

/**
 * Created by binarysailor on 25/04/2016.
 */
class MemoryPhoto implements Photo {

    private Directory directory;
    private String name;
    private long size;

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public long getSize() {
        return size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    @Override
    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(final Directory directory) {
        this.directory = directory;
    }
}
