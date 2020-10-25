package net.runelite.client.plugins.mouselogging;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    String currentTime = getCurrentTime();
    File clickFile = new File(getFolder() + "clicks/" + getStaticTime() + "-clicks.csv");
    File positionFile = new File(getFolder() + "positions/" + getStaticTime() + "-positions.csv");
    File inventoryFile = new File(getFolder() + "clicks/inventoryClicks.csv");

    public void createStructure() {
        setCurrentTime();
        createFile(getClickFile());
        createFile(getPositionFile());
        createFile(getInventoryFile());
    }

    public void createFile(File file) {
        File directory1 = new File(getFolder() + "clicks/");
        File directory2 = new File(getFolder() + "positions/");
        directory1.mkdirs();
        directory2.mkdirs();
        if (directory1.exists() && directory2.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(File file, String string) {
        if (file.exists()) {
            try {
                FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(string);
                pw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            createFile(file);
        }
    }

    public File getClickFile() {
        return clickFile;
    }

    public File getPositionFile() { return positionFile; }

    public File getInventoryFile() { return inventoryFile; }

    public String getFolder() { return FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/NoMoreAHK Scripts/OpenOSRS/"; }

    public String getStaticTime() {
        return currentTime;
    }

    public void setCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        currentTime = dtf.format(now);
    }

    public String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
