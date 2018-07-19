package com.oocl.utils.document;

import com.oocl.utils.ModelResult;
import com.oocl.utils.document.DocumentFile;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.search.ScoreDoc;

import java.util.HashMap;

public class PrintUtils {
    public static HashMap<String, ModelResult> printDocument(Document doc, ScoreDoc scoreDoc, HashMap<String, ModelResult> map) {
        ModelResult modelResult = new ModelResult();
        modelResult.setResultHtml(doc.get("content"));
        modelResult.setScore(scoreDoc.score + "");
        map.put(doc.get("name"), modelResult);
        DocumentFile documentFile=new DocumentFile();
        System.out.println("name   : " + doc.get("name"));
        System.out.println("size   : " + NumberTools.stringToLong(doc.get("size")));
        System.out.println("content   : " + doc.get("content"));
        return map;
    }

}
// 1. The first way to get it
// Field file = doc.getField("name");
// String name = file.stringValue();
