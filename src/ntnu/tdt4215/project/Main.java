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

    public static void main(String[] args) throws IOException, SolrServerException {
	System.out.println("WORKING ..........");

	final String str = "    <exclusion rdf:resource=\"&icd10no;B92\"/> ";
	// final Pattern pattern = Pattern.compile(">.*</");
	// RIGHT TODO ->content final Pattern pattern =
	// Pattern.compile(".*>(.*?)<.*");
	// RIGHT TODO -> name final Pattern pattern =
	// Pattern.compile("<(.*?)\\s");
	// Parser.parserOWL("newIcd.owl","jose.xml");
	System.out.println(IOFileTxt.parseHTM(T_FOLDER + "innhold.htm", ""));
	
	System.out.println("WORKING ..........");
	String url = "http://localhost:8983/solr";
	SolrServer server = new HttpSolrServer(url);
	((HttpSolrServer) server).setParser(new XMLResponseParser());
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
	SolrQuery query = new SolrQuery();
	query.setQuery("Eva Andersen er en skoleelev som har hatt insulinkrevende diabetes mellitus i 3 år");
	query.addField("id");
	QueryResponse rsp = server.query( query );
	SolrDocumentList docs = rsp.getResults();
	Object[] results = rsp.getResults().toArray();
	for(int i=0;i<results.length;i++){
	    System.out.println(results[i].toString());

	}
	System.out.println("done");
    }
}
