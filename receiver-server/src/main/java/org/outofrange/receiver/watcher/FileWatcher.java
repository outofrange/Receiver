package org.outofrange.receiver.watcher;

import org.outofrange.receiver.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
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
            checkFiles(Paths.get(path));
        } else {
            logger.trace("FileWatcher disabled - won't delete any files");
        }
    }

    private void checkFiles(Path directory) {
        logger.trace("Searching for file older than " + cachedMinutes + " minutes in directory " + directory.toAbsolutePath());

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            for (Path file : dirStream) {
                if (Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
                    try {
                        FileTime creationTime = Files.readAttributes(file, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).creationTime();
                        if (oldEnough(creationTime.toMillis())) {
                            logger.debug("Found file older than " + cachedMinutes + ": " + file.toAbsolutePath().toString());
                            Files.delete(file);
                        }

                    } catch (IOException e) {
                        logger.error("Couldn't delete file " + file.getFileName() + "!", e);
                    }

                }
            }
        } catch (IOException e) {
            logger.error("Couldn't list files of " + directory);
        }
    }


    private boolean oldEnough(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        return now - time >= cachedMinutes * 60 * 1000;
    }
}
