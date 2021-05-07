package com.Alvaro.utilities;

import com.Alvaro.model.beans.DataConnection;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
//import org.h2.jdbcx.JdbcDataSource;

import java.io.File;

public class XMLUtil {

    public static void saveFile(String path, DataConnection dc) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(DataConnection.class);
            Marshaller m = jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(dc, new File(path));
            //TODO escribir fichero de la BBDD si no existe
           /*if(dc.getType().equals("H2")){
                File file = new File("vital.mv.db");
                if (!file.exists()){
                    JdbcDataSource ds = new JdbcDataSource();
                    ds.setURL("jdbc:h2:˜/vital");
                    ds.setUser("root");
                    ds.setPassword("12345");
                    Context ctx = new InitialContext();
                    ctx.bind("jdbc/dsName", ds);
                }
            }*/
        } catch (JAXBException e) {
            Dialog.showError("Error XML", "Escribiendo XML", e.toString());
        /*} catch (NamingException e) {
            e.printStackTrace();
            Dialog.showError("Error BBDD","Error al crear el archivo de la BBDD H2", e.toString());*/
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
                Dialog.showError("Error XML", "Leyendo XML",  "Hemos establecido una conexión por defecto");
            }
        } else {
            Dialog.showWarning("Aviso", "No existe el fichero xml", "Se creará un nuevo fichero xml con datos");
            DataConnection dc = new DataConnection("", "vital", "root", "12345", "H2");
            saveFile(path, dc);
            result = dc;
        }
        return result;
    }
}
