module com.example.KeyNest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.commons.csv;

    opens com.example.KeyNest to javafx.fxml;
    exports com.example.KeyNest;
    exports com.example.KeyNest.popups;
    opens com.example.KeyNest.popups to javafx.fxml;
}