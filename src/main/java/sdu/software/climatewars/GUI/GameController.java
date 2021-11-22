package sdu.software.climatewars.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    private Pane northDoor, southDoor, eastDoor, westDoor;

    @FXML
    private Label northText, southText, eastText, westText;

    // Scenario text box
    @FXML
    private AnchorPane scenarioBox;

    @FXML
    private Label scenarioText, scenarioDescription;

    @FXML
    private Button optionOneButton, optionTwoButton;

    // Statistics text box
    @FXML
    private AnchorPane statsBox;

    @FXML
    private Label statsText, foodText, groupSatietyText, groupSizeText;

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


    //set text for stats og scenarios
    public void setStatsText(String text) {
        this.statsText.setText(text);
    }

    public void movePlayer(int dx, int dy) {
        player.requestFocus();

        player.setX(player.getX() + dx);
        player.setY(player.getY() + dy);

        if(checkDoorCollision())
            // Load new scene
            return;
    }

    // Collision detection
    public Boolean checkDoorCollision(){
        if (player.getBoundsInParent().intersects(northDoor.getBoundsInParent())){
            System.out.println("YEEEET");
            return true;
        }
        return false;
    }

    /* ---------------- Methods to be implemented: ---------------- */
    public void setOptionOneButton(String text){this.optionOneButton.setText(text);}

    public void setOptionTwoButton(String text){this.optionTwoButton.setText(text);}

    public void setScenarioDescription(String text){this.scenarioDescription.setText(text);}
    public void setScenarioText(String text){this.scenarioText.setText(text);}

    public void setBackgroundImage (String backgroundName) throws FileNotFoundException {
        String backgroundPath = "src/main/resources/baggrunde/" + backgroundName + ".png";
        FileInputStream inputStream = new FileInputStream(backgroundPath);
        Image background = new Image(inputStream);
        this.backgroundImage.setImage(background);
    }

    public void setCharacterImageImage (String characterName) throws FileNotFoundException {
        String characterPath = "src/main/resources/character/" + characterName + ".png";
        FileInputStream inputStream = new FileInputStream(characterPath);
        Image character = new Image(inputStream);
        this.player.setImage(character);
    }

    public void hideStats(){
        groupSatietyText.setVisible(false);
        groupSizeText.setVisible(false);
        foodText.setVisible(false);
        statsBox.setVisible(false);
    }

    public void hideScenario(){
        scenarioDescription.setVisible(false);
        scenarioText.setVisible(false);
        scenarioBox.setVisible(false);
        optionOneButton.setVisible(false);
        optionTwoButton.setVisible(false);
    }

    public void hideNorth(){
        northText.setVisible(false);
        northDoor.setVisible(false);
    }

    public void hideSouth(){
        southText.setVisible(false);
        southDoor.setVisible(false);
    }

    public void hideEast(){
        eastText.setVisible(false);
        eastDoor.setVisible(false);
    }

    public void hideWest(){
        westDoor.setVisible(false);
        westText.setVisible(false);
    }

    public void showStats(){
        groupSatietyText.setVisible(true);
        groupSizeText.setVisible(true);
        foodText.setVisible(true);
        statsBox.setVisible(true);
    }

    public void showScenario(){
        scenarioDescription.setVisible(true);
        scenarioText.setVisible(true);
        scenarioBox.setVisible(true);
        optionOneButton.setVisible(true);
        optionTwoButton.setVisible(true);
    }

}
