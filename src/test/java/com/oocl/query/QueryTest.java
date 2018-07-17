package com.oocl.query;

import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.junit.Test;

import com.oocl.utils.FileDocumentUtil;
import com.oocl.utils.SearchUtils;

public class QueryTest {

	String indexPath = "D:\\Work Place\\oocl\\lucene\\indexPath";
	Analyzer analyzer = new CJKAnalyzer();

	/**
	 * 通过Query来进行搜索已经打印
	 * 
	 * @param query
	 * @param indexSearcher
	 * @throws Exception
	 */
	public void searchAndPringByQuery(Query query, IndexSearcher indexSearcher) throws Exception {
		System.out.println("对应的查询语法   " + query);
		TopDocs docs = SearchUtils.searchByQuery(query, indexSearcher, indexPath);
		FileDocumentUtil.ergodicDocument(docs, analyzer, indexSearcher, "content", 1000);
	}

	/**
	 * TermQuey : 关键字查询 注意英文关键字全是小写
	 */
	@Test
	public void testTermQuery() throws Exception {
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		// 通过TermQuery
		Term term = new Term("content", "hello");
		Query termQuery = new TermQuery(term);
		searchAndPringByQuery(termQuery, indexSearcher);
	}

	/**
	 * RangeQuer : 范围查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRangeQuery() throws Exception {
		/*通过文件大小的范围查询*/
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		/*Term(String fld, String txt)，其中fld是Field的Name， String是值的大小
		 特别注意，要是100-1000的话，要写成相同的宽度：0100-1000*/
		// Term lowerTerm = new Term("size", "060");
		// Term upperTerm = new Term("size", "110");
		Term lowerTerm = new Term("size", NumberTools.longToString(60));
		Term upperTerm = new Term("size", NumberTools.longToString(112));
		/*RangeQuery(Term lowerTerm, Term upperTerm, boolean inclusive)
		lowerTerm是下界，upperTerm是上届，inclusive是是否包括上界和下界*/
		Query rangeQuery = new RangeQuery(lowerTerm, upperTerm, true);
		searchAndPringByQuery(rangeQuery, indexSearcher);
		
		/*通过时间的范围查询,例子中没有时间就打印好了，和文件大小的做法一样*/
		System.out.println("时间范围搜索的打印");
		System.out.println(DateTools.dateToString(new Date(), Resolution.YEAR));
		System.out.println(DateTools.dateToString(new Date(), Resolution.MONTH));
		System.out.println(DateTools.dateToString(new Date(), Resolution.DAY));
		System.out.println(DateTools.dateToString(new Date(), Resolution.HOUR));
		System.out.println(DateTools.dateToString(new Date(), Resolution.MINUTE));
		System.out.println(DateTools.dateToString(new Date(), Resolution.SECOND));
	}
	
	/**
	 * 通配符查询
	 * 
	 * '?' 代表一个字符
	 * '*' 代表0个或者多个字符
	 */
	@Test
	public void testWildcardQuery() throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Term term = new Term("name","作?");
//		Term term = new Term("content","zi*");
//		Term term = new Term("content","*ic*");
		Query query = new WildcardQuery(term);
		searchAndPringByQuery(query, indexSearcher);
	}
	
	/**
	 * 短语查询
	 */
	@Test
	public void testPhraseQuery() throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		/*知道差几个的情况下*/
		PhraseQuery query = new PhraseQuery();
		query.add(new Term("content","my"),0);
		query.add(new Term("content","food"),3);
		/*my favourite food： 中间隔着一个0 1 2； 而setSlop的意思就是隔着3个以内的都可以*/
		query.setSlop(3);
		searchAndPringByQuery(query, indexSearcher);
	}
	
	/**
	 * 条件查询
	 * 两个条件一起查询
	 */
	@Test
	public void testBooleanQuery() throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		//条件1
		PhraseQuery query1 = new PhraseQuery();
		query1.add(new Term("content","my"),0);
		query1.add(new Term("content","food"),3);
		query1.setSlop(3);
		//条件2
		Term term = new Term("content","4*");
		Query query2 = new WildcardQuery(term);
		/*组合条件
		 * MUST : MUST   A且B 
		 * MUST : MUST_NOT  包含A但是不包含B
		 * SHOULD : SHOULD  或者A，或者B，或者AB
		 * 以下组合无意义
		 * MUST SHOULD
		 * MUST_NOT MUST_NOT
		 * MUST_NOT SHOULD
		 * MUST_NOT单独使用
		 * */
		BooleanQuery query = new BooleanQuery();
		query.add(query1, Occur.SHOULD);
		query.add(query2,Occur.SHOULD);
		searchAndPringByQuery(query, indexSearcher);
	}
	
}
