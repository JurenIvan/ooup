package lab2.zadatak4.generators;

import java.util.ArrayList;
import java.util.List;

public class LinearNumberGenerator implements NumberGenerator {

    private final int start;
    private final int end;
    private final int step;

    public LinearNumberGenerator(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> result = new ArrayList<>((end - start) / step + 1);
        for (int i = start; i <= end; i += step) {
            result.add(i);
        }
        return result;
    }
}
