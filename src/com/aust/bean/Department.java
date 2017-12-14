package com.aust.bean;

import java.util.*;

public class Department{
     private int id;//部门编号
     private String name;//部门名称
     private int leadid;//部门领导   
	private String tel;//部门电话
     private String remark;//备注
     
     private Employee employee; //人员
     
     public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
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
     public int getLeadid(){
         return leadid;
     }
     public void setLeadid(int leadid){
    	 Employee employee = new Employee();
    	 employee.setId(leadid);
    	 this.employee=employee;
         this.leadid = leadid;
     }
     
   
     public String getTel(){
         return tel;
     }
     public void setTel(String tel){
         this.tel = tel;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}