package ntnu.tdt4215.project;

import java.util.Comparator;

public class StringAndFloat implements Comparable {
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
    @Override
    public int compareTo(Object o) {
        float float1 = this.getScore();
        float float2 = ((StringAndFloat) o).getScore();
 
        if (float1 < float2) return +1;
        else if (float1 > float2) return -1;
        else return 0;
    }
}
