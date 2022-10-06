import Chord.*;

import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void main(String[] args) {

//        ArrayList<Integer> list = new ArrayList<Integer>();
//        Collections.addAll(list, 0,1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15);
//        Collections.shuffle(list);
//        System.out.println(list);

        Chord ring = new Chord();
        System.out.println(ring.Ring[0].keys);


        ChordNode node2 = new ChordNode(2);
        node2.successor = ring.Ring[0];
        node2.predecessor = ring.Ring[0];
        ring.Ring[0].predecessor = node2;
        ring.Ring[0].successor = node2;
        for(int i=1; i<3; ++i) {
            node2.keys.add(i);
            ring.Ring[0].keys.removeElement(i);
        }
        System.out.println(node2.keys);
        System.out.println(ring.Ring[0].keys);
        for(int i=0; i<4; ++i) {
            node2.fingerTable[i] = new Finger(node2.fingerStart(i), ring.Ring[0]);
        }
//        for(int i=0; i<4; ++i) {
//            System.out.println(node2.fingerTable[i].start);
//            System.out.println(node2.fingerTable[i].fingerSuccesor.id);
//            System.out.println("\n");
//        }

        for(int i=0; i<4; ++i) {
//            System.out.println(ring.Ring[0].fingerTable[i].start);
            if(ring.Ring[0].fingerTable[i].start < 2) {
                ring.Ring[0].fingerTable[i].fingerSuccesor = node2;
            }
        }
//        System.out.println("\nnode0");
//        for(int i=0; i<4; ++i) {
//            System.out.println(ring.Ring[0].fingerTable[i].start);
//            System.out.println(ring.Ring[0].fingerTable[i].fingerSuccesor.id);
//            System.out.println("\n");
//        }

        ChordNode tmp0 = node2.findPredecessor(1);
        System.out.println(tmp0.id);
        ChordNode tmp2 = node2.findPredecessor(5);
        System.out.println(tmp2.id);

        System.out.println("");
    }

}