package com.howtodoinjava.demo.lucene.highlight;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.howtodoinjava.demo.lucene.Utils.IndexFiles;
import com.howtodoinjava.demo.lucene.analysis.VietnameseAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class RunDemo {
    //This contains the lucene indexed documents
    private static final String INDEX_DIR = "indexedFiles";

    public static void main(String[] args) throws Exception {

        IndexFiles.createIndexDocSearcher();

        //Get directory reference
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));

        //Index reader - an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);

        //Create lucene searcher. It search over a single IndexReader.
        IndexSearcher searcher = new IndexSearcher(reader);

        //analyzer with the default stop words
        Analyzer analyzer = new VietnameseAnalyzer();

        //Query parser to be used for creating TermQuery
        QueryParser qp = new QueryParser("contents", analyzer);

        //Create the query
        Query query = qp.parse("Nước da nhợt trắng ra ở trán, nhưng lại đỏ phừng từng mảng trên hai bầu má còn hơi phinh phính.");

        //Search the lucene documents
        TopDocs hits = searcher.search(query, 10);

//        * Highlighter Code Start ***

        //Uses HTML &lt;B&gt;&lt;/B&gt; tag to highlight the searched terms
        Formatter formatter = new SimpleHTMLFormatter();

        //It scores text fragments by the number of unique query terms found
        //Basically the matching score in layman terms
        QueryScorer scorer = new QueryScorer(query);

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<strong>", "</strong>");
        SimpleHTMLEncoder simpleHTMLEncoder = new SimpleHTMLEncoder();
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, simpleHTMLEncoder, new QueryScorer(query));

        //Total found documents
        System.out.println("Total Results :: " + hits.totalHits);

        //Let's print out the path of files which have searched term
        for (ScoreDoc sd : hits.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println("Path : " + d.get("path") + ", Score : " + sd.score);
        }

        //Iterate over found results
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            System.out.println("==========ROW : " + i + " =============");
            int docid = hits.scoreDocs[i].doc;
            Document doc = searcher.doc(docid);
            //Printing - to which document result belongs
            System.out.println("docid " + " : " + docid);
            System.out.println("Path : " + doc.get("path"));
            System.out.println("Score : " + hits.scoreDocs[i].score);
            //Get stored text from found document
            String text = doc.get("contents");
            //Create token stream
            TokenStream tokenStream = TokenSources.getAnyTokenStream(reader, docid, "contents", analyzer);
            System.out.println("Sentence: " + ": " + doc.getField("contents").stringValue());
            //Get highlighted text fragments
            System.out.println("Sentence highlighter : ");
            TextFragment[] textFragments = highlighter.getBestTextFragments(tokenStream, text, false, 10);
            for (TextFragment textFragment : textFragments) {
                if (textFragment != null && textFragment.getScore() > 0) {
                    System.out.println(textFragment.toString());
                    System.out.println("percentSentence : " +Utils.percentSentence("<strong>",textFragment.toString()));
                }
            }
            System.out.println("=======================");
        }
        dir.close();
    }


}