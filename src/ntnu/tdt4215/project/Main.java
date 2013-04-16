package ntnu.tdt4215.project;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class Main {
    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T/";
    private final static String EASY = "T6.6.htm";
    private final static String MEDIUM = "T1.1.htm";
    private final static String DIFFICULT = "T3.1.htm";

    public static void main(String[] args) throws IOException,
	    SolrServerException {
	System.out.println("WORKING ..........");
	// System.out.println(IOFileTxt.parseHTML(T_FOLDER + EASY, "easy"));
	// System.out.println(IOFileTxt.parseHTML(T_FOLDER + MEDIUM, "medium"));
	System.out.println(IOFileTxt.parseHTML(T_FOLDER + DIFFICULT,
		"difficult"));
	String titolo [] =IOFileTxt.splitTitle("T3.1.4 Spesielle problemer");
	
	for (String t :titolo)	
	{
	    System.out.println("++"+t+"--");
	}
	// IOFileTxt.mainParserHtml(T_FOLDER + "innhold.htm", "easy");
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
	System.out.println("done");
    }
}
