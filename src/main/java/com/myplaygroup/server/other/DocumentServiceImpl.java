package com.myplaygroup.server.other;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Service
public class DocumentServiceImpl {

    private final Path uploadStorageLocation;
    private final Path profileStorageLocation;

    public DocumentServiceImpl(DocumentStorageProperty documentStorageProperty) throws IOException {
        this.uploadStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        this.profileStorageLocation = Paths.get(documentStorageProperty.getProfileDirectory()).toAbsolutePath().normalize();

        Files.createDirectories(this.uploadStorageLocation);
        Files.createDirectories(this.profileStorageLocation);
    }
}
