package Chord;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Vector;

public class ChordNode {
    public Integer id;
    public ChordNode successor;
    public ChordNode predecessor;
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
        System.out.println(this.id+".findSuccessor "+key);
        ChordNode n =  this.findPredecessor(key);
        System.out.println("findSuccessor n: "+n.id);
//        return n.fingerTable[0].fingerSuccesor;
        return n.successor;//добавлено на время
    }

    public ChordNode findPredecessor(Integer key) {
//        System.out.println("findPredecessor "+key);
//        System.out.println("findPredecessor top");
        ChordNode nodeKey = this;
        // получение
        //АХТУНГ!!!!!!!!!!!!! nodekey.id совпадает с finger[0] succesor, в while не входит и всё.
//        while(!(doesBelong(key, nodeKey.id, nodeKey.fingerTable[0].fingerSuccesor.id, false, true)))
        System.out.println(key+" not contains in("+nodeKey.id+";"+nodeKey.fingerTable[0].fingerSuccesor.id+"] "
                +doesBelong(key, nodeKey.id, nodeKey.fingerTable[0].fingerSuccesor.id, false, true));
        while (!(doesBelong(key, nodeKey.id, nodeKey.fingerTable[0].fingerSuccesor.id, false, true))) { // (key > (nodeKey.id - k))&&(key <= (nodeKey.fingerTable[0].fingerSuccesor.id+d))
            System.out.println(nodeKey.id+".closest " + key);
            nodeKey = nodeKey.closestPrecedingFinger(key); //Неправильно присваивается nodeKey
            System.out.println(key+" not contains in("+nodeKey.id+";"+nodeKey.fingerTable[0].fingerSuccesor.id+"] "
                    +doesBelong(key, nodeKey.id, nodeKey.fingerTable[0].fingerSuccesor.id, false, true));
        }
        System.out.println("findPred return: "+nodeKey.id);
        return nodeKey;
    }
    private ChordNode closestPrecedingFinger(Integer key){
        System.out.println(" key: "+key);
        for(int i=3; i>=0; --i){
            //ОШИБКА КРАЙНЯЯ В ИФ. НЕ ВЫПОЛНЯЕТСЯ УСЛОВИЕ. ПРОВЕРИТЬ ЕГО ПРАВИЛЬНОСТЬ.
            System.out.println("iteration: "+i);
            System.out.println(fingerNode(i).id+" contains in("+this.id+";"+key+") "+
                    doesBelong(fingerNode(i).id, this.id, key, false, false));
            if(doesBelong(fingerNode(i).id, this.id, key, false, false)) {
                System.out.println("closest return: "+fingerNode(i).id);
                return fingerNode(i);
            }
        }
        return this;
    }


    public void initFingerTable(@NotNull ChordNode node){
        System.out.println("INIT");
        // заполняем первую строчку в fingerTable
        System.out.println("finger node id"+node.id+"finger start this"+fingerStart(0));
        System.out.println("fingerstart "+node.findSuccesor(fingerStart(0)));
        System.out.println(" init id"+node.findSuccesor(fingerStart(0)).id);
        fingerTable[0] = new Finger(fingerStart(0), node.findSuccesor(fingerStart(0)));
        // заполняем поля predecessor и successor в node\
        predecessor = node.findPredecessor(this.id);
//        successor = fingerTable[0].fingerSuccesor;



        for(int i=1; i<4; ++i){
//            System.out.println("doesBelong: "+doesBelong(fingerStart(i), this.id, fingerTable[i-1].fingerSuccesor.id, true, false));
            //System.out.println("interval ["+this.id+";"+fingerTable[i-1].fingerSuccesor.id+") value "+fingerStart(i));
            System.out.println("init start"+this.fingerTable[i-1]);
            if(doesBelong(fingerStart(i), this.id, fingerTable[i-1].fingerSuccesor.id, true, false)){
                fingerTable[i] = new Finger(fingerStart(i), fingerTable[i-1].fingerSuccesor);
//                System.out.println("if: save "+fingerTable[i].fingerSuccesor.id);
            }
            else{
//                System.out.println("fingerStart "+fingerStart(i)+" node(fingerStart) "+node.findSuccesor(fingerStart(i)).id);
                fingerTable[i] = new Finger(fingerStart(i), node.findSuccesor(fingerStart(i)));
                System.out.println("else: change "+node.id+" "+node.findSuccesor(fingerStart(i)).id);
            }
            System.out.println("finger[i] "+fingerTable[i].fingerSuccesor.id);
        }
        // меняем successor в узле перед node
        predecessor.successor = this;
    }


    public void updateOthers(){
        System.out.println("UPDATE");
        for(int i=0; i<4; ++i){
            int tmp = ((this.id-(int)Math.pow(2, i))<0)?16:0;
            ChordNode p = findPredecessor((this.id+1-(int)Math.pow(2, i))+tmp);
            System.out.println("p: "+p.id+" key " + (this.id+1-(int)Math.pow(2, i)+tmp));
            p.updateFingerTable(this, i);
        }
        successor=fingerTable[0].fingerSuccesor;
        successor.predecessor=this;
    }

    public void updateFingerTable(ChordNode s, int i){
        if(fingerStart(i)!=fingerTable[i].fingerSuccesor.id) {
            if (doesBelong(s.id, fingerStart(i), fingerTable[i].fingerSuccesor.id, true, false)) {
                System.out.println("update s: "+s.id);
                fingerTable[i].fingerSuccesor = s;
                ChordNode p = predecessor;
                p.updateFingerTable(s, i);
            }
        }
    }

    public void join(ChordNode s){
        if( s != null){
            initFingerTable(s);
            updateOthers();
            printFingerTable();
        }
        else{
            for(int i=0;i<4;++i){
                fingerTable[i]= new Finger(fingerStart(i),this);
            }
            predecessor = this;
            successor = this;
            printFingerTable();
        }
    }

    public void printFingerTable(){
        System.out.println("Node id: "+id);
        System.out.println("Predecessor: "+predecessor.id);
        System.out.println("Successor: "+successor.id);
        System.out.println("Keys: "+keys);
        System.out.println("start\tinterval\tsuccessor");
        for(int i=0; i<4; ++i) {
            System.out.println(fingerTable[i].start+"\t" +
                    "\t["+fingerStart(i)+";"+fingerStart(i+1)+")\t" +
                    "\t"+fingerTable[i].fingerSuccesor.id);
        }
        System.out.println("\n");
    }

}
