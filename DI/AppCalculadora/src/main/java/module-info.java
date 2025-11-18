module com.example.appcalculadora {
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

    opens com.example.appcalculadora to javafx.fxml;
    exports com.example.appcalculadora;

    opens com.example.appcalculadora.controler to javafx.fxml;
    exports com.example.appcalculadora.controler;
}