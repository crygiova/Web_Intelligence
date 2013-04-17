package ntnu.tdt4215.project;

import java.util.ArrayList;

public class Handbook {
    private ArrayList<Chapter> book;
    
    public Handbook(){
	book = new ArrayList<Chapter>();
    }
    
    public void addChapter(Chapter chap) {
	book.add(chap);
    }
    
    public ArrayList<Chapter> getBook() {
        return book;
    }

    public void setBook(ArrayList<Chapter> book) {
        this.book = book;
    }
    
    public void addBook(ArrayList<Chapter> book) {
	// TODO implement it Gonzalo
    }
}


class Chapter {
    String title;
    String code;
    ArrayList<Info> content;
    
    public Chapter(String title,String code,ArrayList<Info> sentences){
	this.title = title;
	this.code = code;
	this.content = sentences;
    }
    
    public void addInfo(Info content){
	this.content.add(content);
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

    public ArrayList<Info> getSentences() {
        return content;
    }

    public void setContent(ArrayList<Info> sentences) {
        this.content = sentences;
    }
    
    public Info getContent(int i){
	return content.get(i);
    }
}

class Info {
    private String title;
    private String sentence;
    
    public Info(String title,String sentence){
	this.title = title;
	this.sentence = sentence;
    }
    
    public String getTitle() {
	return title;
    }
    
    public void setTitle(String title) {
	this.title = title;
    }
    
    public String getSentence() {
	return sentence;
    }
    
    public void setSentence(String sentence) {
	this.sentence = sentence;
    }
}