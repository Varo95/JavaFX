package com.Alvaro.utilities;

import com.Alvaro.model.PersonaDAO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class RepositoryUtilities {

    public void saveFile(String path) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(PersonaDAO.class);
            Marshaller m= jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(PersonaDAO.getInstance(), new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(String path) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(PersonaDAO.class);
            Unmarshaller m= jaxbC.createUnmarshaller();
            PersonaDAO newr=(PersonaDAO) m.unmarshal(new File(path));
            PersonaDAO.loadlista(newr.listarTodas());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
