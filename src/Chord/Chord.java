package Chord;

import java.util.Vector;

public class Chord {
    Vector<ChordNode> Ring;

    public Chord(){
        this.Ring = new Vector<ChordNode>(16);

    }



    public ChordNode Find(Integer id){
        return null;
    }

    public void AddNode(Integer id){

        Ring.addElement(new ChordNode(id));
    }

    public void DeleteNode(Integer id){

    }

}
