package sdu.software.climatewars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

enum DirectionEnum {
    UP,
    DOWN,
    RIGHT,
    LEFT
}

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

    // Centered text box
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
    private ImageView player;

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

    @FXML
    private void handleOnKeyPressed(KeyEvent event)
    {
        switch (event.getCode()) {
            case UP -> movePlayer(0, -10);
            case DOWN -> movePlayer(0, 10);
            case LEFT -> movePlayer(-10, 0);
            case RIGHT -> movePlayer(10, 0);
        }
    }

    @FXML
    private void handleOnKeyReleased(KeyEvent event)
    {
        switch (event.getCode()) {
            case UP -> movePlayer(0, -10);
            case DOWN -> movePlayer(0, 10);
            case LEFT -> movePlayer(-10, 0);
            case RIGHT -> movePlayer(10, 0);
        }
    }

    public void setStatsText(String text) {
        this.statsText.setText(text);
    }

    public void movePlayer(int dx, int dy) {
        player.requestFocus();
        player.setX(player.getX() + dx);
        player.setY(player.getY() + dy);
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
