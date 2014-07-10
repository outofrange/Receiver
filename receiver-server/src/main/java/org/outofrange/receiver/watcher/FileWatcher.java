package org.outofrange.receiver.watcher;

import org.outofrange.receiver.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

/**
 * @author outofrange
 */
public class FileWatcher implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(FileWatcher.class);
    private final int cachedMinutes = Integer.valueOf(Config.CONFIG.getProperty("cachedminutes"));
    private final String path = Config.CONFIG.getProperty("upload");

    @Override
    public void run() {
        if (cachedMinutes > 0) {
            checkFiles(new File(path));
        }
    }

    private void checkFiles(File directory) {
        logger.debug("Searching for file older than " + cachedMinutes + " in directory " + directory.getAbsolutePath());
        // TODO use Path instead of File

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                Path path = file.toPath();
                try {
                    FileTime creationTime = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).creationTime();
                    if (oldEnough(creationTime.toMillis())) {
                        logger.debug("Found file older than " + cachedMinutes + ": " + path.toAbsolutePath().toString());
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
        return now - time >= cachedMinutes * 60 * 1000;
    }
}
