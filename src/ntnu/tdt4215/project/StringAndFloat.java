package ntnu.tdt4215.project;

public class StringAndFloat {
    private String id;
    private float score;
    
    public StringAndFloat(String id, float score){
	this.id=id;
	this.score=score;
    }
    public String getId() {
	return id;
    }
    public void setId(String id) {
	this.id = id;
    }
    public float getScore() {
	return score;
    }
    public void setScore(float score) {
	this.score = score;
    }
    @Override
    public String toString(){
	return("["+id+","+score+"], ");
    }
}
