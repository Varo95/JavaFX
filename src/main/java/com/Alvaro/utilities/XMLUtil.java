package com.Alvaro.utilities;

import com.Alvaro.model.beans.DataConnection;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

/**
 * Utilidades para escribir y leer XML
 */
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
        DataConnection result;
        JAXBContext jaxbC;
        File file = new File(path);
        if (file.exists() && file.isFile() && !path.isEmpty()) {
            try {
                jaxbC = JAXBContext.newInstance(DataConnection.class);
                Unmarshaller um = jaxbC.createUnmarshaller();
                result = (DataConnection) um.unmarshal(new File(path));
            } catch (JAXBException e) {
                result = new DataConnection("", "vital", "root", "12345", "H2");
                Dialog.showError("Error XML", "Leyendo XML", "Hemos establecido una conexión por defecto");
            }
        } else {
            Dialog.showWarning("Aviso", "No existe el fichero xml", "Se creará un nuevo fichero xml con datos");
            result = new DataConnection("", "vital", "root", "12345", "H2");
            saveFile(path, result);
        }
        return result;
    }
}
