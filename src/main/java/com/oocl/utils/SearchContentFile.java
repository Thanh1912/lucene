package com.oocl.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchContentFile {

    // word breaker
    public static Analyzer analyzer = new StandardAnalyzer();

    public void search(String queryString, List<String> listPath) throws Exception {
        // 1. Initialize IndexSearch
        createIndexInfiles(listPath);
        IndexSearcher indexSearcher = new IndexSearcher(ConstantValue.INDEX_PATH);
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
        Highlighter highlighter = HighlighterUtils.getHighlighter(query, ConstantValue.PER_TAG, ConstantValue.POST_TAG, 1000);
        // 3. Traverse docs print results
        FileDocumentUtil.ergodicDocument(docs, highlighter, analyzer, indexSearcher, "content", 60);
    }

    private void createIndexInfiles(List<String> listPath) throws IOException {
        IndexWriter indexWriter = new IndexWriter(ConstantValue.INDEX_PATH, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
        listPath.forEach((docPath) -> {
            Document doc = null;
            try {
                doc = FileDocumentUtil.file2Document(docPath);
                indexWriter.addDocument(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        indexWriter.close();
        System.out.println("Successfully created index library");
    }

}
