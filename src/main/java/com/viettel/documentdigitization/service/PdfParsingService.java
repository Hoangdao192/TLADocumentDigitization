package com.viettel.documentdigitization.service;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.aspose.words.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.viettel.documentdigitization.entity.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfParsingService {

    @Autowired
    private ObjectMapper objectMapper;

    private ByteArrayOutputStream convertPdfToDocx(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document pdfDocument = new Document(inputStream);
        DocSaveOptions saveOptions = new DocSaveOptions();
        saveOptions.setMode(DocSaveOptions.RecognitionMode.EnhancedFlow);
        saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
        saveOptions.setRelativeHorizontalProximity(2.5f);
        saveOptions.setRecognizeBullets(true);
        pdfDocument.save(outputStream, saveOptions);
        pdfDocument.close();
        return outputStream;
    }

    public ByteArrayOutputStream convertPdfToDocx(File pdfFile) throws Exception {
        com.aspose.words.Document docxDocument = new com.aspose.words.Document();

        PDDocument pdDocument = PDDocument.load(pdfFile);
        System.out.println("Total page: " + pdDocument.getNumberOfPages());

        int totalPage = pdDocument.getNumberOfPages();
        int lastReadPage = 0;
        while (lastReadPage < totalPage) {
            PDDocument tempDocument = new PDDocument();
            int readed = 0;
            for (int i = 0; i < 4; ++i) {
                if (lastReadPage + i < totalPage) {
                    ++readed;
                    tempDocument.addPage(
                            pdDocument.getPage(lastReadPage + i)
                    );
                } else {
                    break;
                }
            }
            lastReadPage += readed;

            ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream();
            tempDocument.save(tempOutputStream);
            tempDocument.close();

            ByteArrayOutputStream docxOutputStream = convertPdfToDocx(
                    new ByteArrayInputStream(tempOutputStream.toByteArray())
            );
            com.aspose.words.Document tempDocxDocument = new com.aspose.words.Document(
                    new ByteArrayInputStream(docxOutputStream.toByteArray())
            );
            docxDocument.appendDocument(tempDocxDocument, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        }

        ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
        docxDocument.save(resultOutputStream, SaveFormat.DOCX);
        return resultOutputStream;
    }

    public void parseDocx(InputStream inputStream) throws Exception {
        com.aspose.words.Document wordDocument = new com.aspose.words.Document(inputStream);
        NodeCollection nodes = wordDocument.getChildNodes(NodeType.PARAGRAPH, true);
        Iterator<Node> iterator = wordDocument.getChildNodes().iterator();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(
                    SerializationConfig config,
                    BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                return beanProperties.stream().map(bpw -> new BeanPropertyWriter(bpw) {
                    @Override
                    public void serializeAsField(
                            Object bean,
                            JsonGenerator gen,
                            SerializerProvider prov) throws Exception {
                        try {
                            super.serializeAsField(bean, gen, prov);
                        } catch (Exception e) {
                            System.out.println(String.format("ignoring %s for field '%s' of %s instance", e.getClass().getName(), this.getName(), bean.getClass().getName()));
                        }
                    }
                }).collect(Collectors.toList());
            }
        }));


        String json = mapper.writeValueAsString(
                new Data(wordDocument.getChildNodes())
        );
        File file = new File("/media/hoangdao/NewVolume/Workspace/Java/TLADocumentDigitization/src/main/resources/92.2015.QH13.P1.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();

//        while (iterator.hasNext()) {
//            Node node = iterator.next();
//            System.out.println(NodeType.getName(node.getNodeType()));
//            System.out.println(
//                    NodeType.getName(node.getParentNode().getNodeType())
//            );
//            System.out.println(node.getText());
//        }

//        List<Node> cleanedNodes = new ArrayList<>();
//        for (int i = 0; i < nodes.getCount(); ++i) {
//            Node node = nodes.get(i);
//            //  Ignore aspose-words license watermark
//            if (node.getText().trim().contains("Evaluation Only. Created with Aspose.PDF. Copyright 2002-2023 Aspose Pty Ltd.")
//            || node.getText().trim().replace("\n", "").length() == 0) {
//                continue;
//            } else {
//                cleanedNodes.add(node);
//            }
//        }

//        for (Node node : cleanedNodes) {
//            System.out.println(node.getText());
//        }

//        AppDocument appDocument = new AppDocument();
//        appDocument.setIssuingAgency(cleanedNodes.get(6).getText());
//        appDocument.setCode(cleanedNodes.get(9).getText().split(":")[1].trim());
//        appDocument.set

    }

}
