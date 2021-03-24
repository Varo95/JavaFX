module VitalAsistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.activation;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;

    opens com.Alvaro.controllers to javafx.fxml;
    opens com.Alvaro.model to java.xml.bind;
    opens com.Alvaro.utilities to java.xml.bind;
    exports com.Alvaro;
    exports com.Alvaro.model;
    exports com.Alvaro.utilities;
}