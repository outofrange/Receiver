package org.outofrange.receiver.client;



import com.sun.jersey.api.client.Client;

import javax.xml.bind.JAXBContext;
import java.io.File;

/**
 * Created by morg on 07.07.14.
 */
public class Receiver {


    public Receiver(String host, String port) {
        Client client = Client.create();

    }

    public File getFile(String fileId) {
        return null;
    }

    public void sendFile(String fileId, File file) {

    }
}
