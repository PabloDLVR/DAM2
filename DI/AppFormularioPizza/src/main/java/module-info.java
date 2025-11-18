module com.example.appformulariopizza {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires lombok;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.base;

    opens com.example.appformulariopizza to javafx.fxml, java.sql;
    exports com.example.appformulariopizza;
    exports com.example.appformulariopizza.controler;
    opens com.example.appformulariopizza.controler to javafx.fxml, java.sql;

    exports com.example.appformulariopizza.model;
    opens com.example.appformulariopizza.model to lombok, java.sql;
}