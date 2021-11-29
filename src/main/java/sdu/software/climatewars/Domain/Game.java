package sdu.software.climatewars.Domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sdu.software.climatewars.GUI.GameController;
import sdu.software.climatewars.Text.Parser;
import sdu.software.climatewars.Text.Command;
import sdu.software.climatewars.Text.CommandWord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game extends Application {
    private final ArrayList<Challenge> challenges;
    private final Parser parser;
    private Room currentRoom;
    private final Group group;

    public Game() {
        this.group = new Group();
        this.challenges = new ArrayList<Challenge>();
        readFromFile();
        createRooms();
        parser = new Parser();
    }

    @Override
    public void start(Stage stage) throws Exception {

        /*FXMLLoader menu = new FXMLLoader(Game.class.getResource("menu.fxml"));
        Scene menuScene = new Scene(menu.load());
        stage.setTitle("Climate Wars");
        stage.setResizable(false);*/


        FXMLLoader game = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(game.load());
        stage.setTitle("Climate Wars");
        stage.setResizable(false);
        GameController rc = game.getController();

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

        // Pass group into controller, to post data in stats box
        rc.showStats(true, group);

        // Hvis Peters boks fjernes, kan man ikke bevæge spilleren.
        // Der skal være en text input på skærmen, for at kunne detecte key presses
        //rc.hidePetersBox();

        stage.setScene(scene);
        stage.show();
    }



    private void createRooms() {
        Room city, forest, cliffs, hilltops, university, club, beach, lake, fields, cornfield;

        city = new Room("City", "in an abandoned city", "City");
        forest = new Room("Forest", "in a dark forest", getRandomChallenge(), "Forrest");
        cliffs = new Room("Cliffs", "at the cliffs", getRandomChallenge(), "Cliffs");
        hilltops = new Room("Hilltops", "at the hilltops by the cliffs", getRandomChallenge(), "Hilltops");
        university = new Room("University", "in the university", getRandomChallenge(), "University");
        club = new Room("Club", "in a nightclub", getRandomChallenge(), "Club");
        beach = new Room("Beach", "at the beach", getRandomChallenge(), "Beach");
        lake = new Room("Lake", "at the lake", getRandomChallenge(), "Lake");
        fields = new Room("Fields", "at the fields", getRandomChallenge(), "Field");
        cornfield = new Room("Cornfield", "in the cornfield", getRandomChallenge(), "Cornfield");

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

        getWelcome();

        boolean finished = false;
        while (!finished) {

            if (this.group.getGroupSize() == 0) {
                finished = true;
                System.out.println();
                System.out.println("GAME OVER: You group have reached a number of 0.");
            } else {

                Command command = parser.getCommand();

            }
        }
        System.out.println("Thank you for playing. Good bye.");
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
                            ArrayList<String> cEffect = new ArrayList<String>();
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

    private String getWelcome() {
        String aString = "";

        aString += "Welcome to the Climate Wars! \n";
        aString += "Climate Wars will teach you about the disastrous effects of climate change. \n \n";

        // Back story
        aString += "Back story: The year is 2130, due to a lack of action from the world as a whole to solve the climate crisis, a climate catastrophe has reached new highs." + "\n" + "This has led to a total collapse of society. Billions are dead due to food shortages, lack of shelter from the increasingly disastrous weather, and wars fought to gather what resources are left on earth." + "\n" + "The survivors that are now left must roam the lands to scavenge and hunt for food and resources. You must lead a group of people through the dangerous and harsh environments." + "\n" + "You will have to manage the needs of your group, making sure that there is enough food and making tough decisions along the way as the leader of the group." + "\n" + "Group members will come and go as you progress, you will meet new people that may join your ranks, and you will lose people as you attempt to endure the dangers of this world." + "\n" + "Your objective is to keep the group of survivors alive as long as possible, but eventually, the climate claims us all.";
        aString += "Good luck survivor. \n \n";

        aString += "Type '" + CommandWord.HELP + "' if you need help.\n \n";
        aString += "Your initial stats are: \n";
        aString += group.getStats();
        aString += currentRoom.getLongDescription();
        return aString;
    }



}
