package com.Alvaro.utilities;

import com.Alvaro.model.Persona;
import com.Alvaro.model.PersonaDAO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name="repository")
@XmlAccessorType(XmlAccessType.FIELD)
public class RepositoryUtilities implements Serializable {

    @XmlElement(name="persona")
    private List<Persona> lista=PersonaDAO.listarTodas();

    public void saveFile(String path) {
        JAXBContext jaxbC;
        try {
            //TODO cambiar la clase que instancia JAXBContext
            jaxbC = JAXBContext.newInstance(RepositoryUtilities.class);
            Marshaller m= jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(lista, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(String path) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(Persona.class);
            Unmarshaller m= jaxbC.createUnmarshaller();
            List<Persona> newr=(List<Persona>) m.unmarshal(new File(path));
            PersonaDAO.loadlista(newr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
