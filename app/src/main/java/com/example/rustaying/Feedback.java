package com.example.rustaying;

import android.widget.EditText;
import android.widget.RatingBar;

public class Feedback {
    private float rating1, rating2, rating3, rating4, rating5;
    private boolean cb1, cb2, cb3, cb4, sw1;
    private String answer1;


    public Feedback(){

    }

    public Feedback(float rating1, float rating2, float rating3, float rating4, float rating5,
                    String answer1, boolean cb1, boolean cb2, boolean cb3, boolean cb4, boolean sw1){
        this.answer1 = answer1;
        this.rating1 = rating1;
        this.rating2 = rating2;
        this.rating3 = rating3;
        this.rating4 = rating4;
        this.rating5 = rating5;
        this.cb1 = cb1;
        this.cb2 = cb2;
        this.cb3 = cb3;
        this.cb4 = cb4;
        this.sw1= sw1;
        //this.rButton1 = radio1;
        //this.rButton2 = radio2;

    }


    public float getRating1() {
        return rating1;
    }

    public void setRating1(float rating1) {
        this.rating1 = rating1;
    }


    public float getRating2() {
        return rating2;
    }

    public void setRating2(float rating1) {
        this.rating2 = rating2;
    }


    public float getRating3() { return rating3;}

    public void setRating3(float rating1) {
        this.rating3 = rating3;
    }


    public float getRating4() {
        return rating4;
    }

    public void setRating4(float rating1) {
        this.rating4 = rating4;
    }


    public float getRating5() {
        return rating5;
    }

    public void setRating5(float rating1) {
        this.rating5 = rating5;
    }


    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }


}