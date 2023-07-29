package com.viettel.documentdigitization.service;

import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;

@Service
public class PythonExecuteService implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;
    private final SharedInterpreter pythonInterpreter;

    public PythonExecuteService() {
        String pythonPath = getClass()
                .getClassLoader()
                .getResource("python/venv/lib/python3.10/site-packages/")
                .getPath();
        String jepPath = pythonPath + "jep/libjep.so";
        MainInterpreter.setJepLibraryPath(jepPath);

        JepConfig jepConfig = new JepConfig();
        jepConfig.addIncludePaths(pythonPath);
        SharedInterpreter.setConfig(jepConfig);
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
