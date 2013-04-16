package ntnu.tdt4215.project;

import java.util.ArrayList;

public class Handbook {
    private ArrayList<Chapter> book;
    
    public Handbook(){
	book = new ArrayList<Chapter>();
    }
    
    public void addChapter(String title, String code, ArrayList<String> sentences) {
	book.add(new Chapter(title,code,sentences));
    }
    
    public ArrayList<Chapter> getBook() {
        return book;
    }

    public void setBook(ArrayList<Chapter> book) {
        this.book = book;
    }
}


class Chapter {
    String title;
    String code;
    ArrayList<String> sentences;
    
    public Chapter(String title,String code,ArrayList<String> sentences){
	this.title = title;
	this.code = code;
	this.sentences = sentences;
    }
    
    public void addSentence(String sentence){
	sentences.add(sentence);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }
    
    public String getSentence(int i){
	return sentences.get(i);
    }
}