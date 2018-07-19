package com.oocl.utils.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DocumentFile implements Document {

    @Override
    public String readDoc(String filePath) {
        return "Text";
    }

    @Override
    public String readPdf(String filePath) {
        return "Text";
    }

    @Override
    public Boolean writeFileTxt(String filePath, String content) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            bw = new BufferedWriter(fw);
            bw.write(content);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();
                return false;
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }
        return true;
    }

    @Override
    public String readFile(String filePath) {
        String[] arr = {"demo"};
        if (filePath.indexOf(".doc") != -1) {

        } else {
            if (filePath.indexOf(".pdf") != -1) {

            }
        }
        //use tmp to read file text
        File file = new File(filePath);
        try {
            return FileDocumentUtil.getContent(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
