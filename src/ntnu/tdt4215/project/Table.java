package ntnu.tdt4215.project;

import java.util.ArrayList;
import java.util.Collections;

public class Table implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Row> table;

    public Table() {
	table = new ArrayList<Row>();
    }

    public void setMatching(String note, int sentence,
	    ArrayList<StringAndFloat> list) {
	table.add(new Row(note, sentence, list));
    }

    public ArrayList<ArrayList<StringAndFloat>> getMatches(String id) {
	ArrayList<ArrayList<StringAndFloat>> result = new ArrayList<ArrayList<StringAndFloat>>();
	for (int i = 0; i < table.size(); i++) {
	    if (table.get(i).getId().compareTo(id) == 0) {
		result.add(table.get(i).getMatches());
	    }
	}
	return result;
    }

    public int size() {
	return table.size();
    }

    public Table mergeTable() {
	Table result = new Table();
	int counter = 0;
	while (counter < table.size()) {
	    String noteId = table.get(counter).getId();
	    ArrayList<StringAndFloat> merge = table.get(counter).getMatches();
	    counter++;
	    while (counter < table.size() && table.get(counter).getId().compareTo(noteId) == 0) {
		mergeRow(merge, table.get(counter).getMatches());
		counter++;
	    }
	    Collections.sort(merge, new StringAndFloatComparator());
	    ArrayList<StringAndFloat> mergeCut =  cut(merge,10);
	    result.setMatching(noteId, 1, mergeCut);
	}
	System.out.println(result.size());
	return result;
    }

private ArrayList<StringAndFloat> cut(ArrayList<StringAndFloat> merge, int cutSize) {
    	ArrayList<StringAndFloat> result = new ArrayList<StringAndFloat>();
    	for(int i=0;i<cutSize && i < merge.size();i++){
    	    result.add(merge.get(i));
    	}
    	return result;
	
    }

//    private void mergeRow(ArrayList<StringAndFloat> merge,
//	    ArrayList<StringAndFloat> matches) {
//
//	ArrayList<StringAndFloat> array = new ArrayList<StringAndFloat>();
//	for (int i = 0; i < matches.size(); i++) {
//	    for (int j = 0; j < merge.size(); j++) {
//		if (matches.get(i).getId().compareTo(merge.get(j).getId()) == 0) {
//		    merge.get(j)
//			    .setScore(
//				    merge.get(j).getScore()
//					    + matches.get(i).getScore());
//		} else {
//		    array.add(matches.get(i));
//		}
//	    }
//
//	}
//	merge.addAll(array);
//
//    }

    private void mergeRow(ArrayList<StringAndFloat> merge, ArrayList<StringAndFloat> matches) {
	int matchesSize = matches.size();
	Integer mergeSize = new Integer(merge.size());
	boolean match = false;
	for (int i = 0; i < matchesSize; i++) {
	    match = false;
	    for (int j = 0; j < mergeSize; j++) {
		if (matches.get(i).getId().compareTo(merge.get(j).getId()) == 0) {
		    match = true;
		    merge.get(j).setScore(merge.get(j).getScore()+ matches.get(i).getScore());
		} 
	    }
	    if(!match) merge.add(matches.get(i));
	}
    }

    @Override
    public String toString() {
	String result = "";
	for (int i = 0; i < table.size(); i++) {
	    result += table.get(i).toString();
	    result += "\n";
	}
	return result;

    }

}

class Row {
    private String id;
    private int sentence;
    private ArrayList<StringAndFloat> matches;

    public Row(String id, int sentence, ArrayList<StringAndFloat> table) {
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

    @Override
    public String toString() {
	String result = "";
	result += id + " ";
	result += sentence + " ";
	for (int i = 0; i < matches.size(); i++) {
	    result += matches.get(i).toString();
	}
	return result;
    }

}

// class Coordinates {
//
// private String note;
// private int sentence;
//
// public Coordinates(String x, int y) {
// setNote(x);
// setSentence(y);
// }
//
// public String getNote() {
// return note;
// }
//
// public void setNote(String note) {
// this.note = note;
// }
//
// public int getSentence() {
// return sentence;
// }
//
// public void setSentence(int sentence) {
// this.sentence = sentence;
// }
//
// @Override
// public boolean equals(Object o) {
// Coordinates c = (Coordinates) o;
// return(this.note.compareTo(c.getNote())==0 &&
// this.sentence==c.getSentence());
// }
//
// @Override
// public int hashCode(){
// StringBuilder builder = new StringBuilder();
// builder.append(note);
// builder.append(sentence);
// return builder.toString().hashCode();
// }
// }
