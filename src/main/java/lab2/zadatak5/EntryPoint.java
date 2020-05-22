package lab2.zadatak5;

import lab2.zadatak5.observers.AverageWriter;
import lab2.zadatak5.observers.FileWriter;
import lab2.zadatak5.observers.MedianWriter;
import lab2.zadatak5.sources.FileInputSource;
import lab2.zadatak5.sources.KeyboardInputSource;
import lab2.zadatak5.sources.Source;

import java.io.IOException;

public class EntryPoint {

    private static final String path = "src/main/resources/file.txt";
    private static final String pathOut = "src/main/resources/out.txt";

    public static void main(String[] args) throws IOException {

        Source keyboardDS = new KeyboardInputSource();
        Source fileDS = new FileInputSource(path);

        NumberSequence numberSequence = new NumberSequence(fileDS);

        numberSequence.attachObserver(new FileWriter(pathOut));
        numberSequence.attachObserver(new AverageWriter());
        numberSequence.attachObserver(new MedianWriter());
 //       numberSequence.attachObserver(new SumWriter());
        numberSequence.attachObserver(e -> System.out.println("Sum: " + e.getInternalList().stream().mapToInt(y -> y).sum()));

        numberSequence.start();
    }
}
