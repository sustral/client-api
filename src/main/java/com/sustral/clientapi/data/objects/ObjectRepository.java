package com.sustral.clientapi.data.objects;

import java.io.InputStream;

/**
 * Defines contract that allows callers to upload and download objects from remote and local object stores (eg. S3, MinIO).
 *
 * @author Dilanka Dharmasena
 */
public interface ObjectRepository {
    /**
     * Gets a locally or remotely stored object.
     *
     * Caller must ensure that the returned InputStream is closed as soon as possible.
     *
     * @param key   a string identifier of the requested object
     * @return      the object in InputStream form which may be null if not found or an error occurred
     */
    InputStream download(String key);

    /**
     * Stores an object locally or remotely.
     *
     * This function will close the passed in InputStream.
     *
     * @param key       a string identifier of the passed in object
     * @param object    an object passed in by way of InputStream
     * @return          returns 0 if successful, a negative number otherwise (implementation specific)
     */
    int upload(String key, InputStream object);
}
