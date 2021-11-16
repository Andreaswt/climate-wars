package sdu.software.climatewars;

public class Person {
    private String name;
    private int age;
    private int hunger;

    public Person(String name) {
        this.name = name;
        this.hunger = 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }
}
