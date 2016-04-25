package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by binarysailor on 25/04/2016.
 */
class MemoryDirectory implements Directory {

    private final String name;
    private final List<MemoryDirectory> subdirectories = new LinkedList<>();
    private final List<MemoryPhoto> photos = new LinkedList<>();

    MemoryDirectory(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addSubdirectory(final MemoryDirectory subdirectory) {
        subdirectories.add(subdirectory);
    }

    @Override
    public Directory[] getSubdirectories() {
        return subdirectories.toArray(new Directory[0]);
    }

    public void addPhoto(final MemoryPhoto photo) {
        photos.add(photo);
    }

    @Override
    public Photo[] getPhotos() {
        return photos.toArray(new Photo[0]);
    }
}
