package com.viettel.documentdigitization.parser;

import com.viettel.documentdigitization.parser.document.Document;
import lombok.Data;

import java.io.File;
import java.io.InputStream;

@Data
public abstract class DocumentParser {

    protected String storagePath;

    public DocumentParser(String storagePath) {
        this.storagePath = storagePath;
    }

    public abstract Document parse(File sourceFile) throws Exception;
    public abstract Document parse(InputStream inputStream) throws Exception;
}
