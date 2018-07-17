package com.oocl.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;

public class PrintUtils {
	/**
	 * Print doc
	 * @param doc
	 */
	public static void printDocument(Document doc) {
		// 1. The first way to get it
		// Field file = doc.getField("name");
		// String name = file.stringValue();
		// 2. The second way
		System.out.println("name   : " + doc.get("name"));
		System.out.println("size   : " + NumberTools.stringToLong(doc.get("size")));
		System.out.println("content   : " + doc.get("content"));
		// can write file or add list
	}
}
