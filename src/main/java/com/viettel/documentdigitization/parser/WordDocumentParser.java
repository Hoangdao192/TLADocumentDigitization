package com.viettel.documentdigitization.parser;

import com.aspose.words.*;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.parser.document.Paragraph;
import com.viettel.documentdigitization.parser.document.Table;
import com.viettel.documentdigitization.parser.document.*;
import com.viettel.documentdigitization.util.FileHelper;
import com.viettel.documentdigitization.util.ListHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class WordDocumentParser extends DocumentParser {

    public WordDocumentParser(String storagePath) {
        super(storagePath);
    }

    public void getPageCount() throws Exception {
        int sectionIndex = 2;
        com.aspose.words.Document doc = new com.aspose.words.Document(
                "/media/hoangdao/NewVolume/Workspace/Java/TLADocumentDigitization/src/main/resources/TestDocument.docx"
        );

        com.aspose.words.Document tempDoc = (com.aspose.words.Document) doc.deepClone(true);
        tempDoc.removeAllChildren();

        Section targetSection = (Section) tempDoc.importNode(doc.getSections().get(sectionIndex), true);
        tempDoc.appendChild(targetSection);

        System.out.println(tempDoc.getPageCount());
    }

    @Override
    public Document parse(File sourceFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        String extention = sourceFile.getName().split("\\.")[sourceFile.getName().split("\\.").length - 1]
                .toLowerCase();
        if (!(extention.equals("doc") || extention.equals("docx"))) {
            throw new RuntimeException("Wrong file type, require doc or docx but receive " + extention);
        }
        return parse(fileInputStream, extention);
    }

    public Document parse(InputStream inputStream, String wordFileType) throws Exception {
        Document parsedDocument = parse(inputStream);
        File file = new File(storagePath, parsedDocument.getUuid().toString());
        if (!file.exists() || !file.isDirectory()) {
            boolean success = file.mkdirs();
        }
        File sourceFile = new File(
                file.getAbsolutePath(),
                "source-" + parsedDocument.getUuid().toString()
                        + "."
                        + wordFileType
        );
        FileHelper.save(inputStream, sourceFile);
        return parsedDocument;
    }

    public Document parse(InputStream inputStream) throws Exception {
        Document parsedDocument = new Document();

        com.aspose.words.Document wordDocument = new com.aspose.words.Document(inputStream);
        List<Section> sections = ListHelper.toList(wordDocument.getSections());
        for (int i = 0; i < sections.size(); ++i) {
            parsedDocument.addChild(traverse(sections.get(i), parsedDocument, i, 0));
        }
        return parsedDocument;
    }

    private Item traverse(Node node, ContainerItem parent, int page, int depth) {
        for (int i = 0; i < depth; ++i) {
            System.out.print("  ");
        }
        System.out.println(node.getClass());

        Item parsedItem = null;
        if (node instanceof com.aspose.words.Document) {
            parsedItem = new Document();
        } else if (node instanceof com.aspose.words.Paragraph) {
            parsedItem = parseParagraph((com.aspose.words.Paragraph) node);
        } else if (node instanceof com.aspose.words.Table) {
            parsedItem = parseTable((com.aspose.words.Table) node);
        } else if (node instanceof Row) {
            parsedItem = parseRow((Row) node);
        } else if (node instanceof Cell) {
            parsedItem = parseCell((Cell) node);
        }


        if (parent != null && parsedItem != null) {
            parent.addChild(parsedItem);
            if (node.isComposite() && !(node instanceof com.aspose.words.Paragraph)) {
                List<Node> children = ListHelper.toList(((CompositeNode) node).getChildNodes());
                for (Node child : children) {
                    traverse(child, (ContainerItem) parsedItem, page, depth + 1);
                }
            }
        } else if (parent == null && parsedItem != null && node.isComposite() && !(node instanceof com.aspose.words.Paragraph)) {
            List<Node> children = ListHelper.toList(((CompositeNode) node).getChildNodes());
            for (Node child : children) {
                traverse(child, (ContainerItem) parsedItem, page, depth + 1);
            }
        } else if (parsedItem == null && parent != null && node.isComposite() && !(node instanceof com.aspose.words.Paragraph)) {
            List<Node> children = ListHelper.toList(((CompositeNode) node).getChildNodes());
            for (Node child : children) {
                traverse(child, parent, page, depth + 1);
            }
        }


        if (parsedItem != null) {
            parsedItem.setPage(page);
        }
        return parsedItem;
    }

    private String cleanTrashCharacter(String text) {
        text = text.replace("\r", "");
        text = text.replace("\t", "");
        text = text.replace("\f", "");
        text = text.replace("\u000B", "");
//        return text.replaceAll("\\r\\f", "");
        return text;
    }

    private Paragraph parseParagraph(com.aspose.words.Paragraph paragraph) {
        Paragraph parsedParagraph = new Paragraph();
        String text = paragraph.getText();
        text = cleanTrashCharacter(text);
        if (text.trim().length() == 0) {
            return null;
        }
        String[] sentences = text.split("\\.");
        for (String sentence : sentences) {
            parsedParagraph.addChild(
                    new Sentence(sentence + ".")
            );
        }
        return parsedParagraph;
    }

    private Table parseTable(com.aspose.words.Table table) {
        Table parsedTable = new Table();
//        List<Row> rows = ListHelper.toList(table.getRows());
//        for (Row row : rows) {
//            parsedTable.addChild(parseRow(row));
//        }
//        parsedNodeMap.put(table, parsedTable);
        return parsedTable;
    }

    private Table.Row parseRow(Row row) {
        Table.Row parsedRow = new Table.Row();
//        row.
//        List<Cell> cells = ListHelper.toList(row.getCells());
//        for (Cell cell : cells) {
//            parsedRow.addChild(parseCell(cell));
//        }
//        parsedNodeMap.put(row, parsedRow);
        return parsedRow;
    }

    private Table.Cell parseCell(Cell cell) {
        Table.Cell parsedCell = new Table.Cell();
//        List<com.aspose.words.Paragraph> paragraphs = ListHelper.toList(cell.getParagraphs());
//        for (com.aspose.words.Paragraph paragraph : paragraphs) {
//            parsedCell.addChild(parseParagraph(paragraph));
//        }
//        parsedNodeMap.put(cell, parsedCell);
        return parsedCell;
    }

}
