package com.viettel.documentdigitization.parser;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.WordProcessingFileType;
import com.groupdocs.conversion.internal.c.a.s.internal.nx.Fi;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;
import com.viettel.documentdigitization.service.PythonExecuteService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class PdfDocumentParser {

    private final PythonExecuteService pythonExecuteService;

    public PdfDocumentParser(PythonExecuteService pythonExecuteService) {
        this.pythonExecuteService = pythonExecuteService;
    }

    public File convertPdfToDocx(File inputPdf) {
        pythonExecuteService.exec("from pdf2docx import Converter");
        pythonExecuteService.exec(
                String.format("cv = Converter('%s')", inputPdf.getAbsolutePath())
        );
        pythonExecuteService.exec(
                String.format("cv.convert('%s')", "/home/hoangdao/Workspace/Java/TLADocumentDigitization/src/main/resources/temp/doc.docx")
        );
        pythonExecuteService.exec("cv.close()");
        log.info("Convert success!");
        return new File("/home/hoangdao/Workspace/Java/TLADocumentDigitization/src/main/resources/temp/doc.docx");
    }

    public void parse(File inputPdf) {
        convertPdfToDocx(inputPdf);
    }

}
