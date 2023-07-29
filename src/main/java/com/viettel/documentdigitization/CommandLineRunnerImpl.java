package com.viettel.documentdigitization;

import com.viettel.documentdigitization.parser.PdfDocumentParser;
import com.viettel.documentdigitization.parser.WordDocumentParser;
import com.viettel.documentdigitization.parser.WordDocumentParserPOI;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.service.PdfParsingService;
import com.viettel.documentdigitization.service.PythonExecuteService;
import com.viettel.documentdigitization.service.StringParser;
import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private PdfParsingService pdfParsingService;
    @Autowired
    private StringParser stringParser;
    @Autowired
    private PythonExecuteService pythonExecuteService;

    @Override
    public void run(String... args) throws Exception {
//        Document sdocument = new WordDocumentParserPOI().parse(new FileInputStream("/media/hoangdao/NewVolume/Workspace/Java/TLADocumentDigitization/src/main/resources/TestDocument.docx"));
////        System.out.println(document);
//
//        WordDocumentParser parser = new WordDocumentParser();
////        parser.getPageCount();
//        Document document = parser.parse(
//                new FileInputStream("/media/hoangdao/NewVolume/Workspace/Java/TLADocumentDigitization/src/main/resources/TestDocument.docx")
//        );
//        System.out.println(document);

//        new PdfDocumentParser().parse();

//        testEmbeddedPython();
        testPdfDocumentParser();
    }

    public void testPdfDocumentParser() {
        PdfDocumentParser pdfDocumentParser = new PdfDocumentParser(pythonExecuteService);
        pdfDocumentParser.parse(
                new File("/home/hoangdao/Workspace/Java/TLADocumentDigitization/src/main/resources/VanBanGoc_92.2015.QH13.P1.pdf")
        );
    }

    public void testEmbeddedPython() {
        String pythonPath = getClass()
                .getClassLoader()
                .getResource("python/venv/lib/python3.10/site-packages/")
                .getPath();
        String jepPath = pythonPath + "jep/libjep.so";
        MainInterpreter.setJepLibraryPath(jepPath);

        JepConfig jepConfig = new JepConfig();
        jepConfig.addIncludePaths(pythonPath);
        SharedInterpreter.setConfig(jepConfig);
        SharedInterpreter sharedInterpreter = new SharedInterpreter();
        Boolean ok = sharedInterpreter.eval("from pdf2docx import Converter");


        System.out.println(ok);
        sharedInterpreter.close();
    }
}
