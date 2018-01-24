package com.purbon.search.marklogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.document.DocumentManager;
import com.marklogic.client.io.*;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;

public class QueryMarkLogic {

	QueryManager qm;
	
	public QueryMarkLogic(QueryManager qm) {
		this.qm = qm;
	}
	
	public DOMHandle QueryByStringWithXMLResponse(StringQueryDefinition queryString) {
		DOMHandle results = qm.search(queryString, new DOMHandle());
		return results;
	}
	
	public StringHandle QueryByStringWithStringResponse(StringQueryDefinition queryString) {
		StringHandle results = qm.search(queryString, 
										 new StringHandle().withFormat(Format.JSON));

		return results;
	}
	
	public StringHandle QueryByExample(StringQueryDefinition queryString) {
		StringHandle results = qm.search(queryString, 
										 new StringHandle().withFormat(Format.JSON));

		return results;
	}
	
	public SearchHandle QueryByExampleAsSearchHandle(StructuredQueryDefinition queryDefinition) {
		SearchHandle results = qm.search(queryDefinition, new SearchHandle());

		return results;
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
	public void printResults(MatchDocumentSummary[] results) {
		for(MatchDocumentSummary result : results) {
			System.out.println("Fitness: "+result.getFitness());
			System.out.println("Score: "+result.getScore());
			System.out.println("Confidence: "+result.getConfidence());
			System.out.println("Text: "+result.getFirstSnippetText());
			System.out.println("MimeType: "+result.getMimeType());
			System.out.println("URI: "+result.getUri());
		}
	}
	
	public static void main(String [] args) throws Exception {
	
		DatabaseClient client = 
			    DatabaseClientFactory.newClient(
			        "localhost", 8000, "purbon", "VooDoo", Authentication.DIGEST);

		QueryManager qm = client.newQueryManager();

		DocumentManager dm = client.newDocumentManager();

		StringQueryDefinition qd = qm.newStringDefinition();
		qd.setCriteria("Berlin");
		qd.setCollections("searchTestXML");

		QueryMarkLogic query = new QueryMarkLogic(qm);

		StringHandle stringResponse = query.QueryByStringWithStringResponse(qd);

		StructuredQueryBuilder queryBuilder = new StructuredQueryBuilder();
		//StructuredQueryDefinition queryDefinition = queryBuilder.and(queryBuilder.term("Berlin"), queryBuilder.term(1.5, "Product"));

		StructuredQueryDefinition queryDefinition = queryBuilder.and(queryBuilder.term("Berlin"));
		queryDefinition.setCollections("searchTestXML");

		SearchHandle results = query.QueryByExampleAsSearchHandle(queryDefinition);

		query.printResults(results.getMatchResults());



	}
}
