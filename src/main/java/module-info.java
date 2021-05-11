module VitalAsistencia {
    requires com.sun.xml.bind;
    requires jakarta.xml.bind;
    requires java.desktop;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires org.kordamp.bootstrapfx.core;

    opens com.Alvaro.controllers to javafx.fxml, org.kordamp.bootstrapfx.core;
    opens com.Alvaro.model.beans to com.sun.xml.bind, jakarta.xml.bind;
    opens com.Alvaro.utilities to com.sun.xml.bind, jakarta.xml.bind;
    exports com.Alvaro;
}