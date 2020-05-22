package lab2.zadatak4.generators;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements NumberGenerator {

    private final int numberOfElements;

    public FibonacciGenerator(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> result = new ArrayList<>(numberOfElements);
        int first = 1, second = 1;
        result.add(first);
        result.add(second);
        for (int i = 0; i < numberOfElements; i++) {
            int temp = second;
            second = second + first;
            first = temp;
            result.add(second);
        }
        return result;
    }
}
