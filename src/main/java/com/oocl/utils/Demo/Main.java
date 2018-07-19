package com.oocl.utils.Demo;

import com.oocl.utils.ModelResult;
import com.oocl.utils.analyzer.SearchContentFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SearchContentFile searchContentFile = new SearchContentFile();
        String filePath_Leo1 = "D:\\Program Files\\good\\lucene\\filePath\\Leo1.txt";
        String filePath_Leo2 = "D:\\Program Files\\good\\lucene\\filePath\\Leo2.txt";
        String filePath_Leo3 = "D:\\Program Files\\good\\lucene\\filePath\\Leo3.txt";

        String[] arr = {filePath_Leo1, filePath_Leo2};
        List<String> listPath = Arrays.asList(arr);
        try {
            HashMap<String, ModelResult> modelResultHashMap = new HashMap<>();
            modelResultHashMap = searchContentFile.searchContentOnAllFile(listPath, filePath_Leo3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
