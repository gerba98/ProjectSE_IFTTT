module com.ccll.projectse_ifttt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.logging;
    requires java.desktop;

    opens com.ccll.projectse_ifttt to javafx.fxml;
    exports com.ccll.projectse_ifttt;
}