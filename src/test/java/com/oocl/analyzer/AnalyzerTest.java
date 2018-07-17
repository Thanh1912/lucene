package com.oocl.analyzer;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;

public class AnalyzerTest {
	
	String analyzeString = "Leo's School is a beautiful SCHOOL.TXT";
	String chString = "authorDescription";
	
	@Test
	public void testAnalyzer() throws Exception{
		//英文分词器
		Analyzer analyzer1 = new StandardAnalyzer();//Default tokenizer
		analyze(analyzer1, analyzeString);
		//中文分词器
		Analyzer analyzer2 = new CJKAnalyzer();//Chinese dichotomy
		analyze(analyzer2, chString);
	}

	//Pass in the String and the tokenizer that need to be analyzed. Printout result
	public void analyze(Analyzer analyzer, String str) throws Exception{
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(str));
		System.out.println("The word breaker used is ----->" + analyzer.getClass().getName());
		for(Token token = new Token(); (token = tokenStream.next(token))!=null;){
			System.out.println(token);
		}
		System.out.println();
	}

}
