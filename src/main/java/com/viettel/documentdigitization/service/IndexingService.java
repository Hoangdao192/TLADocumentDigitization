package com.viettel.documentdigitization.service;

import com.groupdocs.conversion.internal.c.a.s.internal.nx.Mu;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.parser.index.IndexedDocument;
import com.viettel.documentdigitization.util.FileHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IndexingService {

    public IndexedDocument indexFile(MultipartFile multipartFile) {
        if (FileHelper.isPdf(multipartFile)) {

        }
    }

    public IndexedDocument index(Document document) {

    }

    private IndexedDocument indexLawDocument(Document document) {

    }

}
