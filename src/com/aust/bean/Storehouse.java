package com.aust.bean;

import java.util.*;
public class Storehouse{
     private int id;//编号
     private String name;//名称
     private String address;//地址
     private String type;//规格
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
     public String getAddress(){
         return address;
     }
     public void setAddress(String address){
         this.address = address;
     }
     public String getType(){
         return type;
     }
     public void setType(String type){
         this.type = type;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}