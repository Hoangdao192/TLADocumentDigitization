package com.viettel.documentdigitization;

import com.viettel.documentdigitization.service.ShellCommandExecutor;

import java.io.IOException;

public class TestShellCommandExecutor {
    public static void main(String[] args) throws IOException, InterruptedException {
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        shellCommandExecutor.execute(
                "python3" +
                " /home/hoangdao/Workspace/Java/TLADocumentDigitization/python/main.py" +
                " \"/home/hoangdao/Workspace/Java/TLADocumentDigitization/src/test/resources/VanBanGoc_92.2015.QH13.P1 (1).pdf\"" +
                " /home/hoangdao/Workspace/Java/TLADocumentDigitization/src/test/resources/VanBanGoc_91.2015.QH13.P1.docx"
        );
    }
}
