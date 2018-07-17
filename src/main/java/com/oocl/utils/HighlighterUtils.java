package com.oocl.utils;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class HighlighterUtils {

    /**
     * Construct highlighter
     *
     * @param query       : Search for words converted to Query
     * @param perTag      : highlighted prefix <font color = 'red'>
     * @param postTag     : Highlight suffix </font>
     * @param FramentSize : the size of the highlighted range
     * @return
     */
    public static Highlighter getHighlighter(Query query, String perTag, String postTag, int FramentSize) {
    // Prepare the highlighter
        Formatter formatter = new SimpleHTMLFormatter(perTag, postTag);
        Scorer fragmentScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
       //Define the range of highlighted words
        Fragmenter fragmenter = new SimpleFragmenter(FramentSize);
        highlighter.setTextFragmenter(fragmenter);
        return highlighter;
    }
}
