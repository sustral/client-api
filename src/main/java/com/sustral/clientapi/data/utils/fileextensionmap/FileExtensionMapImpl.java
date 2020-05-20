package com.sustral.clientapi.data.utils.fileextensionmap;

import com.sustral.clientapi.data.types.FileTypeE;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

/**
 * This class is an implementation of FileExtensionMap.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class FileExtensionMapImpl implements FileExtensionMap {

    private final EnumMap<FileTypeE, String> extMap;

    /**
     * Initializes the internal hashmap with the proper values.
     */
    public FileExtensionMapImpl() {
        extMap = new EnumMap<>(FileTypeE.class);

        extMap.put(FileTypeE.RGB_RAW, ".jpg");          // These definitions will never change.
        extMap.put(FileTypeE.RGB_ORTHOMOSAIC, ".jpg");  // Additional enums will be added.
    }

    @Override
    public String getExtension(FileTypeE fileType) {
        return extMap.get(fileType);
    }

}
