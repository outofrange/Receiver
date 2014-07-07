package org.outofrange.receiver;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import util.Config;

import javax.swing.*;
import java.io.IOException;

public class Receiver
{
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	private Config CONFIG = Config.CONFIG;

	public Receiver() {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		logger.debug("Starte");
		HttpServer server = null;
		try {
			server = HttpServerFactory.create(CONFIG.getServerPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();

		JOptionPane.showMessageDialog(null, "Ende");
		server.stop(0);
	}

	public static void main( String[] args ) {
		new Receiver();
	}
}