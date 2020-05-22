package lab3.hr.fer.zemris.ooup.lab2.model.plugins;

import lab3.hr.fer.zemris.ooup.lab2.model.Animal;

public class Parrot extends Animal {

    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "squawk-squawk";
    }

    @Override
    public String menu() {
        return "meat and nuts";
    }

    public Parrot(String name) {
        this.name = name;
    }
}
