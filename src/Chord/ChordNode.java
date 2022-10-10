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

    public void initFingerTable(@NotNull ChordNode node){
        // переделать для инициализации таблицы в текущем классе
        // заполняем первую строчку в fingerTable
        fingerTable[0] = new Finger(fingerStart(0), node.findSuccesor(fingerStart(0)));
        System.out.println(fingerTable[0].start);
        System.out.println(fingerTable[0].fingerSuccesor.id);
        // predecessor = successor.predecessor;
        // заполняем поля predecessor и successor в node
        predecessor = node.findPredecessor(this.id);
        successor = predecessor.successor;

        for(int i=1; i<4; ++i){
            if((fingerStart(i)>=this.id)&&(fingerStart(i)<fingerTable[i-1].fingerSuccesor.id)){
                fingerTable[i] = new Finger(fingerStart(i), fingerTable[i-1].fingerSuccesor);
            }
            else{
                fingerTable[i] = new Finger(fingerStart(i), node.findSuccesor(fingerStart(i)));
            }
        }
    }


    public void updateOthers(){
        for(int i=0; i<4; ++i){
            ChordNode p = findPredecessor(this.id-(int)Math.pow(2, i));
            p.updateFingerTable(this, i);
        }
    }

    public void updateFingerTable(ChordNode s, int i){

        if(this.id<fingerTable[i].fingerSuccesor.id)
        {
            if(s.id>=this.id && s.id<fingerTable[i].fingerSuccesor.id)
            {
                fingerTable[i].fingerSuccesor=s;
                ChordNode p = predecessor;
                p.updateFingerTable(s,i);
            }
        }
        else{
            if(s.id>=this.id || s.id<fingerTable[i].fingerSuccesor.id)
            {
                fingerTable[i].fingerSuccesor=s;
                ChordNode p = predecessor;
                p.updateFingerTable(s,i);
            }
        }
    }

    public void join(ChordNode s){
        if( s !=null){
            initFingerTable(s);
            updateOthers();
        }
        else{
            for(int i=1;i<=4;++i){
                fingerTable[i].fingerSuccesor=this;

            }
            predecessor=this;
        }
    }

    public void printFingerTable(){
        for(int i=0; i<4; ++i) {
            System.out.println(this.fingerTable[i].start);
            System.out.println(this.fingerTable[i].fingerSuccesor.id);
        }
    }

}
