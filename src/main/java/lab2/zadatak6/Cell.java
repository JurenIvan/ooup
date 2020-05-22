package lab2.zadatak6;

import lab2.zadatak5.observers.Observable;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Cell implements Observable<Cell> {

    public static final String DECIMAL_NUMBER_REGEX = "^[+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))$";

    private String exp;
    private Double value;
    private Sheet sheet;

    //used for observers
    private Set<Cell> cellsDependantOnMe;

    //parsed exp
    private List<Cell> parsedCellsFromExp;
    private Double parsedValuesFromExpSummed;

    public Cell(Sheet sheet) {
        this.sheet = sheet;

        this.cellsDependantOnMe = new HashSet<>();
        this.parsedCellsFromExp = new ArrayList<>();
        this.parsedValuesFromExpSummed = 0.0;
        this.value = 0.0;
        this.exp = "";
    }

    public String getExp() {
        return exp;
    }

    public double getValue() {
        if (value != null) return value;
        this.value = parsedCellsFromExp.stream().mapToDouble(Cell::getValue).sum();
        this.value += parsedValuesFromExpSummed != null ? parsedValuesFromExpSummed : 0.0;

        return this.value;
    }

    public void setExp(String exp) {
        this.value = null;
        this.exp = exp;

        if (exp != null && !exp.isBlank()) {
            List<Cell> newCells = stream(exp.split("\\+")).filter(e -> !e.trim().matches(DECIMAL_NUMBER_REGEX)).map(sheet::cell).collect(toList());
            if (newCells.stream().anyMatch(e -> e.hasDependency(this)))
                throw new IllegalArgumentException("Circular Dependency");
            parsedCellsFromExp.forEach(e -> e.cellsDependantOnMe.remove(this));
            parsedCellsFromExp = newCells;
            parsedCellsFromExp.forEach(e -> e.cellsDependantOnMe.add(this));

            parsedValuesFromExpSummed = stream(exp.split("\\+")).filter(e -> e.matches(DECIMAL_NUMBER_REGEX)).mapToDouble(Double::parseDouble).sum();
        }

        notifyDependant();
    }

    private void notifyDependant() {
        cellsDependantOnMe.forEach(e -> e.update(this));
    }

    private boolean hasDependency(Cell cell) {
        if (this == cell) return true;
        return this.parsedCellsFromExp.stream().anyMatch(e -> e.hasDependency(cell));
    }

    public Collection<Cell> getCellsDependantOnMe() {
        return cellsDependantOnMe;
    }

    @Override
    public void update(Cell notifier) {
        this.value = null;
        getValue();
        cellsDependantOnMe.forEach(e -> e.update(this));
    }
}
