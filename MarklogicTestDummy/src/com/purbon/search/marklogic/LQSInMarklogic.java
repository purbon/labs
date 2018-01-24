package com.purbon.search.marklogic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.admin.NamespacesManager;
import com.marklogic.client.admin.QueryOptionsManager;
import com.marklogic.client.document.DocumentManager;
import com.marklogic.client.document.DocumentWriteSet;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.*;
import com.marklogic.client.query.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *  Load, Query and Search documents in a Marklogic database.
 */
public class LQSInMarklogic {

	DatabaseClient client;
	QueryManager qm;
    DocumentMetadataHandle metadataHandle;

	public LQSInMarklogic() {
		client =
			    DatabaseClientFactory.newClient(
			        "localhost", 8000, "purbon", "VooDoo", Authentication.DIGEST);

		qm = client.newQueryManager();
        metadataHandle = new DocumentMetadataHandle(); //.withCollections("searchTest");

	}

	public XMLDocumentManager getXMLDocumentManager() {
	    return client.newXMLDocumentManager();
    }

    public JSONDocumentManager getJSONDocumentManager() {
	    return client.newJSONDocumentManager();
    }

    public DocumentManager getDefaultDocumentManager() {
	    return client.newDocumentManager();
    }

    public void writeData(DocumentManager dm) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        Random rnd = new Random(System.currentTimeMillis());

        String[] cities = new String[]{"Berlin", "Barcelona", "London" };
        String[] roles = new String[]{"Software Developer", "Technical Architect", "Product Manager" };

        // A DocumentWriteSet represents a batch of document content and/or metadata to be written to the database in a single transaction.
        // If any insertion or update in a write set fails, the entire batch fails. You should size each batch according to the guidelines
        // described in Selecting a Batch Size.
        DocumentWriteSet writeBatch = dm.newWriteSet();
        writeBatch.addDefault(metadataHandle);
        Map<String, Object> documentBase = new HashMap<String, Object>();

        for(int i=0; i < 20; i++)  {
            documentBase.clear();
            documentBase.put("title", "Title"+i);
            documentBase.put("city", "Build in the city of "+cities[rnd.nextInt(cities.length)]);
            documentBase.put("role", roles[rnd.nextInt(roles.length)]);
            documentBase.put("cardinality", rnd.nextInt(2));
            String document = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(documentBase);
            writeBatch.add("doc"+i, new StringHandle().with(document).withFormat(Format.JSON));
        }

        dm.write(writeBatch);
    }
	
	public static void main(String [] args) throws Exception {

		LQSInMarklogic marklogic = new LQSInMarklogic();

		marklogic.writeData(marklogic.getJSONDocumentManager());

		 // retrieve the collection name

		JacksonHandle t = marklogic.getXMLDocumentManager().readMetadata("doc1", new JacksonHandle());
		System.out.println("collections: "+t.get().get("collections"));

		// search by docId

		System.out.println("Search by docID: ");
		StringHandle stringHandle = new StringHandle();
		marklogic.getJSONDocumentManager().read("doc1", stringHandle);
		System.out.println(stringHandle.get());
		System.out.println("");

		// Search the content.

		System.out.println("Search by content");
		StringQueryDefinition sqd = marklogic.qm.newStringDefinition();
		sqd.setCriteria("Software OR Berlin");

		SearchHandle sh = marklogic.qm.search(sqd, new SearchHandle());
		printResults(sh.getMatchResults());

		// Search with facets activated.
		System.out.println("");

		System.out.println("Faceted search");

		QueryOptionsManager queryManager  = marklogic.client.newServerConfigManager().newQueryOptionsManager();
		StringHandle queryOptions = new StringHandle().withFormat(Format.JSON);
		
		String constraints = "{\"options\": {";
			   constraints += "\"constraint\": [";
			   constraints += "{ \"name\": \"roles\",";
			   constraints += "\"range\": {";
			   constraints += "\"type\": \"xs:string\",";
			   constraints += "\"facet\": true,";
			   constraints += "\"element\": { \"name\": \"role\" }";
			  // constraints += "\"facet-option\": [ \"frequence-order\", \"descending\" ]";
			   constraints += "}}] }}";
				
		queryOptions.set(constraints);
		queryManager.writeOptionsAs("search-with-facets", Format.JSON, queryOptions);
		QueryManager qm = marklogic.client.newQueryManager();
		
		StringQueryDefinition df = qm.newStringDefinition("search-with-facets");
		df.setCriteria("berlin");
		//df.setCollections("searchTest");
		StringHandle handle = qm.search(df, new StringHandle().withFormat(Format.JSON));
		System.out.println(handle);
		System.out.println("");

		// Query by example
		 System.out.println("Search by example");

		String rawXMLQuery =
				"<q:qbe xmlns:q='http://marklogic.com/appservices/querybyexample'>"+
						"<q:query>" +
						"<city>Build in the city of Berlin</city>" +
						"</q:query>" +
						"</q:qbe>";

		String rawJSONQuery =
				"{\n" +
						"  \"$query\": {\n" +
						"    \"city\": { \n" +
						"      \"$exact\": false, \n" +
						"      \"$value\": \"Build in the city of Berlin\"\n" +
						"    }\n" +
						"  }\n" +
						"}";

		StringHandle rawHandle =
				new StringHandle(rawJSONQuery).withFormat(Format.JSON);
		RawQueryByExampleDefinition querydef =
				marklogic.qm.newRawQueryByExampleDefinition(rawHandle);

		StringHandle resultsHandle =
				marklogic.qm.search(querydef, new StringHandle());
		System.out.println(resultsHandle);
		System.out.println("");

		// Structured search
		/*System.out.println("Structured search");

		StructuredQueryBuilder qb = new StructuredQueryBuilder("search-with-facets");

		StructuredQueryDefinition definition = qb.properties(qb.term("Berlin"));
		SearchHandle ssh = marklogic.qm.search(definition, new SearchHandle());
		System.out.println(ssh.getMatchResults().length);

		System.out.println("");
		*/
		marklogic.client.release();
		
	}


	/**
	 * Print results based on the query answer.
	 * Fitness is a normalized measure of relevance that is based on how well a node matches the query issued, not taking into
	 * account the number of documents in which the query term(s) occur.
	 * Score: TDF-IDF, term match, random score, ...
	 * Confidence is similar to score, except that it is bounded. It is similar to fitness, except that it is influenced by term IDFs.
	 * It is an xs:float in the range of 0.0 to 1.0. It does not include quality.When using with any of the scoring methods,
	 * the confidence is calculated by first bounding the score in the range of 0.0 to 1.0, then taking the square root of that number.
	 * @param results
	 */
	public static void printResults(MatchDocumentSummary[] results) {
		for(MatchDocumentSummary result : results) {
			System.out.println("Fitness: "+result.getFitness());
			System.out.println("Score: "+result.getScore());
			System.out.println("Confidence: "+result.getConfidence());
			System.out.println("Text: "+result.getFirstSnippetText());
			System.out.println("MimeType: "+result.getMimeType());
			System.out.println("URI: "+result.getUri());
		}
	}
}
