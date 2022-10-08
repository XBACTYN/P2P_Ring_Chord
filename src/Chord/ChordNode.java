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
    public ChordNode findSuccesor(Integer key){
        ChordNode n =  this.findPredecessor(key);
        return n.successor;
    }

    public ChordNode findPredecessor(Integer key){
        ChordNode nodeKey = this;
        int d = (nodeKey.id<nodeKey.successor.id)?0:16;
        // получение
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

//    public ChordNode findChordNode(Integer key){
//
//    }

    public void initFingerTable(ChordNode node){
        // заполняем первую строчку в fingerTable
        node.fingerTable[0].start = node.fingerStart(0);
        node.fingerTable[0].fingerSuccesor = findSuccesor(fingerTable[0].start);
//        node.predecessor = ???
//        predecessor = successor.predecessor;
        // заполняем поля predecessor и successor в node
        node.predecessor = findPredecessor(node.id);
        node.successor = node.predecessor.successor;
        // меняем predecessor в следующем узле после node на наш создающийся
        node.successor.predecessor = node;// в сокете: передаем команду на change predecessor, тот выполняет коменду и отправляет сигнал "готово"
        // меняем successor в узле перед node
        node.predecessor.successor = node;

        for(int i=1; i<4; ++i){
            node.fingerTable[i].start = node.fingerStart(i);
            if((node.fingerStart(i)>=this.id)&&(node.fingerStart(i)<node.fingerTable[i-1].fingerSuccesor.id)){
                node.fingerTable[i].fingerSuccesor = node.fingerTable[i-1].fingerSuccesor;
            }
            else{
                node.fingerTable[i].fingerSuccesor = findSuccesor(fingerTable[i].start);
            }
        }
    }


    public void updateOthers(){
        for(int i=0; i<4; ++i){
            ChordNode p = findPredecessor(this.id-(int)Math.pow(2, i));
            // p.updateFingerTable(this.id, i);
        }
    }


    public void printFingerTable(){
        for(int i=0; i<4; ++i) {
            System.out.println(this.fingerTable[i].start);
            System.out.println(this.fingerTable[i].fingerSuccesor.id);
        }
    }
    private void updateTable(){

    }
}
