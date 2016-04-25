package net.binarysailor.photosync;

/**
 * Created by binarysailor on 11/04/2016.
 */
public interface Directory {

    String getName();

    Directory[] getSubdirectories();

    Photo[] getPhotos();

}
