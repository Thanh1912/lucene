package com.oocl.lucene;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeFilter;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.junit.Test;

import com.oocl.utils.FileDocumentUtil;
import com.oocl.utils.HighlighterUtils;
import com.oocl.utils.SearchUtils;

public class LuceneTest {

    // 文件地址
//    String filePath_Leo1 = "D:\\Program Files\\good\\lucene\\filePath\\doc\\doc1.doc";
//    String filePath_Leo2 = "D:\\Program Files\\good\\lucene\\filePath\\doc\\doc2.doc";


    String filePath_Leo1 = "D:\\Program Files\\good\\lucene\\filePath\\Leo1.txt";
    String filePath_Leo2 = "D:\\Program Files\\good\\lucene\\filePath\\Leo2.txt";
    String filePath_Leoboost = "D:\\Program Files\\good\\lucene\\filePath\\boost.txt";
    // 索引库地址
    String indexPath = "D:\\Work Place\\oocl\\lucene\\indexPath";
    // 分词器
    Analyzer analyzer = new StandardAnalyzer();//中文二分法分词器

    /**
     * 构建索引库
     *
     * @throws Exception
     */
    @Test
    public void createIndex() throws Exception {
        IndexWriter indexWriter = new IndexWriter(indexPath, analyzer, true, MaxFieldLength.LIMITED);
        Document doc1 = FileDocumentUtil.file2Document(filePath_Leo1);
        Document doc2 = FileDocumentUtil.file2Document(filePath_Leo2);
        indexWriter.addDocument(doc1);
        indexWriter.addDocument(doc2);
        indexWriter.close();
        System.out.println("Successfully created index library");
    }

    @Test
    public void searchIndex() throws Exception {
        createIndex();
        String queryString = "together the world's GitHub";
        // 1. Initialize IndexSearch
        IndexSearcher indexSearcher = new IndexSearcher(indexPath);
        String[] fields = {"name", "content", "size"};
        // 2. Get Query by keyword
        //=============2.1 Relevance ranking: the greater the proportion of a word in an article, the higher the relevance (default)
        // ============2.2 Custom Order: Custom Ordering Order
        Map<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("name", 3.0f);
        boosts.put("content", 1.0f);//In fact, the default is 1.0f
        Query query = FileDocumentUtil.getQuery(fields, analyzer, queryString, boosts);
        //============Filter
        //new RangeFilter(the name of the field, the upper bound
        // , the lower bound, whether it contains the upper bound, whether it contains the lower bound)
        Filter filter = new RangeFilter("size", NumberTools.longToString(50), NumberTools.longToString(200), false, true);
        // Search for doc by String
        TopDocs docs = SearchUtils.searchByQuery(query, indexSearcher, queryString, filter);
        // 2. Get the highlighter
        Highlighter highlighter = HighlighterUtils.getHighlighter(query, "<font color='red'>", "</font>", 1000);
        // 3. Traverse docs print results
        FileDocumentUtil.ergodicDocument(docs, highlighter, analyzer, indexSearcher, "content", 60);
    }
}
