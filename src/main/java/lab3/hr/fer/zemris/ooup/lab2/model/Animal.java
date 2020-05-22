package lab3.hr.fer.zemris.ooup.lab2.model;

public abstract class Animal {

    public abstract String name();

    public abstract String greet();

    public abstract String menu();

    public void animalPrintGreeting() {
        System.out.printf("Greet of %s is %s!%n", name(), greet());
    }

    public void animalPrintMenu() {
        System.out.printf("Menu of %s is %s!%n", name(), menu());
    }

}
