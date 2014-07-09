package org.outofrange.receiver;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;

public class ReceiverServer
{
	public ReceiverServer() {
		HttpServer server = null;
		try {
			server = HttpServerFactory.create("http://localhost:9090/");
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.start();

		JOptionPane.showMessageDialog(null, "End");

		server.stop(0);
	}

    public static void main( String[] args ) {
		new ReceiverServer();
	}
}