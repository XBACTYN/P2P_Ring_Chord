package Chord;

public class Finger {

    public Integer start;
    public ChordNode fingerSuccesor;

    public Finger(int start, ChordNode fingerSuccesor){
        this.start = start;
        this.fingerSuccesor = fingerSuccesor;
    }

}
