package com.viettel.documentdigitization.service;

import com.groupdocs.conversion.internal.c.a.s.internal.nx.Mu;
import com.viettel.documentdigitization.parser.PdfDocumentParser;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.parser.index.IndexedDocument;
import com.viettel.documentdigitization.util.FileHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class IndexingService {

    @Value("${python.version}")
    private String pythonVersion;
    @Value("${python.path}")
    private String pythonPath;
    @Value("${python.lib}")
    private String pythonLibPath;
    @Value("${parser.storage.path}")
    private String storagePath;

    public Document indexFile(MultipartFile multipartFile) throws Exception {
        Document document = null;
        if (FileHelper.isPdf(multipartFile)) {
            PdfDocumentParser pdfDocumentParser = new PdfDocumentParser(
                    new PythonExecuteService(pythonVersion, pythonLibPath),
                    storagePath
            );
            File directory = new File(storagePath, "temp");
            if (!directory.exists() || !directory.isDirectory()) {
                directory.mkdirs();
            }
            File file = FileHelper.save(multipartFile,
                    new File(directory.getAbsolutePath(), UUID.randomUUID().toString()));
            document = pdfDocumentParser.parse(file);
        }
        return document;
    }

//    public IndexedDocument index(Document document) {
//
//    }
//
//    private IndexedDocument indexLawDocument(Document document) {
//
//    }

}
