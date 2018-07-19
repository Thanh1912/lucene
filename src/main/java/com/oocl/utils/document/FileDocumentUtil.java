package com.oocl.utils.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.oocl.utils.ModelResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;


public class FileDocumentUtil {

    /**
     * Initialize Query
     *
     * @param fields     : collection of fieldName
     * @param analyzer   : lexical analyzer
     * @param findString : The keywords you need to find
     * @return Query
     * @throws Exception
     */
    public static Query getQuery(String[] fields, Analyzer analyzer, String findString) throws Exception {
        QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
        Query query = queryParser.parse(findString);
        return query;
    }

    /**
     * Initialize Query and set the collation
     */
    public static Query getQuery(String[] fields, Analyzer analyzer, String findString, Map<String, Float> boosts) throws Exception {
        QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
        Query query = queryParser.parse(findString);
        return query;
    }

    /**
     * Generate a doc via filepath (this class still needs to be written by itself)
     *
     * @param filepath: the address of the target file
     * @return
     * @throws Exception
     */
    public static Document file2Document(String filepath) throws Exception {
        File file = new File(filepath);
        Document doc = new Document();
        doc.add(new Field("name", file.getName(), Store.YES, Index.ANALYZED));
        // When using String to analyzer
// doc.add(new Field("size", String.valueOf(file.length()), Store.YES,
// Index.ANALYZED));
// When using NumberTools.longToString() to analyzer, you need to build this when building the index library.
        doc.add(new Field("size", NumberTools.longToString(file.length()), Store.YES, Index.ANALYZED));
        doc.add(new Field("content", getContent(file), Store.YES, Index.ANALYZED));
        return doc;
    }

    /**
     * Get the contents of File
     *
     * @param file
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String getContent(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }

    /**
     * Traversing the document
     *
     * @param docs          : TopDocs object, storing documents
     * @param highlighter   : Highlighter
     * @param analyzer      : lexical parser
     * @param indexSearcher : Index Finder Example: IndexSearcher indexSearcher = new
     *                      IndexSearcher(indexPath);
     * @param fieldName     : FieldName
     * @param maxLength     : summary size
     * @throws Exception
     */
    public static HashMap<String, ModelResult> ergodicDocument(TopDocs docs, Highlighter highlighter, Analyzer analyzer,
                                                               IndexSearcher indexSearcher, String fieldName, int maxLength) throws Exception {

        HashMap<String, ModelResult> modelResultHashMap = new HashMap<String, ModelResult>();
        System.out.println("A total of [" + docs.totalHits + "] data \n");
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            int docSn = scoreDoc.doc;

            Document doc = indexSearcher.doc(docSn);
            // If the highlighter can't find the keyword, it will return a null
            String hc = highlighter.getBestFragment(analyzer, fieldName, doc.get(fieldName));
            // It is necessary to empty sentence
            if (hc == null) {
                String str = doc.get(fieldName);
                // If the article length is less than your maximum limit length
                int endIndex = Math.min(str.length(), maxLength);
                //Here, if the endIndex writes 50, it will report an error.
                hc = str.substring(0, endIndex);
            }
            doc.getField(fieldName).setValue(hc);
            modelResultHashMap = PrintUtils.printDocument(doc, scoreDoc, modelResultHashMap);
            //Let's print out the path of files which have searched term
            System.out.println("--------------------" + doc.get("name") + "-----------------");
            System.out.println("score: " + scoreDoc.score + "-----------------");
        }
        indexSearcher.close();
        return modelResultHashMap;
    }
/*
    public static HashMap<String, ModelResult> ergodicDocumentTest(TopDocs docs, Highlighter highlighter, Analyzer analyzer,
                                                                   IndexSearcher indexSearcher, String fieldName, int maxLength) throws Exception {

        HashMap<String, ModelResult> modelResultHashMap = new HashMap<String, ModelResult>();

        System.out.println("A total of [" + docs.totalHits + "] data \n");
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            int docSn = scoreDoc.doc;
            //Let's print out the path of files which have searched term

            Document doc = indexSearcher.doc(docSn);
            // If the highlighter can't find the keyword, it will return a null
            String hc = highlighter.getBestFragment(analyzer, fieldName, doc.get(fieldName));
            // It is necessary to empty sentence
            if (hc == null) {
                String str = doc.get(fieldName);
                // If the article length is less than your maximum limit length
                int endIndex = Math.min(str.length(), maxLength);
                //Here, if the endIndex writes 50, it will report an error.
                hc = str.substring(0, endIndex);
            }
            doc.getField(fieldName).setValue(hc);
            PrintUtils.printDocumentTest(doc);
            System.out.println("--------------------" + doc.get("name") + "-----------------");
            System.out.println("score: " + scoreDoc.score + "-----------------");
        }
        indexSearcher.close();
        return modelResultHashMap;
    }


    public static void ergodicDocument(TopDocs docs, Analyzer analyzer, IndexSearcher indexSearcher, String fieldName,
                                       int maxLength) throws Exception {
        System.out.println("A total of [" + docs.totalHits + "] data \n");
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            int docSn = scoreDoc.doc;
            Document doc = indexSearcher.doc(docSn);
            PrintUtils.printDocumentTest(doc);
            System.out.println("--------------------" + doc.get("name") + "-----------------");
        }
        indexSearcher.close();
    }*/
}
