package lab2.zadatak6;

public class EntryPoint {

    public static void main(String[] args) {
        Sheet s = new Sheet(5, 5);
        System.out.println();

        s.set("A1", "2");
        s.set("A2", "5");
        s.set("A3", "A1+A2");
        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));
        System.out.println();

        s.set("A1", "4");
        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));
        s.set("A4", "A1+A3");
        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));

        try {
            s.set("A1", "A3");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));


        System.out.println();

        s.set("B0", "1");
        s.set("B1", "1");
        s.set("B2", "B0+B1"); s.set("B3", "B1+B2"); s.set("B4", "B2+B3");
        s.set("C0", "B3+B4"); s.set("C1", "B4+C0"); s.set("C2", "C0+C1"); s.set("C3", "C1+C2"); s.set("C4", "C2+C3");


        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));

        s.set("B1", "2");

        System.out.println(s.print(Cell::getExp));
        System.out.println(s.print(e -> s.evaluate(e).toString()));
    }
}
