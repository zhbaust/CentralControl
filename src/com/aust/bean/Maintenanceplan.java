package com.aust.bean;

import java.util.*;
public class Maintenanceplan{
     private int id;//编号
     private int deviceid;//设备编号
     private int maintenanceinterval;//维护周期，天数
     private int operatorid;//维护人员
     private String content;//维护内容
     private String remindtime;//提醒时间
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
     public int getMaintenanceinterval(){
         return maintenanceinterval;
     }
     public void setMaintenanceinterval(int maintenanceinterval){
         this.maintenanceinterval = maintenanceinterval;
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
     public String getContent(){
         return content;
     }
     public void setContent(String content){
         this.content = content;
     }
     public String getRemindtime(){
         return remindtime;
     }
     public void setRemindtime(String remindtime){
         this.remindtime = remindtime;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}