package lab2.zadatak6;

import java.util.Collection;
import java.util.function.Function;

public class Sheet {

    private Cell[][] cells;

    public Sheet(int rows, int cols) {
        cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j] = new Cell(this);
    }

    public void set(String ref, String content) {
        refResolve(ref).setExp(content);
    }

    public Cell cell(String ref) {
        return refResolve(ref);
    }

    public Collection<Cell> getRefs(Cell cell) {
        return cell.getCellsDependantOnMe();
    }

    public Double evaluate(Cell cell) {
        return cell.getValue();
    }

    public String print(Function<Cell, String> cellStringFunction) {
        StringBuilder sb = new StringBuilder();

        sb.append("      ");
        for (int i = 0; i < cells.length; i++) {
            sb.append(String.format("#   %3d  #", i));
        }
        sb.append("\n");

        for (int i = 0; i < cells.length; i++) {
            sb.append(String.format("%3s   ", (char) (i + 'A')));
            for (int j = 0; j < cells[0].length; j++) {
                String valueToPrint = cellStringFunction.apply(cells[i][j]);
                sb.append(String.format("# %6.6s #", valueToPrint == null ? "" : valueToPrint));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Cell refResolve(String ref) {
        try {
            int row = extractLettersPart(ref);
            int column = extractNumbersPart(ref);

            return cells[row][column];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private int extractNumbersPart(String ref) {
        char[] refChars = ref.toCharArray();
        int result = 0;
        for (int i = refChars.length - 1; Character.isDigit(refChars[i]); i--)
            result = result + (int) Math.pow(10, refChars.length - i - 1) * (refChars[i] - '0');

        return result;
    }

    private int extractLettersPart(String ref) {
        char[] refChars = ref.toCharArray();
        int result = 0;
        for (int i = 0; Character.isAlphabetic(refChars[i]); i++)
            result = result * ('z' - 'a') + Character.toLowerCase(refChars[i]) - 'a';

        return result;
    }
}
