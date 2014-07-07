package org.outofrange.receiver.watcher;

import org.outofrange.receiver.util.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

/**
 * @author extmoesl
 *         Created on 07.07.2014.
 */
public class FileWatcher implements Runnable {
    private final Config CONFIG = Config.CONFIG;
    private final String path = CONFIG.getProperty("upload");

    @Override
    public void run() {
        System.out.println("Entered run");
        checkFiles(new File(path));
    }

    private void checkFiles(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                Path path = file.toPath();
                try {
                    FileTime creationTime = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).creationTime();
                    if (oldEnough(creationTime.toMillis())) {
                        Files.delete(path);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean oldEnough(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        return now - time >= Long.valueOf(CONFIG.getProperty("cachedminutes")) * 60 * 1000;
    }
}
