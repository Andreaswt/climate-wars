package sdu.software.climatewars;

import java.util.ArrayList;
import java.util.Random;

public class Group {
    private final Inventory inventory;
    private int satiety = 100;
    final private int foodSatietyValue = 5;
    private int food;
    private final ArrayList<Person> members = new ArrayList<>();

    public Group() {
        this.inventory = new Inventory();
        addToGroup(6);
    }

    public void addToGroup(int toAdd) {
        // Add 6 persons
        for (int i = 0; i < toAdd; i++) {
            Person member = new Person("Person" + i);
            this.members.add(member);
        }

        // Add 10 food units
        this.food = 10;
    }

    public void addFood(int foodToAdd) {
        this.food += foodToAdd;
    }

    public void removeFood(int foodToRemove) {
        this.food -= foodToRemove;
        if (this.food < 0) { this.food = 0; }
    }

    public void eat() {
        boolean starvationMessage = true;
        for (int i = 0; i < getGroupSize(); i++) {
            if (this.food > 0){
                this.food--;
                if (this.satiety < 100){
                    this.satiety += this.foodSatietyValue;
                }
            }
            else{
                if (this.satiety > 0) {
                    this.satiety -= this.foodSatietyValue;
                }
                else{
                    if (starvationMessage == true) {
                        System.out.println("The group has 0% satiety, group members might die of starvation");
                        starvationMessage = false;
                    }
                    killMember(20);
                }
            }
        }
    }

    public void killMember(double chanceOfDeath) {
        int groupSize = getGroupSize();

        // Generate random number, to remove random person member from group and a random number to roll for death
        Random rand = new Random();
        double rollForDeath = rand.nextDouble() * 100;

        if(chanceOfDeath < rollForDeath){
            return;
        }

        int index = rand.nextInt(groupSize);

        this.members.remove(index);
        System.out.println("A member has been killed.");
    }

    public void merge(int membersToAdd) {
        addToGroup(membersToAdd);
        System.out.println("Groups have been merged, and your group now have " + membersToAdd + " new members.");
    }

    public void printStats() {
        System.out.println();
        System.out.println("---- Your stats are ----");

        System.out.println("Food: " + food);

        System.out.println();
        System.out.println("Group satiety: " + this.satiety + "%");

        System.out.println();
        System.out.println("Group size: " + ((members != null) ? getGroupSize() : "No members"));
    }

    public int getGroupSize() {
        if (this.members != null) {
            return this.members.size();
        }
        else {
            return 0;
        }
    }
}
