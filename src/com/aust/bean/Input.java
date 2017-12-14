package com.aust.bean;

import java.util.*;
public class Input{
     private int id;//编号
     private int deviceid;//设备编号
     private String inputdate;//入库日期
     private String productiondate;//生产日期
     private int num;//采购数量
     private int operatorid;//采购人
     private int validity;//有效期，月数
     private int supplierid;//采购厂家，供应商
     private String remark;//备注
     private Device device;
     private Employee operator;
     private Supplier supplier;
     
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
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
     public String getInputdate(){
         return inputdate;
     }
     public void setInputdate(String inputdate){
         this.inputdate = inputdate;
     }
     public String getProductiondate(){
         return productiondate;
     }
     public void setProductiondate(String productiondate){
         this.productiondate = productiondate;
     }
     public int getNum(){
         return num;
     }
     public void setNum(int num){
         this.num = num;
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
     public int getValidity(){
         return validity;
     }
     public void setValidity(int validity){
         this.validity = validity;
     }
     public int getSupplierid(){
         return supplierid;
     }
     public void setSupplierid(int supplierid){
    	 Supplier supplier = new Supplier();
    	 supplier.setId(supplierid);
    	 this.supplier=supplier;
         this.supplierid = supplierid;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}