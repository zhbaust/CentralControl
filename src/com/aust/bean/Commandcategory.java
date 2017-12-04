package com.aust.bean;

public class Commandcategory{
     private int id;//编号
     private String name;//名称
     private String remark;//备注
     public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public String getName(){
         return name;
     }
     public void setName(String name){
         this.name = name;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}