module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jbcrypt;
    requires itextpdf;

    //requires java.datatransfer;
   // requires java.desktop;

    opens com.example.javafx to javafx.fxml;
    exports entities;
    exports com.example.javafx;
}