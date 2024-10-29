package com.ccll.projectse_ifttt.Triggers;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati sull'esistenza di un file.
 * Fornisce un metodo per creare istanze di {@link FileExistenceTrig}, che si attivano quando un file specificato è presente.
 */
public class FileExisTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link FileExistenceTrig} che si attiva quando il file specificato esiste.
     * Il percorso deve essere fornito come stringa, con il formato "folderPath fileName", dove:
     * <ul>
     *     <li>{@code folderPath} è il percorso della cartella</li>
     *     <li>{@code fileName} è il nome del file da monitorare all'interno della cartella</li>
     * </ul>
     *
     * @param path una stringa contenente il percorso della cartella e il nome del file separati da uno spazio.
     * @return un nuovo oggetto {@link Trigger} che si attiva quando il file specificato esiste.
     */
    @Override
    public Trigger createTrigger(String path) {
        String[] parts = path.split(" ");
        Path folderPath = Paths.get(parts[0]);
        Path filePath = folderPath.resolve(parts[1]);
        return new FileExistenceTrig(filePath.toString());
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "file existence".
     */
    @Override
    public String getType() {
        return "file existence";
    }
}
