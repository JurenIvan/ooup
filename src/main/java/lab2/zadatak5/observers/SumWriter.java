package lab2.zadatak5.observers;

import lab2.zadatak5.NumberSequence;

public class SumWriter implements Observable<NumberSequence> {

    @Override
    public void update(NumberSequence notifier) {
        System.out.println("Sum: " + notifier.getInternalList().stream().mapToInt(e -> e).sum());
    }
}
