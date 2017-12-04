package com.aust.bean;

import java.util.*;
public class Inspection{
     private int id;//编号
     private int deviceid;//设备编号
     private String routetime;//巡检时间
     private int operatorid;//巡检人员
     private String dispose;//处理
     private String remark;//备注
     public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public int getDeviceid(){
         return deviceid;
     }
     public void setDeviceid(int deviceid){
         this.deviceid = deviceid;
     }
     public String getRoutetime(){
         return routetime;
     }
     public void setRoutetime(String routetime){
         this.routetime = routetime;
     }
     public int getOperatorid(){
         return operatorid;
     }
     public void setOperatorid(int operatorid){
         this.operatorid = operatorid;
     }
     public String getDispose(){
         return dispose;
     }
     public void setDispose(String dispose){
         this.dispose = dispose;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}