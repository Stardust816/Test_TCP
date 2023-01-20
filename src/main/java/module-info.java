module com.example.test_tcp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.test_tcp to javafx.fxml;
    exports com.example.test_tcp;
}