import Chord.*;

import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<Integer>();
        Collections.addAll(list, 0,1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15);
        Collections.shuffle(list);
        System.out.println(list);

        Chord ring = new Chord();
        ring.AddNode(list.get(0));

        System.out.println("");
    }

}