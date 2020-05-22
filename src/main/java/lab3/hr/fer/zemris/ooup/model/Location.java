package lab3.hr.fer.zemris.ooup.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location implements Comparable<Location> {

    private int line;
    private int column;

    public Location copy() {
        return new Location(line, column);
    }

    @Override
    public int compareTo(Location o) {
        var a = Integer.compare(line, o.line);
        if (a != 0) return a;
        return Integer.compare(column, o.column);
    }
}
