package ntnu.tdt4215.project;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class Main {
    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T/";
    private final static String EASY = "T6.6.htm";
    private final static String MEDIUM = "T1.1.htm";
    private final static String DIFFICULT = "T3.1.htm";


    public static void main(String[] args) throws IOException, SolrServerException {
	//	System.out.println("WORKING ..........");
	//
	//	final String str = "    <exclusion rdf:resource=\"&icd10no;B92\"/> ";

	// final Pattern pattern = Pattern.compile(">.*</");
	// RIGHT TODO ->content final Pattern pattern =
	// Pattern.compile(".*>(.*?)<.*");
	// RIGHT TODO -> name final Pattern pattern =
	// Pattern.compile("<(.*?)\\s");
	// Parser.parserOWL("newIcd.owl","jose.xml");
	/*
	 * System.out.println(IOFileTxt.parseHTM(T_FOLDER + "innhold.htm", ""));
	 * 
	 * System.out.println("WORKING .........."); String url =
	 * "http://localhost:8983/solr"; SolrServer server = new
	 * HttpSolrServer(url); ((HttpSolrServer) server).setParser(new
	 * XMLResponseParser()); // SolrInputDocument doc1 = new
	 * SolrInputDocument(); // doc1.addField( "id", "id1", 1.0f ); //
	 * doc1.addField( "name", "doc1", 1.0f ); // doc1.addField( "price", 10
	 * ); // SolrInputDocument doc2 = new SolrInputDocument(); //
	 * doc2.addField( "id", "id2", 1.0f ); // doc2.addField( "name", "doc2",
	 * 1.0f ); // doc2.addField( "price", 20 ); //
	 * Collection<SolrInputDocument> docs = new
	 * ArrayList<SolrInputDocument>(); // docs.add( doc1 ); // docs.add(
	 * doc2 ); // server.add(docs); // server.commit(); SolrQuery query =
	 * new SolrQuery(); query.setQuery(
	 * "Eva Andersen er en skoleelev som har hatt insulinkrevende diabetes mellitus i 3 år"
	 * ); query.addField("id"); QueryResponse rsp = server.query( query );
	 * SolrDocumentList docs = rsp.getResults(); for(int
	 * i=0;i<docs.size();i++){ System.out.println(docs.get(i).toString()); }
	 */
	//	System.out.println(IOFileTxt.parseHTM(T_FOLDER + "innhold.htm", ""));
	//	
	String url = "http://localhost:8983/solr";
	SolrServer server = new HttpSolrServer(url);
	//IOFileTxt.parserOWL("./owl/atc_no_ext_rdf.owl", "./owl/atc_no_ext_rdf.xml");
	//IOFileTxt.parserOWL("./owl/icd10no.owl", "./owl/icd10no.xml");
//	Table notesVsICD10 = new Table();
	Table notesVsAtc = new Table();
//	Table HandbookVsICD = new Table();
//	Table HandbookVsAtc = new Table();
//	fill(notesVsICD10,server);
	fill(notesVsAtc,server);
//	IOFileTxt.saveObj("./notesVSICD10.table", notesVsICD10);
	IOFileTxt.saveObj("./notesVSAtc.table", notesVsAtc);
	System.out.println("done");
    }

    private static void printTable(ArrayList<ArrayList<String>> notesVsICD10) {
	for(int i = 0; i < notesVsICD10.size();i++) {
	    for(int j = 0; j < notesVsICD10.get(0).size();i++){
		System.out.println(notesVsICD10.get(i).get(j).toString());
	    }
	}
    }

    private static void fill(Table notesVsICD10,SolrServer server) throws IOException, SolrServerException{
	for(Integer i = 1;i<=8;i++){
	    BufferedReader br = new BufferedReader(new FileReader("docu/cases/case"+i+".txt"));
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
		    QueryResponse rsp = server.query( query );
		    SolrDocumentList docs = rsp.getResults();
		    ArrayList<StringAndFloat> articles = new ArrayList<StringAndFloat>(); 
		    for(int j=0;j<docs.size();j++) {
			float score = (Float) docs.get(j).getFieldValue("score");
			String id = docs.get(j).getFieldValue("id").toString();
			articles.add(new StringAndFloat(id,score));
		    }
//		    for(int k=0;k<articles.size();k++){
//			System.out.print(articles.get(k).toString());
//		    }
//		    System.out.println("");
		    notesVsICD10.setMatching(i.toString(), sentence, articles);
//		    System.out.println(notesVsICD10.getMatches(i.toString(), sentence));
		    sentence++;
		    line = br.readLine();
		}
	    } finally {
		br.close();
	    }
	}
    }
}
