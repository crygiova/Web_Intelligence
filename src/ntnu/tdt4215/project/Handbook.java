package ntnu.tdt4215.project;

import java.util.ArrayList;

public class Handbook {
    private ArrayList<Chapter> book;
    
    public Handbook(){
	book = new ArrayList<Chapter>();
    }
    
    public Handbook(ArrayList<Chapter> book){
	this.book=book;
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
	this.book.addAll(book);
    }
    
    public Chapter getChapter(int i){
	return book.get(i);
    }

    public int size() {
	return this.book.size();
    }
}


class Chapter {
    private String chapterTitle;
    private String code;
    private ArrayList<Info> content;
    
    public Chapter(String title,String code,ArrayList<Info> sentences){
	this.chapterTitle = title;
	this.code = code;
	this.content = sentences;
    }
    
    public void addInfo(Info content){
	this.content.add(content);
    }
    
    public String getTitle() {
        return chapterTitle;
    }

    public void setTitle(String title) {
        this.chapterTitle = title;
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
    
    public String getContent(int i){
	return (content.get(i).getCategory()+" "+content.get(i).getSentence());
    }

    public int numberOfSentences() {
	return content.size();
    }
}

class Info {
    private String category;
    private String sentence;
    
    public Info(String title,String sentence){
	this.category = title;
	this.sentence = sentence;
    }
    
    public String getCategory() {
	return category;
    }
    
    public void setCategory(String title) {
	this.category = title;
    }
    
    public String getSentence() {
	return sentence;
    }
    
    public void setSentence(String sentence) {
	this.sentence = sentence;
    }
}