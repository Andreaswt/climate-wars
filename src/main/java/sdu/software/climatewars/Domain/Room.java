package sdu.software.climatewars.Domain;

import java.util.Locale;
import java.util.HashMap;

public class Room {
    private final String name;
    private final HashMap<String, Room> exits;
    private final String backgroundImage;
    private Challenge challenge;

    public Room(String name, String bg) {
        this.name = name;
        exits = new HashMap<>();
        backgroundImage = bg;
    }

    public Room(String name, Challenge challenge, String bg) {
        this.name = name;
        exits = new HashMap<>();
        this.challenge = challenge;
        backgroundImage = bg;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExitName(String direction) {

        if(exits.containsKey("north: " + direction)){
            return exits.get("north: " + direction);
        } else if(exits.containsKey("south: " + direction)){
            return exits.get("south: " + direction);
        } else if(exits.containsKey("west: " + direction)){
            return exits.get("west: " + direction);
        } else if(exits.containsKey("east: " + direction)){
            return exits.get("east: " + direction);
        } else

        return exits.get(direction);
    }

    public HashMap<String, Room> getExits() {
        return this.exits;
    }

    public Challenge getChallenge() {
        return this.challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public String getName() {
        return this.name;
    }

    public String upperCaseOption(int index){
        if(this.challenge != null)
            return challenge.getOptions().get(index).toUpperCase(Locale.ROOT);
        else
            return "UNKNOWN";
    }
}