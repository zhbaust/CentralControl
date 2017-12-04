package com.aust.bean;

import java.util.*;
public class Attendance{
     private int id;//考勤编号
     private String cardnumber;//卡号
     private String cardtime;//
     private int kind;//上班：1 下班：0
     private String ramark;//备注
     public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public String getCardnumber(){
         return cardnumber;
     }
     public void setCardnumber(String cardnumber){
         this.cardnumber = cardnumber;
     }
     public String getCardtime(){
         return cardtime;
     }
     public void setCardtime(String cardtime){
         this.cardtime = cardtime;
     }
     public int getKind(){
         return kind;
     }
     public void setKind(int kind){
         this.kind = kind;
     }
     public String getRamark(){
         return ramark;
     }
     public void setRamark(String ramark){
         this.ramark = ramark;
     }
}