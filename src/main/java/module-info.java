module VitalAsistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.base;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires java.desktop;
    requires java.prefs;

    opens com.Alvaro.controllers to javafx.fxml;
    opens com.Alvaro.model.beans to com.sun.xml.bind, java.xml.bind, javafx.baseEmpty;
    opens com.Alvaro.utilities to java.xml.bind, com.sun.xml.bind, javafx.baseEmpty;
    exports com.Alvaro;
    opens com.Alvaro.model.interfaces to com.sun.xml.bind, java.xml.bind, javafx.baseEmpty;
}