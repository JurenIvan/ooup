package lab3.hr.fer.zemris.ooup.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectionRange {

    private Location start;
    private Location end;

    public void swapStartAndEndIfNecessary() {
        if (end.compareTo(start) < 0) {
            Location start = this.start;
            this.start = end;
            end = start;
        }
    }

    public SelectionRange copy() {
        return new SelectionRange(start.copy(), end.copy());
    }
}
