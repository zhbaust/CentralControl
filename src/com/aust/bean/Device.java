package com.aust.bean;

import java.math.BigDecimal;
import java.util.*;
public class Device{
     private int id;//编号
     private String deviceid;//设备编号
     private String name;//设备名称
     private String modal;//型号
     private int num;//数量
     private float price;//价格
     private String remark;//备注
     public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public String getDeviceid(){
         return deviceid;
     }
     public void setDeviceid(String deviceid){
         this.deviceid = deviceid;
     }
     public String getName(){
         return name;
     }
     public void setName(String name){
         this.name = name;
     }
     public String getModal(){
         return modal;
     }
     public void setModal(String modal){
         this.modal = modal;
     }
     public int getNum(){
         return num;
     }
     public void setNum(int num){
         this.num = num;
     }
     public float getPrice(){
         return price;
     }
     public void setPrice(float price){
         this.price = price;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}