package lab3.hr.fer.zemris.ooup.lab2.model.plugins;

import lab3.hr.fer.zemris.ooup.lab2.model.Animal;

public class Tiger extends Animal {

    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "grrrr";
    }

    @Override
    public String menu() {
        return "zebras";
    }

    public Tiger(String name) {
        this.name = name;
    }
}
