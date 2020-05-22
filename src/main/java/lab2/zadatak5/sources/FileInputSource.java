package lab2.zadatak5.sources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class FileInputSource implements Source {

    private BufferedReader reader;

    public FileInputSource(String path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path));
    }

    @Override
    public Optional<Integer> getNext() {
        try {
            String line = reader.readLine();
            if (line == null) {
                reader.close();
                return empty();
            }
            return of(parseInt(line));
        } catch (IOException | NumberFormatException e) {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return empty();
        }
    }
}
