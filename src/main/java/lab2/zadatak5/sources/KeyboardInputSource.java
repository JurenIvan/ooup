package lab2.zadatak5.sources;

import java.util.Optional;
import java.util.Scanner;

import static java.util.Optional.*;

public class KeyboardInputSource implements Source {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public Optional<Integer> getNext() {
        if (scanner.hasNextInt())
            return of(scanner.nextInt());

        scanner.close();
        return empty();
    }
}
