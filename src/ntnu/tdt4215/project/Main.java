package ntnu.tdt4215.project;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.management.openmbean.InvalidOpenTypeException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class Main {
    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T2/";
    private final static String EASY = "T6.6.htm";
    private final static String MEDIUM = "T1.17.htm";
    private final static String DIFFICULT = "T3.1.htm";
    private final static String HOME = "innhold.htm";

    private final static String NOTE_VS_ICD10 = "notesVsICD10Merged.table";
    private final static String NOTE_VS_ICD10_TXT = "notesVsICD10Merged.txt";
    private final static String FILE_HAND_VS_ICD10 = "handbookVsICD10.table";
    private final static String FILE_HAND_VS_ICD10_TXT = "handbookVsICD10.txt";

    private static MedicalTerms terms = null;
    private static Handbook mainHandbook;
    private static SolrServer server;
    private static Table notesVsICD10;
    private static Table handbookVsICD10;
    private static Table notesVsICD10Merged;
    private static Table handbookVsICD10Merged;

    public static void main(String[] args) throws IOException,
	    SolrServerException {

	System.out.println("WORKING ..........");

	startSolr();

	terms = new MedicalTerms();
	// IOFileTxt.test(T_FOLDER + MEDIUM);
	mainHandbook = IOFileTxt.mainParserHtml(T_FOLDER, HOME);
	//
	// queryHandbook(mainHandbook);

	// IOFileTxt.saveStr( "jose.txt" , mainHandbook.toString());
	notesVsICD10 = queryNotes();
	handbookVsICD10 = queryHandbook(mainHandbook);
	// System.out.println(notesVsICD10.toString());

	notesVsICD10Merged = notesVsICD10.mergeTable();
	IOFileTxt.saveObj("NOTE_VS_ICD10", notesVsICD10Merged);
	IOFileTxt.saveStr("NOTE_VS_ICD10_TXT", notesVsICD10Merged.toString());

	// notesVsICD10Merged = (Table)IOFileTxt.loadObj(NOTE_VS_ICD10);

	handbookVsICD10Merged = handbookVsICD10.mergeTable();
	IOFileTxt.saveObj("FILE_HAND_VS_ICD10", handbookVsICD10Merged);
	IOFileTxt.saveStr("FILE_HAND_VS_ICD10_TXT",
		handbookVsICD10Merged.toString());

	// handbookVsICD10Merged = (Table)IOFileTxt.loadObj(NOTE_VS_ICD10);

	// System.out.println(notesVsICD10Merged.toString());
	// System.out.println(handbookVsICD10Merged.toString());
	System.out.println("----------------------------------");
	System.out.println(".... DONE");
    }

    private static void printTable(ArrayList<ArrayList<String>> notesVsICD10) {
	for (int i = 0; i < notesVsICD10.size(); i++) {
	    for (int j = 0; j < notesVsICD10.get(0).size(); i++) {
		System.out.println(notesVsICD10.get(i).get(j).toString());
	    }
	}
    }

    private static void fill(Table notesVsICD10, SolrServer server)
	    throws IOException, SolrServerException {
	for (Integer i = 1; i <= 8; i++) {
	    BufferedReader br = new BufferedReader(new FileReader(
		    "docu/cases/case" + i + ".txt"));
	    int sentence = 1;
	    try {
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
		    sb.append(line);
		    sb.append("\n");
		    SolrQuery query = new SolrQuery();
		    query.setQuery(line);
		    query.addField("id");
		    query.addField("score");
		    QueryResponse rsp = server.query(query);
		    SolrDocumentList docs = rsp.getResults();
		    ArrayList<StringAndFloat> articles = new ArrayList<StringAndFloat>();
		    for (int j = 0; j < docs.size(); j++) {
			float score = (Float) docs.get(j)
				.getFieldValue("score");
			String id = docs.get(j).getFieldValue("id").toString();
			articles.add(new StringAndFloat(id, score));
		    }
		    // for(int k=0;k<articles.size();k++){
		    // System.out.print(articles.get(k).toString());
		    // }
		    // System.out.println("");
		    notesVsICD10.setMatching(i.toString(), sentence, articles);
		    // System.out.println(notesVsICD10.getMatches(i.toString(),
		    // sentence));
		    sentence++;
		    line = br.readLine();
		}
	    } finally {
		br.close();
	    }
	}
    }

    private static Table queryNotes() throws IOException, SolrServerException {
	Table result = new Table();
	for (Integer i = 1; i <= 8; i++) {
	    BufferedReader br = new BufferedReader(new FileReader(
		    "docu/cases/case" + i + ".txt"));
	    int sentence = 1;
	    try {
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
		    sb.append(line);
		    sb.append("\n");
		    SolrQuery query = new SolrQuery();
		    String queryText = line;
		    queryText = CharacterChecker.filterColumn(queryText);
		    queryText = terms.boostMedicalTerms(queryText);
		    query.setQuery(queryText);
		    query.addField("id");
		    query.addField("score");
		    QueryResponse rsp = server.query(query);
		    // System.out.println(queryText);
		    SolrDocumentList docs = rsp.getResults();
		    ArrayList<StringAndFloat> articles = new ArrayList<StringAndFloat>();
		    for (int j = 0; j < docs.size(); j++) {
			float score = (Float) docs.get(j)
				.getFieldValue("score");
			String id = docs.get(j).getFieldValue("id").toString();
			articles.add(new StringAndFloat(id, score));
		    }
		    result.setMatching(i.toString(), sentence, articles);
		    sentence++;
		    line = br.readLine();
		}
	    } finally {
		br.close();
	    }
	}
	return result;
    }

    private static Table queryHandbook(Handbook handbook)
	    throws SolrServerException {
	Table result = new Table();
	for (int i = 0; i < handbook.size(); i++) {
	    Chapter chap = handbook.getChapter(i);
	    for (int j = 0; j < chap.numberOfSentences(); j++) {
		SolrQuery query = new SolrQuery();

		String q = chap.getContent(j);
		q = CharacterChecker.filterColumn(q);
		q = terms.boostMedicalTerms(q);
		query.setQuery(q);
		query.addField("id");
		query.addField("score");
		QueryResponse rsp = server.query(query, METHOD.POST);
		SolrDocumentList docs = rsp.getResults();
		ArrayList<StringAndFloat> articles = new ArrayList<StringAndFloat>();
		for (int k = 0; k < docs.size(); k++) {
		    String id = docs.get(k).getFieldValue("id").toString();
		    float score = (Float) docs.get(k).getFieldValue("score");
		    articles.add(new StringAndFloat(id, score));
		}
		result.setMatching(chap.getCode(), j + 1, articles);
	    }
	}
	return result;
    }

    private static void startSolr() {
	server = new HttpSolrServer("http://localhost:8983/solr");
    }
}
