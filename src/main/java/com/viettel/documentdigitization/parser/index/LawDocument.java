package com.viettel.documentdigitization.parser.index;

import com.viettel.documentdigitization.parser.document.Paragraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawDocument extends IndexedDocument {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item extends Paragraph {
        private int level = -1;
    }

    private List<Item> contents;

    private Map<String, Object> metadata = new HashMap<>();

}
