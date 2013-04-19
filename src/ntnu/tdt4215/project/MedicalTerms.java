package ntnu.tdt4215.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MedicalTerms {
    public ArrayList<String> terms = new ArrayList<String>();

    public MedicalTerms () throws IOException{
	BufferedReader br = new BufferedReader(new FileReader(
		"./docu/medicalTerms.txt"));
	try {

	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
	    while (line != null) {
		sb.append(line);
		sb.append("\n");
		terms.add(line);
		line = br.readLine();
	    }
	} finally {
	    br.close();
	}
    }

    public ArrayList<String> getTerms() {
	return terms;
    }

    public void setMedicalTerms(ArrayList<String> medicalTerms) {
	this.terms = medicalTerms;
    }

    public String boostMedicalTerms(String s){
	for(int i = 0;i<terms.size();i++){
	    if(s.contains(" "+terms.get(i)+" ")) {
		s = s.replace(" "+terms.get(i)+" ", " " +terms.get(i)+"^100 ");
	    }
	}
	return s;
    }

    public  int size(){
	return terms.size();
    }
}
