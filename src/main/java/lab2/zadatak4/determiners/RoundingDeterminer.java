package lab2.zadatak4.determiners;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class RoundingDeterminer implements Determiner {

    @Override
    public double determine(List<Integer> list, int p) {
        list=new ArrayList<>(list);

        list.sort(Integer::compareTo);
        int rounded = (int) min(ceil(p * list.size() / 100.0), list.size());
        return list.get(rounded - 1);
    }
}
