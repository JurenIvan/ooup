package lab3.hr.fer.zemris.ooup.lab2.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Animal> clazz = (Class<Animal>) Class.forName("lab3.hr.fer.zemris.ooup.lab2.model.plugins." + animalKind);

        Constructor<?> ctr = clazz.getConstructor(String.class);
        return (Animal) ctr.newInstance(name);
    }
}
