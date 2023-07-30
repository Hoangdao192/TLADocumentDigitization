package com.viettel.documentdigitization.parser;

import com.aspose.pdf.facades.PdfConverter;
import com.aspose.words.*;
import com.viettel.documentdigitization.parser.document.Document;
import com.viettel.documentdigitization.parser.document.Paragraph;
import com.viettel.documentdigitization.parser.document.Table;
import com.viettel.documentdigitization.parser.document.*;
import com.viettel.documentdigitization.util.ListHelper;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDocumentParserPOI {

    private Map<Node, Item> parsedNodeMap = new HashMap<>();

    public Document parse(InputStream inputStream) throws Exception {
        Document parsedDocument = new Document();

        XWPFDocument wordDocument = new XWPFDocument(inputStream);
//        wordDocument.createFooter(
//                org.apache.poi.wp.usermodel.HeaderFooterType.
//        )
        CTDocument1 ctDocument1 = wordDocument.getDocument();
        CTBody ctBody = ctDocument1.getBody();
        CTSectPr ctSectPr = ctBody.getSectPr();
        CTPageMar ctPageMar = ctSectPr.getPgMar();

        List<IBodyElement> elements = wordDocument.getBodyElements();
        for (int i = 0; i < elements.size(); ++i) {
            traverse(elements.get(i), parsedDocument, i, 0);
        }

//        Document parsedDocument = new Document();
//
//        com.aspose.words.Document wordDocument = new com.aspose.words.Document(inputStream);
//        List<Section> sections = ListHelper.toList(wordDocument.getSections());
//        for (int i = 0; i < sections.size(); ++i) {
//            parsedDocument.addChild(traverse(sections.get(i), parsedDocument, i, 0));
//        }
//        return parsedDocument;
        return parsedDocument;
    }

//    private void traverse(IBodyElement bodyElement) {
//        System.out.println(bodyElement.getClass());
//        XWPFTable
//    }

    private Item traverse(IBodyElement bodyElement, ContainerItem parent, int page, int depth) {
        for (int i = 0; i < depth; ++i) {
            System.out.print("  ");
        }
        System.out.println(bodyElement.getClass());

        Item parsedItem = null;
        if (bodyElement instanceof XWPFParagraph) {
            parsedItem = parseParagraph((XWPFParagraph) bodyElement);
        }
        else if (bodyElement instanceof XWPFTable) {
            parsedItem = parseTable((XWPFTable) bodyElement);
        }

        if (parent != null && parsedItem != null) {
            parent.addChild(parsedItem);
            if (bodyElement.getBody() != bodyElement.getPart()) {
                for (IBodyElement child : bodyElement.getBody().getBodyElements()) {
                    traverse(child, (ContainerItem) parsedItem, page, depth + 1);
                }
            }
        }
        else if (parent == null && parsedItem != null && bodyElement.getBody() != bodyElement.getPart()) {
            for (IBodyElement child : bodyElement.getBody().getBodyElements()) {
                traverse(child, (ContainerItem) parsedItem, page, depth + 1);
            }
        }
        else if (parsedItem == null && parent != null && bodyElement.getBody() != bodyElement.getPart()) {
            for (IBodyElement child : bodyElement.getBody().getBodyElements()) {
                traverse(child, parent, page, depth + 1);
            }
        }

        if (parsedItem != null) {
            parsedItem.setPage(page);
        }
        return parsedItem;
    }

    private Paragraph parseParagraph(XWPFParagraph paragraph) {
        Paragraph parsedParagraph = new Paragraph();
        parsedParagraph.setPage(paragraph.getDocument().getPosOfParagraph(paragraph));
        paragraph.getBody().getBodyElements();
        String text = paragraph.getText();
        String[] sentences = text.split("\\.");
        for (String sentence : sentences) {
            parsedParagraph.addChild(
                    new Sentence(sentence)
            );
        }
        return parsedParagraph;
    }

    private Table parseTable(XWPFTable table) {
        Table parsedTable = new Table();
        List<XWPFTableRow> rows = table.getRows();
        for (XWPFTableRow row : rows) {
            parsedTable.addChild(parseRow(row));
        }
        return parsedTable;
    }

    private Table.Row parseRow(XWPFTableRow row) {
        Table.Row parsedRow = new Table.Row();
        List<XWPFTableCell> cells = row.getTableCells();
        for (XWPFTableCell cell : cells) {
            parsedRow.addChild(parseCell(cell));
        }
        return parsedRow;
    }

    private Table.Cell parseCell(XWPFTableCell cell) {
        Table.Cell parsedCell = new Table.Cell();
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            parsedCell.addChild(parseParagraph(paragraph));
        }
        return parsedCell;
    }

}
