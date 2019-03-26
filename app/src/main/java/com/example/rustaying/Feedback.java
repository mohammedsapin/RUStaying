package com.example.rustaying;

public class Feedback {
    private float rating;
    private String answer1, answer2;


    public Feedback(){

    }

    public Feedback(float rating, String answer1, String answer2){
        this.rating = rating;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
}