package lab2.zadatak4;

import lab2.zadatak4.determiners.Determiner;
import lab2.zadatak4.determiners.InterpolatingDeterminer;
import lab2.zadatak4.determiners.RoundingDeterminer;

import java.util.List;

public class EntryPoint {

    public static void main(String[] args) {
//        System.out.println(new FibonacciGenerator(10).generate());
//        System.out.println(new LinearNumberGenerator(10, 20, 3).generate());
//        System.out.println(new NormalDistributionGenerator(10,2,1000).generate());

        Determiner roundingDeterminer = new RoundingDeterminer();
        Determiner interpolatingDeterminer = new InterpolatingDeterminer();

        DistributionTester dtRounding = new DistributionTester(() -> List.of(1, 10, 50), roundingDeterminer);
        DistributionTester dtInterpolating = new DistributionTester(() -> List.of(1, 10, 50), interpolatingDeterminer);

        System.out.println(dtRounding.determinePercentile(80));
        System.out.println(dtInterpolating.determinePercentile(80));
    }
}
