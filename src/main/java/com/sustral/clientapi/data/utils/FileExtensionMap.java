package com.sustral.clientapi.data.utils;

import com.sustral.clientapi.data.types.FileTypeE;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * This class maps file types to their extensions.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class FileExtensionMap {

    private final HashMap<FileTypeE, String> extMap;

    /**
     * Initializes the internal hashmap with the proper values.
     */
    public FileExtensionMap() {
        extMap = new HashMap<FileTypeE, String>();

        extMap.put(FileTypeE.RGB_RAW, ".jpg");
        extMap.put(FileTypeE.RGB_ORTHOMOSAIC, ".jpg");
    }

    /**
     * Gets the extension associated with a file type.
     *
     * @param type  a FileTypeE
     * @return      a string with the preceding period
     */
    public String getExtension(FileTypeE type) {
        return extMap.get(type);
    }

}
