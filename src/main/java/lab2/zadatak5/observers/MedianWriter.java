package lab2.zadatak5.observers;

import lab2.zadatak5.NumberSequence;

import java.util.stream.Collectors;

public class MedianWriter implements Observable<NumberSequence> {

    @Override
    public void update(NumberSequence notifier) {
        var numbers = notifier.getInternalList().stream().sorted(Integer::compareTo).collect(Collectors.toList());
        int halfOfLen = numbers.size() / 2;
        if (numbers.size() % 2 == 0)
            System.out.println("Median: " + (numbers.get(halfOfLen) + numbers.get(halfOfLen - 1)) / 2);
         else
            System.out.println("Median: " + numbers.get(halfOfLen));
    }
}
