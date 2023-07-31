package com.viettel.documentdigitization.controller;

import com.viettel.documentdigitization.parser.document.Document;
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
    public Document indexFile(@RequestParam MultipartFile file) throws Exception {
        return indexingService.indexFile(file);
    }

}
