package net.binarysailor.photosync;

/**
 * Created by binarysailor on 11/04/2016.
 */
public interface Storage {

    Directory getRoot();

    Photo findPhoto(String location);
}
