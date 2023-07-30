package com.viettel.documentdigitization.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.WordProcessingFileType;
import com.groupdocs.conversion.internal.c.a.s.internal.nx.Fi;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.service.PythonExecuteService;
import com.viettel.documentdigitization.util.FileHelper;
import lombok.extern.slf4j.Slf4j;

import javax.print.Doc;
import java.io.*;
import java.util.UUID;

@Slf4j
public class PdfDocumentParser extends DocumentParser {

    private final PythonExecuteService pythonExecuteService;
    private final WordDocumentParser wordDocumentParser;

    public PdfDocumentParser(PythonExecuteService pythonExecuteService, String storagePath) {
        super(storagePath);
        this.pythonExecuteService = pythonExecuteService;
        this.wordDocumentParser = new WordDocumentParser(storagePath);
    }

    public File convertPdfToDocx(File inputPdf) {
        pythonExecuteService.exec("from pdf2docx import Converter");
        pythonExecuteService.exec(
                String.format("cv = Converter('%s')", inputPdf.getAbsolutePath())
        );
        File directory = new File(storagePath, "temp");
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        File convertedFile = new File(
                directory, UUID.randomUUID().toString()
        );

        pythonExecuteService.exec(
                String.format("cv.convert('%s')", convertedFile.getAbsolutePath())
        );
        pythonExecuteService.exec("cv.close()");
        log.info("Convert success!");
        return convertedFile;
    }

    @Override
    public Document parse(File sourceFile) throws Exception {
        File docxFile = convertPdfToDocx(sourceFile);
        Document parsedDocument =  wordDocumentParser.parse(
                new FileInputStream(docxFile)
        );

        File directory = new File(
                storagePath, parsedDocument.getUuid().toString()
        );
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        File sourceFileSave = new File(
                directory.getAbsolutePath(),
                "source-" + parsedDocument.getUuid().toString() + ".pdf"
        );
        FileHelper.copy(sourceFile, sourceFileSave);

        File convertedFileSave = new File(
                directory.getAbsolutePath(),
                "converted-" + parsedDocument.getUuid().toString() + ".docx"
        );
        FileHelper.copy(docxFile, convertedFileSave);

        log.info("Parsing successful!");
        return parsedDocument;
    }

    @Override
    public Document parse(InputStream inputStream) {
        return null;
    }

}
