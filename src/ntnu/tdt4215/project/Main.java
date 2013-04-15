package ntnu.tdt4215.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	//	System.out.println(IOFileTxt.parseHTM(T_FOLDER + "innhold.htm", ""));
	//	
	String url = "http://localhost:8983/solr";
	SolrServer server = new HttpSolrServer(url);
	//	((HttpSolrServer) server).setParser(new XMLResponseParser());
	//	SolrInputDocument doc1 = new SolrInputDocument();
	//	doc1.addField( "id", "id1", 1.0f );
	//	doc1.addField( "name", "doc1", 1.0f );
	//	doc1.addField( "price", 10 );
	//	SolrInputDocument doc2 = new SolrInputDocument();
	//	doc2.addField( "id", "id2", 1.0f );
	//	doc2.addField( "name", "doc2", 1.0f );
	//	doc2.addField( "price", 20 );		
	//	Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	//	docs.add( doc1 );
	//	docs.add( doc2 );
	//	server.add(docs);
	//	server.commit();
	Table notesVsICD10 = new Table();
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
		    ArrayList<String> articles = new ArrayList<String>(); 
		    for(int j=0;j<docs.size();j++){
			    articles.add(docs.get(j).getFieldValue("score").toString());
		    }
		    notesVsICD10.setMatching(i.toString(), sentence, articles);
		    System.out.println(notesVsICD10.getMatches(i.toString(), sentence));
		    sentence++;
		    line = br.readLine();
		}
	    } finally {
		br.close();
	    }
	}
    }


    private static void printTable(ArrayList<ArrayList<String>> notesVsICD10) {
	for(int i = 0; i < notesVsICD10.size();i++) {
	    for(int j = 0; j < notesVsICD10.get(0).size();i++){
		System.out.println(notesVsICD10.get(i).get(j).toString());
	    }
	}
    }
}
