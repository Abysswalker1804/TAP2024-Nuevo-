module org.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    //requires java.desktop;

    opens org.example.test.components to javafx.base;
    opens org.example.test to javafx.fxml;
    exports org.example.test;

    requires itextpdf;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    opens org.example.test.modelos;
}