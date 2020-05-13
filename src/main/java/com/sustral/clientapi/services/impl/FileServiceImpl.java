package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.models.FileEntityPK;
import com.sustral.clientapi.data.repositories.FileRepository;
import com.sustral.clientapi.data.types.FileTypeE;
import com.sustral.clientapi.data.utils.CustomUUIDGenerator;
import com.sustral.clientapi.data.utils.FileExtensionMap;
import com.sustral.clientapi.services.FileService;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * An implementation of FileService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CustomUUIDGenerator uuidGenerator;

    @Autowired
    private FileExtensionMap fileExtensionMap;

    @Override
    public ServiceReturn<FileEntity> getOneById(String fieldId, String scanId, String fileId) {
        FileEntityPK id = new FileEntityPK();
        id.setFieldId(fieldId);
        id.setScanId(scanId);
        id.setId(fileId);

        Optional<FileEntity> file = fileRepository.findById(id);

        if (file.isPresent()) {
            return new ServiceReturn<FileEntity>(false, null, null, file.get());
        }

        String errorMessage = "The requested file could not be found";
        return new ServiceReturn<FileEntity>(true, errorMessage, errorMessage, null);
    }

    @Override
    public ServiceReturn<List<FileEntity>> getManyByScanId(String fieldId, String scanId) {
        List<FileEntity> files = fileRepository.findAllByFieldIdAndScanId(fieldId, scanId);

        return new ServiceReturn<List<FileEntity>>(false, null, null, files);
    }

    @Override
    public ServiceReturn<FileEntity> create(String fieldId, String scanId, FileTypeE type) {
        FileEntity file = new FileEntity();
        file.setFieldId(fieldId);
        file.setScanId(scanId);

        String uuid = uuidGenerator.generateUUID();
        file.setId(uuid);

        file.setFileType(type);

        FileEntity updatedFile = fileRepository.save(file);
        return new ServiceReturn<FileEntity>(false, null, null, updatedFile);
    }

    @Override
    public ServiceReturn<String> getObjectId(FileEntity file) {

        String id = file.getFieldId() + "/" +
                file.getScanId() + "/" +
                file.getId() +
                fileExtensionMap.getExtension(file.getFileType());

        return new ServiceReturn<String>(false, null, null, id);
    }

}
