package sdu.software.climatewars.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sdu.software.climatewars.Domain.Group;
import sdu.software.climatewars.Domain.Room;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sdu.software.climatewars.Domain.Room;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class GameController {
    Room currentRoom;
    Room northRoom, southRoom, eastRoom, westRoom;
    Group group;

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
    private void handleOnKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> movePlayer(0, -10);
            case DOWN -> movePlayer(0, 10);
            case LEFT -> movePlayer(-10, 0);
            case RIGHT -> movePlayer(10, 0);
        }
    }

    @FXML
    private void handleOnKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> movePlayer(0, -10);
            case DOWN -> movePlayer(0, 10);
            case LEFT -> movePlayer(-10, 0);
            case RIGHT -> movePlayer(10, 0);
        }
    }

    public void setupRoom(Room currentRoom) {
        this.currentRoom = currentRoom;

        // Clear all doors
        showNorth(false, "");
        showSouth(false, "");
        showEast(false, "");
        showWest(false, "");

        // Set default room
        for (Map.Entry<String, Room> entry : this.currentRoom.getExits().entrySet()) {
            String key = entry.getKey();
            Room value = entry.getValue();

            // If name contains "north", "south", "east" og "west", create the doors
            if (key.contains("north")) {
                this.northRoom = value;
                showNorth(true, key.replace("north: ", ""));
            }
            if (key.contains("south")) {
                this.southRoom = value;
                showSouth(true, key.replace("south: ", ""));
            }

            if (key.contains("east")) {
                this.eastRoom = value;
                showEast(true, key.replace("east: ", ""));
            }

            if (key.contains("west")) {
                this.westRoom = value;
                showWest(true, key.replace("west: ", ""));
            }
        }

        loadNewScene(currentRoom);
    }

    public void movePlayer(int dx, int dy) {
        player.requestFocus();

        player.setX(player.getX() + dx);
        player.setY(player.getY() + dy);

        // Check if player walked into door, and change scene hereafter
        checkDoorCollision();
    }

    // Collision detection
    public void checkDoorCollision() {
        if (player.getBoundsInParent().intersects(northDoor.getBoundsInParent())) {
            setupRoom(northRoom);
            setPlayerDefaultPosition();
        } else if (player.getBoundsInParent().intersects(southDoor.getBoundsInParent())) {
            setupRoom(southRoom);
            setPlayerDefaultPosition();
        } else if (player.getBoundsInParent().intersects(eastDoor.getBoundsInParent())) {
            setupRoom(eastRoom);
            setPlayerDefaultPosition();
        } else if (player.getBoundsInParent().intersects(westDoor.getBoundsInParent())) {
            setupRoom(westRoom);
            setPlayerDefaultPosition();
        }
    }

    public void loadNewScene(Room room) {
        setBackgroundImage(room.getBackgroundImage());
        showScenario(false, room);

        // First room "City", won't have a challenge
        if (room.getName() != "City") {
            showScenario(true, room);
        }
    }

    public void setBackgroundImage(String backgroundName) {
        try {
            String backgroundPath = "src/main/resources/baggrunde/" + backgroundName + ".png";
            FileInputStream inputStream = new FileInputStream(backgroundPath);
            Image background = new Image(inputStream);
            this.backgroundImage.setImage(background);
        } catch (FileNotFoundException e) {
            System.out.println("Background not found");
        }
    }

    public void setCharacterImageImage(String characterName) throws FileNotFoundException {
        String characterPath = "src/main/resources/character/" + characterName + ".png";
        FileInputStream inputStream = new FileInputStream(characterPath);
        Image character = new Image(inputStream);
        this.player.setImage(character);
    }

    public void showStats(Boolean show, Group group) {
        if (group != null) {
            this.group = group;
        }

        // Visibility
        groupSatietyText.setVisible(show);
        groupSizeText.setVisible(show);
        foodText.setVisible(show);
        statsBox.setVisible(show);

        // Set text
        groupSatietyText.setText(String.valueOf("Group satiety: " + this.group.getSatiety()));
        groupSizeText.setText(String.valueOf("Group size: " + this.group.getGroupSize()));
        foodText.setText(String.valueOf("Food: " + this.group.getFood()));
    }

    public void showScenario(Boolean show, Room room) {
        // Visibility
        scenarioDescription.setVisible(show);
        scenarioText.setVisible(show);
        scenarioBox.setVisible(show);
        optionOneButton.setVisible(show);
        optionTwoButton.setVisible(show);

        // If show is false, no need to set the text
        if (!show)
            return;

        // Text
        this.scenarioText.setText(room.getChallenge().getName());
        this.scenarioDescription.setText(room.getChallenge().getDescription());

        // Options
        var options = room.getChallenge().getOptions();
        if (options.size() == 0) {
            setFirstOption(false, "");
            setSecondOption(false, "");
        } else if (options.size() == 1) {
            setFirstOption(true, options.get(0));
        } else if (options.size() == 2) {
            setFirstOption(true, options.get(0));
            setSecondOption(true, options.get(1));
        }
    }

    public void setFirstOption(Boolean show, String name) {
        optionOneButton.setVisible(show);
        optionOneButton.setText(name);
    }

    public void setSecondOption(Boolean show, String name) {
        optionTwoButton.setVisible(show);
        optionTwoButton.setText(name);
    }

    public void showNorth(Boolean show, String text) {
        northText.setText(text);
        northText.setVisible(show);
        northDoor.setVisible(show);
    }

    public void showSouth(Boolean show, String text) {
        southText.setText(text);
        southText.setVisible(show);
        southDoor.setVisible(show);
    }

    public void showEast(Boolean show, String text) {
        eastText.setText(text);
        eastText.setVisible(show);
        eastDoor.setVisible(show);
    }

    public void showWest(Boolean show, String text) {
        westText.setText(text);
        westText.setVisible(show);
        westDoor.setVisible(show);
    }

    public void setPlayerDefaultPosition() {
        player.setX(0);
        player.setY(0);
    }

    public void hidePetersBox() {
        textBox1.setVisible(false);
        title1.setVisible(false);
        description1.setVisible(false);
        button1.setVisible(false);
        textInput1.setVisible(false);
    }
}
