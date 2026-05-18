package com.example.disasterguide;

public class ListViewItemComment {
    private String comment;
    private String time;
    private String memID;
    private String postNum;

    public void setTime(String time){
        this.time = time;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setMemID(String memID){this.memID = memID;}
    public void setPostNum(String postNum){this.postNum = postNum;}

    public String getTime(){
        return this.time;
    }
    public String getComment(){
        return this.comment;
    }
    public String getMemID(){return this.memID;}
    public String getPostNum(){return this.postNum;}
}
