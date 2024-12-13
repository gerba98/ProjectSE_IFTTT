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
    requires java.desktop;
    requires javafx.swing;
    requires jdk.jshell;
    requires java.logging;

    opens com.ccll.projectse_ifttt to javafx.fxml;
    opens com.ccll.projectse_ifttt.Rule to javafx.base;
    exports com.ccll.projectse_ifttt;
}