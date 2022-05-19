package com.myplaygroup.server.other;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "document")
public class DocumentStorageProperty {

    private String uploadDirectory;
    private String profileDirectory;
}
