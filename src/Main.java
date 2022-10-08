import Chord.*;

import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void main(String[] args) {

//        ArrayList<Integer> list = new ArrayList<Integer>();
//        Collections.addAll(list, 0,1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15);
//        Collections.shuffle(list);
//        System.out.println(list);


        // создание узла 0, от которого будем джойнить и убирать узлы.
        ChordNode node0 = new ChordNode(0);
        node0.successor = node0;
        node0.predecessor = node0;
        for(int i=0; i<16; ++i) {
            node0.keys.add((Integer)i);
        }
        for(int i=0; i<4; ++i) {
            node0.fingerTable[i] = new Finger(node0.fingerStart(i), node0);
        }
        System.out.println(node0.keys);

        // пример для тестированя
        // node0.join(id: 2)
        ChordNode node2 = new ChordNode(2);
        node2.successor = node0;
        node2.predecessor = node0;
        node0.predecessor = node2;
        node0.successor = node2;
        for(int i=1; i<3; ++i) {
            node2.keys.add(i);
            node0.keys.removeElement(i);
        }
        System.out.println(node2.keys);
        System.out.println(node0.keys);
        for(int i=0; i<4; ++i) {
            node2.fingerTable[i] = new Finger(node2.fingerStart(i), node0);
        }
        for(int i=0; i<4; ++i) {
            if(node0.fingerTable[i].start < 2) {
                node0.fingerTable[i].fingerSuccesor = node2;
            }
        }


        ChordNode tmp0 = node2.findPredecessor(1);
        System.out.println(tmp0.id);
        ChordNode tmp2 = node2.findPredecessor(5);
        System.out.println(tmp2.id);

        ChordNode tmp3 = node2.findSuccesor(1);
        System.out.println(tmp3.id);
        ChordNode tmp4 = node2.findSuccesor(2);
        System.out.println(tmp4.id);

        System.out.println("");
    }

}