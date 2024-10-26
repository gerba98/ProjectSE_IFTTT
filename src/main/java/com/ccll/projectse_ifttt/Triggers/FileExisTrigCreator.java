package com.ccll.projectse_ifttt.Triggers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileExisTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileExistenceTrig} con il path del file che vogliamo individuare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva quando il file specificato esiste.
     */
    @Override
    public Trigger createTrigger(String path) {
        String[] parts = path.split(" ");
        Path folderPath = Paths.get(parts[0]);
        Path filePath = folderPath.resolve(parts[1]);
        return new FileExistenceTrig(filePath.toString());
    }

    @Override
    public String getType() {
        return "file existence";
    }
}
