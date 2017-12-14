package com.aust.bean;

import java.util.*;
public class Inspection{
     private int id;//编号
     private int deviceid;//设备编号
     private String routetime;//巡检时间
     private int operatorid;//巡检人员
     private String dispose;//处理
     private String remark;//备注
     
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
    	 Employee operator = new Employee();
    	 operator.setId(operatorid);
    	 this.operator=operator;
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