package sdu.software.climatewars.Domain;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private final String name;
    private final String description;
    private final HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private Challenge challenge;
    private String backgroundImage;

    public Room(String name, String description, String bg) {
        this.name = name;
        this.description = description;
        exits = new HashMap<String, Room>();
        backgroundImage = bg;
    }

    public Room(String name, String description, Challenge challenge, String bg) {
        this.name = name;
        this.description = description;
        exits = new HashMap<String, Room>();
        this.challenge = challenge;
        backgroundImage = bg;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getLongDescription() {
        if (this.challenge == null)
            return "You are " + description + ".\n" + getExitString();
        else
            return "You are " + description + ".\n" + getExitString() + "\n" + this.challenge;
    }

    private String getExitString() {
        String returnString = "\n\033[3mWhere would you like to go?\033[0m\n";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += "[\033[1m" + exit + "\033[0m] ";
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
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
}