import com.marklogic.client.*;
import com.marklogic.client.DatabaseClientFactory.*;
import com.marklogic.client.document.*;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.*;

public class LoadDocuments {

		
	public static void main(String[] args) throws Exception {

		DatabaseClient client = 
			    DatabaseClientFactory.newClient(
			        "localhost", 8000, "purbon", "VooDoo", Authentication.DIGEST);
		
		TextDocumentManager docMngr = client.newTextDocumentManager();
		
		String docId = "/example/text.txt"; 
		StringHandle handle = new StringHandle();
		handle.set("A simple text document");
		
		docMngr.write(docId, handle);
		
		System.out.println("Done!");
		
		QueryManager qMgr = client.newQueryManager();
		StringQueryDefinition qDef = qMgr.newStringDefinition();
		qDef.setCriteria("simple");
		StringHandle resultsHandle = new StringHandle();

		qMgr.search(qDef, resultsHandle);
		
		System.out.println(resultsHandle);
		
		client.release();
	}

}
