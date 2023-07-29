package com.viettel.documentdigitization.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StringParser {

    private List<String> regexPattern = new ArrayList<>();

    public void parse() throws FileNotFoundException {
        FileReader fileReader = new FileReader("/media/hoangdao/NewVolume/Workspace/Java/TLADocumentDigitization/src/main/resources/data.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String text = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(text);

        Pattern pattern = Pattern.compile("[A-Za-z\\s@#$%^&*,]+\\.[A-Za-z\\s@#$%^&*,]+|[A-Za-z\\s@#$%^&*,]");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println();
        }

    }

}
