package com.oocl.utils.analyzer;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

public class SearchUtils {

	/**
	 * Search by Query
	 *
	 * @param query
	 * : query object
	 * @param indexPath
	 * : Index Library Address
	 * @throws Exception
	 * : Throw an exception
	 */
	public static TopDocs searchByQuery(Query query, IndexSearcher indexSearcher, String indexPath) throws Exception {
		Filter filter = null;
		TopDocs docs = indexSearcher.search(query, filter, 10000);
		return docs;
	}
	/**
	 * Search by Query and add filters
	 */
	public static TopDocs searchByQuery(Query query, IndexSearcher indexSearcher, String indexPath, Filter filter)
			throws Exception {
		TopDocs docs = indexSearcher.search(query, filter, 10000);
		return docs;
	}
}
