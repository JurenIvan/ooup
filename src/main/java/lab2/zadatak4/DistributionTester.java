package lab2.zadatak4;

import lab2.zadatak4.determiners.Determiner;
import lab2.zadatak4.generators.NumberGenerator;

import static java.util.Objects.requireNonNull;

public class DistributionTester {

    private final NumberGenerator numberGenerator;
    private final Determiner determiner;

    public double determinePercentile(int p) {
        return determiner.determine(numberGenerator.generate(), p);
    }

    public DistributionTester(NumberGenerator numberGenerator, Determiner determiner) {
        requireNonNull(numberGenerator, "Percentile determinator strategy must not be null!.");
        requireNonNull(numberGenerator, "Number generating strategy must not be null!.");

        this.numberGenerator = numberGenerator;
        this.determiner = determiner;
    }
}
