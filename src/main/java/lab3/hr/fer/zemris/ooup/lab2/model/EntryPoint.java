package lab3.hr.fer.zemris.ooup.lab2.model;

import java.lang.reflect.InvocationTargetException;

public class EntryPoint {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Animal tiger = AnimalFactory.newInstance("Tiger", "Tigar teo");
        Animal parrot = AnimalFactory.newInstance("Parrot", "Papiga papa");

        parrot.animalPrintGreeting();
        parrot.animalPrintMenu();

        System.out.println();

        tiger.animalPrintGreeting();
        tiger.animalPrintMenu();
    }
}
