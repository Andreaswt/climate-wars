package sdu.software.climatewars.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import sdu.software.climatewars.Domain.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sdu.software.climatewars.Domain.Room;
import sdu.software.climatewars.Text.Command;
import sdu.software.climatewars.Text.CommandWord;
import sdu.software.climatewars.Text.CommandWords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class GameController {
    ArrayList<Challenge> challenges;
    Room currentRoom;
    Room northRoom, southRoom, eastRoom, westRoom;
    Group group;

    // Menu features
    @FXML
    private BorderPane menu;

    @FXML
    private BorderPane optionsMenu;

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

    // Rain
    @FXML
    private ImageView rain;

    // Fox
    @FXML
    private ImageView fox;

    // Sun
    @FXML
    private ImageView sun;

    // Character
    @FXML
    private ImageView player;

    // Group encounter
    @FXML
    private ImageView groupEncounter;

    // Flood
    @FXML
    private ImageView flood;

    // Fighting
    @FXML
    private ImageView fighting;

    @FXML
    protected void optionOneAction(ActionEvent actionEvent) {
        CommandWord commandWord = CommandWord.valueOf( currentRoom.getChallenge().getOptions().get(0).toUpperCase(Locale.ROOT));
        processCommand(new Command(commandWord, ""));
        System.out.println("Option 1 chosen");
    }

    @FXML
    protected void optionTwoAction(ActionEvent actionEvent) {
        CommandWord commandWord = CommandWord.valueOf( currentRoom.getChallenge().getOptions().get(1).toUpperCase(Locale.ROOT));
        processCommand(new Command(commandWord, ""));
        System.out.println("Option 2 chosen");
    }

    @FXML
    protected void startAction(ActionEvent actionEvent) {
        menu.setVisible(false);
        optionsMenu.setVisible(false);
    }

    @FXML
    protected void quitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    protected void openMenu(ActionEvent esc) {
        menu.setVisible(true);
        optionsMenu.setVisible(false);
    }

    @FXML
    protected void openOptions(ActionEvent options) {
        menu.setVisible(false);
        optionsMenu.setVisible(true);
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
            case ESCAPE -> menu.setVisible(true);
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
        if(currentRoom.getChallenge() == null || !currentRoom.getChallenge().getHasOptions()){
            player.requestFocus();

            player.setX(player.getX() + dx);
            player.setY(player.getY() + dy);

            // Check if player walked into door, and change scene hereafter
            checkDoorCollision();
        }
    }

    // Collision detection
    public void checkDoorCollision() {
        if (player.getBoundsInParent().intersects(northDoor.getBoundsInParent())) {
            processCommand(new Command( CommandWord.GO, northRoom.getName()));
            setupRoom(northRoom);
            setPlayerDefaultPosition();

        } else if (player.getBoundsInParent().intersects(southDoor.getBoundsInParent())) {
            processCommand(new Command( CommandWord.GO, southRoom.getName()));
            setupRoom(southRoom);
            setPlayerDefaultPosition();

        } else if (player.getBoundsInParent().intersects(eastDoor.getBoundsInParent())) {
            processCommand(new Command( CommandWord.GO, eastRoom.getName()));
            setupRoom(eastRoom);
            setPlayerDefaultPosition();

        } else if (player.getBoundsInParent().intersects(westDoor.getBoundsInParent())) {
            processCommand(new Command( CommandWord.GO, westRoom.getName()));
            setupRoom(westRoom);
            setPlayerDefaultPosition();

        }
    }

    public void loadNewScene(Room room) {
        setBackgroundImage(room.getBackgroundImage());

        // First room "City", won't have a challenge
        showScenario(room.getChallenge() != null, room);
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

    public void showRain(Boolean show) {
        rain.setVisible(show);
    }

    public void showFox(Boolean show) {
        fox.setVisible(show);
    }

    public void showSun(Boolean show) {
        sun.setVisible(show);
    }

    public void showFighting(Boolean show) {
        fighting.setVisible(show);
    }

    public void showFlood(Boolean show) {
        flood.setVisible(show);
    }

    public void showGroupEncounter(Boolean show) {
        groupEncounter.setVisible(show);
    }

    public void hidePetersBox() {
        textBox1.setVisible(false);
        title1.setVisible(false);
        description1.setVisible(false);
        button1.setVisible(false);
        textInput1.setVisible(false);
    }


    public void setGroup(Group group){
        this.group = group;
    }

    public void setChallenges(ArrayList<Challenge> c){
        this.challenges = c;
    }

    private boolean processCommand(Command command) {
        if (currentRoom.getChallenge() != null && !currentRoom.getChallenge().getHasOptions()) {
            this.currentRoom.setChallenge(null);
        }

        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO && currentRoom.getChallenge() == null) {
            group.eat();
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.STATS) {
            group.getStats();
        } else {
            if (commandWord == CommandWord.UNKNOWN) {
                System.out.println("I don't know what you mean...");
                return false;
            }
            if (currentRoom.getChallenge() != null) {
                for (String s : currentRoom.getChallenge().getOptions()) {
                    if (s.contains(commandWord.getCommandString())) {
                        currentRoom.getChallenge().applyEffect(commandWord.getCommandString());
                        group.getStats();
                        showStats(true, group);
                        currentRoom.setChallenge(null);
                        return wantToQuit;
                    }
                }
            }
            System.out.println("Trying to cheat are we? Not on my watch");
            return false;
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println();
        System.out.println("\"\033[3mThe climate have changed...");
        System.out.println("For the worse");
        System.out.println("Lead your group to survival.\"\033[0m");
        System.out.println();
        System.out.println("Move through the world by typing in command words");
        System.out.println("You can use the following commands:");
        System.out.println("-------------------------------------------------");
        System.out.println("[go] + [go option] -> e.g 'go forest' \n[help] \n[quit] \n[stats]");
        System.out.println("-------------------------------------------------");
        System.out.println();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExitName(direction);

        if (nextRoom == null) {
            System.out.println("You can't go there!");
        } else {
            System.out.println("------------------ You are here ------------------");
            currentRoom.setChallenge(getRandomChallenge());
            currentRoom = nextRoom;

            // When entering a new place, there's 25% chance of finding a new person
            Random rand = new Random();
            double rollForNewMember = rand.nextInt(100);

            if (rollForNewMember <= 25) {
                System.out.println("You found a lone member straying around, and invited him to join the group.");
                this.group.addToGroup(1);
            }


            System.out.println(currentRoom.getLongDescription());
            this.currentRoom.getChallenge().applyEffect();
            showStats(true, group);
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private Challenge getRandomChallenge() {
        Random rand = new Random();
        int index = rand.nextInt(this.challenges.size());
        return this.challenges.get(index);
    }
}
