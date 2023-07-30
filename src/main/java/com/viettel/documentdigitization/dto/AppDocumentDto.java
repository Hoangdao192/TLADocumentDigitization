package com.viettel.documentdigitization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDocumentDto {

    private int id;
    private String sourceFilePath;
    private String sourceFileExtension;
    private String indexedFilePath;
    //  as json
    private String metadata;
    private Instant createdAt;
    private Instant updatedAt;

}
