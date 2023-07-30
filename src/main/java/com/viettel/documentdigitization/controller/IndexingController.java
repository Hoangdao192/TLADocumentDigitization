package com.viettel.documentdigitization.controller;

import com.viettel.documentdigitization.dto.BaseResponse;
import com.viettel.documentdigitization.parser.index.IndexedDocument;
import com.viettel.documentdigitization.service.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/index")
public class IndexingController {

    @Autowired
    private IndexingService indexingService;

    @ResponseBody
    @PostMapping("file")
    public BaseResponse<IndexedDocument> indexFile(@RequestParam MultipartFile file) {
        return indexingService.indexFile(file);
    }

}
