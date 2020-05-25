package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.models.FileEntityPK;
import com.sustral.clientapi.data.repositories.FileRepository;
import com.sustral.clientapi.data.types.FileTypeE;
import com.sustral.clientapi.data.utils.fileextensionmap.FileExtensionMap;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.FileService;
import com.sustral.clientapi.utils.PaginationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private static final int PAGE_SIZE = 20;

    private final FileRepository fileRepository;
    private final IdGenerator idGenerator;
    private final FileExtensionMap fileExtensionMap;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, IdGenerator idGenerator, FileExtensionMap fileExtensionMap) {
        this.fileRepository = fileRepository;
        this.idGenerator = idGenerator;
        this.fileExtensionMap = fileExtensionMap;
    }

    @Override
    public FileEntity getOneById(String fieldId, String scanId, String fileId) {
        FileEntityPK id = new FileEntityPK(fileId, scanId, fieldId);
        Optional<FileEntity> file = fileRepository.findById(id);

        return file.orElse(null);
    }

    @Override
    public List<FileEntity> getManyByScanId(String fieldId, String scanId, int offset, int limit) {
        PaginationManager<FileEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<FileEntity> files = fileRepository.findAllByFieldIdAndScanId(fieldId, scanId, PageRequest.of(i, PAGE_SIZE));
            if (files == null || files.isEmpty()) { break; }
            paginationManager.addPage(files);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
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
