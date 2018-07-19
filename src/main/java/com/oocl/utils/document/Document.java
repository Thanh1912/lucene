package com.oocl.utils.document;

import java.util.List;

public interface Document {
    public String readDoc(String filePath);

    public String readPdf(String filePath);

    public Boolean writeFileTxt(String filePath, String content);

    public String readFile(String filePath);
}
