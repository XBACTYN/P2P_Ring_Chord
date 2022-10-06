package Chord;

import java.util.Vector;

public class ChordNode {
    public Integer id;
    public ChordNode successor;
    public ChordNode predecessor;
    // переделать таблицу пальцев
    // первая колонка - Integer
    // вторая колонка - указатели на узлы, то есть ChordNode
    public Finger[] fingerTable = new Finger[4];
    public Vector <Integer> keys = new Vector(16);



    public ChordNode(Integer id){
        //Сделать геттеры и сеттеры
        this.id = id;
        //Вызывать функцию Update
    }
    public void Update(){

    }
    public int fingerStart(int i){
        return (this.id + (int)Math.pow(2, i))%16;
    }
    private ChordNode fingerNode(int i){
        return fingerTable[i].fingerSuccesor;
    }
    private void findSuccesor(){

    }
    public ChordNode findPredecessor(Integer key){
        ChordNode nodeKey = this;
        int d = (nodeKey.id<nodeKey.successor.id)?0:16;
        while(!((key > nodeKey.id)&&(key <= nodeKey.successor.id+d))) {
            nodeKey = nodeKey.closestPrecedingFinger(key);
            d = (nodeKey.id<nodeKey.successor.id)?0:16;
        }
        return nodeKey;
    }
    private ChordNode closestPrecedingFinger(Integer key){
        for(int i=3; i>=0; --i){
            if(this.id<key) {
                if ((fingerNode(i).id > this.id) && (fingerNode(i).id < key)) {
                    return fingerNode(i);
                }
            }
            else{
                if ((fingerNode(i).id > key) || (fingerNode(i).id < this.id)) {
                    return fingerNode(i);
                }
            }
        }
        return this;
    }
    private void updateTable(){

    }
}
