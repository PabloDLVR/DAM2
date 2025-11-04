module com.example.formulario {
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

    opens com.example.formulario to javafx.fxml;
    exports com.example.formulario;
    opens com.example.formulario.controller to javafx.fxml;
    exports com.example.formulario.controller;
    opens com.example.formulario.model to lombok;
    exports com.example.formulario.model;
}