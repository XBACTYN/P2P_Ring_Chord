package Chord;

import java.util.Vector;

public class ChordNode {

    Integer id;
    ChordNode successor;
    ChordNode predecessor;
    Vector <Vector<Integer>> finger_table;

    public ChordNode(Integer id){
        //Сделать геттеры и сеттеры
        this.id = id;
        //Вызывать функцию Update
    }
    public void Update(){

    }
    private void CalculateSuccesor(){

    }
    private void CalculatePredecessor(){

    }
    private void UpdateTable(){

    }
}
