package control;

import java.util.Iterator;

import adt.ArrayList;
import adt.HashMap;

/**
 * @author hanyue1014
 */
public class TeachingAssignment {
    public static void main(String[] args) {
        // ArrayList<Integer> a = new ArrayList<>();

        // System.out.println(a);

        // ArrayList<Integer> b = new ArrayList<>(new Integer[] {1, 2, 3, 4, 5});

        // System.out.println(b);

        // b.insert(6).insert(0, 0);
        
        // System.out.println(b);
        // int c = b.remove(2);
        // System.out.println("c:" + c);

        // for (int i = 0; i < b.getNumberOfEntries(); i++)
        //     System.out.println(b.get(i));

        // for (Integer x: b)
        //     System.out.println(x);
        // for (Integer x: b.toArray(Integer.class))
        //     System.out.println(x);
        // for (Integer d: b.filter((el) -> {return el < 4;}))
        //     System.out.println(d);

        // test HashMap.BinarySearchTree
        HashMap<Integer, Integer> test = new HashMap<>();
        HashMap<Integer, Integer>.BinarySearchTree bst = test.new BinarySearchTree() {{
            insert(10, 10);
            insert(5, 5);
            insert(50, 50);
            insert(20, 20);
            insert(30, 30);
        }};

        Iterator itr = bst.iterator();
        System.out.println("Number of el: " + bst.getNumberOfElements());
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println(bst.delete(10).toString());
        System.out.println("Number of el: " + bst.getNumberOfElements());
        itr = bst.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        } 
        
    }
}
