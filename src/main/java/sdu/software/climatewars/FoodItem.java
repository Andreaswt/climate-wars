package sdu.software.climatewars;

public class FoodItem extends Item {
    private int satisfactionLevel;

    public FoodItem(String name, int satisfactionLevel) {
        super(name);
        this.satisfactionLevel = satisfactionLevel;
    }

    public int getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(int satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }
}
