module VitalAsistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;

    opens com.Alvaro.controllers to javafx.fxml;
    opens com.Alvaro.model to java.xml.bind, javafx.baseEmpty;
    opens com.Alvaro.utilities to java.xml.bind, javafx.baseEmpty;
    exports com.Alvaro;
    exports com.Alvaro.controllers;
    exports com.Alvaro.model;
    exports com.Alvaro.utilities;
}