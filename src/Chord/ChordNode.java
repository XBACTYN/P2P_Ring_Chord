package Chord;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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

    public boolean doesBelong(int idx, int start, int end, boolean leftIncluded, boolean rightIncluded) {
        if (leftIncluded && rightIncluded) {  // [..., ...]
            if (start < end) {
                return (idx >= start && idx <= end);
            }
            else if (end < start) {
                return !(idx > end && idx < start);
            }
            else {
                return true;
            }
        }
        else if (leftIncluded && !(rightIncluded)) {  // [..., ...)
            if (start < end) {
                return (idx >= start && idx < end);
            }
            else if (end < start) {
                return !(idx >= end && idx < start);
            }
            else {
                return true;
            }
        }
        else if (!leftIncluded && rightIncluded) {  // (..., ...]
            if (start < end) {
                return (idx > start && idx <= end);
            }
            else if (end < start) {
                return !(idx > end && idx <= start);
            }
            else {
                return true;
            }
        }
        else {  // (..., ...)
            if (start < end) {
                return (idx > start && idx < end);
            }
            else if (end < start) {
                return !(idx >= end && idx <= start);
            }
            else {
                return !(idx == start);
            }
        }
    }

    public ChordNode(Integer id){
        this.id = id;
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
        return n.fingerTable[0].fingerSuccesor;
    }

    public ChordNode findPredecessor(Integer key){
//        System.out.println("key "+key);
        ChordNode nodeKey = this;
        // поправить условие для варианта node.id>node.successor
//        int d = (key<nodeKey.fingerTable[0].fingerSuccesor.id)?0:16;
//        int k = (key<nodeKey.fingerTable[0].fingerSuccesor.id)?16:0;
        // получение
        while(!(doesBelong(key, nodeKey.id, nodeKey.fingerTable[0].fingerSuccesor.id, false, true))) { // (key > (nodeKey.id - k))&&(key <= (nodeKey.fingerTable[0].fingerSuccesor.id+d))
            nodeKey = nodeKey.closestPrecedingFinger(key);
//            d = (key<nodeKey.fingerTable[0].fingerSuccesor.id)?0:16;
//            k = (key<nodeKey.fingerTable[0].fingerSuccesor.id)?16:0;
        }
        return nodeKey;
    }
    private ChordNode closestPrecedingFinger(Integer key){
        for(int i=3; i>=0; --i){
            if(doesBelong(fingerNode(i).id, this.id, key, false, false)) {
                return fingerNode(i);
            }
        }
        return this;
    }


    public void initFingerTable(@NotNull ChordNode node){
        // переделать для инициализации таблицы в текущем классе
        // заполняем первую строчку в fingerTable
        System.out.println("fuck "+node.findSuccesor(fingerStart(0)).id + "hsvkf"+fingerStart(0));
        fingerTable[0] = new Finger(fingerStart(0), node.findSuccesor(fingerStart(0)));
        // заполняем поля predecessor и successor в node\
        predecessor = node.findPredecessor(this.id);
//        successor = predecessor.successor;

        // меняем successor в узле перед node
        predecessor.successor = this;
        // меняем successor в узле перед node
//        successor.predecessor = this;

        for(int i=1; i<4; ++i){
            if(doesBelong(fingerStart(i), this.id, fingerTable[i-1].fingerSuccesor.id, true, false)){
                fingerTable[i] = new Finger(fingerStart(i), fingerTable[i-1].fingerSuccesor);
            }
            else{
                fingerTable[i] = new Finger(fingerStart(i), node.findSuccesor(fingerStart(i)));
            }
        }
        printFingerTable();
    }


    public void updateOthers(){

        for(int i=0; i<4; ++i){
            int tmp = ((this.id-(int)Math.pow(2, i))<0)?16:0;
            ChordNode p = findPredecessor((this.id+1-(int)Math.pow(2, i))+tmp);
//            System.out.println("p is predecessor of "+this.id+" is "+p.id);
            p.updateFingerTable(this, i);
        }
    }

    public void updateFingerTable(ChordNode s, int i){
        if(fingerStart(i)!=fingerTable[i].fingerSuccesor.id) {
            if (doesBelong(s.id, this.id, fingerTable[i].fingerSuccesor.id, true, false)) {
                fingerTable[i].fingerSuccesor = s;
                ChordNode p = predecessor;
                p.updateFingerTable(s, i);
            }
            // меняем predecessor в следующем узле после node на наш создающийся
//            int d = (s.id<this.id)?0:16;
//            if((s.id>(this.predecessor.id))&&(s.id<(this.id+d))){
//                successor.predecessor = this;
//            }
        }
    }

    public void join(ChordNode s){
        if( s.predecessor !=null){
            initFingerTable(s);
            updateOthers();
        }
        else{
            for(int i=0;i<4;++i){
                fingerTable[i]= new Finger(fingerStart(i),this);

            }
            predecessor = this;
            successor = this;
        }
    }

    public void printFingerTable(){
        for(int i=0; i<4; ++i) {
            System.out.println("start: "+this.fingerTable[i].start);
            System.out.println("successor: "+this.fingerTable[i].fingerSuccesor.id);
        }
    }

}
