package com.oocl.directory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import com.oocl.utils.FileDocumentUtil;

public class DirectoryTest {

	//索引库地址
	String indexPath = "D:\\Program Files\\good\\indexPath";
	//文件地址
	String filePath_octopus = "D:\\Program Files\\good\\lucene\\filePath\\Leo1.txt";
	String filePath_Sying = "D:\\Program Files\\good\\lucene\\filePath\\Leo2.txt";
	//分词器，普通分词器
	Analyzer analyzer = new StandardAnalyzer();
			
	@Test
	public void testDirectory() throws Exception {
		//索引库的目录分为两种：FSDirectory(系统中的)， RAMDirectory(RAM虚拟内存中的)
		FSDirectory fsDirectory = FSDirectory.getDirectory(indexPath);
		
		//运行时候读取
		RAMDirectory ramDirectory = new RAMDirectory(fsDirectory);
		IndexWriter ramIndexWriter = new IndexWriter(ramDirectory, analyzer, true, MaxFieldLength.LIMITED);
		Document document1 = FileDocumentUtil.file2Document(filePath_octopus);
		Document document2 = FileDocumentUtil.file2Document(filePath_Sying);
		ramIndexWriter.addDocument(document1);
		ramIndexWriter.addDocument(document2);
		ramIndexWriter.close();
		
		//结束的时候保存
		IndexWriter fsIndexWriter = new IndexWriter(fsDirectory, analyzer, true, MaxFieldLength.LIMITED);
		fsIndexWriter.addIndexesNoOptimize(new Directory[]{ramDirectory});
		
		//例如有很多个_a.cfs,_b.cfs,c_cfs文件。使用了optimize之后就会把多个小文件合并成一个大文件。_d.cfs.
		//fsIndexWriter.optimize();
		
		//清空缓存：
		//indexWriter好比一个厕所，每个人用完就冲一下厕所。虽然close测试之前会flush一下。但是可以flush一下继续用。没必要总是拆
		//fsIndexWriter.flush();过期了用commit来代替
		//fsIndexWriter.commit();
		
		fsIndexWriter.close();
	}
}
