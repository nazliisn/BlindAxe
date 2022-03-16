package com.example.blindaxe;

public class Questions {
    String question;
    String A;
    String B;
    String C;

    long correctAnswers;


    public Questions(String question, String a, String b, String c, long correctAnswers) {
        this.question = question;
        A = a;
        B = b;
        C = c;
        this.correctAnswers = correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public long getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(long correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
