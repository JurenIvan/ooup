package lab2.zadatak5.observers;

import lab2.zadatak5.NumberSequence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

public class FileWriter implements Observable<NumberSequence> {

    private String path;

    public FileWriter(String path) {
        this.path = path;
    }

    @Override
    public void update(NumberSequence notifier) {
        try {
            Files.writeString(Path.of(path), notifier.getInternalList().stream().map(Object::toString).collect(Collectors.joining("\n")), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
