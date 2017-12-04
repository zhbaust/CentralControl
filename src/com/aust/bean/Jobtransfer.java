package com.aust.bean;

import java.util.*;
public class Jobtransfer{
     private int id;//调动编号
     private int employeeid;//员工编号
     private int olddepartmentid;//原部门编号
     private int departmentid;//部门编号
     private String transferdate;//调动日期
     private String transferreson;//调动原因
     private int operatorid;//负责人
     private String remark;//备注
     private Employee employee;
     private Department olddepartment;
     private Department department;
     private Employee operator;
     
     public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Department getOlddepartment() {
		return olddepartment;
	}
	public void setOlddepartment(Department olddepartment) {
		this.olddepartment = olddepartment;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
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
     public int getEmployeeid(){
         return employeeid;
     }
     public void setEmployeeid(int employeeid){
    	 Employee employee = new Employee();
    	 employee.setId(employeeid);
    	 this.employee= employee;
         this.employeeid = employeeid;
     }
     public int getOlddepartmentid(){
         return olddepartmentid;
     }
     public void setOlddepartmentid(int olddepartmentid){
    	 Department department = new Department();
    	 department.setId(olddepartmentid);
    	 this.olddepartment=department;
         this.olddepartmentid = olddepartmentid;
     }
     public int getDepartmentid(){
         return departmentid;
     }
     public void setDepartmentid(int departmentid){
    	 Department department = new Department();
    	 department.setId(departmentid);
    	 this.department=department;
         this.departmentid = departmentid;
     }
     public String getTransferdate(){
         return transferdate;
     }
     public void setTransferdate(String transferdate){
         this.transferdate = transferdate;
     }
     public String getTransferreson(){
         return transferreson;
     }
     public void setTransferreson(String transferreson){
         this.transferreson = transferreson;
     }
     public int getOperatorid(){
         return operatorid;
     }
     public void setOperatorid(int operatorid){
    	 Employee employee = new Employee();
    	 employee.setId(employeeid);
    	 this.operator= employee;
         this.operatorid = operatorid;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}