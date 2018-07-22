package com.howtodoinjava.demo.lucene.Utils;

import com.howtodoinjava.demo.lucene.highlight.Utils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class IndexFiles {
    private static final String INDEX_DIR = "indexedFiles";

    public static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        //It is an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
        //Index searcher
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public static IndexWriter createIndexDocSearcher() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        int i = 1;
        while (i <= 3) {
            Document doc = new Document();
            TextField textField = new TextField("contents", "", Field.Store.YES);
            TextField textFieldPath = new TextField("path", "", Field.Store.YES);
            String[] contents = Utils.convertStringFileToList("F:\\Code-Github\\LuceneExamples\\inputFiles\\data" + i + ".txt");
            for (String content : contents) {
                textField.setStringValue(content);
                textFieldPath.setStringValue("data" + i + ".txt");
                doc.removeField("contents");
                doc.removeField("path");
                doc.add(textField);
                doc.add(textFieldPath);
                indexWriter.addDocument(doc);
            }
            i++;
        }
        indexWriter.commit();
        System.out.println("Susscess");
        return indexWriter;
    }

}
