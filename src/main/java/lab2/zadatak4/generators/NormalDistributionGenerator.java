package lab2.zadatak4.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalDistributionGenerator implements NumberGenerator {

    private final int mean;
    private final int deviation;
    private final int numberOfElements;

    public NormalDistributionGenerator(int mean, int deviation, int numberOfElements) {
        this.mean = mean;
        this.deviation = deviation;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public List<Integer> generate() {
        Random random = new Random();
        List<Integer> result = new ArrayList<>(numberOfElements);

        for (int i = 0; i < numberOfElements; i++)
            result.add((int) Math.round(mean + random.nextGaussian() * deviation));

        return result;
    }
}
