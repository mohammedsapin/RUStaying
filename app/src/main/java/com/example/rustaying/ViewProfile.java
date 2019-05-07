/************************
 Authors:
 Zain Sayed
 Keya Patel
 *************************/

package com.example.rustaying;

public class ViewProfile {
    private String first_name;
    private String last_name;
    private String email;

    public ViewProfile(){

    }
    public String getEmail(){
        return email;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setFirst_name(String first_name){
        this.first_name=first_name;
    }
    public void setLast_name(String last_name){
        this.last_name=last_name;
    }

}
