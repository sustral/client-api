package com.sustral.clientapi.data.utils.fileextensionmap;

import com.sustral.clientapi.data.types.FileTypeE;

/**
 * This interface defines a method that maps a FileType to its extension.
 *
 * @author Dilanka Dharmasena
 */
public interface FileExtensionMap {

    /**
     * This method takes a FileTypeE and returns an associated file extension.
     *
     * @param fileType  the FileTypeE of the file in question
     * @return          the file extension as a String with the leading period
     */
    String getExtension(FileTypeE fileType);

}
