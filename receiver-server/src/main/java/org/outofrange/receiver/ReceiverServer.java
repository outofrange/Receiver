package org.outofrange.receiver;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.outofrange.receiver.rest.ConfigRestServiceImpl;
import org.outofrange.receiver.rest.FileRestServiceImpl;
import org.outofrange.receiver.util.Config;
import org.outofrange.receiver.watcher.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReceiverServer {
    private static final Logger logger = LoggerFactory.getLogger(ReceiverServer.class);

    private Config config = Config.CONFIG;

    public ReceiverServer() {
        logger.debug("Installing slf4j root logger handler");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        logger.info("Starting server");


        final Runnable watcher = new FileWatcher();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> watcherHandle = scheduler.scheduleAtFixedRate(watcher, 10, 10, TimeUnit.SECONDS);

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerClasses(FileRestServiceImpl.class, ConfigRestServiceImpl.class, MultiPartFeature.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(config.getProperty("address")), resourceConfig);

        try {
            server.start();
        } catch (IOException e) {
            logger.error("Couldn't start server", e);
        }

        JOptionPane.showMessageDialog(null, "Ende");

        logger.info("Stopping server.");
        server.stop();
        watcherHandle.cancel(true);
        logger.debug("Stopped server - quitting");
    }

    public static void main(String[] args) {
        new ReceiverServer();
    }
}