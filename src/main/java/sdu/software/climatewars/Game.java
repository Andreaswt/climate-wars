package sdu.software.climatewars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Climate Wars");
        stage.setResizable(false);
        GameController rc = fxmlLoader.getController();

        // Default room setup
        rc.setupRoom(this.currentRoom);

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
        city.setExit("south: forest", forest);
        city.setExit("east: club", club);
        city.setExit("north: university", university);
        city.setExit("west: fields", fields);

        // Fields
        fields.setExit("north: cornfield", cornfield);
        fields.setExit("east: city", city);

        // Cornfield
        cornfield.setExit("south: fields", fields);

        // Club
        club.setExit("west: city", city);

        // Dark forest
        forest.setExit("north: city", city);
        forest.setExit("east: hilltops", hilltops);

        // Hilltops
        hilltops.setExit("south: cliffs", cliffs);
        hilltops.setExit("west: forest", forest);

        // Cliffs
        cliffs.setExit("north: hilltops", hilltops);

        // University
        university.setExit("north: lake", lake);
        university.setExit("south: city", city);

        // Lake
        lake.setExit("south: university", university);
        lake.setExit("east: beach", beach);

        // Beach
        beach.setExit("west: lake", lake);

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
                finished = processCommand(command);
            }
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void readFromFile() {
        //Read file
        try {
            File myObj = new File("src/main/java/sdu/software/climatewars/Challenges.txt");
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

    private boolean processCommand(Command command) {
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

        Room nextRoom = currentRoom.getExit(direction);

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
            if (!currentRoom.getChallenge().getHasOptions()) {
                this.currentRoom.setChallenge(null);
            }
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
}
