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

    public FileExtensionMap() {
        extMap = new HashMap<FileTypeE, String>();

        extMap.put(FileTypeE.RGB_RAW, ".jpg");
        extMap.put(FileTypeE.RGB_ORTHOMOSAIC, ".jpg");
    }

    public String getExtension(FileTypeE type) {
        return extMap.get(type);
    }

}
