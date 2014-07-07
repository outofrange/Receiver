package org.outofrange.receiver;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.outofrange.receiver.watcher.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.outofrange.receiver.util.Config;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReceiverServer
{
	private static final Logger logger = LoggerFactory.getLogger(ReceiverServer.class);

	private Config CONFIG = Config.CONFIG;

	public ReceiverServer() {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		logger.debug("Starte");


        final Runnable watcher = new FileWatcher();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> watcherHandle = scheduler.scheduleAtFixedRate(watcher, 10, 10, TimeUnit.SECONDS);


		HttpServer server = null;
		try {
			server = HttpServerFactory.create(CONFIG.getServerPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();

		JOptionPane.showMessageDialog(null, "Ende");

		server.stop(0);
        watcherHandle.cancel(true);
	}
    public static void main( String[] args ) {
		new ReceiverServer();
	}
}