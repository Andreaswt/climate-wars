package sdu.software.climatewars.Domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Challenge {
    private final String name;
    private String description;
    private Map<String, String> options;
    private ArrayList<String> effect;
    private final boolean hasOptions;
    private final Group group;

    public Challenge(String name, String description, Map<String, String> options, Group group) {
        this.name = "\033[1m" + name.toUpperCase() + "\033[0m";
        this.description = description;
        if (this.description.contains("XX")) {
            this.description = this.description.replace("XX", String.valueOf(randomGroupSizeGenerator()));
        }
        this.options = options;
        this.hasOptions = true;
        this.group = group;
    }

    public Challenge(String name, String description, ArrayList<String> effect, Group group) {
        this.name = "\033[1m" + name.toUpperCase() + "\033[0m";
        this.description = description;
        this.effect = effect;
        this.hasOptions = false;
        this.group = group;
    }

    public void applyEffect() {
        if (this.effect != null) {
            for (String s : this.effect) {
                switch (s) {
                    case "Kill member" -> killMember();
                    case "Remove people and food" -> removeFoodAndPeople();
                    case "Fight" -> fight();
                    case "Flee" -> flee();
                    case "Kill members" -> killMembers();
                }
            }
        }
    }

    public void applyEffect(String option) {
        if (this.options.containsKey(option)) {
            String effect = this.options.get(option);
            switch (effect) {
                case "Kill member":
                case "Exile":
                    killMember();
                    break;
                case "Remove people and food":
                case "Give":
                    removeFoodAndPeople();
                    break;
                case "Fight":
                    fight();
                    break;
                case "Flee":
                    flee();
                    break;
                case "Kill members":
                    killMembers();
                    break;
                case "Merge":
                    merge();
                    break;
                case "Nothing":
                    break;
            }
        }
    }

    public void merge() {
        int membersToAdd = getRandomGroupSize();
        this.group.merge(membersToAdd);
    }

    public void fight() {
        // Get group size of opponents. Constant for now
        int opponentSize = getRandomGroupSize();

        // Fight member by member, until all members have fought.
        // Each member have a 50% chance of dying during fight
        System.out.println("Your group will fight against " + opponentSize + " opponents, with a 50/50 chance of dying during fight");
        for (int i = 0; i < opponentSize; i++) {
            if(this.group.getGroupSize() != 0){
                this.group.killMember(50);
            }
        }

        // Gain food for fighting, otherwise there's no reason to fight over fleeing
        System.out.println("You stole 10 units of food from your opponents while fighting them");
        this.group.addFood(10);
    }

    public void flee() {
        System.out.println("You fled. Now, continue your journey...");
    }

    public void killMember() {
        this.group.killMember(100);
    }

    public void killMembers() {
        this.group.killMember(100);
        this.group.killMember(100);
    }

    public void removeFoodAndPeople() {
        System.out.println("10 units of food have been removed");
        this.group.removeFood(10);
        this.group.killMember(100);
        this.group.killMember(100);
    }

    public boolean getHasOptions() {
        return this.hasOptions;
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> rString = new ArrayList<String>();
        if (this.options != null) {
            this.options.forEach((k, v) -> {
                rString.add(k);
            });
        }
        return rString;
    }

    @Override
    public String toString() {
        String s;
        s = "\n" + this.name + "\n" + this.description;

        if (this.options != null) {
            s += "\n" + "\n" + "Your options are:";

            for (String aS : getOptions()) {
                s += "\n";
                s += "[" + aS + "]";
            }
        }
        return s;
    }

    public int randomGroupSizeGenerator() {
        return (new Random().nextInt(7) + 2);
    }

    public int getRandomGroupSize() {
        return Integer.parseInt(description.replaceAll("[^0-9]", ""));
    }
}
