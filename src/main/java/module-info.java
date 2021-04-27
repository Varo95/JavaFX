module VitalAsistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.base;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;
    requires com.sun.xml.bind;

    opens com.Alvaro.controllers to javafx.fxml;
    opens com.Alvaro.model.worker to com.sun.xml.bind, java.xml.bind, javafx.baseEmpty;
    opens com.Alvaro.model.task to com.sun.xml.bind, java.xml.bind, javafx.baseEmpty;
    opens com.Alvaro.utilities to java.xml.bind, com.sun.xml.bind, javafx.baseEmpty;
    exports com.Alvaro;

}