package lab2.zadatak4.determiners;

import java.util.ArrayList;
import java.util.List;

public class InterpolatingDeterminer implements Determiner {

    @Override
    public double determine(List<Integer> list, int p) {
        list=new ArrayList<>(list);

        int len = list.size();
        if (p <= 100 * 0.5 / len)
            return list.get(0);
        if (p >= (100 - 0.5 * len))
            return list.get(len - 1);

        int i = (int) Math.round(p * len / 100.0);

        double vpi1 = (i - 0.5) / len;
        double vpi2 = (i + 0.5) / len;

        return list.get(i - 1) + (list.get(i) - list.get(i - 1)) / (vpi2 - vpi1) * (p / 100.0 - vpi1);
    }
}
