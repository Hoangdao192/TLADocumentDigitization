package com.viettel.documentdigitization;

import com.viettel.documentdigitization.service.PdfParsingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class, args);

    }

}
