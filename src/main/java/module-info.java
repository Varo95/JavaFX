module VitalAsistencia {
    requires java.sql;
    requires jakarta.xml.bind;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires com.sun.xml.bind;

    opens com.Alvaro.controllers to javafx.fxml;
    opens com.Alvaro.model.beans to jakarta.xml.bind, com.sun.xml.bind;
    opens com.Alvaro.utilities to jakarta.xml.bind, com.sun.xml.bind;
    exports com.Alvaro;
}