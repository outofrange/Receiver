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
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class ReceiverServer {
    private static final Logger logger = LoggerFactory.getLogger(ReceiverServer.class);

    public ReceiverServer() {
        logger.debug("Installing slf4j root logger handler");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        logger.info("Starting server");

        final Runnable watcher = new FileWatcher();
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(watcher, 10, 10, TimeUnit.SECONDS);

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerClasses(FileRestServiceImpl.class, ConfigRestServiceImpl.class, MultiPartFeature.class);
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(Config.CONFIG.getProperty
                ("address")), resourceConfig);

        try {
            server.start();
        } catch (IOException e) {
            logger.error("Couldn't start server", e);
        }

        SignalHandler handler = new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logger.info("Stopping server.");
                server.stop();
                scheduler.shutdown();
                logger.debug("Stopped server - quitting");
            }
        };
        Signal.handle(new Signal("INT"), handler);
        Signal.handle(new Signal("TERM"), handler);
    }

    public static void main(String[] args) {
        new ReceiverServer();
    }
}