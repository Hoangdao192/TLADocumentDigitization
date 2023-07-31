package com.viettel.documentdigitization.service;

import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;
import java.nio.file.Path;

public class PythonExecuteService implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;
    private final SharedInterpreter pythonInterpreter;
    private static boolean initialized = false;

    public PythonExecuteService(
            String pythonVersion, String pythonLibPath
    ) {
        if (!initialized) {
            String jepPath = Path.of(pythonLibPath, "jep/libjep.so")
                    .toAbsolutePath().toString();
            MainInterpreter.setJepLibraryPath(jepPath);

            JepConfig jepConfig = new JepConfig();
            jepConfig.addIncludePaths(pythonLibPath);
            SharedInterpreter.setConfig(jepConfig);
            initialized = true;
        }

        this.pythonInterpreter = new SharedInterpreter();
        this.cleanable = cleaner.register(this, () -> {
             this.pythonInterpreter.close();
        });
    }

    public boolean exec(String command) {
        return pythonInterpreter.eval(command);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
