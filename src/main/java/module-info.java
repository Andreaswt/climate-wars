module sdu.software.climatewars {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens sdu.software.climatewars to javafx.fxml;
    exports sdu.software.climatewars;
}