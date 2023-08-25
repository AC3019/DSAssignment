package control;

import adt.ArrayList;

/**
 * @author hanyue1014
 */
public class TeachingAssignment {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();

        System.out.println(a);

        ArrayList<Integer> b = new ArrayList<>(new Integer[] {1, 2, 3, 4, 5});

        System.out.println(b);

        b.insert(6).insert(0, 0);
        
        System.out.println(b);
        int c = b.remove(2);
        System.out.println("c:" + c);

        for (int i = 0; i < b.getNumberOfEntries(); i++)
            System.out.println(b.get(i));

        for (Integer x: b)
            System.out.println(x);

        for (Integer d: b.filter((el) -> {boolean f = el < 4; System.out.println("Is error here?"); return f;}))
            System.out.println(d);
    }
}
