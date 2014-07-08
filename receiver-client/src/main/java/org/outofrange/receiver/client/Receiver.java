package org.outofrange.receiver.client;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.outofrange.receiver.rest.FileRestService;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.net.URI;

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
        return (File) service.getFile(fileId).getEntity();
    }

    public void sendFile(String fileId, File file) {

    }

    public static void main(String[] bla) {
        Receiver r = new Receiver("localhost", "9090");
        r.getFile("123");
    }
}
