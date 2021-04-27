package com.Alvaro.utilities;

import com.Alvaro.model.task.Task;
import com.Alvaro.model.worker.Worker;
import com.Alvaro.model.worker.WorkerDAO;

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
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="Repository")
@XmlAccessorType(XmlAccessType.FIELD)
public class RepositoryUtilities implements Serializable {

    @XmlElement(name="Worker")
    private List<Worker> workerlist;
    @XmlElement(name="Task")
    private List<Task> tasklist;

    public void saveFile(String path, String datatype) {
        JAXBContext jaxbC;
        try {
            //TODO cambiar la clase que instancia JAXBContext
            jaxbC = JAXBContext.newInstance(RepositoryUtilities.class);
            Marshaller m = jaxbC.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
            if (datatype == "Worker") {
                workerlist=WorkerDAO.listAll();
                m.marshal(workerlist, new File(path));
            } else if (datatype == "Task") {
                tasklist=new ArrayList<>();
                m.marshal(tasklist, new File(path));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(String path) {
        JAXBContext jaxbC;
        try {
            jaxbC = JAXBContext.newInstance(Worker.class);
            Unmarshaller m= jaxbC.createUnmarshaller();
            List<Worker> newr=(List<Worker>) m.unmarshal(new File(path));
            WorkerDAO.loadList(newr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
