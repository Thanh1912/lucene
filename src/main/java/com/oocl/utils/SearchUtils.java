package com.oocl.utils;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

public class SearchUtils {

	/**
	 * 通过Query来进行打搜索
	 * 
	 * @param query
	 *            ： query对象
	 * @param indexPath
	 *            ： 索引库地址
	 * @throws Exception
	 *             ： 抛出异常
	 */
	public static TopDocs searchByQuery(Query query, IndexSearcher indexSearcher, String indexPath) throws Exception {
		Filter filter = null;
		TopDocs docs = indexSearcher.search(query, filter, 10000);
		return docs;
	}

	/**
	 * 通过Query来进行搜索，并且添加过滤器
	 */
	public static TopDocs searchByQuery(Query query, IndexSearcher indexSearcher, String indexPath, Filter filter)
			throws Exception {
		TopDocs docs = indexSearcher.search(query, filter, 10000);
		return docs;
	}
}
