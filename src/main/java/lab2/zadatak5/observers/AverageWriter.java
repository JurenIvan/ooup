package lab2.zadatak5.observers;

import lab2.zadatak5.NumberSequence;

public class AverageWriter implements Observable<NumberSequence> {

    @Override
    public void update(NumberSequence notifier) {
        System.out.println("Average: " + notifier.getInternalList().stream().mapToInt(e -> e).average().orElseThrow(IllegalStateException::new));
    }
}
