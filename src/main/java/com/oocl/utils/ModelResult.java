package com.oocl.utils;

public class ModelResult {
    private String nameFile;
    private String score;
    private String resultHtml;

    public ModelResult() {
    }

    public ModelResult(String nameFile, String score, String resultHtml) {
        this.nameFile = nameFile;
        this.score = score;
        this.resultHtml = resultHtml;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResultHtml() {
        return resultHtml;
    }

    public void setResultHtml(String resultHtml) {
        this.resultHtml = resultHtml;
    }
}
