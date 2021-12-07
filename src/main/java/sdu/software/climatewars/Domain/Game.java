package sdu.software.climatewars.Domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdu.software.climatewars.GUI.GameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game extends Application {
    private final ArrayList<Challenge> challenges;
    private final Group group;
    private Room currentRoom;

    public Game() {
        this.group = new Group();
        this.challenges = new ArrayList<>();
        readFromFile();
        createRooms();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Climate Wars");
        stage.setResizable(false);
        GameController rc = fxmlLoader.getController();

        // Default room setup
        rc.setChallenges(this.challenges);
        rc.setGroup(this.group);
        rc.setupRoom(this.currentRoom);

        // Hide rain as default
        rc.showRain(false);

        // Hide fox as default
        rc.showFox(false);

        // Hide sun as default
        rc.showSun(false);

        // Hide fighting as default
        rc.showFighting(false);

        // Hide flood as default
        rc.showFlood(false);

        // Hide group encounter as default
        rc.showGroupEncounter(false);

        //Hide exile animation as default
        rc.showExile(false);

        // Pass group into controller, to post data in stats box
        rc.showStats(true, group);

        rc.showGameOver();

        stage.setScene(scene);
        stage.show();
    }

    private void createRooms() {
        Room city, forest, cliffs, hilltops, university, club, beach, lake, fields, cornfield;

        city = new Room("City", "City");
        forest = new Room("Forest", getRandomChallenge(), "Forrest");
        cliffs = new Room("Cliffs", getRandomChallenge(), "Cliffs");
        hilltops = new Room("Hilltops", getRandomChallenge(), "Hilltops");
        university = new Room("University", getRandomChallenge(), "University");
        club = new Room("Club", getRandomChallenge(), "Club");
        beach = new Room("Beach", getRandomChallenge(), "Beach");
        lake = new Room("Lake", getRandomChallenge(), "Lake");
        fields = new Room("Fields", getRandomChallenge(), "Field");
        cornfield = new Room("Cornfield", getRandomChallenge(), "Cornfield");

        // Abandoned city
        city.setExit("south: Forest", forest);
        city.setExit("east: Club", club);
        city.setExit("north: University", university);
        city.setExit("west: Fields", fields);

        // Fields
        fields.setExit("north: Cornfield", cornfield);
        fields.setExit("east: City", city);

        // Cornfield
        cornfield.setExit("south: Fields", fields);

        // Club
        club.setExit("west: City", city);

        // Dark forest
        forest.setExit("north: City", city);
        forest.setExit("east: Hilltops", hilltops);

        // Hilltops
        hilltops.setExit("south: Cliffs", cliffs);
        hilltops.setExit("west: Forest", forest);

        // Cliffs
        cliffs.setExit("north: Hilltops", hilltops);

        // University
        university.setExit("north: Lake", lake);
        university.setExit("south: City", city);

        // Lake
        lake.setExit("south: University", university);
        lake.setExit("east: Beach", beach);

        // Beach
        beach.setExit("west: Lake", lake);

        currentRoom = city;
    }

    public void play() {
        launch();
    }

    private void readFromFile() {
        //Read file
        try {
            File myObj = new File("src/main/java/sdu/software/climatewars/Domain/Challenges.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!data.contains("$")) {
                    if (data.contains("Title:")) {
                        String cName;
                        cName = data.replace("Title: ", "");

                        data = myReader.nextLine();
                        String cDescription = data;

                        data = myReader.nextLine();
                        if (data.contains("Effect")) {
                            ArrayList<String> cEffect = new ArrayList<>();
                            int e = Integer.parseInt(data.replace("Effect: ", ""));
                            for (int i = 0; i < e; i++) {
                                data = myReader.nextLine();
                                cEffect.add(data);
                            }
                            this.challenges.add(new Challenge(cName, cDescription, cEffect, this.group));
                        }
                        if (data.contains("Options")) {
                            Map<String, String> cOptions = new HashMap<>();
                            int e = Integer.parseInt(data.replace("Options: ", ""));
                            for (int i = 0; i < e; i++) {
                                data = myReader.nextLine();
                                String mOption = data.replace(data.substring(data.lastIndexOf(": ")), "");
                                String mEffect = data.substring(data.lastIndexOf(": ") + 2);
                                cOptions.put(mOption, mEffect);
                            }
                            this.challenges.add(new Challenge(cName, cDescription, cOptions, this.group));
                        }

                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private Challenge getRandomChallenge() {
        Random rand = new Random();
        int index = rand.nextInt(this.challenges.size());
        return this.challenges.get(index);
    }
}
