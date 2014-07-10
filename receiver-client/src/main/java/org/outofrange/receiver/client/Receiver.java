package org.outofrange.receiver.client;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.outofrange.receiver.rest.FileRestService;

import java.io.File;
import java.io.IOException;

/**
 * Created by morg on 07.07.14.
 */
public class Receiver {
    private FileRestService service;

    public Receiver(String host, String port) {
        JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
        bean.setServiceClass(FileRestService.class);
        bean.setAddress("http://" + host + ":" + port);
        service = bean.create(FileRestService.class);
    }

    public File getFile(String fileId) {
        return service.getFile(fileId).readEntity(File.class);
    }

    public void sendFile(String fileId, File file) throws IOException {

    }

    public static void main(String[] bla) {

    }
}
