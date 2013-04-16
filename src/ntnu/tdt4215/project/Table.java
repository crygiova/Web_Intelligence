package ntnu.tdt4215.project;

import java.util.ArrayList;
import java.util.HashMap;

public class Table implements java.io.Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HashMap<Coordinates,ArrayList<StringAndFloat>> table;

    public Table(){
	table = new HashMap<Coordinates,ArrayList<StringAndFloat>>();
    }

    public void setMatching (String note, int sentence, ArrayList<StringAndFloat> list) {
	table.put(new Coordinates(note, sentence), list);
    }

    public ArrayList<StringAndFloat> getMatches(String note,int sentence) {
	return table.get(new Coordinates(note,sentence));
    }

    public int size(){
	return table.size();
    }
}

class Coordinates {

    private String note;
    private int sentence;

    public Coordinates(String x, int y) {
	setNote(x);
	setSentence(y);
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    public int getSentence() {
	return sentence;
    }

    public void setSentence(int sentence) {
	this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
	Coordinates c = (Coordinates) o;
	return(this.note.compareTo(c.getNote())==0 && this.sentence==c.getSentence());
    }

    @Override
    public int hashCode(){
	StringBuilder builder = new StringBuilder();
	builder.append(note);
	builder.append(sentence);
	return builder.toString().hashCode();
    }
}
