package ntnu.tdt4215.project;

import java.util.ArrayList;

public class Table implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList <Row> table;

    public Table(){
	table = new ArrayList <Row>();
    }

    public void setMatching (String note, int sentence, ArrayList<StringAndFloat> list) {
	table.add(new Row(note, sentence, list));
    }

    public ArrayList<ArrayList<StringAndFloat>> getMatches(String id) {
	ArrayList<ArrayList<StringAndFloat>> result = new ArrayList<ArrayList<StringAndFloat>>();
	for(int i = 0;i<table.size();i++){
	    if(table.get(i).getId().compareTo(id)==0) {
		result.add(table.get(i).getMatches());
	    }
	}
	return result;
    }

    public int size(){
	return table.size();
    }
}

class Row {
    private String id;
    private int sentence;
    private ArrayList<StringAndFloat> matches;


    public Row(String id, int sentence, ArrayList<StringAndFloat> table){
	this.id = id;
	this.sentence = sentence;
	this.matches = table;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public int getSentence() {
	return sentence;
    }

    public void setSentence(int sentence) {
	this.sentence = sentence;
    }

    public ArrayList<StringAndFloat> getMatches() {
	return matches;
    }

    public void setMatches(ArrayList<StringAndFloat> table) {
	this.matches = table;
    }


}

//class Coordinates {
//
//    private String note;
//    private int sentence;
//
//    public Coordinates(String x, int y) {
//	setNote(x);
//	setSentence(y);
//    }
//
//    public String getNote() {
//	return note;
//    }
//
//    public void setNote(String note) {
//	this.note = note;
//    }
//
//    public int getSentence() {
//	return sentence;
//    }
//
//    public void setSentence(int sentence) {
//	this.sentence = sentence;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//	Coordinates c = (Coordinates) o;
//	return(this.note.compareTo(c.getNote())==0 && this.sentence==c.getSentence());
//    }
//
//    @Override
//    public int hashCode(){
//	StringBuilder builder = new StringBuilder();
//	builder.append(note);
//	builder.append(sentence);
//	return builder.toString().hashCode();
//    }
//}
