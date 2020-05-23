package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.models.FileEntityPK;
import com.sustral.clientapi.data.repositories.FileRepository;
import com.sustral.clientapi.data.types.FileTypeE;
import com.sustral.clientapi.data.utils.fileextensionmap.FileExtensionMap;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.FileService;
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
    private IdGenerator idGenerator;

    @Autowired
    private FileExtensionMap fileExtensionMap;

    @Override
    public FileEntity getOneById(String fieldId, String scanId, String fileId) {
        FileEntityPK id = new FileEntityPK();
        id.setFieldId(fieldId);
        id.setScanId(scanId);
        id.setId(fileId);

        Optional<FileEntity> file = fileRepository.findById(id);

        return file.orElse(null);
    }

    @Override
    public List<FileEntity> getManyByScanId(String fieldId, String scanId) {
        return fileRepository.findAllByFieldIdAndScanId(fieldId, scanId); // Guaranteed by JPARepository to not be null, but may be empty
    }

    @Override
    public FileEntity create(String fieldId, String scanId, FileTypeE type) {
        FileEntity file = new FileEntity();
        file.setFieldId(fieldId);
        file.setScanId(scanId);

        String newId = idGenerator.generateId();
        file.setId(newId);

        file.setFileType(type);

        return fileRepository.save(file); // Guaranteed to never be null
    }

    @Override
    public String getObjectId(FileEntity file) {
        return file.getFieldId() + "/" +
                file.getScanId() + "/" +
                file.getId() +
                fileExtensionMap.getExtension(file.getFileType());
    }

}
