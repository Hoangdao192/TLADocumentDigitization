package com.viettel.documentdigitization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

//@Entity
//@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppDocument {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sourceFilePath;
    private String sourceFileExtension;
    private String indexedFilePath;
    //  as json
    private String metadata;
    private Instant createdAt;
    private Instant updatedAt;

}
