package sdu.software.climatewars.Domain;

import java.util.ArrayList;
import java.util.Random;

public class Group {
    private int satiety = 100;
    final private int foodSatietyValue = 5;
    private int food;
    private int members;

    public Group() {
        addToGroup(6);
    }

    public void addToGroup(int toAdd) {
        // Add 6 persons
        for (int i = 0; i < toAdd; i++) {
            if(this.members<10) {
                this.members++;
            }
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
        for (int i = 0; i < members; i++) {
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
                        starvationMessage = false;
                    }
                    killMember(20);
                }
            }
        }
    }

    public void killMember(double chanceOfDeath) {

        // Generate random number, to remove random person member from group and a random number to roll for death
        Random rand = new Random();
        double rollForDeath = rand.nextDouble() * 100;

        if(chanceOfDeath < rollForDeath){
            return;
        }

        this.members--;
    }

    public void merge(int membersToAdd) {
        addToGroup(membersToAdd);
    }

    public int getMembers(){
        return this.members;
    }

    public int getFood() {
        return this.food;
    }

    public int getSatiety() {
        return this.satiety;
    }
}
