package com.viettel.documentdigitization.util;

import com.groupdocs.conversion.internal.c.a.s.internal.nx.Mu;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileHelper {

    public static final File save(InputStream inputStream, File dest) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer) != -1) {
            fileOutputStream.write(buffer);
        }
        fileOutputStream.close();
        inputStream.close();
        return dest;
    }

    public static final File save(MultipartFile multipartFile, File dest) throws IOException {
        return save(multipartFile.getInputStream(), dest);
    }

    public static final File copy(File source, File dest) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        return save(fileInputStream, dest);
    }

    public static final String getExtension(MultipartFile multipartFile) {
        String[] split = multipartFile.getOriginalFilename().split("\\.");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return null;
    }

    public static final boolean isPdf(MultipartFile multipartFile) {
        return getExtension(multipartFile).toLowerCase().equals("pdf");
    }

    public static final boolean isDocx(MultipartFile multipartFile) {
        return getExtension(multipartFile).toLowerCase().equals("docx");
    }

    public static final boolean isDoc(MultipartFile multipartFile) {
        return getExtension(multipartFile).toLowerCase().equals("doc");
    }

}
