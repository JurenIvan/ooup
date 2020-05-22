package lab2.zadatak5;

import lab2.zadatak5.observers.Observable;
import lab2.zadatak5.sources.Source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberSequence {

    private ArrayList<Integer> internalList = new ArrayList<>();
    private Set<Observable<NumberSequence>> observables = new HashSet<>();

    private Source dataSource;

    public NumberSequence(Source dataSource) {
        this.dataSource = dataSource;
    }

    public void start() {

        new Thread(() -> dataSource.getNext().ifPresent(e -> {
            internalList.add(e);
            notifyObservers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            this.start();
        })).start();

    }

    public void attachObserver(Observable<NumberSequence> observable) {
        observables.add(observable);
    }

    public void detachObserver(Observable<NumberSequence> observable) {
        observables.remove(observable);
    }

    private void notifyObservers() {
        observables.forEach(e -> e.update(this));
    }

    public List<Integer> getInternalList() {
        return internalList;
    }
}
