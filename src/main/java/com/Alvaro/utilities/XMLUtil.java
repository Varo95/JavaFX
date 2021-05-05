package com.Alvaro.utilities;

import com.Alvaro.model.beans.DataConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLUtil {

    public static void saveFile(String path, DataConnection dc) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(DataConnection.class);
            Marshaller m = jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(dc, new File(path));
        } catch (JAXBException e) {
            Dialog.showError("Error XML", "Escribiendo XML", e.toString());
        }
    }

    public static DataConnection loadFile(String path) {
        DataConnection result = null;
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(DataConnection.class);
            Unmarshaller um = jaxbC.createUnmarshaller();
            result = (DataConnection) um.unmarshal(new File(path));
        } catch (JAXBException e) {
            result = new DataConnection("localhost", "vital", "root", "");
            Dialog.showError("Error XML", "Leyendo XML", e.toString() + "/n" + "Hemos establecido una conexión por defecto");
        }
        return result;
    }
}
