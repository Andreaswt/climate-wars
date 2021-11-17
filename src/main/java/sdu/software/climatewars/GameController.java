package sdu.software.climatewars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class GameController {
    // Background image for room
    @FXML
    private ImageView backgroundImage;

    // Doors and their text
    @FXML
    private Pane northDoor;

    @FXML
    private Pane southDoor;

    @FXML
    private Pane eastDoor;

    @FXML
    private Pane westDoor;

    @FXML
    private Label northText;

    @FXML
    private Label southText;

    @FXML
    private Label eastText;

    @FXML
    private Label westText;

    // Scenario text box
    @FXML
    private AnchorPane scenarioBox;

    @FXML
    private Label scenarioText;

    @FXML
    private Label scenarioDescription;

    @FXML
    private Button optionOneButton;

    @FXML
    private Button optionTwoButton;

    // Statistics text box
    @FXML
    private AnchorPane statsBox;

    @FXML
    private Label statsText;

    @FXML
    private Label foodText;

    @FXML
    private Label groupSatietyText;

    @FXML
    private Label groupSizeText;

    // Bottom right corner text box
    @FXML
    private AnchorPane textBox1;

    @FXML
    private Label title1;

    @FXML
    private Label description1;

    @FXML
    private Button button1;

    @FXML
    private TextArea textInput1;

    // Character
    @FXML
    private ImageView characterImage;

    @FXML
    protected void optionOneAction(ActionEvent actionEvent) {
        System.out.println("Option 1 chosen");
    }

    @FXML
    protected void optionTwoAction(ActionEvent actionEvent) {
        System.out.println("Option 2 chosen");
    }

    @FXML
    protected void button1Action(ActionEvent actionEvent) {
        System.out.println("Button 1 activated");
    }

    public void setDescription1(String text) {
        this.description1.setText(text);
    }

    /* ---------------- Methods to be implemented: ---------------- */

    // Hide statsbox

    // Show stats box + text, and set text.

    // Show scenario box + text, and set text and option buttons.

    // Hide scenario

    // Hide white boxes with text over: go east, and the other ones. Method for each.

    // Set background image

    // Set character image

}
