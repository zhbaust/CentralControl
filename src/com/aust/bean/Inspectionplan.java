package com.aust.bean;

import java.util.*;
public class Inspectionplan{
     private int id;//编号
     private int deviceid;//设备编号
     private int inspectioninterval;//维护周期，天数
     private String content;//维护内容
     private int operatorid;//操作人员
     
     private Device device;
     private Employee operator;
     
     public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Employee getOperator() {
		return operator;
	}
	public void setOperator(Employee operator) {
		this.operator = operator;
	}
     
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
    	 Device device = new Device();
    	 device.setId(deviceid);
    	 this.device=device;
         this.deviceid = deviceid;
     }
     public int getInspectioninterval(){
         return inspectioninterval;
     }
     public void setInspectioninterval(int inspectioninterval){
         this.inspectioninterval = inspectioninterval;
     }
     public String getContent(){
         return content;
     }
     public void setContent(String content){
         this.content = content;
     }
     public int getOperatorid(){
         return operatorid;
     }
     public void setOperatorid(int operatorid){
    	 Employee operator = new Employee();
    	 operator.setId(operatorid);
    	 this.operator=operator;
         this.operatorid = operatorid;
     }
}