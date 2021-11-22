module sdu.software.climatewars {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens sdu.software.climatewars to javafx.fxml;
    exports sdu.software.climatewars;
    exports sdu.software.climatewars.Domain;
    opens sdu.software.climatewars.Domain to javafx.fxml;
    exports sdu.software.climatewars.Text;
    opens sdu.software.climatewars.Text to javafx.fxml;
    exports sdu.software.climatewars.GUI;
    opens sdu.software.climatewars.GUI to javafx.fxml;
}