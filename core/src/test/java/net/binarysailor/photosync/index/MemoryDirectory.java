package net.binarysailor.photosync.index;

import net.binarysailor.photosync.Directory;
import net.binarysailor.photosync.Photo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by binarysailor on 25/04/2016.
 */
class MemoryDirectory implements Directory {

    private final Directory parent;
    private final String name;
    private final List<MemoryDirectory> subdirectories = new LinkedList<>();
    private final List<MemoryPhoto> photos = new LinkedList<>();

    MemoryDirectory(final MemoryDirectory parent, final String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    public MemoryDirectory addSubdirectory(final String subdirectoryName) {
        final MemoryDirectory subdirectory = new MemoryDirectory(this, subdirectoryName);
        subdirectories.add(subdirectory);
        return subdirectory;
    }

    @Override
    public Directory[] getSubdirectories() {
        return subdirectories.toArray(new Directory[0]);
    }

    public void addPhoto(final MemoryPhoto photo) {
        photos.add(photo);
        photo.setDirectory(this);
    }

    @Override
    public Photo[] getPhotos() {
        return photos.toArray(new Photo[0]);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof MemoryDirectory)) {
            return false;
        }

        final MemoryDirectory other = (MemoryDirectory)obj;
        if (this.parent == null) {
            return other.parent == null && this.name.equals(other.name);
        } else {
            return this.parent.equals(other.parent) && this.name.equals(other.name);
        }
    }

    @Override
    public int hashCode() {
        if (parent == null) {
            return name.hashCode();
        } else {
            return 7 * parent.hashCode() + 11 * name.hashCode();
        }
    }
}
