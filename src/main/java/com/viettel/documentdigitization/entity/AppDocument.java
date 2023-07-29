package com.viettel.documentdigitization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rawDocumentPath;
    private String parsedDocumentPath;
    private String signer;
    private String issuingAgency;
    private String code;
    private String name;
    private String type;
    private Date publishedDate;
    private List<DocumentItem> documentItems;

}
