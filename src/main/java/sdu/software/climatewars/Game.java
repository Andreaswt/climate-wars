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
        stage.setScene(scene);
        stage.show();

        // How to access labels etc in controllers from start method
        GameController rc = fxmlLoader.getController();
        rc.setStatsText("Stats");
        rc.setOptionOneButton("Knap 1");
        rc.setOptionTwoButton("Knap 2");
        rc.setScenarioDescription("Scenario description");
        rc.setScenarioText("Scenario");
        rc.setBackgroundImage("Beach");
        rc.hideStats();
        //rc.setCharacterImageImage();

    }

    private void createRooms() {
        Room city, forest, cliffs, hilltops, university, club, beach, lake, fields, cornField;

        city = new Room("in an abandoned city");
        forest = new Room("in a dark forest", getRandomChallenge());
        cliffs = new Room("at the cliffs", getRandomChallenge());
        hilltops = new Room("at the hilltops by the cliffs", getRandomChallenge());
        university = new Room("in the university", getRandomChallenge());
        club = new Room("in a nightclub", getRandomChallenge());
        beach = new Room("at the beach", getRandomChallenge());
        lake = new Room("at the lake", getRandomChallenge());
        fields = new Room("at the fields", getRandomChallenge());
        cornField = new Room("in the cornfield", getRandomChallenge());

        // Abandoned city
        city.setExit("forest", forest);
        city.setExit("club", club);
        city.setExit("university", university);
        city.setExit("fields", fields);

        // Fields
        fields.setExit("cornfield", cornField);
        fields.setExit("city", city);

        // Cornfield
        cornField.setExit("fields", fields);

        // Club
        club.setExit("city", city);

        // Dark forest
        forest.setExit("city", city);
        forest.setExit("hilltops", hilltops);

        // Hilltops
        hilltops.setExit("cliffs", cliffs);
        hilltops.setExit("forest", forest);

        // Cliffs
        cliffs.setExit("hilltops", hilltops);

        // University
        university.setExit("lake", lake);
        university.setExit("city", city);

        // Lake
        lake.setExit("university", university);
        lake.setExit("beach", beach);

        // Beach
        beach.setExit("lake", lake);

        currentRoom = city;
    }

    public void play() {
        launch();

        printWelcome();

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

    private void printWelcome() {
        System.out.println();
        System.out.println("\033[1mWelcome to the Climate Wars!\033[0m");
        System.out.println("Climate Wars will teach you about the disastrous effects of climate change.");

        // Back story
        System.out.println();
        System.out.println("Back story: The year is 2130, due to a lack of action from the world as a whole to solve the climate crisis, a climate catastrophe has reached new highs." + "\n" + "This has led to a total collapse of society. Billions are dead due to food shortages, lack of shelter from the increasingly disastrous weather, and wars fought to gather what resources are left on earth." + "\n" + "The survivors that are now left must roam the lands to scavenge and hunt for food and resources. You must lead a group of people through the dangerous and harsh environments." + "\n" + "You will have to manage the needs of your group, making sure that there is enough food and making tough decisions along the way as the leader of the group." + "\n" + "Group members will come and go as you progress, you will meet new people that may join your ranks, and you will lose people as you attempt to endure the dangers of this world." + "\n" + "Your objective is to keep the group of survivors alive as long as possible, but eventually, the climate claims us all.");
        System.out.println("Good luck survivor.");
        System.out.println();

        System.out.println("Type '" + CommandWord.HELP + "' if you need help.\n");
        System.out.println("Your initial stats are: ");
        group.printStats();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO && currentRoom.getChallenges() == null) {
            group.eat();
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.STATS) {
            group.printStats();
        } else {
            if (commandWord == CommandWord.UNKNOWN) {
                System.out.println("I don't know what you mean...");
                return false;
            }
            if (currentRoom.getChallenges() != null) {
                for (String s : currentRoom.getChallenges().getOptions()) {
                    if (s.contains(commandWord.getCommandString())) {
                        currentRoom.getChallenges().applyEffect(commandWord.getCommandString());
                        group.printStats();
                        currentRoom.setChallenges(null);
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
            currentRoom.setChallenges(getRandomChallenge());
            currentRoom = nextRoom;

            // When entering a new place, there's 25% chance of finding a new person
            Random rand = new Random();
            double rollForNewMember = rand.nextInt(100);

            if (rollForNewMember <= 25) {
                System.out.println("You found a lone member straying around, and invited him to join the group.");
                this.group.addToGroup(1);
            }

            System.out.println(currentRoom.getLongDescription());
            this.currentRoom.getChallenges().applyEffect();
            if (!currentRoom.getChallenges().getHasOptions()) {
                this.currentRoom.setChallenges(null);
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
