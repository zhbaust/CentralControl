package com.aust.bean;

import java.util.*;
public class Output{
     private int id;//编号
     private int deviceid;//设备编号
     private String outputdate;//出库日期
     private int num;//数量
     private int receiveid;//领取人员
     private int sendid;//发放人员
     private String remark;//备注
     
     private Device device;
     private Employee receiver;
     private Employee sender;
     
     public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Employee getReceiver() {
		return receiver;
	}
	public void setReceiver(Employee receiver) {
		this.receiver = receiver;
	}
	public Employee getSender() {
		return sender;
	}
	public void setSender(Employee sender) {
		this.sender = sender;
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
     public String getOutputdate(){
         return outputdate;
     }
     public void setOutputdate(String outputdate){
         this.outputdate = outputdate;
     }
     public int getNum(){
         return num;
     }
     public void setNum(int num){
         this.num = num;
     }
     public int getReceiveid(){
         return receiveid;
     }
     public void setReceiveid(int receiveid){
    	 Employee receiver = new Employee();
    	 receiver.setId(receiveid);
    	 this.receiver=receiver;
         this.receiveid = receiveid;
     }
     public int getSendid(){
         return sendid;
     }
     public void setSendid(int sendid){
    	 Employee sender = new Employee();
    	 sender.setId(sendid);
    	 this.sender= sender;
         this.sendid = sendid;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}