/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

public class Feedback {

    private String rating1;
    private String rating2;
    private String rating3;
    private String rating4;
    private String rating5;
    private boolean cb1, cb2, cb3, cb4, sw1;
    private String answer1;


    public Feedback(){
    }

    public Feedback(String rating1, String rating2, String rating3, String rating4, String rating5,
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
    }

    public String getRating1() {
        return rating1;
    }


    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }

    public String getRating3() {
        return rating3;
    }

    public void setRating3(String rating3) {
        this.rating3 = rating3;
    }

    public String getRating4() {
        return rating4;
    }

    public void setRating4(String rating4) {
        this.rating4 = rating4;
    }

    public String getRating5() {
        return rating5;
    }

    public void setRating5(String rating5) {
        this.rating5 = rating5;
    }

    public boolean isCb1() {
        return cb1;
    }

    public void setCb1(boolean cb1) {
        this.cb1 = cb1;
    }

    public boolean isCb2() {
        return cb2;
    }

    public void setCb2(boolean cb2) {
        this.cb2 = cb2;
    }

    public boolean isCb3() {
        return cb3;
    }

    public void setCb3(boolean cb3) {
        this.cb3 = cb3;
    }

    public boolean isCb4() {
        return cb4;
    }

    public void setCb4(boolean cb4) {
        this.cb4 = cb4;
    }

    public boolean isSw1() {
        return sw1;
    }

    public void setSw1(boolean sw1) {
        this.sw1 = sw1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }
}