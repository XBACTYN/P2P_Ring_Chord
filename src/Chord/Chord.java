package Chord;

import java.util.Vector;

// Chord - это модель сети и в ней хранится массив узло и таблица хэшей, больше ничего
// все заимодействия между узлами внутри узлов через доступ к указателям
// вместо указателе в идеале сделать соединения сокетами,
// но это слишком много раелизовывать(поэтому пользуемся указателями)
// в сети обязательно изначально должен быть узел 0
public class Chord {
    public ChordNode[] Ring;
    // HashTable

    public Chord(){
        System.out.println("Chord construct!");
        this.Ring = new ChordNode[16];
        ChordNode node0 = new ChordNode(0);
        node0.successor = node0;
        node0.predecessor = node0;
//        System.out.println(node0.successor.id);
//        System.out.println(node0.predecessor.id);
        for(int i=0; i<16; ++i) {
            node0.keys.add((Integer)i);
        }
//        System.out.println(node0.keys);
        for(int i=0; i<4; ++i) {
            node0.fingerTable[i] = new Finger(node0.fingerStart(i), node0);
        }
//        for(int i=0; i<4; ++i) {
//            System.out.println(node0.fingerTable[i].start);
//            System.out.println(node0.fingerTable[i].fingerSuccesor.id);
//            System.out.println("\n");
//        }
        this.Ring[0] = node0;
    }

//    public ChordNode Find(Integer id){
//        return null;
//    }
//
//    public void AddNode(Integer id){
//
//        Ring.addElement(new ChordNode(id));
//    }
//
//    public void DeleteNode(Integer id){
//
//    }

}
